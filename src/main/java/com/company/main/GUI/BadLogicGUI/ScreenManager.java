package com.company.main.GUI.BadLogicGUI;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.company.main.GUI.BadLogicGUI.Screens.GameScreen;
import com.company.main.GUI.BadLogicGUI.Screens.MenuScreen;

public class ScreenManager extends Game {
    public SpriteBatch batch;

    @Override
    public void create() {
        batch = new SpriteBatch();
        setScreen(new GameScreen(this, 10, 10 ,35));
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {
        Gdx.app.exit();
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width,height);
    }

    @Override
    public void pause() {
        //left empty
    }

    @Override
    public void resume() {
        //left empty
    }
}
