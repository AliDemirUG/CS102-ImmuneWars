package com.immunewars.game;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
public class DesktopLauncher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setWindowIcon("logo.png"); 
		config.setForegroundFPS(GameConfig.targetFPS);
		config.setWindowedMode(GameConfig.resolutionX, GameConfig.resolutionY);
		config.setTitle("Immune Wars");
		new Lwjgl3Application(new ImmuneWars(), config);
	}
}
