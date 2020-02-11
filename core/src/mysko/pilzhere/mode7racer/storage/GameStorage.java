package mysko.pilzhere.mode7racer.storage;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.utils.Json;

import mysko.pilzhere.mode7racer.Mode7Racer;
import mysko.pilzhere.mode7racer.storage.inputs.InputsData;

public class GameStorage {
	
	private static final String PREFS_FILENAME = "Mode7Racer";
	private static final String KEY_INPUTS = "controls";
	
	private Preferences preferences;

	private InputsData inputs;
	
	private Mode7Racer game;
	
	public GameStorage(Mode7Racer game) {
		this.game = game;
	}

	public void load(){
		preferences = Gdx.app.getPreferences(PREFS_FILENAME);
		loadInputs();
	}
	
	public void save(){
		saveInputs();
		preferences.flush();
	}
	
	private void loadInputs(){
		String json = preferences.getString(KEY_INPUTS, null);
		if(json != null){
			inputs = new Json().fromJson(InputsData.class, json);
		}else{
			inputs = new InputsData();
		}
		game.INPUTS.load(inputs);
	}
	
	private void saveInputs(){
		game.INPUTS.save(inputs);
		preferences.putString(KEY_INPUTS, new Json().toJson(inputs));
	}

	public void reloadInputs() {
		game.INPUTS.load(inputs);
	}
	
}
