package mysko.pilzhere.mode7racer.entities;

public enum SpriteSize {
	ONE(1),
	TWO(2),
	THREE(3),
	FOUR(4),
	FIVE(5),
	SIX(6),
	SEVEN(7),
	EIGHT(8),
	NINE(9);
	
	private final int size;
    SpriteSize(int value) {
        this.size = value;
    }

    public int getSize() {
        return size;
    }
}
