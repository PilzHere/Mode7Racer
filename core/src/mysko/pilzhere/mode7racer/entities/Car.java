package mysko.pilzhere.mode7racer.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

import mysko.pilzhere.mode7racer.constants.GameConstants;
import mysko.pilzhere.mode7racer.entities.colliders.Checkpoint;
import mysko.pilzhere.mode7racer.entities.colliders.Curb;
import mysko.pilzhere.mode7racer.entities.colliders.Edge;
import mysko.pilzhere.mode7racer.entities.colliders.FinishLine;
import mysko.pilzhere.mode7racer.entities.colliders.Jump;
import mysko.pilzhere.mode7racer.entities.colliders.Recovery;
import mysko.pilzhere.mode7racer.managers.AssetsManager;
import mysko.pilzhere.mode7racer.screens.GameScreen;

public class Car extends Entity {
	public float getCurrentMaximumSpeed() {
		return currentMaximumSpeed;
	}

	public float getDistanceToNextCheckpoint() {
		return distanceToNextCheckpoint;
	}

	public float getCurrentSpeed() {
		return currentSpeed;
	}

	public float getCurrentSpeedKMpH() {
		return currentSpeedKMpH;
	}

	public int getCurrentTurbos() {
		return currentTurbos;
	}

	public boolean getHasTurbo() {
		return hasTurbo;
	}

	public int getLap() {
		return lap;
	}

	public int getCurrentCheckpoint() {
		return currentCheckpoint;
	}

	public int getRank() {
		return rank;
	}

	public int getHp() {
		return hp;
	}
	
	public void setRank(int rank) {
		this.rank = rank;
	}

	private int lap = 0;
	private int newCheckpoint;
//	private int nextCheckpoint;
	private int currentCheckpoint;
//	private float lapDegrees;
	private int rank;

//	private final boolean isLocalPlayer;
	private final boolean IS_CPU;

	private final float RECT_SIZE = 0.25f;

	private Sprite sprite;

	private final int MAX_TURBOS = 3;
	private  int currentTurbos = MAX_TURBOS;
	private boolean hasTurbo;
	
	private final CarType CAR_TYPE;

	public Car(final GameScreen screen, final Vector3 position, final boolean isCPU, final CarType carType) {
		super(screen, position);
//		this.isLocalPlayer = isLocalPlayer;
		this.IS_CPU = isCPU;
		this.CAR_TYPE = carType;

		getTextures();

		sprite = new Sprite(texCarSize09Back01);
		rect = new Rectangle(position.x - RECT_SIZE / 2, position.z - RECT_SIZE / 2, RECT_SIZE, RECT_SIZE);
	}

	private Texture texCarSize09Back01, texCarSize08Back01, texCarSize07Back01, texCarSize06Back01,
			texCarSize05Back01, texCarSize04Back01, texCarSize03Back01, texCarSize02Back01,
			texCarSize01Back01;

	private Texture texCarBigBackTurnRight02;

	private Texture texCarSize09BackLeft01, texCarSize08BackLeft01, texCarSize07BackLeft01,
			texCarSize06BackLeft01, texCarSize05BackLeft01, texCarSize04BackLeft01, texCarSize03BackLeft01,
			texCarSize02BackLeft01, texCarSize01BackLeft01;

	private Texture texCarSize09Left01, texCarSize08Left01, texCarSize07Left01, texCarSize06Left01,
			texCarSize05Left01, texCarSize04Left01, texCarSize03Left01, texCarSize02Left01,
			texCarSize01Left01;

	private Texture texCarSize09Front01, texCarSize08Front01, texCarSize07Front01, texCarSize06Front01,
			texCarSize05Front01, texCarSize04Front01, texCarSize03Front01, texCarSize02Front01,
			texCarSize01Front01;

	private Texture texCarSize09FrontLeft01, texCarSize08FrontLeft01, texCarSize07FrontLeft01,
			texCarSize06FrontLeft01, texCarSize05FrontLeft01, texCarSize04FrontLeft01, texCarSize03FrontLeft01,
			texCarSize02FrontLeft01, texCarSize01FrontLeft01;

	private void getTextures() {
		final AssetsManager assMan = screen.assMan;

		switch (CAR_TYPE) {
		case INVISIBLE:
			
//			This might have a purpose in the future.
			
			break;
			
		case BLUE:
//			Back
			texCarSize09Back01 = assMan.get(assMan.CAR_01_SIZE_09_BACK_01);
			texCarSize08Back01 = assMan.get(assMan.CAR_01_SIZE_08_BACK_01);
			texCarSize07Back01 = assMan.get(assMan.CAR_01_SIZE_07_BACK_01);
			texCarSize06Back01 = assMan.get(assMan.CAR_01_SIZE_06_BACK_01);
			texCarSize05Back01 = assMan.get(assMan.CAR_01_SIZE_05_BACK_01);
			texCarSize04Back01 = assMan.get(assMan.CAR_01_SIZE_04_BACK_01);
			texCarSize03Back01 = assMan.get(assMan.CAR_01_SIZE_03_BACK_01);
			texCarSize02Back01 = assMan.get(assMan.CAR_01_SIZE_02_BACK_01);
			texCarSize01Back01 = assMan.get(assMan.CAR_01_SIZE_01_BACK_01);

//			Back - Turn
			texCarBigBackTurnRight02 = assMan.get(assMan.CAR_01_SIZE_09_BACK_TURN_RIGHT_02);

//			BackLeft
			texCarSize09BackLeft01 = assMan.get(assMan.CAR_01_SIZE_09_BACK_LEFT_01);
			texCarSize08BackLeft01 = assMan.get(assMan.CAR_01_SIZE_08_BACK_LEFT_01);
			texCarSize07BackLeft01 = assMan.get(assMan.CAR_01_SIZE_07_BACK_LEFT_01);
			texCarSize06BackLeft01 = assMan.get(assMan.CAR_01_SIZE_06_BACK_LEFT_01);
			texCarSize05BackLeft01 = assMan.get(assMan.CAR_01_SIZE_05_BACK_LEFT_01);
			texCarSize04BackLeft01 = assMan.get(assMan.CAR_01_SIZE_04_BACK_LEFT_01);
			texCarSize03BackLeft01 = assMan.get(assMan.CAR_01_SIZE_03_BACK_LEFT_01);
			texCarSize02BackLeft01 = assMan.get(assMan.CAR_01_SIZE_02_BACK_LEFT_01);
			texCarSize01BackLeft01 = assMan.get(assMan.CAR_01_SIZE_01_BACK_LEFT_01);

//			Left
			texCarSize09Left01 = assMan.get(assMan.CAR_01_SIZE_09_LEFT_01);
			texCarSize08Left01 = assMan.get(assMan.CAR_01_SIZE_08_LEFT_01);
			texCarSize07Left01 = assMan.get(assMan.CAR_01_SIZE_07_LEFT_01);
			texCarSize06Left01 = assMan.get(assMan.CAR_01_SIZE_06_LEFT_01);
			texCarSize05Left01 = assMan.get(assMan.CAR_01_SIZE_05_LEFT_01);
			texCarSize04Left01 = assMan.get(assMan.CAR_01_SIZE_04_LEFT_01);
			texCarSize03Left01 = assMan.get(assMan.CAR_01_SIZE_03_LEFT_01);
			texCarSize02Left01 = assMan.get(assMan.CAR_01_SIZE_02_LEFT_01);
			texCarSize01Left01 = assMan.get(assMan.CAR_01_SIZE_01_LEFT_01);

//			Front
			texCarSize09Front01 = assMan.get(assMan.CAR_01_SIZE_09_FRONT_01);
			texCarSize08Front01 = assMan.get(assMan.CAR_01_SIZE_08_FRONT_01);
			texCarSize07Front01 = assMan.get(assMan.CAR_01_SIZE_07_FRONT_01);
			texCarSize06Front01 = assMan.get(assMan.CAR_01_SIZE_06_FRONT_01);
			texCarSize05Front01 = assMan.get(assMan.CAR_01_SIZE_05_FRONT_01);
			texCarSize04Front01 = assMan.get(assMan.CAR_01_SIZE_04_FRONT_01);
			texCarSize03Front01 = assMan.get(assMan.CAR_01_SIZE_03_FRONT_01);
			texCarSize02Front01 = assMan.get(assMan.CAR_01_SIZE_02_FRONT_01);
			texCarSize01Front01 = assMan.get(assMan.CAR_01_SIZE_01_FRONT_01);

//			FrontLeft
			texCarSize09FrontLeft01 = assMan.get(assMan.CAR_01_SIZE_09_FRONT_LEFT_01);
			texCarSize08FrontLeft01 = assMan.get(assMan.CAR_01_SIZE_08_FRONT_LEFT_01);
			texCarSize07FrontLeft01 = assMan.get(assMan.CAR_01_SIZE_07_FRONT_LEFT_01);
			texCarSize06FrontLeft01 = assMan.get(assMan.CAR_01_SIZE_06_FRONT_LEFT_01);
			texCarSize05FrontLeft01 = assMan.get(assMan.CAR_01_SIZE_05_FRONT_LEFT_01);
			texCarSize04FrontLeft01 = assMan.get(assMan.CAR_01_SIZE_04_FRONT_LEFT_01);
			texCarSize03FrontLeft01 = assMan.get(assMan.CAR_01_SIZE_03_FRONT_LEFT_01);
			texCarSize02FrontLeft01 = assMan.get(assMan.CAR_01_SIZE_02_FRONT_LEFT_01);
			texCarSize01FrontLeft01 = assMan.get(assMan.CAR_01_SIZE_01_FRONT_LEFT_01);
			break;
			
		case YELLOW:
//			Back
			texCarSize09Back01 = assMan.get(assMan.CAR_02_SIZE_09_BACK_01);
			texCarSize08Back01 = assMan.get(assMan.CAR_02_SIZE_08_BACK_01);
			texCarSize07Back01 = assMan.get(assMan.CAR_02_SIZE_07_BACK_01);
			texCarSize06Back01 = assMan.get(assMan.CAR_02_SIZE_06_BACK_01);
			texCarSize05Back01 = assMan.get(assMan.CAR_02_SIZE_05_BACK_01);
			texCarSize04Back01 = assMan.get(assMan.CAR_02_SIZE_04_BACK_01);
			texCarSize03Back01 = assMan.get(assMan.CAR_02_SIZE_03_BACK_01);
			texCarSize02Back01 = assMan.get(assMan.CAR_02_SIZE_02_BACK_01);
			texCarSize01Back01 = assMan.get(assMan.CAR_02_SIZE_01_BACK_01);

//			Back - Turn
			texCarBigBackTurnRight02 = assMan.get(assMan.CAR_02_SIZE_09_BACK_TURN_RIGHT_02);

//			BackLeft
			texCarSize09BackLeft01 = assMan.get(assMan.CAR_02_SIZE_09_BACK_LEFT_01);
			texCarSize08BackLeft01 = assMan.get(assMan.CAR_02_SIZE_08_BACK_LEFT_01);
			texCarSize07BackLeft01 = assMan.get(assMan.CAR_02_SIZE_07_BACK_LEFT_01);
			texCarSize06BackLeft01 = assMan.get(assMan.CAR_02_SIZE_06_BACK_LEFT_01);
			texCarSize05BackLeft01 = assMan.get(assMan.CAR_02_SIZE_05_BACK_LEFT_01);
			texCarSize04BackLeft01 = assMan.get(assMan.CAR_02_SIZE_04_BACK_LEFT_01);
			texCarSize03BackLeft01 = assMan.get(assMan.CAR_02_SIZE_03_BACK_LEFT_01);
			texCarSize02BackLeft01 = assMan.get(assMan.CAR_02_SIZE_02_BACK_LEFT_01);
			texCarSize01BackLeft01 = assMan.get(assMan.CAR_02_SIZE_01_BACK_LEFT_01);

//			Left
			texCarSize09Left01 = assMan.get(assMan.CAR_02_SIZE_09_LEFT_01);
			texCarSize08Left01 = assMan.get(assMan.CAR_02_SIZE_08_LEFT_01);
			texCarSize07Left01 = assMan.get(assMan.CAR_02_SIZE_07_LEFT_01);
			texCarSize06Left01 = assMan.get(assMan.CAR_02_SIZE_06_LEFT_01);
			texCarSize05Left01 = assMan.get(assMan.CAR_02_SIZE_05_LEFT_01);
			texCarSize04Left01 = assMan.get(assMan.CAR_02_SIZE_04_LEFT_01);
			texCarSize03Left01 = assMan.get(assMan.CAR_02_SIZE_03_LEFT_01);
			texCarSize02Left01 = assMan.get(assMan.CAR_02_SIZE_02_LEFT_01);
			texCarSize01Left01 = assMan.get(assMan.CAR_02_SIZE_01_LEFT_01);

//			Front
			texCarSize09Front01 = assMan.get(assMan.CAR_02_SIZE_09_FRONT_01);
			texCarSize08Front01 = assMan.get(assMan.CAR_02_SIZE_08_FRONT_01);
			texCarSize07Front01 = assMan.get(assMan.CAR_02_SIZE_07_FRONT_01);
			texCarSize06Front01 = assMan.get(assMan.CAR_02_SIZE_06_FRONT_01);
			texCarSize05Front01 = assMan.get(assMan.CAR_02_SIZE_05_FRONT_01);
			texCarSize04Front01 = assMan.get(assMan.CAR_02_SIZE_04_FRONT_01);
			texCarSize03Front01 = assMan.get(assMan.CAR_02_SIZE_03_FRONT_01);
			texCarSize02Front01 = assMan.get(assMan.CAR_02_SIZE_02_FRONT_01);
			texCarSize01Front01 = assMan.get(assMan.CAR_02_SIZE_01_FRONT_01);

//			FrontLeft
			texCarSize09FrontLeft01 = assMan.get(assMan.CAR_02_SIZE_09_FRONT_LEFT_01);
			texCarSize08FrontLeft01 = assMan.get(assMan.CAR_02_SIZE_08_FRONT_LEFT_01);
			texCarSize07FrontLeft01 = assMan.get(assMan.CAR_02_SIZE_07_FRONT_LEFT_01);
			texCarSize06FrontLeft01 = assMan.get(assMan.CAR_02_SIZE_06_FRONT_LEFT_01);
			texCarSize05FrontLeft01 = assMan.get(assMan.CAR_02_SIZE_05_FRONT_LEFT_01);
			texCarSize04FrontLeft01 = assMan.get(assMan.CAR_02_SIZE_04_FRONT_LEFT_01);
			texCarSize03FrontLeft01 = assMan.get(assMan.CAR_02_SIZE_03_FRONT_LEFT_01);
			texCarSize02FrontLeft01 = assMan.get(assMan.CAR_02_SIZE_02_FRONT_LEFT_01);
			texCarSize01FrontLeft01 = assMan.get(assMan.CAR_02_SIZE_01_FRONT_LEFT_01);
			
			break;
		}

	}

	private float angle = 0;
	private final float ANGLE_INCREMENT = 2.5f;
	private float currentSpeed;
	private float toKMpH = 440f;
	private float currentSpeedKMpH;
	private boolean rightTurn; // For moving bg's.
	private boolean leftTurn;
	private final float MIN_TURN_AMOUNT = 0.55f;
	private final float MAX_TURN_AMOUNT = 1.0f;
	private float currentTurnAmount = MIN_TURN_AMOUNT;

	public void onInputA(final float delta) {
		if (currentSpeed > 0) {
			if (currentSpeed < MIN_TURN_AMOUNT) {
				currentTurnAmount = ANGLE_INCREMENT * MIN_TURN_AMOUNT * delta;
				angle -= currentTurnAmount;
			} else {
				if (currentSpeed > MAX_TURN_AMOUNT) {
					currentTurnAmount = ANGLE_INCREMENT * MAX_TURN_AMOUNT * delta;
					angle -= currentTurnAmount;
				} else {
					currentTurnAmount = ANGLE_INCREMENT * currentSpeed * delta;
					angle -= currentTurnAmount;
				}
			}

			if (!IS_CPU) // TESTING SPRITES
				leftTurn = true;

//			set currentTurnAmount negative for left turns, dont use this for car after.
			currentTurnAmount = -currentTurnAmount;
		}
	}

	public void onInputD(final float delta) {
		if (currentSpeed > 0) {
			if (currentSpeed < MIN_TURN_AMOUNT) {
				currentTurnAmount = ANGLE_INCREMENT * MIN_TURN_AMOUNT * delta;
				angle += currentTurnAmount;
			} else {
				if (currentSpeed > MAX_TURN_AMOUNT) {
					currentTurnAmount = ANGLE_INCREMENT * MAX_TURN_AMOUNT * delta;
					angle += currentTurnAmount;
				} else {
					currentTurnAmount = ANGLE_INCREMENT * currentSpeed * delta;
					angle += currentTurnAmount;
				}
			}

			if (!IS_CPU) // TESTING SPRITES
				rightTurn = true;
		}
	}

	private boolean shiftLeft;

	public void onInputShiftLeft(final float delta) {
		if (currentSpeed > 0)
			shiftLeft = true;
	}

	private boolean shiftRight;

	public void onInputShiftRight(final float delta) {
		if (currentSpeed > 0)
			shiftRight = true;
	}

	private final float TURBO_SPEED_INCREMENT = 0.5f;
	private final float NORMAL_SPEED_INCREMENT = 0.33f; // was 2 should slow it
	private float currentSpeedIncrement = NORMAL_SPEED_INCREMENT;
	private final float MOTOR_BRAKE_STRENGTH = 0.35f;
	private final int BOUNCE_BRAKES_STRENGTH = 16;

	private final long TURBO_COOLDOWN = 2250L;
	private boolean newTurboCdSet;
	private long newTurboCdTime;

	public void onInputT(final float delta) {
		if (currentSpeed > 0) {
			if (!newTurboCdSet) {
				if (currentTurbos > 0) {
					hasTurbo = true;
					currentTurbos--;
				}
			}
		}
	}

	public void onNoInputW(final float delta) {
		if (bounceX || bounceZ)
//			Bounce brake.
			currentSpeed -= currentSpeedIncrement * BOUNCE_BRAKES_STRENGTH * MOTOR_BRAKE_STRENGTH * delta;
		else {
			currentSpeed -= currentSpeedIncrement * MOTOR_BRAKE_STRENGTH * delta; // Motor brake.
		}
	}

	public void onInputW(final float delta) {
		currentSpeed += currentSpeedIncrement * delta;
	}

	private final float BRAKES_STRENGTH = 0.33f;

	public void onInputS(final float delta) {
		currentSpeed -= currentSpeedIncrement * BRAKES_STRENGTH * delta;
	}

	private final int MAX_HP = 100;
	private int hp = MAX_HP;
	private boolean gotHitTimeSet;
	private long hitNewTime;
	private final long HIT_COOLDOWN = 420L;

	private boolean gotHealedTimeSet;
	private long healNewTime;
	private final long HEAL_COOLDOWN = 420L;

	private boolean jump;
	private boolean isInAir;

	@Override
	public void onHit(final Object object, final float delta) {
		if (object instanceof Checkpoint) {
			for (int i = 0; i < screen.getCurrentMap().getCheckpoints().size; i++) {
				if (object == screen.getCurrentMap().getCheckpoints().get(i)) {
					newCheckpoint = i;

					if (newCheckpoint == 0 && currentCheckpoint == screen.getCurrentMap().getCheckpoints().size - 1) {
						lap++;
						currentCheckpoint = newCheckpoint;
					} else if (newCheckpoint == currentCheckpoint + 1) {
						currentCheckpoint = newCheckpoint;
					} else if (newCheckpoint == currentCheckpoint - 1) {
						currentCheckpoint = newCheckpoint;
					}
				}
			}

		} else if (object instanceof Car) {
			System.err.println("Hit a car!");
		} else if (object instanceof Curb) {
			if (!isInAir) {
				curbsBlink = true;

				if (!gotHitTimeSet) {
					if (hp > 0) {
						hp = hp - 5;
					}
					hitNewTime = screen.getCurrentTime() + HIT_COOLDOWN;
//					System.err.println("Car damage taken!");
					gotHitTimeSet = true;
				}
			}
		} else if (object instanceof Edge) {
//			System.out.println("Car edge hit!");
		} else if (object instanceof Jump) {
			if (!isInAir) {
				System.err.println("Jump HIT");
				jump = true;
			}
		} else if (object instanceof Recovery) {
			if (hp < 100) {
				if (!gotHealedTimeSet) {
					hp += 10;
					healNewTime = screen.getCurrentTime() + HEAL_COOLDOWN;
//					System.err.println("Car damage taken!");
					gotHealedTimeSet = true;
				}
			}
		} else if (object instanceof FinishLine) {
			System.err.println("Finishline!");
//			if (lapDegrees >= screen.getCurrentMap().angle - 0.1f
//					|| lapDegrees <= screen.getCurrentMap().angle + 0.1f) {
//				
//			}
		}
	}

//	public void resetPosition() {
//		position.set(Vector3.Zero);
//	}

	private long bounceXTotalTime;
	private long bounceZTotalTime;
	private boolean bounceTimerXSet;
	private boolean bounceTimerZSet;

	private final Vector3 CAMERA_TEMPORARY_POSITION = new Vector3();
	
	private float distanceToNextCheckpoint;

	@Override
	public void tick(final float delta) {
		resetOverlaps();

//		TEST
//		lapDegrees = MathUtils.atan2(position.z - screen.getCurrentMap().lapOrigo.z,
//				position.x - screen.getCurrentMap().lapOrigo.x);

//		if (this == screen.playerCar)
//			System.err.println(lapDegrees);
//		TEST END
		
//		System.err.println("NEWCAR: CarRank: " + rank + " Checkpoint#: " + currentCheckpoint + " DistToNextCheckpoint: " + distanceToNextCheckpoint);

		int nextCheckpoint = 0;
		if (currentCheckpoint != screen.getCurrentMap().getCheckpoints().size - 1) {
			nextCheckpoint = currentCheckpoint + 1;
		}
		
		distanceToNextCheckpoint = Vector2.dst(position.x, position.y,
				screen.getCurrentMap().getCheckpoints().get(nextCheckpoint).getRect().x,
				screen.getCurrentMap().getCheckpoints().get(nextCheckpoint).getRect().y);

		updateJump(delta);
		updatePositionYIfNegative();
		updateInAirState();
		updateVelocityInAir(delta);

//		Mini AI
		if (IS_CPU) {
			onInputA(delta); // turn left
//			onInputD(delta); // turn right
			onInputW(delta); // pedal to the metal
		}

		setCurrentSpeedAndSpeedLimit(delta);
		updateTurboCooldown();

		if (this == screen.playerCar)
			updateCurbsBlink();
		updateGotHitTimeSet();
		updateGotHealedTimeSet();

		updateBounceX(delta);
		updateBounceZ(delta);

		clampAngle();
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

		currentSpeedKMpH = currentSpeed * toKMpH;

		if (hp > MAX_HP)
			hp = MAX_HP;
		else if (hp < 0)
			hp = 0;
	}

	private final float GRAVITY_Y = -12.5f;
	private float velocityY;

	private void updateVelocityInAir(final float delta) {
		if (isInAir) {
			velocityY += GRAVITY_Y * delta;
			position.y += velocityY * delta;
		}
	}

	private void updateInAirState() {
		if (position.y > 0) {
			isInAir = true;
		} else {
			isInAir = false;
		}
	}

	private void updatePositionYIfNegative() {
		if (position.y < 0) {
			position.y = 0;
		}
	}

	private float currentJumpForce;
	private final float JUMP_FORCE_BOOST = 8f;

	private void updateJump(final float delta) {
		if (jump) {
			currentJumpForce = currentSpeed / currentMaximumSpeed;

			velocityY = currentJumpForce * JUMP_FORCE_BOOST;
			position.y += velocityY * delta;

			jump = false;
		}
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

	private void setCurrentSpeedAndSpeedLimit(final float delta) {
		if (hasTurbo) {
			currentMaximumSpeed = TURBO_MAXIMUM_SPEED;
			currentSpeedIncrement = TURBO_SPEED_INCREMENT;

			if (!newTurboCdSet) {
				newTurboCdTime = screen.getCurrentTime() + TURBO_COOLDOWN;
				newTurboCdSet = true;
			}
		} else {
			currentMaximumSpeed = NORMAL_MAXIMUM_SPEED;
			currentSpeedIncrement = NORMAL_SPEED_INCREMENT;
		}
	}

	private void updateGotHitTimeSet() {
		if (gotHitTimeSet) {
			if (screen.getCurrentTime() > hitNewTime) {
				gotHitTimeSet = false;
			}
		}
	}

	private void updateGotHealedTimeSet() {
		if (gotHealedTimeSet) {
			if (screen.getCurrentTime() > healNewTime) {
				gotHealedTimeSet = false;
			}
		}
	}

	private boolean curbsBlink;
	private final long BLINK_COOLDOWN = 100L;
	private long nextBlinkTime;
	private boolean nextBlinkTimeSet;

	private void updateCurbsBlink() {
		if (curbsBlink) {
			if (!nextBlinkTimeSet) {
				screen.getCurrentMap().setBlink(!screen.getCurrentMap().isBlink());
				nextBlinkTime = screen.getCurrentTime() + BLINK_COOLDOWN;
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
	private final int BOUNCE_DECREMENT = 10;
	private boolean bounceX;
	private boolean bounceZ;
	private final long BOUNCE_TIME = 175L; // time until bounce released.
	private final int BOUNCE_AMOUNT = 20;

	private void updateBounceX(final float delta) {
		if (bounceX) {
			if (!bounceTimerXSet) {
				bounceXTotalTime = screen.getCurrentTime() + BOUNCE_TIME;
				currentBounceX = BOUNCE_AMOUNT;
				currentSpeed = currentSpeed / 2;
				bounceTimerXSet = true;
			} else {

				if (screen.getCurrentTime() > bounceXTotalTime) {
					currentBounceX = 0;
//					System.err.println("Bouncing X gone!");
					bounceX = false;
				} else {
//					System.out.println("Bouncing X");
					currentBounceX -= BOUNCE_DECREMENT * delta;
				}
			}
		} else {
			bounceTimerXSet = false;
		}
	}

	private void updateBounceZ(final float delta) {
		if (bounceZ) {
			if (!bounceTimerZSet) {
				bounceZTotalTime = screen.getCurrentTime() + BOUNCE_TIME;
				currentBounceZ = BOUNCE_AMOUNT;
				currentSpeed = currentSpeed / 2;
				bounceTimerZSet = true;
			} else {
				if (screen.getCurrentTime() > bounceZTotalTime) {
					currentBounceZ = 0;
//					System.err.println("Bouncing Z gone!");
					bounceZ = false;
				} else {
//					System.out.println("Bouncing Z");
					currentBounceZ -= BOUNCE_DECREMENT * delta;
				}
			}
		} else {
			bounceTimerZSet = false;
		}
	}

	private boolean overlapX;
	private boolean overlapY;

	private void resetOverlaps() {
		overlapX = false;
		overlapY = false;
	}

	/**
	 * Clamp angle.
	 */
	private void clampAngle() {
		if (angle > 360 * MathUtils.degreesToRadians) {
			final float newAngle = angle - (360 * MathUtils.degreesToRadians);
			angle = newAngle;
		} else if (angle < -360 * MathUtils.degreesToRadians) {
			final float newAngle = angle + (360 * MathUtils.degreesToRadians);
			angle = newAngle;
		}
	}

	private final Vector3 LAST_POSITION = new Vector3();

	/**
	 * Updates camera last position.
	 */
	private void updateLastPosition() {
		LAST_POSITION.set(position.cpy());
	}

	/**
	 * Move bg's depending on turn direction.
	 * 
	 * @param delta
	 */

	private void moveBackgroundsWithTurn(final float delta) {
//		Not perfect but works...
		screen.getCurrentMap().setBgFrontPosX(screen.getCurrentMap().getBgFrontPosX() + currentTurnAmount * 128); // currentTurnAmount
																													// has
																													// delta.
		screen.getCurrentMap().setBgBackPosX(screen.getCurrentMap().getBgBackPosX() + currentTurnAmount * 64); // currentTurnAmount
																												// has
																												// delta.

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

	private final float NORMAL_MAXIMUM_SPEED = 0.75f; // was 1
	private final float TURBO_MAXIMUM_SPEED = 1.0f; // was 1.5
	private float currentMaximumSpeed = NORMAL_MAXIMUM_SPEED;

//	private boolean breakFromTurbo;

	/**
	 * Limit car speed.
	 */
	private void clampSpeed(final float delta) {
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

		if (currentSpeed > currentMaximumSpeed) {
//			if (!breakFromTurbo) {
			currentSpeed = currentMaximumSpeed;
//			}
		} else if (currentSpeed < 0) {
			currentSpeed = 0;
		}
	}

	private float newPosX;
	private float newPosZ;

	private final int SPEED_INCREMENT_BOOST = 40; // was 32

	private float sinAngle = angle;
	private float cosAngle = angle;

	private final float SLIDE_ANGLE = 11.25f * MathUtils.degreesToRadians; // was 22.5f

	/**
	 * Move with speed in angle direction.
	 * 
	 * @param delta
	 */
	private void moveWithAngle(final float delta) {
		sinAngle = angle;
		cosAngle = angle;

		adjustAngleForShifting(shiftLeft, shiftRight, sinAngle, cosAngle);

		if (!bounceX) {
			newPosX += currentSpeed * SPEED_INCREMENT_BOOST * MathUtils.sin(sinAngle) * delta;
		} else {
			newPosX -= currentSpeed * currentBounceX * MathUtils.sin(sinAngle) * delta;
		}

		if (!bounceZ) {
			newPosZ -= currentSpeed * SPEED_INCREMENT_BOOST * MathUtils.cos(cosAngle) * delta;
		} else {
			newPosZ += currentSpeed * currentBounceZ * MathUtils.cos(cosAngle) * delta;
		}
	}

	private void adjustAngleForShifting(final boolean shiftLeft, final boolean shiftRight, float sinAngle, float cosAngle) {
		if (shiftLeft) {
//			System.err.println("SHIFT LEFT");
			sinAngle -= SLIDE_ANGLE;
			cosAngle -= SLIDE_ANGLE;
		}

		if (shiftRight) {
//			System.err.println("SHIFT Right");
			sinAngle += SLIDE_ANGLE;
			cosAngle += SLIDE_ANGLE;
		}

		this.sinAngle = sinAngle;
		this.cosAngle = cosAngle;
	}

	/**
	 * Check for collisions on X and Y.
	 * 
	 * @param delta
	 */
	private void checkForCollision(final float delta) {
//		Check collisions X
		rect.setX(newPosX - RECT_SIZE / 2);

		for (Entity ent : screen.getEntities()) {
			if (ent.rect != null) {
				if (ent.rect != this.rect) {
					if (ent.rect.overlaps(this.rect)) {
						onHit(ent, delta);
						if (!isInAir) {
							if (ent instanceof Edge) {
								overlapX = true;
							}
						}
					}
				}
			}
		}

		for (Car car : screen.cars.values()) {
			if (car.rect != null) {
				if (car.rect != this.rect) {
					if (car.rect.overlaps(this.rect)) {
						if (isInAir == car.isInAir) {
							onHit(car, delta);
							overlapX = true;
						}
					}
				}
			}
		}

		for (Checkpoint checkpoint : screen.getCurrentMap().getCheckpoints()) {
			if (checkpoint.rect != null) {
				if (checkpoint.rect != this.rect) {
					if (checkpoint.rect.overlaps(this.rect)) {
						onHit(checkpoint, delta);
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

		rect.setX(position.x - RECT_SIZE / 2);

//		Check collisions Y
		rect.setY(newPosZ - RECT_SIZE / 2);

		for (Entity ent : screen.getEntities()) {
			if (ent.rect != null) {
				if (ent.rect != this.rect) {
					if (ent.rect.overlaps(this.rect)) {
						onHit(ent, delta);
						if (!isInAir) {
							if (ent instanceof Edge) {
								overlapY = true;
							}
						}
					}
				}
			}
		}

		for (Car car : screen.cars.values()) {
			if (car.rect != null) {
				if (car.rect != this.rect) {
					if (car.rect.overlaps(this.rect)) {
						if (isInAir == car.isInAir) {
							onHit(car, delta);
							overlapY = true;
						}
					}
				}
			}
		}

		for (Checkpoint checkpoint : screen.getCurrentMap().getCheckpoints()) {
			if (checkpoint.rect != null) {
				if (checkpoint.rect != this.rect) {
					if (checkpoint.rect.overlaps(this.rect)) {
						onHit(checkpoint, delta);
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

		rect.setY(position.z - RECT_SIZE / 2);
	}

	/**
	 * Updates camera positon and rotation.
	 * 
	 * @param delta
	 */
	private void updateCamPos(final float delta) {
		if (!LAST_POSITION.idt(position.cpy())) {
			screen.getCam().position.set(position.cpy());
//			screen.cam.position.sub(position.cpy().sub(oldPosition.cpy()).nor().scl(screen.camDesiredDistFromCar)); // old one, works but not too good in rotations.
			CAMERA_TEMPORARY_POSITION.x = -currentSpeed * SPEED_INCREMENT_BOOST * delta * MathUtils.sin(angle);
			CAMERA_TEMPORARY_POSITION.z = currentSpeed * SPEED_INCREMENT_BOOST * delta * MathUtils.cos(angle);
			screen.getCam().position.add(CAMERA_TEMPORARY_POSITION.cpy().nor().scl(screen.camDesiredDistFromCar));

			screen.getCam().lookAt(position.cpy());
		}
	}

	private boolean render = true;
	private float distFromCam;
	private final float DISTANCE_CHANGE_SPRITE = 4.4f;
	private boolean updateSpriteSize = true;
	private float angleFromPlayerCar;

	private SpriteSize spriteSize = SpriteSize.NINE;
	private SpriteDirection spriteDirection = SpriteDirection.UP;

	private boolean flipSpriteX;

	@Override
	public void render2D(final SpriteBatch batch, final float delta) {
		render = true;
		flipSpriteX = false;

		screenPos.set(screen.getViewport().project(position.cpy()));
		screenPos.z = 0;

		distFromCam = Vector3.dst(position.x, position.y, position.z, screen.getCam().position.x,
				screen.getCam().position.y, screen.getCam().position.z);

//		Get angle from playe car. Will this be correct if camera leaves playercar? or just spawn hidden car that cam follows? ;)
		angleFromPlayerCar = screen.playerCar.angle - angle; // compare with playercars angle.

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
			setSpriteSizeWithDistanceFromCam(distFromCam, DISTANCE_CHANGE_SPRITE);
		else
			spriteSize = SpriteSize.ONE;
		setSpriteTextureWithSizeAndDirection(spriteSize, spriteDirection, flipSpriteX);

//		temp?
		if (this == screen.playerCar) {
			if (leftTurn) {
				sprite.setTexture(texCarBigBackTurnRight02);
				sprite.setFlip(true, false);
			} else if (rightTurn) {
				sprite.setTexture(texCarBigBackTurnRight02);
				sprite.setFlip(false, false);
			} else {
				sprite.setTexture(texCarSize09Back01);
			}
		}

//		Cant size be 1?
//		Change cam proj to 299 * 224? in GameScreen.
		
		sprite.setSize(
				MathUtils.round(
						sprite.getTexture().getWidth() * (Gdx.graphics.getWidth() / GameConstants.VIEWPORT_WIDTH_STRETCHED)
								* ((float) GameConstants.VIEWPORT_WIDTH_STRETCHED / GameConstants.VIEWPORT_WIDTH)),
				MathUtils.round(sprite.getTexture().getHeight() * (Gdx.graphics.getHeight() / GameConstants.VIEWPORT_HEIGHT)));
		
		sprite.setX(screenPos.x - (sprite.getWidth() / 2));
		sprite.setY(screenPos.y - (sprite.getHeight() / 2));

//		if (!overlapX)
//			rect.setX(position.x - rectSize / 2);

//		if (!overlapY)
//			rect.setY(position.z - rectSize / 2);

		render = screen.spriteFitsInScreen(screenPos.cpy(), sprite);

		if (render) {
			screen.sprites.add(sprite);
		}

//		reset stuff
		currentTurnAmount = 0;
		rightTurn = false;
		leftTurn = false;
		shiftLeft = false;
		shiftRight = false;
	}

	/**
	 * Calculate sprite direction by the angle to player car.
	 * 
	 * @param angleFromPlayerCar
	 */
	private void calculateSpriteDirection(final float angleFromPlayerCar) {
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
	 * 
	 * @param distance
	 * @param distanceMultiplier
	 */
	private void setSpriteSizeWithDistanceFromCam(final float distance, final float distanceMultiplier) {
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
	 * 
	 * @param size
	 * @param dir
	 * @param flipX
	 */
	private void setSpriteTextureWithSizeAndDirection(final SpriteSize size, final SpriteDirection dir, final boolean flipX) {
		switch (size) {
		case ONE:
			switch (dir) {
			case DOWN:
				sprite.setTexture(texCarSize09Front01);
				sprite.setFlip(false, false);
				break;
			case DOWNLEFT:
				sprite.setTexture(texCarSize09FrontLeft01);
				if (flipSpriteX)
					sprite.setFlip(true, false);
				else
					sprite.setFlip(false, false);
				break;
			case DOWNRIGHT:
				sprite.setTexture(texCarSize09FrontLeft01);
				if (flipSpriteX)
					sprite.setFlip(false, false);
				else
					sprite.setFlip(true, false);
				break;
			case LEFT:
				sprite.setTexture(texCarSize09Left01);
				if (flipSpriteX)
					sprite.setFlip(true, false);
				else
					sprite.setFlip(false, false);
				break;
			case RIGHT:
				sprite.setTexture(texCarSize09Left01);
				if (flipSpriteX)
					sprite.setFlip(false, false);
				else
					sprite.setFlip(true, false);
				break;
			case UP:
				sprite.setTexture(texCarSize09Back01);
				sprite.setFlip(false, false);
				break;
			case UPLEFT:
				sprite.setTexture(texCarSize09BackLeft01);
				if (flipSpriteX)
					sprite.setFlip(true, false);
				else
					sprite.setFlip(false, false);
				break;
			case UPRIGHT:
				sprite.setTexture(texCarSize09BackLeft01);
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
				sprite.setTexture(texCarSize08Front01);
				sprite.setFlip(false, false);
				break;
			case DOWNLEFT:
				sprite.setTexture(texCarSize08FrontLeft01);
				if (flipSpriteX)
					sprite.setFlip(true, false);
				else
					sprite.setFlip(false, false);
				break;
			case DOWNRIGHT:
				sprite.setTexture(texCarSize08FrontLeft01);
				if (flipSpriteX)
					sprite.setFlip(false, false);
				else
					sprite.setFlip(true, false);
				break;
			case LEFT:
				sprite.setTexture(texCarSize08Left01);
				if (flipSpriteX)
					sprite.setFlip(true, false);
				else
					sprite.setFlip(false, false);
				break;
			case RIGHT:
				sprite.setTexture(texCarSize08Left01);
				if (flipSpriteX)
					sprite.setFlip(false, false);
				else
					sprite.setFlip(true, false);
				break;
			case UP:
				sprite.setTexture(texCarSize08Back01);
				sprite.setFlip(false, false);
				break;
			case UPLEFT:
				sprite.setTexture(texCarSize08BackLeft01);
				if (flipSpriteX)
					sprite.setFlip(true, false);
				else
					sprite.setFlip(false, false);
				break;
			case UPRIGHT:
				sprite.setTexture(texCarSize08BackLeft01);
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
				sprite.setTexture(texCarSize07Front01);
				sprite.setFlip(false, false);
				break;
			case DOWNLEFT:
				sprite.setTexture(texCarSize07FrontLeft01);
				if (flipSpriteX)
					sprite.setFlip(true, false);
				else
					sprite.setFlip(false, false);
				break;
			case DOWNRIGHT:
				sprite.setTexture(texCarSize07FrontLeft01);
				if (flipSpriteX)
					sprite.setFlip(false, false);
				else
					sprite.setFlip(true, false);
				break;
			case LEFT:
				sprite.setTexture(texCarSize07Left01);
				if (flipSpriteX)
					sprite.setFlip(true, false);
				else
					sprite.setFlip(false, false);
				break;
			case RIGHT:
				sprite.setTexture(texCarSize07Left01);
				if (flipSpriteX)
					sprite.setFlip(false, false);
				else
					sprite.setFlip(true, false);
				break;
			case UP:
				sprite.setTexture(texCarSize07Back01);
				sprite.setFlip(false, false);
				break;
			case UPLEFT:
				sprite.setTexture(texCarSize07BackLeft01);
				if (flipSpriteX)
					sprite.setFlip(true, false);
				else
					sprite.setFlip(false, false);
				break;
			case UPRIGHT:
				sprite.setTexture(texCarSize07BackLeft01);
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
				sprite.setTexture(texCarSize06Front01);
				sprite.setFlip(false, false);
				break;
			case DOWNLEFT:
				sprite.setTexture(texCarSize06FrontLeft01);
				if (flipSpriteX)
					sprite.setFlip(true, false);
				else
					sprite.setFlip(false, false);
				break;
			case DOWNRIGHT:
				sprite.setTexture(texCarSize06FrontLeft01);
				if (flipSpriteX)
					sprite.setFlip(false, false);
				else
					sprite.setFlip(true, false);
				break;
			case LEFT:
				sprite.setTexture(texCarSize06Left01);
				if (flipSpriteX)
					sprite.setFlip(true, false);
				else
					sprite.setFlip(false, false);
				break;
			case RIGHT:
				sprite.setTexture(texCarSize06Left01);
				if (flipSpriteX)
					sprite.setFlip(false, false);
				else
					sprite.setFlip(true, false);
				break;
			case UP:
				sprite.setTexture(texCarSize06Back01);
				sprite.setFlip(false, false);
				break;
			case UPLEFT:
				sprite.setTexture(texCarSize06BackLeft01);
				if (flipSpriteX)
					sprite.setFlip(true, false);
				else
					sprite.setFlip(false, false);
				break;
			case UPRIGHT:
				sprite.setTexture(texCarSize06BackLeft01);
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
				sprite.setTexture(texCarSize05Front01);
				sprite.setFlip(false, false);
				break;
			case DOWNLEFT:
				sprite.setTexture(texCarSize05FrontLeft01);
				if (flipSpriteX)
					sprite.setFlip(true, false);
				else
					sprite.setFlip(false, false);
				break;
			case DOWNRIGHT:
				sprite.setTexture(texCarSize05FrontLeft01);
				if (flipSpriteX)
					sprite.setFlip(false, false);
				else
					sprite.setFlip(true, false);
				break;
			case LEFT:
				sprite.setTexture(texCarSize05Left01);
				if (flipSpriteX)
					sprite.setFlip(true, false);
				else
					sprite.setFlip(false, false);
				break;
			case RIGHT:
				sprite.setTexture(texCarSize05Left01);
				if (flipSpriteX)
					sprite.setFlip(false, false);
				else
					sprite.setFlip(true, false);
				break;
			case UP:
				sprite.setTexture(texCarSize05Back01);
				sprite.setFlip(false, false);
				break;
			case UPLEFT:
				sprite.setTexture(texCarSize05BackLeft01);
				if (flipSpriteX)
					sprite.setFlip(true, false);
				else
					sprite.setFlip(false, false);
				break;
			case UPRIGHT:
				sprite.setTexture(texCarSize05BackLeft01);
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
				sprite.setTexture(texCarSize04Front01);
				sprite.setFlip(false, false);
				break;
			case DOWNLEFT:
				sprite.setTexture(texCarSize04FrontLeft01);
				if (flipSpriteX)
					sprite.setFlip(true, false);
				else
					sprite.setFlip(false, false);
				break;
			case DOWNRIGHT:
				sprite.setTexture(texCarSize04FrontLeft01);
				if (flipSpriteX)
					sprite.setFlip(false, false);
				else
					sprite.setFlip(true, false);
				break;
			case LEFT:
				sprite.setTexture(texCarSize04Left01);
				if (flipSpriteX)
					sprite.setFlip(true, false);
				else
					sprite.setFlip(false, false);
				break;
			case RIGHT:
				sprite.setTexture(texCarSize04Left01);
				if (flipSpriteX)
					sprite.setFlip(false, false);
				else
					sprite.setFlip(true, false);
				break;
			case UP:
				sprite.setTexture(texCarSize04Back01);
				sprite.setFlip(false, false);
				break;
			case UPLEFT:
				sprite.setTexture(texCarSize04BackLeft01);
				if (flipSpriteX)
					sprite.setFlip(true, false);
				else
					sprite.setFlip(false, false);
				break;
			case UPRIGHT:
				sprite.setTexture(texCarSize04BackLeft01);
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
				sprite.setTexture(texCarSize03Front01);
				sprite.setFlip(false, false);
				break;
			case DOWNLEFT:
				sprite.setTexture(texCarSize03FrontLeft01);
				if (flipSpriteX)
					sprite.setFlip(true, false);
				else
					sprite.setFlip(false, false);
				break;
			case DOWNRIGHT:
				sprite.setTexture(texCarSize03FrontLeft01);
				if (flipSpriteX)
					sprite.setFlip(false, false);
				else
					sprite.setFlip(true, false);
				break;
			case LEFT:
				sprite.setTexture(texCarSize03Left01);
				if (flipSpriteX)
					sprite.setFlip(true, false);
				else
					sprite.setFlip(false, false);
				break;
			case RIGHT:
				sprite.setTexture(texCarSize03Left01);
				if (flipSpriteX)
					sprite.setFlip(false, false);
				else
					sprite.setFlip(true, false);
				break;
			case UP:
				sprite.setTexture(texCarSize03Back01);
				sprite.setFlip(false, false);
				break;
			case UPLEFT:
				sprite.setTexture(texCarSize03BackLeft01);
				if (flipSpriteX)
					sprite.setFlip(true, false);
				else
					sprite.setFlip(false, false);
				break;
			case UPRIGHT:
				sprite.setTexture(texCarSize03BackLeft01);
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
				sprite.setTexture(texCarSize02Front01);
				sprite.setFlip(false, false);
				break;
			case DOWNLEFT:
				sprite.setTexture(texCarSize02FrontLeft01);
				if (flipSpriteX)
					sprite.setFlip(true, false);
				else
					sprite.setFlip(false, false);
				break;
			case DOWNRIGHT:
				sprite.setTexture(texCarSize02FrontLeft01);
				if (flipSpriteX)
					sprite.setFlip(false, false);
				else
					sprite.setFlip(true, false);
				break;
			case LEFT:
				sprite.setTexture(texCarSize02Left01);
				if (flipSpriteX)
					sprite.setFlip(true, false);
				else
					sprite.setFlip(false, false);
				break;
			case RIGHT:
				sprite.setTexture(texCarSize02Left01);
				if (flipSpriteX)
					sprite.setFlip(false, false);
				else
					sprite.setFlip(true, false);
				break;
			case UP:
				sprite.setTexture(texCarSize02Back01);
				sprite.setFlip(false, false);
				break;
			case UPLEFT:
				sprite.setTexture(texCarSize02BackLeft01);
				if (flipSpriteX)
					sprite.setFlip(true, false);
				else
					sprite.setFlip(false, false);
				break;
			case UPRIGHT:
				sprite.setTexture(texCarSize02BackLeft01);
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
				sprite.setTexture(texCarSize01Front01);
				sprite.setFlip(false, false);
				break;
			case DOWNLEFT:
				sprite.setTexture(texCarSize01FrontLeft01);
				if (flipSpriteX)
					sprite.setFlip(true, false);
				else
					sprite.setFlip(false, false);
				break;
			case DOWNRIGHT:
				sprite.setTexture(texCarSize01FrontLeft01);
				if (flipSpriteX)
					sprite.setFlip(false, false);
				else
					sprite.setFlip(true, false);
				break;
			case LEFT:
				sprite.setTexture(texCarSize01Left01);
				if (flipSpriteX)
					sprite.setFlip(true, false);
				else
					sprite.setFlip(false, false);
				break;
			case RIGHT:
				sprite.setTexture(texCarSize01Left01);
				if (flipSpriteX)
					sprite.setFlip(false, false);
				else
					sprite.setFlip(true, false);
				break;
			case UP:
				sprite.setTexture(texCarSize01Back01);
				sprite.setFlip(false, false);
				break;
			case UPLEFT:
				sprite.setTexture(texCarSize01BackLeft01);
				if (flipSpriteX)
					sprite.setFlip(true, false);
				else
					sprite.setFlip(false, false);
				break;
			case UPRIGHT:
				sprite.setTexture(texCarSize01BackLeft01);
				if (flipSpriteX)
					sprite.setFlip(false, false);
				else
					sprite.setFlip(true, false);
				break;
			}
			break;
		}
	}

	@Override
	public void render3D(ModelBatch batch, float delta) {
		
	}

	@Override
	public void destroy() {
		
	}
}
