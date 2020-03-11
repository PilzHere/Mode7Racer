package mysko.pilzhere.mode7racer.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.FitViewport;

import mysko.pilzhere.mode7racer.Mode7Racer;
import mysko.pilzhere.mode7racer.constants.GameConstants;
import mysko.pilzhere.mode7racer.game.GameMode;
import mysko.pilzhere.mode7racer.game.GameSettings;
import mysko.pilzhere.mode7racer.ui.GameConfigUI;
import mysko.pilzhere.mode7racer.ui.MenuUI;
import mysko.pilzhere.mode7racer.ui.events.DismissEvent;
import mysko.pilzhere.mode7racer.ui.events.GameStartEvent;
import mysko.pilzhere.mode7racer.ui.events.GrandPrixStartEvent;
import mysko.pilzhere.mode7racer.ui.events.PracticeStartEvent;
import mysko.pilzhere.mode7racer.ui.events.ShowRecordsEvent;
import mysko.pilzhere.mode7racer.ui.events.ShowSettingsEvent;
import mysko.pilzhere.mode7racer.ui.settings.SettingsUI;

public class MenuScreen extends ScreenAdapter
{
	private GameSettings gameSettings; // TODO should be in game!
	private Stage stage;
	private Mode7Racer game;

	public MenuScreen(Mode7Racer game) {
		this.game = game;
		this.gameSettings = new GameSettings();
		// XXX sclae 1 is fine
		int scale = 1;
		stage = new Stage(new FitViewport(GameConstants.VIEWPORT_WIDTH_STRETCHED * scale, GameConstants.VIEWPORT_HEIGHT * scale));
		showMainMenu();
	}
	
	private void showMainMenu(){
		stage.clear();
		MenuUI ui = new MenuUI(game.getSkin());
		ui.setFillParent(true);
		stage.addActor(ui);
		ui.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				if(event instanceof GrandPrixStartEvent){
					gameSettings.gameMode = GameMode.GRAND_PRIX;
					showGameSettings();
				}else if(event instanceof PracticeStartEvent){
					gameSettings.gameMode = GameMode.PRACTICE;
					showGameSettings();
				}else if(event instanceof ShowRecordsEvent){
					// TODO records menu
				}else if(event instanceof ShowSettingsEvent){
					showSettings();
				}
			}
		});
	}
	
	protected void showSettings() {
		stage.clear();
		SettingsUI ui = new SettingsUI(game, game.getSkin());
		ui.setFillParent(true);
		stage.addActor(ui);
		ui.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				if(event instanceof DismissEvent){
					showMainMenu();
				}
			}
		});
	}

	private void showGameSettings() {
		stage.clear();
		GameConfigUI ui = new GameConfigUI(game, gameSettings, game.getSkin());
		ui.setFillParent(true);
		stage.addActor(ui);
		ui.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				if(event instanceof DismissEvent){
					showMainMenu();
				}else if(event instanceof GameStartEvent){
					game.setScreen(new GameScreen(game));
				}
			}
		});
	}

	@Override
	public void resize(int width, int height) {
		stage.getViewport().update(width, height, true);
	}
	
	@Override
	public void show() {
		Gdx.input.setInputProcessor(stage);
	}
	@Override
	public void hide() {
		Gdx.input.setInputProcessor(null);
	}
	
	@Override
	public void render(float delta) {
		stage.act();
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stage.draw();
	}
}
