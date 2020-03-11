package mysko.pilzhere.mode7racer.ui.settings;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ButtonGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

import mysko.pilzhere.mode7racer.Mode7Racer;
import mysko.pilzhere.mode7racer.ui.events.DismissEvent;

public class SettingsUI extends Table
{
	private ButtonGroup<Button> btGroup;

	private Cell paneCell;

	private InputsUI inputUI;

	public SettingsUI(final Mode7Racer game, Skin skin) {
		super(skin);
		
		// reload inputs in case player just plugged a gamepad.
		game.getStorage().reloadInputs();
		
		btGroup = new ButtonGroup<Button>();
		btGroup.setMinCheckCount(1);
		btGroup.setMaxCheckCount(1);
		
		Table menu = new Table(skin);
		menu.pad(10);
		menu.defaults().expandX().left();
		
		addSection(menu, "CONTROLLERS");
		for(int i=0 ; i<game.INPUTS.getMaxPlayers() ; i++){
			final int player = i;
			addItem(menu, "Player " + (i+1), new Runnable() {
				@Override
				public void run() {
					paneCell.setActor(inputUI = new InputsUI(game, player, getSkin()));
				}
			});
		}
		
		addSection(menu, "MISC");
		addItem(menu, "Display", new Runnable() {
			@Override
			public void run() {
				paneCell.setActor(new DisplaySettingsUI(game, getSkin()));
			}
		});
		addItem(menu, "Audio", new Runnable() {
			@Override
			public void run() {
				paneCell.setActor(new AudioSettingsUI(game, getSkin()));
			}
		});
		addItem(menu, "Debug", new Runnable() {
			@Override
			public void run() {
				paneCell.setActor(new DebugSettingsUI(game, getSkin()));
			}
		});
		
		
		TextButton btClose = new TextButton("Exit", skin);
		menu.add(btClose).row();
		btClose.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				clearPane(); // required to cancel learning
				game.getStorage().save();
				fire(new DismissEvent());
			}
		});
		
		add(menu);
		paneCell = add().grow();
		row();
		
		// XXX set first (should be auto via button group)
		paneCell.setActor(inputUI = new InputsUI(game, 0, getSkin()));
	}
	
	private void addSection(Table menu, String title) {
		menu.add(new Label(title, getSkin(), "strong")).row();
	}

	private void addItem(Table menu, String title, final Runnable callback){
		final TextButton bt = new TextButton(title, getSkin(), "toggle");
		menu.add(bt).padLeft(10).row();
		btGroup.add(bt);
		bt.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				clearPane();
				if(bt.isChecked()){
					callback.run();
				}
			}
		});
	}
	
	private void clearPane(){
		if(inputUI != null){
			inputUI.cancelLearn();
			inputUI = null;
		}
		paneCell.setActor(null);
	}
}
