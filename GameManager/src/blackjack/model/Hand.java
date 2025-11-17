package blackjack.model;

import java.util.ArrayList;
import java.util.List;

public class Hand {
    private final List<Card> cards;

    public Hand() {
        this.cards = new ArrayList<>();
    }

    public void add(Card c) {
        if (c != null) {
            cards.add(c);
        }
    }

    public List<Card> getCards() {
        return new ArrayList<>(cards); // return copy to preserve encapsulation
    }

    public int value() {
        int total = 0;
        int aceCount = 0;

        for (Card c : cards) {
            switch (c.getRank()) {
                case TWO: total += 2; break;
                case THREE: total += 3; break;
                case FOUR: total += 4; break;
                case FIVE: total += 5; break;
                case SIX: total += 6; break;
                case SEVEN: total += 7; break;
                case EIGHT: total += 8; break;
                case NINE: total += 9; break;
                case TEN:
                case JACK:
                case QUEEN:
                case KING:
                    total += 10; break;
                case ACE:
                    total += 11;
                    aceCount++;
                    break;
            }
        }

        // downgrade Aces from 11 → 1 if busting
        while (total > 21 && aceCount > 0) {
            total -= 10;
            aceCount--;
        }

        return total;
    }

    public boolean isBust() {
        return value() > 21;
    }

    public boolean isSoft() {
        int total = 0;
        int aceCount = 0;

        for (Card c : cards) {
            switch (c.getRank()) {
                case TWO: total += 2; break;
                case THREE: total += 3; break;
                case FOUR: total += 4; break;
                case FIVE: total += 5; break;
                case SIX: total += 6; break;
                case SEVEN: total += 7; break;
                case EIGHT: total += 8; break;
                case NINE: total += 9; break;
                case TEN:
                case JACK:
                case QUEEN:
                case KING:
                    total += 10; break;
                case ACE:
                    total += 11;
                    aceCount++;
                    break;
            }
        }

        // if total ≤ 21 and at least one Ace counted as 11, it's soft
        return total <= 21 && aceCount > 0;
    }

    @Override
    public String toString() {
        return cards.toString() + " (" + value() + ")";
    }
}

