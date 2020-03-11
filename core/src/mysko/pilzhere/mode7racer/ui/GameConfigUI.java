package mysko.pilzhere.mode7racer.ui;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.ButtonGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.Scaling;

import mysko.pilzhere.mode7racer.Mode7Racer;
import mysko.pilzhere.mode7racer.entities.CarType;
import mysko.pilzhere.mode7racer.game.GameClass;
import mysko.pilzhere.mode7racer.game.GameLeague;
import mysko.pilzhere.mode7racer.game.GameSettings;
import mysko.pilzhere.mode7racer.ui.events.DismissEvent;
import mysko.pilzhere.mode7racer.ui.events.GameStartEvent;
import mysko.pilzhere.mode7racer.ui.events.VehicleSelectedEvent;

public class GameConfigUI extends Table
{
	private final Table contextTable;
	private final Mode7Racer game;
	private final GameSettings gameSettings;
	private ButtonGroup btGroupCars, btGroupLeague, btGroupClass;
	

	public GameConfigUI(Mode7Racer game, GameSettings gameSettings, Skin skin) {
		super(skin);
		this.game = game;
		this.gameSettings = gameSettings;
		
		btGroupCars = new ButtonGroup();
		btGroupCars.setMinCheckCount(0);
		
		btGroupLeague = new ButtonGroup();
		btGroupLeague.setMinCheckCount(0);
		
		btGroupClass = new ButtonGroup();
		btGroupClass.setMinCheckCount(0);
		
		Table menuTable = new Table(skin);
		contextTable = new Table(skin);
		
		add(menuTable).expandY();
		add(contextTable).grow();
		
		createCarsMenu(menuTable);
		
		showInitial();
	}
	
	private void createCarsMenu(Table table){
		
		CarType [] cars = {CarType.BLUE, CarType.YELLOW};
		
		for(int i=0 ; i<cars.length ; i++){
			table.add(createCarItem(cars[i])).pad(10).row();
		}
		
	}
	
	private Actor createCarItem(final CarType carType) 
	{
		// TODO need a meta car repository
		Texture texture;
		switch (carType) {
		case BLUE:
			texture = game.getAssMan().get(game.getAssMan().CAR_01_SIZE_09_FRONT_LEFT_01);
			break;
		case YELLOW:
			texture = game.getAssMan().get(game.getAssMan().CAR_02_SIZE_09_FRONT_LEFT_01);
			break;
		default:
			throw new GdxRuntimeException("oops!");
		}
		
		// TODO cusrsor and blink and such
		Image image = new Image(texture);
		image.setScaling(Scaling.none);
		
		final TextButton bt = new TextButton("", getSkin(), "toggle");
		bt.add(image);
		
		bt.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				if(bt.isChecked()) selectCar(carType);
			}
		});
		
		btGroupCars.add(bt);

		return bt;
	}

	private void selectCar(final CarType carType) {
		contextTable.clear();
		CarSelectionUI ui = new CarSelectionUI(carType, getStage(), getSkin());
		contextTable.add(ui);
		
		ui.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				if(event instanceof DismissEvent){
					event.stop();
					showInitial();
				}else if(event instanceof VehicleSelectedEvent){
					gameSettings.carType = carType;
					showGameSettings();
				}
			}
		});
	}

	private void showInitial(){
		contextTable.clear();
		String title = gameSettings.gameMode.toString() + " MODE";
		contextTable.add(title).row();
		contextTable.add("SELECT YOUR CAR")
		.padTop(10) // FIXME font line height seams off...
		.row();
	}
	
	private void showGameSettings(){
		contextTable.clear();
		
		btGroupLeague.clear();
		btGroupClass.clear();
		
		Table outer  = new Table(getSkin());
		outer.setBackground("box-ten");
		outer.pad(10);
		contextTable.add(outer).grow();
		
		Table root = new Table(getSkin());
		root.defaults().expandX().left();
		
		Label label = new Label("SELECT", getSkin(), "button");
		outer.add(label).expandX().center().row();
		outer.add(root).expand().center().row();
		
		Table leagueTable = new Table(getSkin());
		root.add(new Label("LEAGUE", getSkin(), "strong")).padTop(10).row();
		root.add(leagueTable).padLeft(10).row();
		
		for(final GameLeague league: GameLeague.values()){
			final TextButton bt = new TextButton(league.toString() + " LEAGUE", getSkin(), "toggle");
			leagueTable.add(bt).expandX().left().row();
			btGroupLeague.add(bt);
			bt.addListener(new ChangeListener(){
				@Override
				public void changed(ChangeEvent event, Actor actor) {
					if(bt.isChecked()){
						gameSettings.gameLeague = league;
						checkEnd();
					}
				}
			});
		}
		
		Table classTable = new Table(getSkin());
		root.add(new Label("CLASS", getSkin(), "strong")).padTop(10).row();
		root.add(classTable).padLeft(10).expandX().left().row();
		
		for(final GameClass clazz: GameClass.values()){
			final TextButton bt = new TextButton(clazz.toString(), getSkin(), "toggle");
			classTable.add(bt).expandX().left().row();
			btGroupClass.add(bt);
			bt.addListener(new ChangeListener(){
				@Override
				public void changed(ChangeEvent event, Actor actor) {
					if(bt.isChecked()){
						gameSettings.gameClass = clazz;
						checkEnd();
					}
				}
			});
		}
	}

	protected void checkEnd() {
		if(gameSettings.gameLeague != null && gameSettings.gameClass != null){
			fire(new GameStartEvent());
		}
	}
}
