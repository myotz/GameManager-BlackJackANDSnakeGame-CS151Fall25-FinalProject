Compare text

Find the difference between two text files
Real-time diff
Unified diff
Collapse lines
Highlight change
Syntax highlighting
Tools
Diffchecker Desktop icon
Diffchecker Desktop The most secure way to run Diffchecker. Get the Diffchecker Desktop app: your diffs never leave your computer!
Untitled diff
20 removals
	
	
	
	
357 lines
20 additions
	
	
	
	
357 lines
package blackjack;

import blackjack.model.*;
import manager.GameManager;

public class BlackjackGame {

    public interface Listener {
        void onStateChanged(GameState state, String message);

        void onRoundEnded(GameState state, String message);
    }

    private GameState state;
    private Listener listener;
    //private final SoundManager sound;
    //private final GameManager manager;

    private int startHumanMoney;
    private int startBot1Money;
    private int startBot2Money;
    private int lastHumanBet;

    public BlackjackGame(String username, int startingMoney) {
        state = new GameState(username, startingMoney);
        // state.human = new Player(username, startingMoney);
        state.bot1 = new Bot("Bot 1", 1000, 16);
        state.bot2 = new Bot("Bot 2", 1000, 14);
        state.dealer = new Dealer();
        //sound = new SoundManager(manager.getMusicVolume(), manager.getSfxVolume());

        //sound.playBackground();
    }

    public void setListener(Listener listener) {
        this.listener = listener;
        notifyChange("Welcome to Blackjack! Start a new game or load a save.");
    }

    public GameState getState() {
        return state;
    }

    public int getStartHumanMoney() {
        return startHumanMoney;
    }

    public int getStartBot1Money() {
        return startBot1Money;
    }

    public int getStartBot2Money() {
        return startBot2Money;
    }

    public int getLastHumanBet() {
        return lastHumanBet;
    }

    public void newGame(String username, int money) {
        state.resetRound();
        // state = new GameState(username, money);
        state.phase = GameState.Phase.BETTING;
        state.turnIndex = 0;
        state.revealDealerHole = false;
        state.message = "Started new round. Place your bet!";
        //sound.stopBackground();
        //sound.playBackground();
        notifyChange(state.message);
    }

    public void startRoundIfReady() {
        if (state.phase != GameState.Phase.BETTING)
            return;

        //sound.playDrawCard();
        // Save starting money for this round
        startHumanMoney = state.human.getMoney();
        startBot1Money = state.bot1.getMoney();
        startBot2Money = state.bot2.getMoney();

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
                // sound.playDrawCard();
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
        //sound.playDrawCard();

        if (state.human.isBust()) {
            state.message = "Human busts!";
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
            //sound.playDrawCard();
Saved diffs
Your saved diffs will appear here.
undefined icon

Fastest Google Search API. Get results in 0.6s with 100% success. No blocks. No slowdowns. Just speed.
ads via Carbon
Original text
320
321
322
323
324
325
326
327
328
329
330
331
332
333
334
335
336
337
338
339
340
341
342
343
344
345
346
347
348
349
350
351
352
353
354
355
356
357
            if (cardsDealt && state.phase == GameState.Phase.BETTING) {
                state.phase = GameState.Phase.DEAL;
            }

            notifyChange("Game loaded.");
            //if (sound != null) {
                //sound.playBackground();
            //}

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

Changed text
320
321
322
323
324
325
326
327
328
329
330
331
332
333
334
335
336
337
338
339
340
341
342
343
344
345
346
347
348
349
350
351
352
353
354
355
356
357
            if (cardsDealt && state.phase == GameState.Phase.BETTING) {
                state.phase = GameState.Phase.DEAL;
            }

            notifyChange("Game loaded.");
            // if (sound != null) {
            // sound.playBackground();
            // }

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

Web Awesome icon
Make Something Awesome Build faster with Web Awesome — open-source web components for modern developers.
Squarespace icon
Grow your client list with Squarespace With Squarespace, you can book projects, send documents, and get paid—all in one place.

    © 2025 Checker Software Inc.ContactCLITermsPrivacy PolicyAPIiManageCompare Text

    EnglishFrançaisEspañolPortuguêsItalianoDeutschहिन्दी简体繁體日本語

