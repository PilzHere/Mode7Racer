package mysko.pilzhere.mode7racer.ui;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

import mysko.pilzhere.mode7racer.Mode7Racer;
import mysko.pilzhere.mode7racer.screens.GameScreen;
import mysko.pilzhere.mode7racer.ui.events.DismissEvent;

public class GameHUD extends Table
{
	private Mode7Racer game;
	private GameScreen screen;
	private SettingsUI settingsUI;
	
	public GameHUD(Mode7Racer game, GameScreen screen, Skin skin) {
		super(skin);
		this.game = game;
		this.screen = screen;
		setFillParent(true);
	}
	
	public void showSettings(){
		if(!isSettingsOpened()){
			getStage().addActor(settingsUI = new SettingsUI(game, game.getSkin()));
			settingsUI.addListener(new ChangeListener() {
				@Override
				public void changed(ChangeEvent event, Actor actor) {
					if(event instanceof DismissEvent){
						hideSettings();
					}
				}
			});
		}
	}
	public void hideSettings(){
		if(isSettingsOpened()){
			settingsUI.remove();
			settingsUI = null;
		}
	}
	public boolean isSettingsOpened(){
		return settingsUI != null;
	}
}
