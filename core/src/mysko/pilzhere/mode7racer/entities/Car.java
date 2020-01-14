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
	private final Vector3 oldPosition = new Vector3();

	private Sprite sprite;

//	private Texture currentTexture;

	private Texture texCar01Size09Back01, texCar01Size08Back01, texCar01Size07Back01, texCar01Size06Back01,
			texCar01Size05Back01, texCar01Size04Back01, texCar01Size03Back01, texCar01Size02Back01,
			texCar01Size01Back01;

	private Texture texCar01BigBackTurnRight02;

	private float angle = 0;
	private final float angleIncrement = 2.5f;
	private final float carSpeedIncrement = 0.5f; // was 2 should slow it
	private final int carSpeedIncrementBoost = 32;
	private float carCurrentSpeed;
	private final float carMaximumSpeed = 1.0f;
	private final float motorBrakeStrength = 0.35f;
	private final float brakesStrength = 0.33f;
	private final int bounceBrakesStrength = 16;

	private boolean rightTurn; // For moving bg's.
	private boolean leftTurn;

	private final float rectSize = 0.25f;

	public int hp = 100; // get
	private boolean gotHitTimeSet;
	private long hitNewTime;
	private final long hitCd = 420L;

	public Car(GameScreen screen, Vector3 position) {
		super(screen, position);

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

		sprite = new Sprite(texCar01Size09Back01);

		rect = new Rectangle(position.x - rectSize / 2, position.z - rectSize / 2, rectSize, rectSize);
	}

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

	public void onInputS(float delta) {
		carCurrentSpeed -= carSpeedIncrement * brakesStrength * delta;
	}

	private boolean curbsBlink;
	private final long blinkCd = 100L;
	private long nextBlinkTime;
	private boolean nextBlinkTimeSet;

	@Override
	public void onHit(Object object, float delta) {
		if (object instanceof Curb) {
			// limit speed here?

			curbsBlink = true;

			if (!gotHitTimeSet) {
				hp = hp - 5;
				hitNewTime = currentTime + hitCd;
//				System.err.println("Car damage taken!");
				gotHitTimeSet = true;
			}
		} else if (object instanceof Edge) {
//			System.out.println("Car edge hit!");
		}
	}

	private boolean overlapX;
	private boolean overlapY;

	private float newPosX;
	private float newPosZ;

	private float currentBounceX;
	private float currentBounceZ;
	private final int bounceDecrement = 10;
	private boolean bounceX;
	private boolean bounceZ;
	private final long bounceTime = 175L; // time until bounce released.
	private final int bounceAmount = 20;
	private long bounceXTotalTime;
	private long bounceZTotalTime;
	private boolean bounceTimerXSet;
	private boolean bounceTimerZSet;

	private long currentTime;

	private final Vector3 camTempPos = new Vector3();

	@Override
	public void tick(float delta) {
		currentTime = System.currentTimeMillis();

		overlapX = false;
		overlapY = false;

		if (curbsBlink) {
			if (!nextBlinkTimeSet) {
				screen.currentMap.blink = !screen.currentMap.blink;
				nextBlinkTime = currentTime + blinkCd;
				nextBlinkTimeSet = true;
			} else {
				if (currentTime > nextBlinkTime) {
					curbsBlink = false;
					nextBlinkTimeSet = false;
				}
			}
		} else {
			nextBlinkTimeSet = false;
			screen.currentMap.blink = false;
		}

		if (gotHitTimeSet) {
			if (currentTime > hitNewTime) {
				gotHitTimeSet = false;
			}
		}

		if (bounceX) {
			if (!bounceTimerXSet) {
				bounceXTotalTime = currentTime + bounceTime;
				currentBounceX = bounceAmount;
				carCurrentSpeed = carCurrentSpeed / 2;
				bounceTimerXSet = true;
			} else {

				if (currentTime > bounceXTotalTime) {
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

		if (bounceZ) {
			if (!bounceTimerZSet) {
				bounceZTotalTime = currentTime + bounceTime;
				currentBounceZ = bounceAmount;
				carCurrentSpeed = carCurrentSpeed / 2;
				bounceTimerZSet = true;
			} else {
				if (currentTime > bounceZTotalTime) {
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

//		clamp angle
		if (angle > 360 * MathUtils.degreesToRadians) {
			final float newAngle = angle - (360 * MathUtils.degreesToRadians);
			angle = newAngle;
		} else if (angle < -360 * MathUtils.degreesToRadians) {
			final float newAngle = angle + (360 * MathUtils.degreesToRadians);
			angle = newAngle;
		}

//		limit car speed

		if (carCurrentSpeed > carMaximumSpeed)
			carCurrentSpeed = carMaximumSpeed;
		else if (carCurrentSpeed < 0)
			carCurrentSpeed = 0;

//		move with speed in angle direction
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

//		Check collisions X
		rect.setX(newPosX - rectSize / 2);

		for (Entity ent : screen.entities) {
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

		if (overlapX) {
			newPosX = position.x; // go back
			bounceX = true;
		} else {
			position.x = newPosX;
		}

		rect.setX(position.x - rectSize / 2);

//		Check collisions Y
		rect.setY(newPosZ - rectSize / 2);

		for (Entity ent : screen.entities) {
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

		if (overlapY) {
			newPosZ = position.z; // go back
			bounceZ = true;
		} else {
			position.z = newPosZ;
		}

		rect.setY(position.z - rectSize / 2);

//		set camera positon
		if (!oldPosition.idt(position)) {
			screen.cam.position.set(position.cpy());
//			screen.cam.position.sub(position.cpy().sub(oldPosition.cpy()).nor().scl(screen.camDesiredDistFromCar)); // old one, works but not too good in rotations.
			camTempPos.x = -carCurrentSpeed * carSpeedIncrementBoost * delta * MathUtils.sin(angle);
			camTempPos.z = carCurrentSpeed * carSpeedIncrementBoost * delta * MathUtils.cos(angle);
			screen.cam.position.add(camTempPos.cpy().nor().scl(screen.camDesiredDistFromCar));

			screen.cam.lookAt(position.cpy());
		}

//		can only turn one direction
		if (rightTurn && leftTurn) {
			rightTurn = false;
			leftTurn = false;
		}

//		move bg depening on turn direction
		if (rightTurn) {
			screen.currentMap.bgFrontPosX += screen.bgMoveSpeed * screen.bgMoveSpeedBoost * delta;
			screen.currentMap.bgBackPosX += screen.bgMoveSpeed * delta;
		} else if (leftTurn) {
			screen.currentMap.bgFrontPosX -= screen.bgMoveSpeed * screen.bgMoveSpeedBoost * delta;
			screen.currentMap.bgBackPosX -= screen.bgMoveSpeed * delta;
		}

//		reset stuff
		oldPosition.set(position.cpy());

//		System.out.println(position.cpy().sub(screen.carTwo.position.cpy()));

//		System.out.println("Speed: " + carCurrentSpeed);
	}

	private final int renderHeightLimit = 186 + 16; // Screen height limit for rendering when in window scale of 1. This is a
												// fix for cars that are behind camera, they might render in the
												// background
												// above player.
	private int currentRenderHeightLimit; // screen position varies by window height
	private boolean render = true;

	@Override
	public void render2D(SpriteBatch batch, float delta) {
		render = true;
		currentRenderHeightLimit = renderHeightLimit * Gdx.graphics.getHeight() / screen.viewportHeight;

		screenPos.set(screen.viewport.project(position.cpy()));
		screenPos.z = 0;

		final float distFromCam = Vector3.dst(position.x, position.y, position.z, screen.cam.position.x,
				screen.cam.position.y, screen.cam.position.z);
		if (distFromCam < 7) {
			if (leftTurn) {
				sprite.setTexture(texCar01BigBackTurnRight02);
				sprite.setFlip(true, false);
			} else if (rightTurn) {
				sprite.setTexture(texCar01BigBackTurnRight02);
				sprite.setFlip(false, false);
			} else {
				sprite.setTexture(texCar01Size09Back01);
			}
		} else if (distFromCam < 14) {
			sprite.setTexture(texCar01Size08Back01);
		} else if (distFromCam < 21) {
			sprite.setTexture(texCar01Size07Back01);
		} else if (distFromCam < 28) {
			sprite.setTexture(texCar01Size06Back01);
		} else if (distFromCam < 35) {
			sprite.setTexture(texCar01Size05Back01);
		} else if (distFromCam < 42) {
			sprite.setTexture(texCar01Size04Back01);
		} else if (distFromCam < 49) {
			sprite.setTexture(texCar01Size03Back01);
		} else if (distFromCam < 56) {
			sprite.setTexture(texCar01Size02Back01);
		} else if (distFromCam < 63) {
			sprite.setTexture(texCar01Size01Back01);
		} else {
			render = false;
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

		if (this == screen.carTwo) {
			System.out.println(distFromCam + " units from cam.");
//			System.out.println(position.y);
		}

		if (screenPos.y > currentRenderHeightLimit)
			render = false;

		if (render)
			sprite.draw(batch);

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
