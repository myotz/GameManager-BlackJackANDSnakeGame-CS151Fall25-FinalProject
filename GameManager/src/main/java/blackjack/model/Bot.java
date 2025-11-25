package main.java.blackjack.model;

public class Bot extends Player {
    private final int hitThreshold;

    public Bot(String name, int money, int hitThreshold) {
        super(name, money);
        this.hitThreshold = hitThreshold;
    }

    public boolean hit() {
        return getValue() < hitThreshold;
    }

    public int getHitThreshold() {
        return hitThreshold;
    }

    @Override
    public String encodeType(String type) {
        return super.encodeType(type) + ":" + hitThreshold;
    }

    public static int parseThresholdOrDefault(String[] fields, int index, int def) {
        return (fields.length > index) ? Integer.parseInt(fields[index]) : def;
    }

}
