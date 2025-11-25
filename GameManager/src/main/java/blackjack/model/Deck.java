package main.java.blackjack.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Deck {
    private final List<Card> cards = new ArrayList<>();
    private int index = 0;
    // private static final String[] SUITS = {"Hearts", "Diamonds", "Clubs",
    // "Spades"};

    public Deck() {
        // cards = new ArrayList<>();
        for (Suit suit : Suit.values()) {
            for (Rank rank : Rank.values()) {
                cards.add(new Card(suit, rank));
            }
        }
        shuffle();
    }

    public void shuffle() {
        Collections.shuffle(cards, new Random());
        index = 0;
    }

    public Card dealCard() {
        if (index >= cards.size()) {
            // reshuffle if out of cards
            shuffle();
        }
        return cards.get(index++);
    }

    // returns number of remaining cards in the deck
    public int remainingCards() {
        return cards.size() - index;
    }

    // index= int|seq=H-3,D-10,...
    // Encoder to save the game state
    public String encode() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < cards.size(); i++) {
            if (i > 0)
                sb.append(",");
            sb.append(cards.get(i).encode());
        }
        return "index=" + index + "|seq=" + sb;
    }

    // To restore and rebuild the deck from the encoded string
    public static Deck decode(String data) {
        String[] parts = data.split("\\|");
        int idx = Integer.parseInt(parts[0].substring(parts[0].indexOf('=') + 1));
        String seq = parts[1].substring(parts[1].indexOf('=') + 1);
        String[] toks = seq.isEmpty() ? new String[0] : seq.split(",");

        Deck d = new Deck();
        d.cards.clear();
        for (String t : toks)
            d.cards.add(Card.decode(t));
        d.index = Math.max(0, Math.min(d.cards.size(), idx));
        return d;
    }

    @Override
    public String toString() {
        return "Deck(" + remainingCards() + " cards remaining)";
    }
}
