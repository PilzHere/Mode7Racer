package mysko.pilzhere.mode7racer.ui;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ButtonGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

import mysko.pilzhere.mode7racer.Mode7Racer;

public class PlayersInputUI extends Table
{
	private Mode7Racer game;
	
	private ButtonGroup<Button> btGroup;

	private Cell paneCell;

	private InputsUI inputUI;

	public PlayersInputUI(Mode7Racer game, int numPlayers, Skin skin) {
		super(skin);
		this.game = game;
		
		btGroup = new ButtonGroup<Button>();
		
		Table menu = new Table(skin);
		for(int i=0 ; i<numPlayers ; i++){
			final TextButton bt = new TextButton("Player " + (i+1), skin, "toggle");
			menu.add(bt);
			if(i >= game.inputs.getMaxPlayers()){
				bt.setDisabled(true);
			}
			btGroup.add(bt);
		}
		btGroup.setMinCheckCount(1);
		btGroup.setMaxCheckCount(1);
		menu.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				changeTab();
			}
		});
		
		add(menu).expandX().left().row();
		paneCell = add();
		row();
		
		changeTab();
	}
	
	public void cancelLearn(){
		if(inputUI != null){
			inputUI.cancelLearn();
		}
	}

	private void changeTab() {
		int tabIndex = btGroup.getCheckedIndex();
		if(tabIndex < 0) return;
		cancelLearn();
		paneCell.setActor(inputUI = new InputsUI(game, tabIndex, getSkin()));
	}
}
