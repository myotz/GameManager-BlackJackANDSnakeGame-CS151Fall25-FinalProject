package blackjack.model;

/*enum Rank {
   TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, TEN, JACK, QUEEN, KING, ACE
}*/

public class Card {
    private final Suit suit;
    private final Rank rank;
    // private final int value;

    public Card(Suit suit, Rank rank) {
        this.suit = suit;
        this.rank = rank;
        // this.value = value;
    }

    public Suit getSuit() {
        return suit;
    }

    public Rank getRank() {
        return rank;
    }

    public int getValue() {
        return rank.getValue();
    }
    
    public String encode() {
        return suit.code() + "-" + rank.code();
    }

    public static Card decode(String token) {
        String[] parts = token.split("-", 2);
        return new Card(Suit.fromCode(parts[0]), Rank.fromCode(parts[1]));
    }

    @Override
    public String toString() {
        return rank + " of " + suit;
    }
}
