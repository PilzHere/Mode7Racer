package mysko.pilzhere.mode7racer.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
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

//	private final boolean isLocalPlayer;
	private final boolean isCPU;

	private final float rectSize = 0.25f;

	private Sprite sprite;
	
	private boolean inAir;
	
	private final int maxTurbos = 3;
	public int currentTurbos = maxTurbos; // get
	public boolean hasTurbo; // get

	public Car(GameScreen screen, Vector3 position, boolean isLocalPlayer, boolean isCPU) {
		super(screen, position);
//		this.isLocalPlayer = isLocalPlayer;
		this.isCPU = isCPU;

		getTextures();

		sprite = new Sprite(texCar01Size09Back01);
		rect = new Rectangle(position.x - rectSize / 2, position.z - rectSize / 2, rectSize, rectSize);
	}

	private Texture texCar01Size09Back01, texCar01Size08Back01, texCar01Size07Back01, texCar01Size06Back01,
			texCar01Size05Back01, texCar01Size04Back01, texCar01Size03Back01, texCar01Size02Back01,
			texCar01Size01Back01;

	private Texture texCar01BigBackTurnRight02;

	private Texture texCar01Size09BackLeft01, texCar01Size08BackLeft01, texCar01Size07BackLeft01,
			texCar01Size06BackLeft01, texCar01Size05BackLeft01, texCar01Size04BackLeft01, texCar01Size03BackLeft01, texCar01Size02BackLeft01, texCar01Size01BackLeft01;

	private Texture texCar01Size09Left01, texCar01Size08Left01, texCar01Size07Left01, texCar01Size06Left01,
			texCar01Size05Left01, texCar01Size04Left01, texCar01Size03Left01, texCar01Size02Left01, texCar01Size01Left01;

	private Texture texCar01Size09Front01, texCar01Size08Front01, texCar01Size07Front01, texCar01Size06Front01,
			texCar01Size05Front01, texCar01Size04Front01, texCar01Size03Front01, texCar01Size02Front01, texCar01Size01Front01;

	private Texture texCar01Size09FrontLeft01, texCar01Size08FrontLeft01, texCar01Size07FrontLeft01,
			texCar01Size06FrontLeft01, texCar01Size05FrontLeft01, texCar01Size04FrontLeft01, texCar01Size03FrontLeft01, texCar01Size02FrontLeft01, texCar01Size01FrontLeft01;

	private void getTextures() {
//		Back
		texCar01Size09Back01 = screen.assMan.get("car01Size09Back01.png");
		texCar01Size08Back01 = screen.assMan.get("car01Size08Back01.png");
		texCar01Size07Back01 = screen.assMan.get("car01Size07Back01.png");
		texCar01Size06Back01 = screen.assMan.get("car01Size06Back01.png");
		texCar01Size05Back01 = screen.assMan.get("car01Size05Back01.png");
		texCar01Size04Back01 = screen.assMan.get("car01Size04Back01.png");
		texCar01Size03Back01 = screen.assMan.get("car01Size03Back01.png");
		texCar01Size02Back01 = screen.assMan.get("car01Size02Back01.png");
		texCar01Size01Back01 = screen.assMan.get("car01Size01Back01.png");

//		Back - Turn
		texCar01BigBackTurnRight02 = screen.assMan.get("car01Size09BackTurnRight02.png");

//		BackLeft
		texCar01Size09BackLeft01 = screen.assMan.get("car01Size09BackLeft01.png");
		texCar01Size08BackLeft01 = screen.assMan.get("car01Size08BackLeft01.png");
		texCar01Size07BackLeft01 = screen.assMan.get("car01Size07BackLeft01.png");
		texCar01Size06BackLeft01 = screen.assMan.get("car01Size06BackLeft01.png");
		texCar01Size05BackLeft01 = screen.assMan.get("car01Size05BackLeft01.png");
		texCar01Size04BackLeft01 = screen.assMan.get("car01Size04BackLeft01.png");
		texCar01Size03BackLeft01 = screen.assMan.get("car01Size03BackLeft01.png");
		texCar01Size02BackLeft01 = screen.assMan.get("car01Size02BackLeft01.png");
		texCar01Size01BackLeft01 = screen.assMan.get("car01Size01BackLeft01.png");

//		Left
		texCar01Size09Left01 = screen.assMan.get("car01Size09Left01.png");
		texCar01Size08Left01 = screen.assMan.get("car01Size08Left01.png");
		texCar01Size07Left01 = screen.assMan.get("car01Size07Left01.png");
		texCar01Size06Left01 = screen.assMan.get("car01Size06Left01.png");
		texCar01Size05Left01 = screen.assMan.get("car01Size05Left01.png");
		texCar01Size04Left01 = screen.assMan.get("car01Size04Left01.png");
		texCar01Size03Left01 = screen.assMan.get("car01Size03Left01.png");
		texCar01Size02Left01 = screen.assMan.get("car01Size02Left01.png");
		texCar01Size01Left01 = screen.assMan.get("car01Size01Left01.png");

//		Front
		texCar01Size09Front01 = screen.assMan.get("car01Size09Front01.png");
		texCar01Size08Front01 = screen.assMan.get("car01Size08Front01.png");
		texCar01Size07Front01 = screen.assMan.get("car01Size07Front01.png");
		texCar01Size06Front01 = screen.assMan.get("car01Size06Front01.png");
		texCar01Size05Front01 = screen.assMan.get("car01Size05Front01.png");
		texCar01Size04Front01 = screen.assMan.get("car01Size04Front01.png");
		texCar01Size03Front01 = screen.assMan.get("car01Size03Front01.png");
		texCar01Size02Front01 = screen.assMan.get("car01Size02Front01.png");
		texCar01Size01Front01 = screen.assMan.get("car01Size01Front01.png");

//		FrontLeft
		texCar01Size09FrontLeft01 = screen.assMan.get("car01Size09FrontLeft01.png");
		texCar01Size08FrontLeft01 = screen.assMan.get("car01Size08FrontLeft01.png");
		texCar01Size07FrontLeft01 = screen.assMan.get("car01Size07FrontLeft01.png");
		texCar01Size06FrontLeft01 = screen.assMan.get("car01Size06FrontLeft01.png");
		texCar01Size05FrontLeft01 = screen.assMan.get("car01Size05FrontLeft01.png");
		texCar01Size04FrontLeft01 = screen.assMan.get("car01Size04FrontLeft01.png");
		texCar01Size03FrontLeft01 = screen.assMan.get("car01Size03FrontLeft01.png");
		texCar01Size02FrontLeft01 = screen.assMan.get("car01Size02FrontLeft01.png");
		texCar01Size01FrontLeft01 = screen.assMan.get("car01Size01FrontLeft01.png");
	}

	public float angle = 0; // get?
	private final float angleIncrement = 2.5f;
	public float carCurrentSpeed; // get
	private float toKMpH = 440f;
	public float carCurrentSpeedKMpH;
	private boolean rightTurn; // For moving bg's.
	private boolean leftTurn;
	private final float minTurnAmount = 0.55f;
	private final float maxTurnAmount = 1.0f;
	private float currentTurnAmount = minTurnAmount;

	public void onInputA(float delta) {
		if (carCurrentSpeed > 0) {
			if (carCurrentSpeed < minTurnAmount) {
				currentTurnAmount = angleIncrement * minTurnAmount * delta;
				angle -= currentTurnAmount;
			} else {
				if (carCurrentSpeed > maxTurnAmount) {
					currentTurnAmount = angleIncrement * maxTurnAmount * delta;
					angle -= currentTurnAmount;
				} else {
					currentTurnAmount = angleIncrement * carCurrentSpeed * delta;
					angle -= currentTurnAmount;
				}
			}
			
			if (!isCPU) // TESTING SPRITES
				leftTurn = true;
			
//			set currentTurnAmount negative for left turns, dont use this for car after.
			currentTurnAmount = -currentTurnAmount;
		}
	}

	public void onInputD(float delta) {
		if (carCurrentSpeed > 0) {
			if (carCurrentSpeed < minTurnAmount) {
				currentTurnAmount = angleIncrement * minTurnAmount * delta;
				angle += currentTurnAmount;
			} else {
				if (carCurrentSpeed > maxTurnAmount) {
					currentTurnAmount = angleIncrement * maxTurnAmount * delta;
					angle += currentTurnAmount;
				} else {
					currentTurnAmount = angleIncrement * carCurrentSpeed * delta;
					angle += currentTurnAmount;
				}
			}
			
			if (!isCPU) // TESTING SPRITES
				rightTurn = true;
		}
	}
	
	private boolean shiftLeft;
	public void onInputShiftLeft(float delta) {
		shiftLeft = true;
	}
	
	private boolean shiftRight;
	public void onInputShiftRight(float delta) {
		shiftRight = true;
	}

	private final float carTurboSpeedIncrement = 0.5f;
	private final float carNormalSpeedIncrement = 0.33f; // was 2 should slow it
	private float carCurrentSpeedIncrement = carNormalSpeedIncrement;
	private final float motorBrakeStrength = 0.35f;
	private final int bounceBrakesStrength = 16;

	private final long turboCd = 2250L;
	private boolean newTurboCdSet;
	private long newTurboCdTime;
	
	public void onInputT(float delta) {
		if (carCurrentSpeed > 0) {
			if (!newTurboCdSet) {
				if (currentTurbos > 0) {
					hasTurbo = true;
					currentTurbos--;
				}
			}
		}
	}
	
	public void onNoInputW(float delta) {
		if (bounceX || bounceZ)
			carCurrentSpeed -= carCurrentSpeedIncrement * bounceBrakesStrength * motorBrakeStrength * delta; // Bounce brake.
		else {
			carCurrentSpeed -= carCurrentSpeedIncrement * motorBrakeStrength * delta; // Motor brake.
		}
	}
	
	public void onInputW(float delta) {
		carCurrentSpeed += carCurrentSpeedIncrement * delta;
	}

	private final float brakesStrength = 0.33f;

	public void onInputS(float delta) {
		carCurrentSpeed -= carCurrentSpeedIncrement * brakesStrength * delta;
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

	public void moveUp(float delta) {
		position.y += 1 * delta;
	}
	
	private long bounceXTotalTime;
	private long bounceZTotalTime;
	private boolean bounceTimerXSet;
	private boolean bounceTimerZSet;

	private final Vector3 camTempPos = new Vector3();

	@Override
	public void tick(float delta) {
		resetData();
		
		if (isCPU) {
			onInputA(delta); // turn left
//			onInputD(delta); // turn right
			onInputW(delta); // pedal to the metal
		}
		
		setCurrentSpeedAndSpeedLimit(delta);
		updateTurboCooldown();

		if (this == screen.playerCar)
			updateCurbsBlink();
		updateGotHitTimeSet();

		updateBounceX(delta);
		updateBounceZ(delta);

		clampAngle(delta);
		clampSpeed(delta);
		moveWithAngle(delta);

		checkForCollision(delta);

		if (this == screen.playerCar) {
			updateCamPos(delta);
		}

		checkRightOrLeftTurn();

		
		if (this == screen.playerCar) {
			moveBackgroundsWithTurn(delta);
		}
		
		updateLastPosition();
		
		carCurrentSpeedKMpH = carCurrentSpeed * toKMpH;
	}

	private void updateTurboCooldown() {
		if (hasTurbo) {
			if (newTurboCdSet) {
				if (screen.getCurrentTime() > newTurboCdTime) {
					newTurboCdSet = false;
					hasTurbo = false;
				}
			}
		}
	}
	
	private void setCurrentSpeedAndSpeedLimit(float delta) {
		if (hasTurbo) {
			carCurrentMaximumSpeed = carTurboMaximumSpeed;
			carCurrentSpeedIncrement = carTurboSpeedIncrement;
			
			if (!newTurboCdSet) {
				newTurboCdTime = screen.getCurrentTime() + turboCd;
				newTurboCdSet = true;
			}
		} else {
			carCurrentMaximumSpeed = carNormalMaximumSpeed;			
			carCurrentSpeedIncrement = carNormalSpeedIncrement;
		}
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
	private void updateLastPosition() {
		lastPosition.set(position.cpy());
	}

	/**
	 * Move bg's depending on turn direction.
	 * 
	 * @param delta
	 */
	
	private void moveBackgroundsWithTurn(float delta) {
//		Not perfect but works...
		screen.getCurrentMap().setBgFrontPosX(
				screen.getCurrentMap().getBgFrontPosX() + currentTurnAmount * 100 * 128 * delta);
		screen.getCurrentMap().setBgBackPosX(screen.getCurrentMap().getBgBackPosX() + currentTurnAmount * 100 * 64 * delta);
		
//		Old
//		if (rightTurn) {
//			screen.getCurrentMap().setBgFrontPosX(
//					screen.getCurrentMap().getBgFrontPosX() + screen.bgMoveSpeed * screen.bgMoveSpeedBoost * delta);
//			screen.getCurrentMap().setBgBackPosX(screen.getCurrentMap().getBgBackPosX() + screen.bgMoveSpeed * delta);
//			
//		} else if (leftTurn) {
//			screen.getCurrentMap().setBgFrontPosX(
//					screen.getCurrentMap().getBgFrontPosX() - screen.bgMoveSpeed * screen.bgMoveSpeedBoost * delta);
//			screen.getCurrentMap().setBgBackPosX(screen.getCurrentMap().getBgBackPosX() - screen.bgMoveSpeed * delta);
//		}
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

	private final float carNormalMaximumSpeed = 1.0f;
	private final float carTurboMaximumSpeed = 1.5f;
	public float carCurrentMaximumSpeed = carNormalMaximumSpeed; // get
	
//	private boolean breakFromTurbo;
	
	/**
	 * Limit car speed.
	 */
	private void clampSpeed(float delta) {
//		breakFromTurbo = false;
		
//		TODO: this code deosnt work properly, change it or do we even need it? (deaccelerate after turbo).
//		if (!hasTurbo) {
//			if (carCurrentSpeed > carCurrentMaximumSpeed) {
//				System.err.println("Speed is above normal max speed! Brakeing!");
//				carCurrentSpeed -= carCurrentSpeedIncrement * bounceBrakesStrength * motorBrakeStrength * delta; // Bounce brake.
//				breakFromTurbo = true;
//			}
//		} else {
//			if (!breakFromTurbo)
//				carCurrentSpeed = carCurrentMaximumSpeed;
//		}
		
		
		if (carCurrentSpeed > carCurrentMaximumSpeed) {
//			if (!breakFromTurbo) {
				carCurrentSpeed = carCurrentMaximumSpeed;
//			}
		} else if (carCurrentSpeed < 0) {
			carCurrentSpeed = 0;
		}
	}

	private float newPosX;
	private float newPosZ;

	private final int carSpeedIncrementBoost = 40; // was 32
	
	private float sinAngle = angle;
	private float cosAngle = angle;
	
	/**
	 * Move with speed in angle direction.
	 * 
	 * @param delta
	 */
	private void moveWithAngle(float delta) {
//		if (carCurrentSpeed > 0) {
		
		sinAngle = angle;
		cosAngle = angle;
		
//		Add shift here?
		if (shiftLeft) {
			System.err.println("SHIFT LEFT");
			sinAngle -= 45 * MathUtils.degreesToRadians;
			cosAngle -= 45 * MathUtils.degreesToRadians;
		} else if (shiftRight) {
			System.err.println("SHIFT Right");
			sinAngle += 45 * MathUtils.degreesToRadians;
			cosAngle += 45 * MathUtils.degreesToRadians;
		}
		
		
		
		if (!bounceX) {
			newPosX += carCurrentSpeed * carSpeedIncrementBoost * MathUtils.sin(sinAngle) * delta;
		} else {
			newPosX -= carCurrentSpeed * currentBounceX * MathUtils.sin(sinAngle) * delta;
		}
		
//		if (sinAngle != angle)
//			newPosX += 20 * MathUtils.sin(sinAngle) * delta;
		

		if (!bounceZ) {
			newPosZ -= carCurrentSpeed * carSpeedIncrementBoost * MathUtils.cos(cosAngle) * delta;
		} else {
			newPosZ += carCurrentSpeed * currentBounceZ * MathUtils.cos(cosAngle) * delta;
		}
		
//		if (cosAngle != angle)
//			newPosZ -= 20 * MathUtils.cos(cosAngle) * delta;
		
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
						if (ent instanceof Edge) {
							overlapX = true;
						}
					}
				}
			}
		}
		
		for (Car car : screen.cars.values()) {
			if (car.rect != null) {
				if (car.rect.overlaps(this.rect)) {
					if (car.rect != this.rect) {
						onHit(car, delta);
						overlapX = true;
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
						if (ent instanceof Edge) {
							overlapY = true;
						}
					}
				}
			}
		}
		
		for (Car car : screen.cars.values()) {
			if (car.rect != null) {
				if (car.rect.overlaps(this.rect)) {
					if (car.rect != this.rect) {
						onHit(car, delta);
						overlapY = true;
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
		if (!lastPosition.idt(position.cpy())) {
			screen.getCam().position.set(position.cpy());
//			screen.cam.position.sub(position.cpy().sub(oldPosition.cpy()).nor().scl(screen.camDesiredDistFromCar)); // old one, works but not too good in rotations.
			camTempPos.x = -carCurrentSpeed * carSpeedIncrementBoost * delta * MathUtils.sin(angle);
			camTempPos.z = carCurrentSpeed * carSpeedIncrementBoost * delta * MathUtils.cos(angle);
			screen.getCam().position.add(camTempPos.cpy().nor().scl(screen.camDesiredDistFromCar));

			screen.getCam().lookAt(position.cpy());
		}
	}

	private boolean render = true;
	private float distFromCam;
	private final float distChangeSprite = 4.4f;
	private boolean updateSpriteSize = true;
	private float angleFromPlayerCar;

	private SpriteSize spriteSize = SpriteSize.NINE;
	private SpriteDirection spriteDirection = SpriteDirection.UP;

	private boolean flipSpriteX;

	@Override
	public void render2D(SpriteBatch batch, float delta) {
		render = true;
		flipSpriteX = false;

		screenPos.set(screen.getViewport().project(position.cpy()));
		screenPos.z = 0;

		distFromCam = Vector3.dst(position.x, position.y, position.z, screen.getCam().position.x,
				screen.getCam().position.y, screen.getCam().position.z);

//		Get angle from playe car. Will this be correct if camera leaves playercar? // or just spawn hidden car that cam follows? ;)
		angleFromPlayerCar = screen.cars.get(0).angle - angle; // compare with playercars angle.

//		FIXME: THIS IS NOT NEEDED IT SEEMS? Was used before flipping sprite...<
//		if (screen.cars.get(0).angle < 0) {
//			Displaying car's sprite according to car's rotation; angleFromPlayerCar needs to be positive.
//			angleFromPlayerCar = angleFromPlayerCar + 360 * MathUtils.degreesToRadians;
//		}
//		>

		if (angleFromPlayerCar > 360 * MathUtils.degreesToRadians) {
			final float newAngle = angleFromPlayerCar - (360 * MathUtils.degreesToRadians);
			angleFromPlayerCar = newAngle;
		} else if (angleFromPlayerCar < -360 * MathUtils.degreesToRadians) {
			final float newAngle = angleFromPlayerCar + (360 * MathUtils.degreesToRadians);
			angleFromPlayerCar = newAngle;
		}

		calculateSpriteDirection(angleFromPlayerCar);
		if (updateSpriteSize)
			setSpriteSizeWithDistanceFromCam(distFromCam, distChangeSprite);
		else
			spriteSize = SpriteSize.ONE;
		setSpriteTextureWithSizeAndDirection(spriteSize, spriteDirection, flipSpriteX);		

//		temp?
		if (this == screen.playerCar) {
			if (leftTurn) {
				sprite.setTexture(texCar01BigBackTurnRight02);
				sprite.setFlip(true, false);
			} else if (rightTurn) {
				sprite.setTexture(texCar01BigBackTurnRight02);
				sprite.setFlip(false, false);
			} else {
				sprite.setTexture(texCar01Size09Back01);
			}
		}	

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
		
		render = screen.spriteFitsInScreen(screenPos.cpy(), sprite);

		if (render) {
			screen.sprites.add(sprite);
		}
//		else {
//			if (this != screen.playerCar) {
//				System.err.println("Not rendering other car! Y: " + screenPos.y);
//			}
//		}

//		reset stuff
		currentTurnAmount = 0;
		rightTurn = false;
		leftTurn = false;
		shiftLeft = false;
		shiftRight = false;
	}

	/**
	 * Calculate sprite direction by the angle to player car.
	 * @param angleFromPlayerCar
	 */
	private void calculateSpriteDirection(float angleFromPlayerCar) {
		if (angleFromPlayerCar > 0) {
//			System.err.println("POSITIVE");
			if (angleFromPlayerCar < 22.5f * MathUtils.degreesToRadians) {
				spriteDirection = SpriteDirection.UP;
			} else if (angleFromPlayerCar < 67.5f * MathUtils.degreesToRadians) {
				spriteDirection = SpriteDirection.UPLEFT;
			} else if (angleFromPlayerCar < 112.5f * MathUtils.degreesToRadians) {
				spriteDirection = SpriteDirection.LEFT;
			} else if (angleFromPlayerCar < 157.5f * MathUtils.degreesToRadians) {
				spriteDirection = SpriteDirection.DOWNLEFT;
			} else if (angleFromPlayerCar < 202.5f * MathUtils.degreesToRadians) {
				spriteDirection = SpriteDirection.DOWN;
			} else if (angleFromPlayerCar < 247.5f * MathUtils.degreesToRadians) {
				spriteDirection = SpriteDirection.DOWNRIGHT;
			} else if (angleFromPlayerCar < 292.5f * MathUtils.degreesToRadians) {
				spriteDirection = SpriteDirection.RIGHT;
			} else if (angleFromPlayerCar < 337.5f * MathUtils.degreesToRadians) {
				spriteDirection = SpriteDirection.UPRIGHT;
			} else { // more than 337.5f
				spriteDirection = SpriteDirection.UP;
			}
		} else {
//			System.err.println("NEGATIVE");
			flipSpriteX = true;
			if (angleFromPlayerCar > -22.5f * MathUtils.degreesToRadians) {
				spriteDirection = SpriteDirection.UP;
			} else if (angleFromPlayerCar > -67.5f * MathUtils.degreesToRadians) {
				spriteDirection = SpriteDirection.UPLEFT;
			} else if (angleFromPlayerCar > -112.5f * MathUtils.degreesToRadians) {
				spriteDirection = SpriteDirection.LEFT;
			} else if (angleFromPlayerCar > -157.5f * MathUtils.degreesToRadians) {
				spriteDirection = SpriteDirection.DOWNLEFT;
			} else if (angleFromPlayerCar > -202.5f * MathUtils.degreesToRadians) {
				spriteDirection = SpriteDirection.DOWN;
			} else if (angleFromPlayerCar > -247.5f * MathUtils.degreesToRadians) {
				spriteDirection = SpriteDirection.DOWNRIGHT;
			} else if (angleFromPlayerCar > -292.5f * MathUtils.degreesToRadians) {
				spriteDirection = SpriteDirection.RIGHT;
			} else if (angleFromPlayerCar > -337.5f * MathUtils.degreesToRadians) {
				spriteDirection = SpriteDirection.UPRIGHT;
			} else { // less than -337.5f
				spriteDirection = SpriteDirection.UP;
			}
		}
	}
	
	/**
	 * Update sprite size depending on distance from player camera.
	 * @param distance
	 * @param distanceMultiplier
	 */
	private void setSpriteSizeWithDistanceFromCam(float distance, float distanceMultiplier) {
		if (distance < distanceMultiplier) {
			spriteSize = SpriteSize.ONE;
		} else if (distance < distanceMultiplier * SpriteSize.TWO.getSize()) {
			spriteSize = SpriteSize.TWO;
		} else if (distance < distanceMultiplier * SpriteSize.THREE.getSize()) {
			spriteSize = SpriteSize.THREE;
		} else if (distance < distanceMultiplier * SpriteSize.FOUR.getSize()) {
			spriteSize = SpriteSize.FOUR;
		} else if (distance < distanceMultiplier * SpriteSize.FIVE.getSize()) {
			spriteSize = SpriteSize.FIVE;
		} else if (distance < distanceMultiplier * SpriteSize.SIX.getSize()) {
			spriteSize = SpriteSize.SIX;
		} else if (distance < distanceMultiplier * SpriteSize.SEVEN.getSize()) {
			spriteSize = SpriteSize.SEVEN;
		} else if (distance < distanceMultiplier * SpriteSize.EIGHT.getSize()) {
			spriteSize = SpriteSize.EIGHT;
		} else if (distance < distanceMultiplier * SpriteSize.NINE.getSize()) {
			spriteSize = SpriteSize.NINE;
		}
	}
	
	/**
	 * Upates sprite texture depending on size and direction.
	 * @param size
	 * @param dir
	 * @param flipX
	 */
	private void setSpriteTextureWithSizeAndDirection(SpriteSize size, SpriteDirection dir, boolean flipX) {
		switch (size) {
		case ONE:
			switch (dir) {
			case DOWN:
				sprite.setTexture(texCar01Size09Front01);
				sprite.setFlip(false, false);
				break;
			case DOWNLEFT:
				sprite.setTexture(texCar01Size09FrontLeft01);
				if (flipSpriteX)
					sprite.setFlip(true, false);
				else
					sprite.setFlip(false, false);
				break;
			case DOWNRIGHT:
				sprite.setTexture(texCar01Size09FrontLeft01);
				if (flipSpriteX)
					sprite.setFlip(false, false);
				else
					sprite.setFlip(true, false);
				break;
			case LEFT:
				sprite.setTexture(texCar01Size09Left01);
				if (flipSpriteX)
					sprite.setFlip(true, false);
				else
					sprite.setFlip(false, false);
				break;
			case RIGHT:
				sprite.setTexture(texCar01Size09Left01);
				if (flipSpriteX)
					sprite.setFlip(false, false);
				else
					sprite.setFlip(true, false);
				break;
			case UP:
				sprite.setTexture(texCar01Size09Back01);
				sprite.setFlip(false, false);
				break;
			case UPLEFT:
				sprite.setTexture(texCar01Size09BackLeft01);
				if (flipSpriteX)
					sprite.setFlip(true, false);
				else
					sprite.setFlip(false, false);
				break;
			case UPRIGHT:
				sprite.setTexture(texCar01Size09BackLeft01);
				if (flipSpriteX)
					sprite.setFlip(false, false);
				else
					sprite.setFlip(true, false);
				break;
			}
			break;
		case TWO:
			switch (dir) {
			case DOWN:
				sprite.setTexture(texCar01Size08Front01);
				sprite.setFlip(false, false);
				break;
			case DOWNLEFT:
				sprite.setTexture(texCar01Size08FrontLeft01);
				if (flipSpriteX)
					sprite.setFlip(true, false);
				else
					sprite.setFlip(false, false);
				break;
			case DOWNRIGHT:
				sprite.setTexture(texCar01Size08FrontLeft01);
				if (flipSpriteX)
					sprite.setFlip(false, false);
				else
					sprite.setFlip(true, false);
				break;
			case LEFT:
				sprite.setTexture(texCar01Size08Left01);
				if (flipSpriteX)
					sprite.setFlip(true, false);
				else
					sprite.setFlip(false, false);
				break;
			case RIGHT:
				sprite.setTexture(texCar01Size08Left01);
				if (flipSpriteX)
					sprite.setFlip(false, false);
				else
					sprite.setFlip(true, false);
				break;
			case UP:
				sprite.setTexture(texCar01Size08Back01);
				sprite.setFlip(false, false);
				break;
			case UPLEFT:
				sprite.setTexture(texCar01Size08BackLeft01);
				if (flipSpriteX)
					sprite.setFlip(true, false);
				else
					sprite.setFlip(false, false);
				break;
			case UPRIGHT:
				sprite.setTexture(texCar01Size08BackLeft01);
				if (flipSpriteX)
					sprite.setFlip(false, false);
				else
					sprite.setFlip(true, false);
				break;
			}
			break;
		case THREE:
			switch (dir) {
			case DOWN:
				sprite.setTexture(texCar01Size07Front01);
				sprite.setFlip(false, false);
				break;
			case DOWNLEFT:
				sprite.setTexture(texCar01Size07FrontLeft01);
				if (flipSpriteX)
					sprite.setFlip(true, false);
				else
					sprite.setFlip(false, false);
				break;
			case DOWNRIGHT:
				sprite.setTexture(texCar01Size07FrontLeft01);
				if (flipSpriteX)
					sprite.setFlip(false, false);
				else
					sprite.setFlip(true, false);
				break;
			case LEFT:
				sprite.setTexture(texCar01Size07Left01);
				if (flipSpriteX)
					sprite.setFlip(true, false);
				else
					sprite.setFlip(false, false);
				break;
			case RIGHT:
				sprite.setTexture(texCar01Size07Left01);
				if (flipSpriteX)
					sprite.setFlip(false, false);
				else
					sprite.setFlip(true, false);
				break;
			case UP:
				sprite.setTexture(texCar01Size07Back01);
				sprite.setFlip(false, false);
				break;
			case UPLEFT:
				sprite.setTexture(texCar01Size07BackLeft01);
				if (flipSpriteX)
					sprite.setFlip(true, false);
				else
					sprite.setFlip(false, false);
				break;
			case UPRIGHT:
				sprite.setTexture(texCar01Size07BackLeft01);
				if (flipSpriteX)
					sprite.setFlip(false, false);
				else
					sprite.setFlip(true, false);
				break;
			}
			break;
		case FOUR:
			switch (dir) {
			case DOWN:
				sprite.setTexture(texCar01Size06Front01);
				sprite.setFlip(false, false);
				break;
			case DOWNLEFT:
				sprite.setTexture(texCar01Size06FrontLeft01);
				if (flipSpriteX)
					sprite.setFlip(true, false);
				else
					sprite.setFlip(false, false);
				break;
			case DOWNRIGHT:
				sprite.setTexture(texCar01Size06FrontLeft01);
				if (flipSpriteX)
					sprite.setFlip(false, false);
				else
					sprite.setFlip(true, false);
				break;
			case LEFT:
				sprite.setTexture(texCar01Size06Left01);
				if (flipSpriteX)
					sprite.setFlip(true, false);
				else
					sprite.setFlip(false, false);
				break;
			case RIGHT:
				sprite.setTexture(texCar01Size06Left01);
				if (flipSpriteX)
					sprite.setFlip(false, false);
				else
					sprite.setFlip(true, false);
				break;
			case UP:
				sprite.setTexture(texCar01Size06Back01);
				sprite.setFlip(false, false);
				break;
			case UPLEFT:
				sprite.setTexture(texCar01Size06BackLeft01);
				if (flipSpriteX)
					sprite.setFlip(true, false);
				else
					sprite.setFlip(false, false);
				break;
			case UPRIGHT:
				sprite.setTexture(texCar01Size06BackLeft01);
				if (flipSpriteX)
					sprite.setFlip(false, false);
				else
					sprite.setFlip(true, false);
				break;
			}
			break;
		case FIVE:
			switch (dir) {
			case DOWN:
				sprite.setTexture(texCar01Size05Front01);
				sprite.setFlip(false, false);
				break;
			case DOWNLEFT:
				sprite.setTexture(texCar01Size05FrontLeft01);
				if (flipSpriteX)
					sprite.setFlip(true, false);
				else
					sprite.setFlip(false, false);
				break;
			case DOWNRIGHT:
				sprite.setTexture(texCar01Size05FrontLeft01);
				if (flipSpriteX)
					sprite.setFlip(false, false);
				else
					sprite.setFlip(true, false);
				break;
			case LEFT:
				sprite.setTexture(texCar01Size05Left01);
				if (flipSpriteX)
					sprite.setFlip(true, false);
				else
					sprite.setFlip(false, false);
				break;
			case RIGHT:
				sprite.setTexture(texCar01Size05Left01);
				if (flipSpriteX)
					sprite.setFlip(false, false);
				else
					sprite.setFlip(true, false);
				break;
			case UP:
				sprite.setTexture(texCar01Size05Back01);
				sprite.setFlip(false, false);
				break;
			case UPLEFT:
				sprite.setTexture(texCar01Size05BackLeft01);
				if (flipSpriteX)
					sprite.setFlip(true, false);
				else
					sprite.setFlip(false, false);
				break;
			case UPRIGHT:
				sprite.setTexture(texCar01Size05BackLeft01);
				if (flipSpriteX)
					sprite.setFlip(false, false);
				else
					sprite.setFlip(true, false);
				break;
			}
			break;
		case SIX:
			switch (dir) {
			case DOWN:
				sprite.setTexture(texCar01Size04Front01);
				sprite.setFlip(false, false);
				break;
			case DOWNLEFT:
				sprite.setTexture(texCar01Size04FrontLeft01);
				if (flipSpriteX)
					sprite.setFlip(true, false);
				else
					sprite.setFlip(false, false);
				break;
			case DOWNRIGHT:
				sprite.setTexture(texCar01Size04FrontLeft01);
				if (flipSpriteX)
					sprite.setFlip(false, false);
				else
					sprite.setFlip(true, false);
				break;
			case LEFT:
				sprite.setTexture(texCar01Size04Left01);
				if (flipSpriteX)
					sprite.setFlip(true, false);
				else
					sprite.setFlip(false, false);
				break;
			case RIGHT:
				sprite.setTexture(texCar01Size04Left01);
				if (flipSpriteX)
					sprite.setFlip(false, false);
				else
					sprite.setFlip(true, false);
				break;
			case UP:
				sprite.setTexture(texCar01Size04Back01);
				sprite.setFlip(false, false);
				break;
			case UPLEFT:
				sprite.setTexture(texCar01Size04BackLeft01);
				if (flipSpriteX)
					sprite.setFlip(true, false);
				else
					sprite.setFlip(false, false);
				break;
			case UPRIGHT:
				sprite.setTexture(texCar01Size04BackLeft01);
				if (flipSpriteX)
					sprite.setFlip(false, false);
				else
					sprite.setFlip(true, false);
				break;
			}
			break;
		case SEVEN:
			
//			TODO: THESE SPRITES DO NOT EXIST. MAYBE IMPLEMENT LATER?
			
			switch (dir) {
			case DOWN:
				sprite.setTexture(texCar01Size03Front01);
				sprite.setFlip(false, false);
				break;
			case DOWNLEFT:
				sprite.setTexture(texCar01Size03FrontLeft01);
				if (flipSpriteX)
					sprite.setFlip(true, false);
				else
					sprite.setFlip(false, false);
				break;
			case DOWNRIGHT:
				sprite.setTexture(texCar01Size03FrontLeft01);
				if (flipSpriteX)
					sprite.setFlip(false, false);
				else
					sprite.setFlip(true, false);
				break;
			case LEFT:
				sprite.setTexture(texCar01Size03Left01);
				if (flipSpriteX)
					sprite.setFlip(true, false);
				else
					sprite.setFlip(false, false);
				break;
			case RIGHT:
				sprite.setTexture(texCar01Size03Left01);
				if (flipSpriteX)
					sprite.setFlip(false, false);
				else
					sprite.setFlip(true, false);
				break;
			case UP:
				sprite.setTexture(texCar01Size03Back01);
				sprite.setFlip(false, false);
				break;
			case UPLEFT:
				sprite.setTexture(texCar01Size03BackLeft01);
				if (flipSpriteX)
					sprite.setFlip(true, false);
				else
					sprite.setFlip(false, false);
				break;
			case UPRIGHT:
				sprite.setTexture(texCar01Size03BackLeft01);
				if (flipSpriteX)
					sprite.setFlip(false, false);
				else
					sprite.setFlip(true, false);
				break;
			}
			break;
		case EIGHT:
			switch (dir) {
			case DOWN:
				sprite.setTexture(texCar01Size02Front01);
				sprite.setFlip(false, false);
				break;
			case DOWNLEFT:
				sprite.setTexture(texCar01Size02FrontLeft01);
				if (flipSpriteX)
					sprite.setFlip(true, false);
				else
					sprite.setFlip(false, false);
				break;
			case DOWNRIGHT:
				sprite.setTexture(texCar01Size02FrontLeft01);
				if (flipSpriteX)
					sprite.setFlip(false, false);
				else
					sprite.setFlip(true, false);
				break;
			case LEFT:
				sprite.setTexture(texCar01Size02Left01);
				if (flipSpriteX)
					sprite.setFlip(true, false);
				else
					sprite.setFlip(false, false);
				break;
			case RIGHT:
				sprite.setTexture(texCar01Size02Left01);
				if (flipSpriteX)
					sprite.setFlip(false, false);
				else
					sprite.setFlip(true, false);
				break;
			case UP:
				sprite.setTexture(texCar01Size02Back01);
				sprite.setFlip(false, false);
				break;
			case UPLEFT:
				sprite.setTexture(texCar01Size02BackLeft01);
				if (flipSpriteX)
					sprite.setFlip(true, false);
				else
					sprite.setFlip(false, false);
				break;
			case UPRIGHT:
				sprite.setTexture(texCar01Size02BackLeft01);
				if (flipSpriteX)
					sprite.setFlip(false, false);
				else
					sprite.setFlip(true, false);
				break;
			}
			break;
		case NINE:
			switch (dir) {
			case DOWN:
				sprite.setTexture(texCar01Size01Front01);
				sprite.setFlip(false, false);
				break;
			case DOWNLEFT:
				sprite.setTexture(texCar01Size01FrontLeft01);
				if (flipSpriteX)
					sprite.setFlip(true, false);
				else
					sprite.setFlip(false, false);
				break;
			case DOWNRIGHT:
				sprite.setTexture(texCar01Size01FrontLeft01);
				if (flipSpriteX)
					sprite.setFlip(false, false);
				else
					sprite.setFlip(true, false);
				break;
			case LEFT:
				sprite.setTexture(texCar01Size01Left01);
				if (flipSpriteX)
					sprite.setFlip(true, false);
				else
					sprite.setFlip(false, false);
				break;
			case RIGHT:
				sprite.setTexture(texCar01Size01Left01);
				if (flipSpriteX)
					sprite.setFlip(false, false);
				else
					sprite.setFlip(true, false);
				break;
			case UP:
				sprite.setTexture(texCar01Size01Back01);
				sprite.setFlip(false, false);
				break;
			case UPLEFT:
				sprite.setTexture(texCar01Size01BackLeft01);
				if (flipSpriteX)
					sprite.setFlip(true, false);
				else
					sprite.setFlip(false, false);
				break;
			case UPRIGHT:
				sprite.setTexture(texCar01Size01BackLeft01);
				if (flipSpriteX)
					sprite.setFlip(false, false);
				else
					sprite.setFlip(true, false);
				break;
			}
			break;
		}
	}
}
