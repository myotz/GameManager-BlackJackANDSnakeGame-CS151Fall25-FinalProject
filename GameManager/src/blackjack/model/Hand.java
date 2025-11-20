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
            total += c.getValue(); // use Rank’s built-in value
            if (c.getRank() == Rank.ACE) {
                aceCount++;
            }
        }

        // downgrade Aces from 11 → 1 if busting
        while (total > 21 && aceCount > 0) {
            total -= 10; // adjust one Ace
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
            total += c.getValue();
            if (c.getRank() == Rank.ACE) {
                aceCount++;
            }
        }

        int softAces = aceCount;
        while (total > 21 && softAces > 0) {
            total -= 10;
            softAces--;
        }

        return softAces > 0;
    }

    // clear the cards
    public void clear() {
        cards.clear();
    }

    //H-A, C-9, D-2, etc
    public String encode() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < cards.size(); i++) {
            if (i > 0)
                sb.append(",");
            sb.append(cards.get(i).encode());
        }
        return sb.toString();
    }

    
    public static Hand decode(String data) {
        Hand h = new Hand();
        if (data == null || data.isEmpty())
            return h;
        for (String t : data.split(",")) {
            h.add(Card.decode(t));
        }
        return h;
    }

    @Override
    public String toString() {
        return cards.toString() + " (" + value() + ")";
    }
}
