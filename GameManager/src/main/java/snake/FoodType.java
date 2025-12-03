package snake;

public enum FoodType {
    APPLE(100), PEAR(200), BANANAPEEL(50);
    private final int scoreValue;

    FoodType(int score) {
        this.scoreValue = score;
    }

    public int getScoreValue() {
        return scoreValue;
    }
}
