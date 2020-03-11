package mysko.pilzhere.mode7racer.ui.settings;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

import mysko.pilzhere.mode7racer.Mode7Racer;

public class DisplaySettingsUI extends Table
{
	private Mode7Racer game;

	public DisplaySettingsUI(Mode7Racer game, Skin skin) {
		super(skin);
		this.game = game;
		
		setBackground("box-ten");
		pad(11);
		
		add("TODO display");
	}
	
}
