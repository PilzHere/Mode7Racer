package mysko.pilzhere.mode7racer.inputs.gamepad;

import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.ControllerAdapter;
import com.badlogic.gdx.controllers.PovDirection;

import mysko.pilzhere.mode7racer.inputs.base.Command;
import mysko.pilzhere.mode7racer.inputs.base.ControllerBase;
import mysko.pilzhere.mode7racer.inputs.base.TriggerBase;

public class GamepadController extends ControllerBase
{
	Controller controller;
	
	protected GamepadTrigger lastTrigger;

	private ControllerAdapter listener;

	private final int index;
	
	public GamepadController(Controller controller, int index) {
		super();
		this.controller = controller;
		this.index = index;
	}
	
	@Override
	public String getID() {
		return controller.getName() + "#" + index;
	}
	
	@Override
	public String toString() {
		String name = controller.getName();
		if(name.length() >= 16){
			name = name.substring(0, 16);
		}
		return name + " #" + (index+1);
	}
	
	@Override
	public TriggerBase parseTrigger(String data) {
		String[] params = data.split("\\|");
		if(params[0].equals("POV")){
			return new GamepadPovTrigger(Integer.parseInt(params[1]), PovDirection.valueOf(params[2]));
		}
		else if(params[0].equals("BUTTON")){
			return new GamepadButtonTrigger(Integer.parseInt(params[1]));
		}
		else if(params[0].equals("AXIS")){
			return new GamepadAxisTrigger(Integer.parseInt(params[1]), "+".equals(params[2]));
		}
		else if(params[0].equals("SLIDER")){
			return new GamepadSliderTrigger(Integer.parseInt(params[1]), "y".equals(params[2]));
		}
		else{
			throw new IllegalArgumentException("unrecognized gamepad trigger: " + data);
		}
	}

	@Override
	public TriggerBase learn() {
		GamepadTrigger trigger = lastTrigger;
		lastTrigger = null;
		return trigger;
	}

	@Override
	public void learnStart(final Command cmd) {
		learnStop();
		
		controller.addListener(listener = new ControllerAdapter(){
			@Override
			public boolean axisMoved(Controller controller, int axisIndex, float value) {
				if(value > 0.5f){
					lastTrigger = new GamepadAxisTrigger(axisIndex, true);
				}else if(value < -0.5f){
					lastTrigger = new GamepadAxisTrigger(axisIndex, false);
				}
				return false;
			}
			@Override
			public boolean buttonDown(Controller controller, int buttonIndex) {
				lastTrigger = new GamepadButtonTrigger(buttonIndex);
				return false;
			}
			@Override
			public boolean povMoved(Controller controller, int povIndex, PovDirection value) {
				switch(value){
				case north:
				case east:
				case west:
				case south:
					lastTrigger = new GamepadPovTrigger(povIndex, value);
					break;
				default:
					break;
				}
				return false;
			}
			@Override
			public boolean xSliderMoved(Controller controller, int sliderIndex, boolean value) {
				lastTrigger = new GamepadSliderTrigger(sliderIndex, false);
				return false;
			}
			@Override
			public boolean ySliderMoved(Controller controller, int sliderIndex, boolean value) {
				lastTrigger = new GamepadSliderTrigger(sliderIndex, true);
				return false;
			}
			
		});
	}

	@Override
	public void learnStop() {
		if(listener != null){
			controller.removeListener(listener);
			listener = null;
			lastTrigger = null;
		}
	}

}
