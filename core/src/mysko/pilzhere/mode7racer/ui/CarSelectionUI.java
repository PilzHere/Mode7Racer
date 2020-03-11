package mysko.pilzhere.mode7racer.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

import mysko.pilzhere.mode7racer.entities.CarType;
import mysko.pilzhere.mode7racer.ui.events.DismissEvent;
import mysko.pilzhere.mode7racer.ui.events.VehicleSelectedEvent;
import space.earlygrey.shapedrawer.GraphDrawer;
import space.earlygrey.shapedrawer.ShapeDrawer;
import space.earlygrey.shapedrawer.scene2d.GraphDrawerDrawable;

public class CarSelectionUI extends Table
{
	public CarSelectionUI(CarType carType, Stage stage, Skin skin) {
		super(skin);
		
		// TODO use car type
		
		Table root = new Table(skin);
		root.setBackground("box-ten");
		root.pad(10);
		add(root);
		
		Label label = new Label(carType.name, getSkin(), "button");
		root.add(label).expandY().top();
		
		root.row();
		Table table = new Table();
		root.add(table);
		
		table.defaults().space(5).uniformX().left();
		label = new Label("ENGINE UNIT", getSkin());
		table.add(label);
		
		label = new Label(": " + carType.engineUnit, getSkin());
		table.add(label);
		
		table.row();
		label = new Label("MAX POWER", getSkin());
		table.add(label);
		
		label = new Label(": " + carType.maxPower + "ps", getSkin());
		table.add(label);
		
		table.row();
		label = new Label("MAX SPEED", getSkin());
		table.add(label);
		
		label = new Label(": " + carType.maxSpeed  + "km-h", getSkin());
		table.add(label);
		
		table.row();
		label = new Label("WEIGHT", getSkin());
		table.add(label);
		
		label = new Label(": " + carType.weight + "kg", getSkin());
		table.add(label);
		
		root.row();
		label = new Label("ACCELERATION", getSkin());
		root.add(label).space(10);
		
		root.row();
		Stack stack = new Stack();
		root.add(stack).size(150, 75);
		
		Image image = new Image(getSkin(), "acceleration-grid-ten");
		stack.add(image);
		
		Container container = new Container();
		stack.add(container);
		
		//create graph
		final GraphDrawerDrawable graphDrawerDrawable = new GraphDrawerDrawable(new GraphDrawer(new ShapeDrawer(stage.getBatch(), getSkin().getRegion("white"))));
		//This controls the appearance of the graph. It can be assigned to any of the default Interpolations or you can create one with your own formula.
		graphDrawerDrawable.setInterpolation(carType.curve);
		graphDrawerDrawable.setColor(Color.WHITE);
		//start domain end at 0 for the beginning of the animation.
		graphDrawerDrawable.setDomainEnd(0);
		image = new Image(graphDrawerDrawable);
		container.setActor(image);
		container.fill().padLeft(25).padBottom(10).padTop(20).padRight(5);
		//over 2 seconds, increase the domain end to 1 for a reveal from left animation.
		image.addAction(new TemporalAction(2f) {
			@Override
			protected void update(float percent) {
				graphDrawerDrawable.setDomainEnd(percent);
			}
		});
		
		root.row();
		table = new Table();
		root.add(table).expandY().bottom();
		
		ImageButton imageButton = new ImageButton(getSkin(), "yes");
		table.add(imageButton);
		imageButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				fire(new VehicleSelectedEvent());
			}
		});
		
		imageButton = new ImageButton(getSkin(), "no");
		table.add(imageButton);
		imageButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				fire(new DismissEvent());
			}
		});
	}
	
}
