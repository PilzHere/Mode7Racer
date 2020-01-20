package mysko.pilzhere.mode7racer.inputs.base;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.ObjectMap.Entry;

public abstract class ControllerBase {
	
	public static interface CommandListener {
		public void onCommand(Command cmd);
	}
	
	public final Array<CommandListener> listeners = new Array<CommandListener>();
	
	public final ObjectMap<TriggerBase, Command> triggers = new ObjectMap<TriggerBase, Command>();

	public abstract String getID();
	
	public abstract TriggerBase learn();

	public void clear(Command cmd) {
		Array<TriggerBase> toRemove = new Array<TriggerBase>();
		for(Entry<TriggerBase, Command> e : triggers){
			if(e.value == cmd){
				toRemove.add(e.key);
			}
		}
		for(TriggerBase t : toRemove){
			triggers.remove(t);
		}
	}

	abstract public void learnStart(Command cmd);
	abstract public void learnStop();
	abstract public TriggerBase parseTrigger(String data);
	
	public boolean isOn(Object command) {
		for(Entry<TriggerBase, Command> e : triggers){
			if(e.value.cmd == command){
				if(e.key.isOn(this)){
					return true;
				}
			}
		}
		return false;
	}
	
	public boolean isJustOn(Object command){
		for(Entry<TriggerBase, Command> e : triggers){
			if(e.value.cmd == command){
				if(e.key.isOn(this) && !e.key.wasOn){
					return true;
				}
			}
		}
		return false;
	}
	
}