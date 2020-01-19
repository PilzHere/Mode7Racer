package mysko.pilzhere.mode7racer.entities;

public enum SpriteDirection {
	UP(1),
	UPLEFT(2),
	LEFT(3),
	DOWNLEFT(4),
	DOWN(5),
	DOWNRIGHT(6),
	RIGHT(7),
	UPRIGHT(8);
	
	private final int direction;
    private SpriteDirection(int value) {
        this.direction = value;
    }

    public int getDirection() {
        return direction;
    }
}
