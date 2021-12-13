package com.company.main.GUI.BadLogicGUI;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.company.main.GUI.BadLogicGUI.Screens.MenuScreen;

public class ScreenManager extends Game {
    public SpriteBatch batch;
    @Override
    public void create() {
        batch = new SpriteBatch();
        setScreen(new MenuScreen(this));
    }
}
