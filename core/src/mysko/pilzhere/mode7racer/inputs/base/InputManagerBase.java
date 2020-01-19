package mysko.pilzhere.mode7racer.inputs.base;

import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.ObjectMap.Entry;

import mysko.pilzhere.mode7racer.inputs.gamepad.GamepadController;
import mysko.pilzhere.mode7racer.inputs.keyboard.KeyboardController;
import mysko.pilzhere.mode7racer.inputs.keyboard.KeyboardTrigger;
import mysko.pilzhere.mode7racer.storage.inputs.ControllerData;
import mysko.pilzhere.mode7racer.storage.inputs.InputsData;
import mysko.pilzhere.mode7racer.storage.inputs.PlayerControllersData;

public abstract class InputManagerBase {

	abstract protected void setDefault();

	public final Array<Command> commands = new Array<Command>();

	public final Array<Array<ControllerBase>> availableControllers = new Array<Array<ControllerBase>>();
	public final Array<ControllerBase> activeControllers = new Array<ControllerBase>();

	private final int maxPlayers;
	
	public InputManagerBase(int maxPlayers){
		this.maxPlayers = maxPlayers;
		activeControllers.setSize(maxPlayers);
		availableControllers.setSize(maxPlayers);
	}
	
	public int getMaxPlayers() {
		return maxPlayers;
	}
	
	public void setController(int player, ControllerBase controller) {
		ensurePlayer(player);
		activeControllers.set(player, controller);
	}
	
	public ControllerBase getController(int player) {
		ensurePlayer(player);
		ControllerBase c = activeControllers.get(player);
		if(c == null) throw new IllegalStateException("player index " + player + " not set yet");
		return c;
	}
	
	public Array<ControllerBase> getControllers(int player){
		ensurePlayer(player);
		Array<ControllerBase> controllers = availableControllers.get(player);
		if(controllers == null) throw new IllegalStateException("not initialized yet");
		return controllers;
	}
	
	private void ensurePlayer(int player){
		if(player < 0) throw new IllegalArgumentException("player index should be positive");
		if(player >= maxPlayers) throw new IllegalArgumentException("player index out of bounds: " + player);
	}
	
	protected void addCommand(Object cmd, String id, String label) {
		commands.add(new Command(cmd, id, label));
	}
	
	protected void addKeys(int player, Object cmd, int...keys) {
		Command cm = null;
		for(Command c : commands){
			if(c.cmd == cmd){
				cm = c;
				break;
			}
		}
		ControllerBase keyboard = getControllers(player).first();
		for(int key : keys){
			keyboard.triggers.put(new KeyboardTrigger(key), cm);
		}
	}

	public void load(InputsData data) {
		
		// refresh controller list
		
		availableControllers.clear();
		
		// each player can chose keyboard or any available gamepads.
		for(int player = 0 ; player < maxPlayers ; player++){
			Array<ControllerBase> controllers = new Array<ControllerBase>();
			controllers.add(new KeyboardController());
			ObjectMap<String, Integer> indexer = new ObjectMap<String, Integer>();
			for(Controller gamepad : Controllers.getControllers()){
				int index = indexer.get(gamepad.getName(), 0);
				controllers.add(new GamepadController(gamepad, index));
				indexer.put(gamepad.getName(), index+1);
			}
			availableControllers.add(controllers);
		}
		
		// initialize defaults
		
		commands.clear();
		
		setDefault();
		
		// parse data
		
		for(int player = 0 ; player < maxPlayers && player < data.playersControllers.size ; player++){
			Array<ControllerBase> playerControllers = availableControllers.get(player);
			PlayerControllersData pcd = data.playersControllers.get(player);
			
			// parse individual settings
			for(ControllerData cd : pcd.controllers){
				ControllerBase c = findController(playerControllers, cd.id);
				if(c != null){
					c.triggers.clear(); // clear default
					for(Entry<String, String> e : cd.triggers){
						TriggerBase trigger = c.parseTrigger(e.key);
						if(trigger != null){
							for(Command cmd : commands){
								if(cmd.id.equals(e.value)){
									c.triggers.put(trigger, cmd);
								}
							}
						}
					}
				}
			}
			// set active controller (default keyboard)
			ControllerData cd = pcd.controllers.get(pcd.selected);
			ControllerBase c = findController(playerControllers, cd.id);
			if(c == null){
				c = playerControllers.first();
			}
			activeControllers.set(player, c);
		}
		
		// set default controller in case not set yet
		for(int player = 0 ; player < maxPlayers ; player++){
			if(activeControllers.get(player) == null){
				activeControllers.set(player, availableControllers.get(player).first());
			}
		}
	}

	private ControllerBase findController(Array<ControllerBase> controllers, String id) {
		for(ControllerBase c : controllers){
			if(id.equals(c.getID())) return c;
		}
		return null;
	}

	public void save(InputsData data) 
	{
		for(int player = 0 ; player<maxPlayers ; player++){
			Array<ControllerBase> playerControllers = availableControllers.get(player);
			PlayerControllersData pcd;
			if(player < data.playersControllers.size){
				pcd = data.playersControllers.get(player);
			}else{
				pcd = new PlayerControllersData();
				data.playersControllers.add(pcd);
			}
			
			for(ControllerBase c : playerControllers){
				ControllerData cd = findControllerData(pcd.controllers, c.getID());
				if(cd == null){
					cd = new ControllerData();
					cd.id = c.getID();
					pcd.controllers.add(cd);
				}else{
					cd.triggers.clear();
				}
				for(Entry<TriggerBase, Command> e : c.triggers){
					cd.triggers.put(e.key.format(), e.value.id);
				}
			}
			
			ControllerBase c = activeControllers.get(player);
			ControllerData cd = findControllerData(pcd.controllers, c.getID());
			pcd.selected = pcd.controllers.indexOf(cd, true);
		}
		
	}

	private ControllerData findControllerData(Array<ControllerData> controllers, String id) {
		for(ControllerData c : controllers){
			if(id.equals(c.id)) return c;
		}
		return null;
	}

}
