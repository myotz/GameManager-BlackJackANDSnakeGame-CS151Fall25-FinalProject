package blackjack.model;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Deck {
    private final List<Card> cards;
    private int currentIndex = 0;
    private static final String[] SUITS = {"Hearts", "Diamonds", "Clubs", "Spades"};

    public Deck() {
        cards = new ArrayList<>();
        // initialize deck with 52 cards
        for (String suit : SUITS) {
            for (Rank rank : Rank.values()) {
                int value;
                switch (rank) {
                    case TWO: value = 2; break;
                    case THREE: value = 3; break;
                    case FOUR: value = 4; break;
                    case FIVE: value = 5; break;
                    case SIX: value = 6; break;
                    case SEVEN: value = 7; break;
                    case EIGHT: value = 8; break;
                    case NINE: value = 9; break;
                    case TEN:
                    case JACK:
                    case QUEEN:
                    case KING:
                        value = 10; break;
                    case ACE:          
                        value = 11; break; // initially treat Ace as 11
                    default:
                        value = 0; // should not happen
                }
                cards.add(new Card(suit, rank, value));
            }
        }
        shuffle(); // shuffle the deck upon creation
    }

    
    public void shuffle() {
        Collections.shuffle(cards, new Random());
        currentIndex = 0;
    }

    
    public Card dealCard() {
        if (currentIndex >= cards.size()) {
          // reshuffle if out of cards
            shuffle();
        }
        return cards.get(currentIndex++);
    }

    // returns number of remaining cards in the deck
    public int size() {
        return cards.size() - currentIndex;
    }

    @Override
    public String toString() {
        return "Deck(" + size() + " cards remaining)";
    }
}

