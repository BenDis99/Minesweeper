package com.company.main.GUI.BadLogicGUI.Stage;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.company.main.Game.Coords;
import com.company.main.Game.Minesweeper;

import java.awt.*;
import java.util.EventListener;
import java.util.HashMap;
import java.util.Vector;

public class BoardStage extends InputAdapter {
    public OrthographicCamera boardCam;
    public OrthogonalTiledMapRenderer mapRenderer;

    HashMap<String,TextureRegion> cellTextures;

    Minesweeper gameBoard;

    TiledMap boardMap;
    TiledMapTileLayer topLayer;   // Contains the covers over unknown cells
    TiledMapTileLayer itemLayer;  // Contains numbers and mines
    TiledMapTileLayer boardLayer; // Contains the background of cells

    public BoardStage(Minesweeper gameBoard){
        this.gameBoard = gameBoard;

        // Set up cellTextures
        TextureRegion[][] cellTextureRegions = TextureRegion.split(new Texture("gameObjects/gameObjectsMap2.png"), 32, 32);
        cellTextures = new HashMap<>();
        cellTextures.put("0",new TextureRegion(cellTextureRegions[3][3]));
        cellTextures.put("1",new TextureRegion(cellTextureRegions[0][0]));
        cellTextures.put("2",new TextureRegion(cellTextureRegions[0][1]));
        cellTextures.put("3",new TextureRegion(cellTextureRegions[0][2]));
        cellTextures.put("4",new TextureRegion(cellTextureRegions[0][3]));
        cellTextures.put("5",new TextureRegion(cellTextureRegions[1][0]));
        cellTextures.put("6",new TextureRegion(cellTextureRegions[1][1]));
        cellTextures.put("7",new TextureRegion(cellTextureRegions[1][2]));
        cellTextures.put("8",new TextureRegion(cellTextureRegions[1][3]));
        cellTextures.put("L",new TextureRegion(cellTextureRegions[2][0])); // Locked cell
        cellTextures.put("O",new TextureRegion(cellTextureRegions[2][1])); // Open cell
        cellTextures.put("M",new TextureRegion(cellTextureRegions[2][2])); // Mine
        cellTextures.put("F",new TextureRegion(cellTextureRegions[2][3])); // Flag
        cellTextures.put("R",new TextureRegion(cellTextureRegions[3][0])); // RedBackground
        cellTextures.put("E",new TextureRegion(cellTextureRegions[3][3])); // Empty (transparent

        // setting up cells by gameMatrix
        topLayer = new TiledMapTileLayer(gameBoard.getBoardWidth(), gameBoard.getBoardHeight(), 32,32);
        itemLayer = new TiledMapTileLayer(gameBoard.getBoardWidth(), gameBoard.getBoardHeight(), 32,32);
        boardLayer = new TiledMapTileLayer(gameBoard.getBoardWidth(), gameBoard.getBoardHeight(), 32,32);

        for(int y = 0; y < gameBoard.getBoardHeight(); y++) {
            for(int x = 0; x < gameBoard.getBoardWidth(); x++) {
                topLayer.setCell(x,y,new TiledMapTileLayer.Cell());
                itemLayer.setCell(x,y,new TiledMapTileLayer.Cell());
                boardLayer.setCell(x,y,new TiledMapTileLayer.Cell());
                topLayer.getCell(x,y).setTile(new StaticTiledMapTile(cellTextures.get("L")));
                boardLayer.getCell(x,y).setTile(new StaticTiledMapTile(cellTextures.get("O")));
                TextureRegion item = cellTextures.get(gameBoard.getCell(new Coords(x,y)));
                itemLayer.getCell(x,y).setTile(new StaticTiledMapTile(item));
            }
        }

        boardMap = new TiledMap();
        boardMap.getLayers().add(boardLayer);
        boardMap.getLayers().add(itemLayer);
        boardMap.getLayers().add(topLayer);

        boardCam = new OrthographicCamera();
        boardCam.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        boardCam.translate((float) boardLayer.getWidth()/2,(float) boardLayer.getHeight()/2);
        boardCam.zoom = (float) 0.01f;
        boardCam.update();

        mapRenderer = new OrthogonalTiledMapRenderer(boardMap,(float) 1/32);
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        Coords boardCords = translateScreenCoordsToBoardCords(screenX, screenY);
        if(!gameBoard.gameOver() && boardCords != null){
            gameBoard.select(boardCords);
            updateTileMap();
        }

        return super.touchDown(screenX, screenY, pointer, button);
    }

    private void updateTileMap() {
        for(int y = 0; y < gameBoard.getBoardHeight(); y++){
            for(int x = 0; x < gameBoard.getBoardWidth(); x++){
                if(gameBoard.isCellVisited(new Coords(x,y)))
                    topLayer.getCell(x,y).getTile().setTextureRegion(cellTextures.get("E"));
                else
                    topLayer.getCell(x,y).getTile().setTextureRegion(cellTextures.get("L"));
            }
        }
        if(gameBoard.isExploded()){
            Coords last = gameBoard.getLastSelected();
            boardLayer.getCell(last.getX(), last.getY()).getTile().setTextureRegion(cellTextures.get("R"));
        }
    }

    @Override
    public boolean scrolled(int amount) {
        float zoomScale = (float) amount/150;
        if(boardCam.zoom+zoomScale>0) boardCam.zoom += zoomScale;
        return super.scrolled(amount);
    }

    public void moveBoard(int screenX, int screenY) {
        if(Gdx.input.isButtonPressed(1)) { //Right mouse button.
            float width = Gdx.graphics.getWidth();
            float height = Gdx.graphics.getHeight();

            float boardWidth = boardLayer.getWidth();
            float boardHeight = boardLayer.getHeight();

            int fraction = 2;
            float amount = (float) 1 / 32; //amount*1 till 10 good boundaries

            int topSpeed = 10;
            float paddingY = height / fraction ;
            float paddingX = width / fraction ;
            if (screenX < paddingX) {
                if (boardCam.position.x > 0) {
                    float speed = topSpeed * amount * (paddingX - screenX) / paddingX;
                    boardCam.translate(-speed, 0);
                }
            } else if (screenX > width - paddingX) {
                if (boardCam.position.x < boardWidth) {
                    float speed = topSpeed * amount * (paddingX - (width - screenX)) / paddingX;
                    boardCam.translate(speed, 0);
                }
            }
            if (screenY < paddingY) {
                if (boardCam.position.y < boardHeight) {
                    float speed = topSpeed * amount * (paddingY - screenY) / paddingY;
                    boardCam.translate(0, speed);
                }
            } else if (screenY > height - paddingY) {
                if (boardCam.position.y > 0) {
                    float speed = topSpeed * amount * (paddingY - (height - screenY)) / paddingY;
                    boardCam.translate(0, -speed);
                }
            }
        }
    }

    private Coords translateScreenCoordsToBoardCords(int x, int y){
        int boardX = (int) ((boardCam.viewportWidth*boardCam.zoom*(x-Gdx.graphics.getWidth()/2)/Gdx.graphics.getWidth())+boardCam.position.x);
        int boardY = (int) ((boardCam.viewportHeight*boardCam.zoom*(-y+Gdx.graphics.getHeight()/2)/Gdx.graphics.getHeight())+boardCam.position.y);
        if(0 <= boardX && boardX < gameBoard.getBoardWidth() &&  0 <= boardY && boardY < gameBoard.getBoardHeight())
            return new Coords(boardX,boardY);
        return null;
    }
}
