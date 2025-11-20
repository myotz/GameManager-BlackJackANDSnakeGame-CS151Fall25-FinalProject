package blackjack.model;

public class GameState {
    public enum Phase {
        BETTING,
        DEAL,
        TURN_HUMAN,
        TURN_AI1,
        TURN_AI2,
        TURN_DEALER,
        SETTLE,
        RESET
    }

    // Game participants
    public Deck deck;
    public Player human;
    public Player ai1;
    public Player ai2;
    public Dealer dealer;

    // Game state info
    public Phase phase;
    public int round;
    public String message;

    public GameState() {
        // initialize default state
        this.phase = Phase.BETTING;
        this.round = 1;
        this.message = "";
    }

    // ---- Getter / Setter ----
    public Deck getDeck() {
        return deck;
    }

    public Player getHuman() {
        return human;
    }

    public Player getAi1() {
        return ai1;
    }

    public Player getAi2() {
        return ai2;
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
        return "Round " + round + " | Phase: " + phase + " | Msg: " + message;
    }
}
