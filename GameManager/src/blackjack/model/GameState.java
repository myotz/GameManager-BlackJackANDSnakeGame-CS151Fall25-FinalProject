package blackjack.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the current state of the Blackjack game
 * Tracks which phase we're in, who’s turn it is.
 */
public class GameState {

    // Game Phases
    public enum Phase {
        BETTING,
        DEAL,
        TURN_HUMAN,
        TURN_BOT1,
        TURN_BOT2,
        TURN_DEALER,
        SETTLE,
        RESET
    }

    public Deck deck;
    public Player human;
    public Bot bot1;
    public Bot bot2;
    public Dealer dealer;

    //Game control state
    public Phase phase = Phase.BETTING;
    public int round = 1;
    public int turnIndex = 0; // 0=human, 1=bot1, 2=bot2, 3=dealer
    public boolean revealDealerHole = false;
    public String message = "";

    //Constructor

    public GameState() {
        this("Human", 1000); 
    }

    public GameState(String username, int startingMoney) {
        this.deck = new Deck();
        this.human = new Player(username, startingMoney);
        this.bot1 = new Bot("Bot 1", 1000, 16);
        this.bot2 = new Bot("Bot 2", 1000, 14);
        this.dealer = new Dealer();
    }

    // Turn order helper
    public List<Player> turnOrder() {
        List<Player> list = new ArrayList<>();
        list.add(human);
        list.add(bot1);
        list.add(bot2);
        list.add(dealer);
        return list;
    }

    //Turn progression 
    public Player currentPlayer() {
        return turnOrder().get(turnIndex);
    }

    public void nextTurn() {
        turnIndex = (turnIndex + 1) % 4;
    }

    // Reset for a new round
    public void resetRound() {
        for (Player p : turnOrder()) {
            p.clear();
        }
        deck.shuffle();
        revealDealerHole = false;
        phase = Phase.BETTING;
        message = "New round started!";
        round++;
    }

    // Getter / Setter
    public Deck getDeck() {
        return deck;
    }

    public Player getHuman() {
        return human;
    }

    public Bot getAi1() {
        return bot1;
    }

    public Bot getAi2() {
        return bot2;
    }

    public Dealer getDealer() {
        return dealer;
    }

    public Phase getPhase() {
        return phase;
    }

    public int getRound() {
        return round;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "Round " + round + " | Phase: " + phase +
                " | Turn: " + turnIndex +
                " | Message: " + message;
    }
}
