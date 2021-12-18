package com.company.main;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.company.main.GUI.BadLogicGUI.ScreenManager;
import com.company.main.GUI.ConsoleGUI;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;

public class Main {

    public static void main(String[] args) {
        Lwjgl3ApplicationConfiguration cfg = new Lwjgl3ApplicationConfiguration();
        cfg.setTitle("Minesweeper");
        cfg.setWindowedMode(1000,500);

        new Lwjgl3Application(new ScreenManager(), cfg);
    }
}
