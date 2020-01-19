package mysko.pilzhere.mode7racer.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ButtonGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap.Entry;

import mysko.pilzhere.mode7racer.Mode7Racer;
import mysko.pilzhere.mode7racer.inputs.GameInputManager;
import mysko.pilzhere.mode7racer.inputs.base.Command;
import mysko.pilzhere.mode7racer.inputs.base.ControllerBase;
import mysko.pilzhere.mode7racer.inputs.base.TriggerBase;
import mysko.pilzhere.mode7racer.ui.events.DismissEvent;

public class InputsUI extends Table
{
	private Table cmdTable;
	
	private Command learningCommand;
	private SelectBox<ControllerBase> controllerSelector;
	
	private ButtonGroup<Button> buttons = new ButtonGroup<Button>();
	private Label learnLabel;

	private final Mode7Racer game;

	private int playerID;

	public InputsUI(Mode7Racer game, final int playerID, Skin skin) {
		super(skin);
		this.game = game;
		this.playerID = playerID;
		
		final GameInputManager inputs = game.inputs;
		
		setBackground("default-rect");
		defaults().pad(10);
		
		buttons.setMinCheckCount(0);
		buttons.setMaxCheckCount(1);
		
		add("CONTROLLER SETTINGS").row();
		
		controllerSelector = new SelectBox<ControllerBase>(skin);
		controllerSelector.setItems(inputs.getControllers(playerID));
		controllerSelector.setSelected(inputs.getController(playerID));
		
		add(controllerSelector).row();
		
		add(cmdTable = new Table(skin)).row();
		
		
		TextButton btClose = new TextButton("OK", skin);
		add(btClose);
		
		displayController(controllerSelector.getSelected());

		btClose.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				fire(new DismissEvent());
			}
		});
		controllerSelector.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				cancelLearn();
				InputsUI.this.game.inputs.setController(playerID, controllerSelector.getSelected());
				displayController(controllerSelector.getSelected());
			}
		});
		
	}
	
	private void displayController(final ControllerBase ce){
		cmdTable.clearChildren();
		
		cmdTable.defaults().pad(5);
		
		buttons.clear();
		
		for(final Command cmd : game.inputs.commands){
			cmdTable.add(cmd.label);
			
			final Label keysLabel = cmdTable.add(triggersToText(ce, cmd)).getActor();
			keysLabel.setColor(Color.BLACK);
			
			final TextButton btLearn = new TextButton("change", getSkin(), "toggle");
			cmdTable.add(btLearn).row();
			
			buttons.add(btLearn);
			
			btLearn.addListener(new ChangeListener() {

				@Override
				public void changed(ChangeEvent event, Actor actor) {
					if(btLearn.isChecked()){
						keysLabel.setText("...");
						learnLabel = keysLabel;
						learningCommand = cmd;
						ce.clear(cmd);
						ce.learnStart(cmd);
					}else{
						keysLabel.setText(triggersToText(ce, cmd));
						if(learningCommand == cmd){
							learningCommand = null;
							learnLabel = null;
							ce.learnStop();
						}
					}
				}
			});
		}
	}
	
	protected CharSequence triggersToText(ControllerBase ce, Command cmd) {
		Array<TriggerBase> triggers = new Array<TriggerBase>();
		
		for(Entry<TriggerBase, Command> entry : ce.triggers){
			if(entry.value == cmd){
				entry.key.toString();
				triggers.add(entry.key);
			}
		}
		
		String s = "";
		for(int i=0 ; i<triggers.size ; i++){
			if(i > 0) s += ", ";
			s += triggers.get(i).toString();
		}
		return s;
	}

	@Override
	public void act(float delta) {
		if(learningCommand != null){
			ControllerBase ce = controllerSelector.getSelected();
			TriggerBase trigger = ce.learn();
			if(trigger != null){
				ce.triggers.put(trigger, learningCommand);
				learnLabel.setText(triggersToText(ce, learningCommand));
			}
		}
		super.act(delta);
	}

	public void cancelLearn() {
		InputsUI.this.game.inputs.getController(playerID).learnStop();
	}

}
