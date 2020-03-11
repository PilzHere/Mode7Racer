package mysko.pilzhere.mode7racer.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

import mysko.pilzhere.mode7racer.Mode7Racer;
import mysko.pilzhere.mode7racer.ui.events.DismissEvent;
import mysko.pilzhere.mode7racer.ui.settings.SettingsUI;

public class GameHUD extends Table
{
	private Mode7Racer game;
	private Table settingsUI;
	
	public GameHUD(Mode7Racer game, Skin skin) {
		super(skin);
		this.game = game;
		setFillParent(true);
	}
	
	public void spawnTitle(String title){
		
		Table table = new Table(getSkin());
		addActor(table);
		table.setFillParent(true);
		
		table.add(new Label(title, getSkin(), "heading")).expand().center();
		
		table.addAction(Actions.sequence(Actions.delay(2f), Actions.alpha(0, .3f), Actions.removeActor()));
	}
	
	public void showSettings(){
		if(!isSettingsOpened()){
			settingsUI = new Table(getSkin());
			settingsUI.setFillParent(true);
			settingsUI.setBackground(getSkin().newDrawable("white", new Color(0,0,0,.9f)));
			settingsUI.add(new SettingsUI(game, game.getSkin())).grow();
			getStage().addActor(settingsUI);
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
