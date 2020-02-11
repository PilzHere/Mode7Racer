package mysko.pilzhere.mode7racer.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.graphics.Color;

import mysko.pilzhere.mode7racer.Mode7Racer;
import mysko.pilzhere.mode7racer.constants.GameConstants;

public class DesktopLauncher {
	public static void main(String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

		config.title = GameConstants.GAME_TITLE;
		config.width = GameConstants.SCREEN_WIDTH_STRETCHED * 4;
		config.height = GameConstants.SCREEN_HEIGHT * 4;

		config.allowSoftwareMode = true;
		config.foregroundFPS = 60; // 60
		config.backgroundFPS = 60;
		config.fullscreen = false;
		config.resizable = true;
		config.initialBackgroundColor = Color.WHITE;
		config.samples = 0;
		config.vSyncEnabled = false;

//		config.addIcon(path, fileType);

		new LwjglApplication(new Mode7Racer(), config);
	}
}
