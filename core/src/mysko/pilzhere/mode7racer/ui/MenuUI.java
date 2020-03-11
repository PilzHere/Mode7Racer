package mysko.pilzhere.mode7racer.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

import mysko.pilzhere.mode7racer.ui.events.GrandPrixStartEvent;
import mysko.pilzhere.mode7racer.ui.events.PracticeStartEvent;
import mysko.pilzhere.mode7racer.ui.events.ShowRecordsEvent;
import mysko.pilzhere.mode7racer.ui.events.ShowSettingsEvent;

public class MenuUI extends Table
{
	// TODO set menu selection with blink ?
	
	public MenuUI(Skin skin) {
		super(skin);
		setBackground(skin.newDrawable("white", Color.DARK_GRAY)); // TODO use title image instead
		
		Table root = new Table(skin);
		add(root);
		
		TextButton textButton = new TextButton("GRAND PRIX", getSkin(), "menu");
		root.add(textButton);
		textButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				fire(new GrandPrixStartEvent());
			}
		});
		
		root.row();
		textButton = new TextButton("PRACTICE", getSkin(), "menu-faded");
		root.add(textButton);
		textButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				fire(new PracticeStartEvent());
			}
		});
		
		root.row();
		textButton = new TextButton("RECORDS", getSkin(), "menu-faded");
		root.add(textButton);
		textButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				fire(new ShowRecordsEvent());
			}
		});
		
		root.row();
		textButton = new TextButton("SETTINGS", getSkin(), "menu-faded");
		root.add(textButton);
		textButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				fire(new ShowSettingsEvent());
			}
		});
	}
	
}
