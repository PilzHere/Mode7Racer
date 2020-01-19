package mysko.pilzhere.mode7racer.inputs.keyboard;

import com.badlogic.gdx.Gdx;

import mysko.pilzhere.mode7racer.inputs.base.Command;
import mysko.pilzhere.mode7racer.inputs.base.ControllerBase;
import mysko.pilzhere.mode7racer.inputs.base.TriggerBase;

public class KeyboardController extends ControllerBase
{
	@Override
	public String getID() {
		return "Keyboard";
	}
	@Override
	public String toString() {
		return "Keyboard";
	}
	
	@Override
	public TriggerBase parseTrigger(String data) {
		String[] params = data.split("\\|");
		if(params[0].equals("KEY")){
			return new KeyboardTrigger(Integer.parseInt(params[1]));
		}else{
			throw new IllegalArgumentException("unrecognized keyboard trigger: " + data);
		}
	}
	
	@Override
	public TriggerBase learn() {
		for(int i=0 ; i<256 ; i++){
			if(Gdx.input.isKeyJustPressed(i)){
				return new KeyboardTrigger(i);
			}
		}
		return null;
	}
	@Override
	public void learnStart(Command cmd) {
		// nothing to do
	}
	@Override
	public void learnStop() {
		// nothing to do
	}

}
