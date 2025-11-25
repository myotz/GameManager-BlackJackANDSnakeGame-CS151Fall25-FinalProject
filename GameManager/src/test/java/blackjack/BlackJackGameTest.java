package test.java.blackjack;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BlackJackGameTest {

    @Test
    public void testInitialMoney() {
        BlackJackGame game = new BlackJackGame("Alice", 1000);
        assertEquals(1000, game.getMoney("Alice")); // ensure you have getMoney()
    }
}
