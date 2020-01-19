package mysko.pilzhere.mode7racer.ui;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

import mysko.pilzhere.mode7racer.Mode7Racer;
import mysko.pilzhere.mode7racer.ui.events.DismissEvent;

public class SettingsUI extends Table
{
	private PlayersInputUI playersInputs;

	public SettingsUI(final Mode7Racer game, Skin skin) {
		super(skin);
		setFillParent(true);
		
		// reload inputs in case player just plugged a gamepad.
		game.getStorage().reloadInputs();
		
		// 2 players settings tab pane
		add(playersInputs = new PlayersInputUI(game, 2, skin)).expand().center();
		
		// save settings and hide on dismiss
		addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				if(event instanceof DismissEvent){
					playersInputs.cancelLearn();
					game.getStorage().save();
				}
			}
		});
	}
	
}
