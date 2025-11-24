package blackjack;

import blackjack.model.*;

public class BlackJackGame {

    public interface Listener {
        void onStateChanged(GameState state, String message);

        void onRoundEnded(GameState state, String message);
    }

    private GameState state;
    private Listener listener;
    private final SoundManager sound;

    public BlackJackGame(String username, int startingMoney) {
        state = new GameState(username, startingMoney);
        // state.human = new Player(username, startingMoney);
        state.bot1 = new Bot("Bot 1", 1000, 16);
        state.bot2 = new Bot("Bot 2", 1000, 14);
        state.dealer = new Dealer();
        sound = new SoundManager(0.5, 0.85);
    }

    public void setListener(Listener listener) {
        this.listener = listener;
        notifyChange("Welcome to Blackjack! Start a new game or load a save.");
    }

    public GameState getState() {
        return state;
    }

    public void newGame(String username, int money) {
        state.resetRound();
        //state = new GameState(username, money);
        state.phase = GameState.Phase.BETTING;
        state.turnIndex = 0;
        state.revealDealerHole = false;
        state.message = "Started new round. Place your bet!";
        notifyChange(state.message);
        sound.playBackground();
    }

    public void startRoundIfReady() {
        if (state.phase != GameState.Phase.BETTING)
            return;

        if (state.bot1.getBet() == 0)
            state.bot1.playBet(Math.min(25, state.bot1.getMoney()));
        if (state.bot2.getBet() == 0)
            state.bot2.playBet(Math.min(25, state.bot2.getMoney()));
        // dealer bet stays 0

        dealInitialCards();
        state.phase = GameState.Phase.DEAL;
        state.turnIndex = 0; // Human first
        state.message = "Cards dealt. Human's turn.";
        notifyChange(state.message);
    }

    private void dealInitialCards() {
        // Clear hands but keep balances
        for (Player p : state.turnOrder())
            p.clear();
        state.revealDealerHole = false;

        // Everyone gets 2 cards
        for (int i = 0; i < 2; i++) {
            for (Player p : state.turnOrder()) {
                p.add(state.deck.dealCard());
                sound.playDrawCard();
            }
        }
    }

    // Betting

    public boolean canHumanBet() {
        return state.phase == GameState.Phase.BETTING;
    }

    public void humanBetAdd(int amount) {
        if (!canHumanBet())
            return;
        try {
            state.human.playBet(amount);
            notifyChange("Bet: +" + amount);
        } catch (IllegalArgumentException ex) {
            notifyChange("Bet failed: " + ex.getMessage());
        }
    }

    public void humanBetClear() {
        if (!canHumanBet())
            return;
        // Return current bet to money
        state.human.pushBet(); // push returns bet to money
        notifyChange("Bet cleared.");
    }

    // Turns: Human Actions

    public boolean isHumansTurn() {
        return state.phase == GameState.Phase.DEAL && state.turnIndex == 0;
    }

    public void humanHit() {
        if (!isHumansTurn())
            return;
        state.human.add(state.deck.dealCard());
        sound.playDrawCard();
        if (state.human.isBust()) {
            state.message = "Human busts!";
            sound.playDealerWin(); 
            nextTurn();
        } else {
            state.message = "Human hits.";
        }
        notifyChange(state.message);
    }

    public void humanStand() {
        if (!isHumansTurn())
            return;

        state.message = "Human stands.";
        notifyChange(state.message);

        // Move to next player (bot1, bot2, dealer)
        nextTurn();

        // If we’re about to hit the dealer’s turn, reveal now
        if (state.turnIndex == 3) {
            state.revealDealerHole = true;
            notifyChange("Dealer reveals the hole card...");
        }

        // Continue auto turns for bots/dealer
        runAutoTurnsIfNeeded();
    }

    // Auto players & dealer

    private void runAutoTurnsIfNeeded() {
        // Continues through bot1, bot2, and dealer
        while (state.phase == GameState.Phase.DEAL && state.turnIndex > 0) {
            if (state.turnIndex == 1) { // Bot1
                autoBotTurn(state.bot1);
            } else if (state.turnIndex == 2) { // Bot2
                autoBotTurn(state.bot2);
            } else if (state.turnIndex == 3) { // Dealer
                autoDealerTurn();
            } else {
                break;
            }
        }
    }

    private void autoBotTurn(Bot bot) {
        // Bot.hit()
        if (!bot.isBust() && bot.hit()) {
            bot.add(state.deck.dealCard());
            sound.playDrawCard();  
            state.message = bot.getName() + " hits.";
            if (bot.isBust()) {
                state.message = bot.getName() + " busts!";
                nextTurn();
            }
        } else {
            state.message = bot.getName() + " stands.";
            nextTurn();
        }
        notifyChange(state.message);

        // Continue until it's not a bot's turn anymore
        if (state.turnIndex == 1 || state.turnIndex == 2)
            return;
    }

    private void autoDealerTurn() {
        // Reveal hole at dealer turn start
        state.revealDealerHole = true;

        Dealer d = state.dealer;
        if (!d.isBust() && d.shouldHit()) {
            d.add(state.deck.dealCard());
            sound.playDrawCard(); 
            state.message = "Dealer hits.";
            if (d.isBust()) {
                state.message = "Dealer busts!";
                nextTurn();
            }
        } else {
            state.message = "Dealer stands.";
            nextTurn();
        }
        notifyChange(state.message);
    }

    private void nextTurn() {
        // move to next player if dealer finished
        state.turnIndex = (state.turnIndex + 1) % 4;

        if (state.turnIndex == 0) {
            settleRound();
        }
    }

    // Settle

    private void settleRound() {
        state.phase = GameState.Phase.SETTLE;
        state.revealDealerHole = true;

        notifyChange("Dealer reveals the hole card...");

        javafx.animation.PauseTransition pause = new javafx.animation.PauseTransition(
                javafx.util.Duration.seconds(0.8));
        pause.setOnFinished(e -> {
            Player dealer = state.dealer;
            
            boolean humanSoundPlayed = false;
            // Determine outcome for each player
            for (Player p : new Player[] { state.human, state.bot1, state.bot2 }) {
                GameLogic.Outcome out = GameLogic.compare(p, dealer);

                switch (out) {
                    case WIN -> {
                        p.winBet();
                        if(!humanSoundPlayed && p == state.human) {
                            sound.playPlayerWin();
                            humanSoundPlayed = true;
                        }
                        //System.out.println(p.getName() + " wins. New balance: " + p.getMoney());
                    }
                    case PUSH -> {
                        p.pushBet(); 
                        //System.out.println(p.getName() + " pushes. New balance: " + p.getMoney());
                    }
                    case LOSE -> {
                        p.loseBet(); 
                        if (!humanSoundPlayed && p == state.human) {
                            sound.playDealerWin(); 
                            humanSoundPlayed = true;
                        }
                        //System.out.println(p.getName() + " loses. New balance: " + p.getMoney());
                    }
                }
            }

            // Clear all bets after settlement
            for (Player p : state.turnOrder()) {
                p.clearBet();
            }

            if (listener != null) {
                listener.onRoundEnded(state, null);
                //listener.onStateChanged(state, "Round updated.");
            }
        });

        pause.play();
    }

    public void startNextRound() {
        state.resetRound();
        if (listener != null) {
            listener.onStateChanged(state, "Started new round. Place your bet!");
        }
        sound.playBackground();
    }
    
    // Save / Load

    public String save() {
        return GameSaveHandler.save(state);
    }

    public boolean load(String save) {
        try {
            GameState loaded = GameSaveHandler.load(save);
            this.state = loaded;

            boolean cardsDealt = !state.human.getHand().getCards().isEmpty() ||
                    !state.bot1.getHand().getCards().isEmpty() ||
                    !state.bot2.getHand().getCards().isEmpty() ||
                    !state.dealer.getHand().getCards().isEmpty();

            if (cardsDealt && state.phase == GameState.Phase.BETTING) {
                state.phase = GameState.Phase.DEAL;
            }

            notifyChange("Game loaded.");

            // Resume the correct turn
            if (state.phase == GameState.Phase.DEAL) {
                if (state.turnIndex == 0) {
                    notifyChange("Your turn resumed.");
                } else {
                    runAutoTurnsIfNeeded();
                }
            }

            return true;
        } catch (Exception ex) {
            notifyChange("Load failed: " + ex.getMessage());
            return false;
        }
    }

    // Helpers

    private void notifyChange(String msg) {
        if (listener != null)
            listener.onStateChanged(state, msg);
        // If it’s bots/dealer turn, keep auto play
        if (state.phase == GameState.Phase.DEAL && state.turnIndex > 0) {
            runAutoTurnsIfNeeded();
        }
    }
}
