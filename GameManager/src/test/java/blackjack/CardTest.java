package blackjack;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import blackjack.model.Card;
import blackjack.model.Rank;
import blackjack.model.Suit;

public class CardTest {

    @Test
    public void testCardConstructor() {
        Card card = new Card(Suit.HEARTS, Rank.ACE);
        assertEquals(Suit.HEARTS, card.getSuit());
        assertEquals(Rank.ACE, card.getRank());
    }

    @Test
    public void testCardValue() {
        Card card = new Card(Suit.CLUBS, Rank.TEN);
        assertEquals(10, card.getValue());
    }

    @Test
    public void testCardToString() {
        Card card = new Card(Suit.SPADES, Rank.QUEEN);
        assertEquals("QUEEN-SPADES", card.toString());
    }

    @Test
    public void testCardEncodeDecode() {
        Card card = new Card(Suit.DIAMONDS, Rank.KING);
        String encoded = card.encode();
        Card decoded = Card.decode(encoded);
        assertEquals(card.getSuit(), decoded.getSuit());
        assertEquals(card.getRank(), decoded.getRank());
    }
}
