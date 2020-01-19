package mysko.pilzhere.mode7racer.inputs;

import com.badlogic.gdx.Input;

import mysko.pilzhere.mode7racer.inputs.base.InputManagerBase;

public class GameInputManager extends InputManagerBase
{
	/** from https://www.retrogames.cz/manualy/SNES/F-Zero_-_Manual_-_SNES.pdf */
	public static enum PlayerCommand {
		LEFT, RIGHT, UP, DOWN, THROTTLE, TURBO, BRAKE, SHIFT_LEFT, SHIFT_RIGHT, PAUSE, SELECT
	}
	
	public GameInputManager() {
		super(2); // XXX one player only for now
	}

	@Override
	protected void setDefault() {
		
		// register game commands
		addCommand(PlayerCommand.LEFT, "left", "Left");
		addCommand(PlayerCommand.RIGHT, "right", "Right");
		addCommand(PlayerCommand.UP, "up", "Up");
		addCommand(PlayerCommand.DOWN, "down", "Down");
		
		addCommand(PlayerCommand.SHIFT_LEFT, "shift-left", "Shift Left");
		addCommand(PlayerCommand.SHIFT_RIGHT, "shit-right", "Shidt Right");
		
		addCommand(PlayerCommand.THROTTLE, "throttle", "Throttle");
		addCommand(PlayerCommand.BRAKE, "brake", "Brake");
		addCommand(PlayerCommand.TURBO, "turbo", "Turbo");
		
		addCommand(PlayerCommand.PAUSE, "pause", "Pause");
		addCommand(PlayerCommand.SELECT, "select", "Select");
		
		// set default keys for player 1
		
		// WASD/ZQSD (qwerty/azerty) for basic commands
		addKeys(0, PlayerCommand.LEFT, Input.Keys.Q, Input.Keys.A);
		addKeys(0, PlayerCommand.RIGHT, Input.Keys.D);
		addKeys(0, PlayerCommand.BRAKE, Input.Keys.S);
		addKeys(0, PlayerCommand.THROTTLE, Input.Keys.Z, Input.Keys.W);
		
		// arrows for extra commands
		addKeys(0, PlayerCommand.UP, Input.Keys.UP);
		addKeys(0, PlayerCommand.DOWN, Input.Keys.DOWN);
		addKeys(0, PlayerCommand.SHIFT_LEFT, Input.Keys.LEFT);
		addKeys(0, PlayerCommand.SHIFT_RIGHT, Input.Keys.RIGHT);

		// misc commands
		addKeys(0, PlayerCommand.TURBO, Input.Keys.T);
		addKeys(0, PlayerCommand.PAUSE, Input.Keys.P);
		addKeys(0, PlayerCommand.SELECT, Input.Keys.O);
	}

}