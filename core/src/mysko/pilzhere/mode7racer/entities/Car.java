package mysko.pilzhere.mode7racer.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

import mysko.pilzhere.mode7racer.entities.colliders.Curb;
import mysko.pilzhere.mode7racer.entities.colliders.Edge;
import mysko.pilzhere.mode7racer.screens.GameScreen;

public class Car extends Entity {
	public int getHp() {
		return hp;
	}

	private final float rectSize = 0.25f;

	public boolean isClient = false;

	private Sprite sprite;

	public Car(GameScreen screen, Vector3 position) {
		super(screen, position);

		getTextures();

		sprite = new Sprite(texCar01Size09Back01);
		rect = new Rectangle(position.x - rectSize / 2, position.z - rectSize / 2, rectSize, rectSize);
	}

	private Texture texCar01Size09Back01, texCar01Size08Back01, texCar01Size07Back01, texCar01Size06Back01,
			texCar01Size05Back01, texCar01Size04Back01, texCar01Size03Back01, texCar01Size02Back01,
			texCar01Size01Back01;
	private Texture texCar01BigBackTurnRight02;

	private void getTextures() {
		texCar01Size09Back01 = screen.assMan.get("car01Size09Back01.png");
		texCar01Size08Back01 = screen.assMan.get("car01Size08Back01.png");
		texCar01Size07Back01 = screen.assMan.get("car01Size07Back01.png");
		texCar01Size06Back01 = screen.assMan.get("car01Size06Back01.png");
		texCar01Size05Back01 = screen.assMan.get("car01Size05Back01.png");
		texCar01Size04Back01 = screen.assMan.get("car01Size04Back01.png");
		texCar01Size03Back01 = screen.assMan.get("car01Size03Back01.png");
		texCar01Size02Back01 = screen.assMan.get("car01Size02Back01.png");
		texCar01Size01Back01 = screen.assMan.get("car01Size01Back01.png");

		texCar01BigBackTurnRight02 = screen.assMan.get("car01Size09BackTurnRight02.png");
	}

	private float angle = 0;
	private final float angleIncrement = 2.5f;
	private float carCurrentSpeed;
	private boolean rightTurn; // For moving bg's.
	private boolean leftTurn;

	public void onInputA(float delta) {
		if (carCurrentSpeed > 0) {
			angle -= angleIncrement * delta;
			leftTurn = true;
		}
	}

	public void onInputD(float delta) {
		if (carCurrentSpeed > 0) {
			angle += angleIncrement * delta;
			rightTurn = true;
		}
	}

	private final float carSpeedIncrement = 0.5f; // was 2 should slow it
	private final float motorBrakeStrength = 0.35f;
	private final int bounceBrakesStrength = 16;

	public void onNoInputW(float delta) {
		if (bounceX || bounceZ)
			carCurrentSpeed -= carSpeedIncrement * bounceBrakesStrength * motorBrakeStrength * delta; // Bounce brake.
		else {
			carCurrentSpeed -= carSpeedIncrement * motorBrakeStrength * delta; // Motor brake.
		}
	}

	public void onInputW(float delta) {
		carCurrentSpeed += carSpeedIncrement * delta;
	}

	private final float brakesStrength = 0.33f;

	public void onInputS(float delta) {
		carCurrentSpeed -= carSpeedIncrement * brakesStrength * delta;
	}

	private int hp = 100; // get
	private boolean gotHitTimeSet;
	private long hitNewTime;
	private final long hitCd = 420L;

	@Override
	public void onHit(Object object, float delta) {
		if (object instanceof Car) {
			System.err.println("Hit a car!");
		} else if (object instanceof Curb) {
			// limit speed here?

			curbsBlink = true;

			if (!gotHitTimeSet) {
				hp = hp - 5;
				hitNewTime = screen.getCurrentTime() + hitCd;
//				System.err.println("Car damage taken!");
				gotHitTimeSet = true;
			}
		} else if (object instanceof Edge) {
//			System.out.println("Car edge hit!");
		}
	}

	private long bounceXTotalTime;
	private long bounceZTotalTime;
	private boolean bounceTimerXSet;
	private boolean bounceTimerZSet;

	private final Vector3 camTempPos = new Vector3();

	@Override
	public void tick(float delta) {
		resetData();

		updateCurbsBlink();
		updateGotHitTimeSet();

		updateBounceX(delta);
		updateBounceZ(delta);

		clampAngle(delta);
		clampSpeed();
		moveWithAngle(delta);

		checkForCollision(delta);

		if (this == screen.playerCar) {
			updateCamPos(delta);
		}

		checkRightOrLeftTurn();

		if (this == screen.playerCar) {
			moveBackgroundsWithTurn(delta);

			updateCamLastPosition();
		}

//		System.out.println(position.cpy().sub(screen.carTwo.position.cpy()));
//		System.out.println("Speed: " + carCurrentSpeed);

//		if (this == screen.carTwo) {
//			System.err.println("carTwoDistFromCam: " +  distFromCam);
//		}
//		else {
//			System.out.println("playerCarDistFromCam: " + distFromCam);
//		}
	}

	private void updateGotHitTimeSet() {
		if (gotHitTimeSet) {
			if (screen.getCurrentTime() > hitNewTime) {
				gotHitTimeSet = false;
			}
		}
	}

	private boolean curbsBlink;
	private final long blinkCd = 100L;
	private long nextBlinkTime;
	private boolean nextBlinkTimeSet;

	private void updateCurbsBlink() {
		if (curbsBlink) {
			if (!nextBlinkTimeSet) {
				screen.getCurrentMap().setBlink(!screen.getCurrentMap().isBlink());
				nextBlinkTime = screen.getCurrentTime() + blinkCd;
				nextBlinkTimeSet = true;
			} else {
				if (screen.getCurrentTime() > nextBlinkTime) {
					curbsBlink = false;
					nextBlinkTimeSet = false;
				}
			}
		} else {
			nextBlinkTimeSet = false;
			screen.getCurrentMap().setBlink(false);
		}
	}

	private float currentBounceX;
	private float currentBounceZ;
	private final int bounceDecrement = 10;
	private boolean bounceX;
	private boolean bounceZ;
	private final long bounceTime = 175L; // time until bounce released.
	private final int bounceAmount = 20;

	private void updateBounceX(float delta) {
		if (bounceX) {
			if (!bounceTimerXSet) {
				bounceXTotalTime = screen.getCurrentTime() + bounceTime;
				currentBounceX = bounceAmount;
				carCurrentSpeed = carCurrentSpeed / 2;
				bounceTimerXSet = true;
			} else {

				if (screen.getCurrentTime() > bounceXTotalTime) {
					currentBounceX = 0;
//					System.err.println("Bouncing X gone!");
					bounceX = false;
				} else {
//					System.out.println("Bouncing X");
					currentBounceX -= bounceDecrement * delta;
				}
			}
		} else {
			bounceTimerXSet = false;
		}
	}

	private void updateBounceZ(float delta) {
		if (bounceZ) {
			if (!bounceTimerZSet) {
				bounceZTotalTime = screen.getCurrentTime() + bounceTime;
				currentBounceZ = bounceAmount;
				carCurrentSpeed = carCurrentSpeed / 2;
				bounceTimerZSet = true;
			} else {
				if (screen.getCurrentTime() > bounceZTotalTime) {
					currentBounceZ = 0;
//					System.err.println("Bouncing Z gone!");
					bounceZ = false;
				} else {
//					System.out.println("Bouncing Z");
					currentBounceZ -= bounceDecrement * delta;
				}
			}
		} else {
			bounceTimerZSet = false;
		}
	}

	private boolean overlapX;
	private boolean overlapY;

	private void resetData() {
		overlapX = false;
		overlapY = false;
	}

	/**
	 * Clamp angle.
	 * 
	 * @param delta
	 */
	private void clampAngle(float delta) {
		if (angle > 360 * MathUtils.degreesToRadians) {
			final float newAngle = angle - (360 * MathUtils.degreesToRadians);
			angle = newAngle;
		} else if (angle < -360 * MathUtils.degreesToRadians) {
			final float newAngle = angle + (360 * MathUtils.degreesToRadians);
			angle = newAngle;
		}
	}

	private final Vector3 lastPosition = new Vector3();

	/**
	 * Updates camera last position.
	 */
	private void updateCamLastPosition() {
		lastPosition.set(position.cpy());
	}

	/**
	 * Move bg's depending on turn direction.
	 * 
	 * @param delta
	 */
	private void moveBackgroundsWithTurn(float delta) {
		if (rightTurn) {
			screen.getCurrentMap().setBgFrontPosX(
					screen.getCurrentMap().getBgFrontPosX() + screen.bgMoveSpeed * screen.bgMoveSpeedBoost * delta);
			screen.getCurrentMap().setBgBackPosX(screen.getCurrentMap().getBgBackPosX() + screen.bgMoveSpeed * delta);
		} else if (leftTurn) {
			screen.getCurrentMap().setBgFrontPosX(
					screen.getCurrentMap().getBgFrontPosX() - screen.bgMoveSpeed * screen.bgMoveSpeedBoost * delta);
			screen.getCurrentMap().setBgBackPosX(screen.getCurrentMap().getBgBackPosX() - screen.bgMoveSpeed * delta);
		}
	}

	/**
	 * Can only turn one direction...
	 */
	private void checkRightOrLeftTurn() {
		if (rightTurn && leftTurn) {
			rightTurn = false;
			leftTurn = false;
		}
	}

	private final float carMaximumSpeed = 1.0f;

	/**
	 * Limit car speed.
	 */
	private void clampSpeed() {
		if (carCurrentSpeed > carMaximumSpeed)
			carCurrentSpeed = carMaximumSpeed;
		else if (carCurrentSpeed < 0)
			carCurrentSpeed = 0;
	}

	private float newPosX;
	private float newPosZ;

	private final int carSpeedIncrementBoost = 40; // was 32

	/**
	 * Move with speed in angle direction.
	 * 
	 * @param delta
	 */
	private void moveWithAngle(float delta) {
//		if (carCurrentSpeed > 0) {
		if (!bounceX) {
			newPosX += carCurrentSpeed * carSpeedIncrementBoost * delta * MathUtils.sin(angle);
		} else {
			newPosX -= carCurrentSpeed * currentBounceX * delta * MathUtils.sin(angle);
		}

		if (!bounceZ) {
			newPosZ -= carCurrentSpeed * carSpeedIncrementBoost * delta * MathUtils.cos(angle);
		} else {
			newPosZ += carCurrentSpeed * currentBounceZ * delta * MathUtils.cos(angle);
		}
//		}
	}

	/**
	 * Check for collisions on X and Y.
	 * 
	 * @param delta
	 */
	private void checkForCollision(float delta) {
//		Check collisions X
		rect.setX(newPosX - rectSize / 2);

		for (Entity ent : screen.getEntities()) {
			if (ent.rect != null) {
				if (ent.rect.overlaps(this.rect)) {
					if (ent.rect != this.rect) {
						onHit(ent, delta);
						if (ent instanceof Edge || ent instanceof Car) {
							overlapX = true;
						}
					}
				}
			}
		}

		if (overlapX) {
			newPosX = position.x; // go back
			bounceX = true;
		} else {
			position.x = newPosX;
		}

		rect.setX(position.x - rectSize / 2);

//		Check collisions Y
		rect.setY(newPosZ - rectSize / 2);

		for (Entity ent : screen.getEntities()) {
			if (ent.rect != null) {
				if (ent.rect.overlaps(this.rect)) {
					if (ent.rect != this.rect) {
						onHit(ent, delta);
						if (ent instanceof Edge || ent instanceof Car) {
							overlapY = true;
						}
					}
				}
			}
		}

		if (overlapY) {
			newPosZ = position.z; // go back
			bounceZ = true;
		} else {
			position.z = newPosZ;
		}

		rect.setY(position.z - rectSize / 2);
	}

	/**
	 * Updates camera positon and rotation.
	 * 
	 * @param delta
	 */
	private void updateCamPos(float delta) {
		if (!lastPosition.idt(position)) {
			screen.getCam().position.set(position.cpy());
//			screen.cam.position.sub(position.cpy().sub(oldPosition.cpy()).nor().scl(screen.camDesiredDistFromCar)); // old one, works but not too good in rotations.
			camTempPos.x = -carCurrentSpeed * carSpeedIncrementBoost * delta * MathUtils.sin(angle);
			camTempPos.z = carCurrentSpeed * carSpeedIncrementBoost * delta * MathUtils.cos(angle);
			screen.getCam().position.add(camTempPos.cpy().nor().scl(screen.camDesiredDistFromCar));

			screen.getCam().lookAt(position.cpy());
		}
	}

	private boolean render = true;
	private final int renderHeightLimit = 186 + 16; // Screen height limit for rendering when in window scale of 1. This
	private final int renderWidthLimit = 299;
	// is a
	// fix for cars that are behind camera, they might render in the
	// background
	// above player.
	private int currentRenderHeightLimit; // screen position varies by window height
	private int currentRenderWidthLimit; // screen position varies by window height

	private float distFromCam;
	private final int distChangeSprite = 7;
	private float angleFromCam;

	private SpriteSize spriteSize;

	@Override
	public void render2D(SpriteBatch batch, float delta) {
		render = true;

		// TODO: This doesnt have to be set for every sprite/object! Only ince in
		// gamescreen tick/render.
		currentRenderHeightLimit = renderHeightLimit * Gdx.graphics.getHeight() / screen.viewportHeight;
		currentRenderWidthLimit = renderWidthLimit * Gdx.graphics.getWidth() / screen.viewportWidthStretched;

		screenPos.set(screen.getViewport().project(position.cpy()));
		screenPos.z = 0;

		distFromCam = Vector3.dst(position.x, position.y, position.z, screen.getCam().position.x,
				screen.getCam().position.y, screen.getCam().position.z);

		angleFromCam = (screen.getCam().direction.cpy().dot(position.cpy()) * MathUtils.PI);
		
		if (distFromCam < distChangeSprite) {
			spriteSize = SpriteSize.ONE;
		} else if (distFromCam < distChangeSprite * SpriteSize.TWO.getSize()) {
			spriteSize = SpriteSize.TWO;
		} else if (distFromCam < distChangeSprite * SpriteSize.THREE.getSize()) {
			spriteSize = SpriteSize.THREE;
		} else if (distFromCam < distChangeSprite * SpriteSize.FOUR.getSize()) {
			spriteSize = SpriteSize.FOUR;
		} else if (distFromCam < distChangeSprite * SpriteSize.FIVE.getSize()) {
			spriteSize = SpriteSize.FIVE;
		} else if (distFromCam < distChangeSprite * SpriteSize.SIX.getSize()) {
			spriteSize = SpriteSize.SIX;
		} else if (distFromCam < distChangeSprite * SpriteSize.SEVEN.getSize()) {
			spriteSize = SpriteSize.SEVEN;
		} else if (distFromCam < distChangeSprite * SpriteSize.EIGHT.getSize()) {
			spriteSize = SpriteSize.EIGHT;
		} else if (distFromCam < distChangeSprite * SpriteSize.NINE.getSize()) {
			spriteSize = SpriteSize.NINE;
		} else {
			render = false;
		}
		
		switch (spriteSize) {
		case ONE:
			if (leftTurn) {
				sprite.setTexture(texCar01BigBackTurnRight02);
				sprite.setFlip(true, false);
			} else if (rightTurn) {
				sprite.setTexture(texCar01BigBackTurnRight02);
				sprite.setFlip(false, false);
			} else {
				sprite.setTexture(texCar01Size09Back01);
			}
			break;
		case TWO:
			sprite.setTexture(texCar01Size08Back01);
			break;
		case THREE:
			sprite.setTexture(texCar01Size07Back01);
			break;
		case FOUR:
			sprite.setTexture(texCar01Size06Back01);
			break;
		case FIVE:
			sprite.setTexture(texCar01Size05Back01);
			break;
		case SIX:
			sprite.setTexture(texCar01Size04Back01);
			break;
		case SEVEN:
			sprite.setTexture(texCar01Size03Back01);
			break;
		case EIGHT:
			sprite.setTexture(texCar01Size02Back01);
			break;
		case NINE:
			sprite.setTexture(texCar01Size01Back01);
			break;
		}

//		if (this == screen.carTwo) {
//			System.out.println(angleFromCam);
//		}

		

		sprite.setSize(
				MathUtils.round(
						sprite.getTexture().getWidth() * (Gdx.graphics.getWidth() / screen.viewportWidthStretched)
								* ((float) screen.viewportWidthStretched / screen.viewportWidth)),
				MathUtils.round(sprite.getTexture().getHeight() * (Gdx.graphics.getHeight() / screen.viewportHeight)));

		sprite.setX(MathUtils.round(screenPos.x - (sprite.getWidth() / 2))); // set size before this to avoid jitter on
																				// sprite texturechange.
		sprite.setY(MathUtils.round(screenPos.y - (sprite.getHeight() / 2)));

//		if (!overlapX)
//			rect.setX(position.x - rectSize / 2);

//		if (!overlapY)
//			rect.setY(position.z - rectSize / 2);

//		if (this == screen.carTwo) {
////			System.out.println(distFromCam + " units from cam.");
//			System.out.println(screen.carTwo.screenPos.x);
//		}

		if (screenPos.x < 0 - (sprite.getWidth() / 2) || screenPos.x > currentRenderWidthLimit + (sprite.getWidth() / 2)
				|| screenPos.y < 0 - (sprite.getHeight() / 2) || screenPos.y > currentRenderHeightLimit) {
			render = false;
		}

		if (render) {
			screen.sprites.add(sprite);
		}

//		reset stuff
		rightTurn = false;
		leftTurn = false;
	}

	@Override
	public void render3D(ModelBatch batch, float delta) {
	}

	@Override
	public void destroy() {
	}

}
