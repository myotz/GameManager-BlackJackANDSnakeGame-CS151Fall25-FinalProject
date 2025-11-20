package blackjack;


import blackjack.model.Dealer;
import blackjack.model.Deck;
import blackjack.model.Player;
import blackjack.model.GameState;


public final class BlackJackController {

    // constants   
    private static final int DEFAULT_AI_BET  = 20;
    private final GameState state;

    // constructor
    public BlackJackController(String username, int startingBalance) {
        this.state = new GameState();
        this.state.deck = new Deck();
        this.state.human = new Player(username, startingBalance);
        this.state.ai1   = new Player("AI-1", startingBalance); 
        this.state.ai2   = new Player("AI-2", startingBalance); 
        this.state.dealer= new Dealer(); 
        this.state.phase = GameState.Phase.BETTING; 
        this.state.round = 1; 
        this.state.message = "Place your bet to start.";
    }

    // Player actions
    public void placeBet(int amount) {
        requirePhase(GameState.Phase.BETTING, "placeBet only allowed in BETTING phase");
        if (amount <= 0) throw new IllegalArgumentException("Bet must be > 0");
        if (amount > state.human.getMoney()) throw new IllegalArgumentException("Insufficient balance");

        state.human.playBet(amount);
        // AI bets
        int aiBet = Math.min(DEFAULT_AI_BET, Math.max(1, state.ai1.getMoney()));
        if (aiBet > 0 && aiBet <= state.ai1.getMoney()) state.ai1.playBet(aiBet);
        aiBet = Math.min(DEFAULT_AI_BET, Math.max(1, state.ai2.getMoney()));
        if (aiBet > 0 && aiBet <= state.ai2.getMoney()) state.ai2.playBet(aiBet);

        state.message = "Bets placed. Click 'Deal' to start.";
    }

    // Check current game state
    private void requirePhase(GameState.Phase expected, String msgIfNot) {
        if (state.phase != expected) {
            throw new IllegalStateException(msgIfNot + " (current=" + state.phase + ")");
        }
    }
}