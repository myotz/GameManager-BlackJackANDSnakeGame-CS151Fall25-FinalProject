package blackjack.model;

public class Dealer extends Player {

    public Dealer() {
        super("Dealer", 0);
    }

    // Dealer's decision logic: hit on soft 17 or less
    public boolean shouldHit() {
        int value = hand.value();
        // Dealer hits on soft 17
        return value < 17 || (value == 17 && hand.isSoft());
    }
}
