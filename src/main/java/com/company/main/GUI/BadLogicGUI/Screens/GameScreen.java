package com.company.main.GUI.BadLogicGUI.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.company.main.GUI.BadLogicGUI.ScreenManager;
import com.company.main.GUI.BadLogicGUI.Stage.BoardStage;
import com.company.main.GUI.MinesweeperGUI;
import com.company.main.Game.Minesweeper;

public class GameScreen implements Screen, MinesweeperGUI {
    ScreenManager screenManager;
    Minesweeper minesMatrix;
    BoardStage boardStage;

    public GameScreen(ScreenManager screenManager, int width, int height, int numMines) {
        minesMatrix = new Minesweeper(width,height,numMines);
        this.screenManager = screenManager;
    }

    @Override
    public void show() {
        boardStage = new BoardStage(minesMatrix);
        Gdx.input.setInputProcessor(boardStage);
    }

    @Override
    public void render(float v) {
        Gdx.gl.glClearColor(0,0,0,0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        boardStage.moveBoard(Gdx.input.getX(),Gdx.input.getY());

        boardStage.boardCam.update();
        boardStage.mapRenderer.setView(boardStage.boardCam);
        boardStage.mapRenderer.render();
        screenManager.batch.setProjectionMatrix(boardStage.boardCam.combined);


    }

    @Override
    public void resize(int i, int i1) {
        boardStage.boardCam.setToOrtho(false,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }

    @Override
    public void run() {

    }

    @Override
    public void selectCell() {

    }

    @Override
    public void update() {

    }
}
