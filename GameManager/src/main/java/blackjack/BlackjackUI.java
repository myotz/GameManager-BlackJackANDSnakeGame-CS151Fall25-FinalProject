package blackjack;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import blackjack.model.*;
import manager.GameManager;
import manager.models.User;

public class BlackjackUI extends BorderPane implements BlackJackGame.Listener {
    private final User CurUser;
    private final BlackJackGame game;
    private final GameManager gameManager;

    // Main menu nodes
    private final VBox menuPane = new VBox(12);

    // Table nodes
    private final VBox tablePane = new VBox(10);
    private final Label phaseLabel = new Label();
    private final Label turnLabel = new Label();
    private final Label msgLabel = new Label();

    private final Label humanMoney = new Label();
    private final Label bot1Money = new Label();
    private final Label bot2Money = new Label();
    private final Label dealerMoney = new Label();

    private final Label humanBet = new Label();
    private final Label bot1Bet = new Label();
    private final Label bot2Bet = new Label();
    private final Label dealerBet = new Label();

    private final HBox humanCards = new HBox(6);
    private final HBox bot1Cards = new HBox(6);
    private final HBox bot2Cards = new HBox(6);
    private final HBox dealerCards = new HBox(6);

    private final Button hitBtn = new Button("Hit");
    private final Button standBtn = new Button("Stand");
    private final Button nextRoundBtn = new Button("Play Next Round");

    private final Button bet1 = new Button("+1");
    private final Button bet5 = new Button("+5");
    private final Button bet25 = new Button("+25");
    private final Button bet50 = new Button("+50");
    private final Button betClear = new Button("Clear Bet");
    private final Button betDeal = new Button("Deal");

    private final Button saveBtn = new Button("Save");
    private final Button loadBtn = new Button("Load");

    public BlackjackUI(GameManager manager) {
        this.gameManager = manager;
        this.CurUser = manager.getCurrentUser();
        int savedMoney = gameManager.getHighScoreManager().getBlackjackScore(CurUser.getUserName());
        this.game = new BlackJackGame(CurUser.getUserName(), savedMoney);

        this.game.setListener(this);

        setPadding(new Insets(10));

        buildMenuPane();
        buildTablePane();

        setCenter(menuPane);
        setStyle("-fx-background-color: #098109e7;");

    }

    private void buildMenuPane() {
        menuPane.setAlignment(Pos.CENTER);
        menuPane.setPadding(new Insets(20));

        Label title = new Label("Blackjack");
        title.setFont(Font.font(28));

        Button newGame = new Button("Start New Game");
        newGame.setOnAction(e -> {
            int savedMoney = gameManager.getHighScoreManager().getBlackjackScore(CurUser.getUserName());
            game.newGame(CurUser.getUserName(), savedMoney);
            setCenter(tablePane);
        });

        Button load = new Button("Load from Save String");
        load.setOnAction(e -> loadGame());

        menuPane.getChildren().addAll(title, newGame, load);
    }

    private void buildTablePane() {
        tablePane.setPadding(new Insets(10));
        tablePane.setAlignment(Pos.TOP_CENTER);

        phaseLabel.setFont(Font.font(16));
        turnLabel.setFont(Font.font(16));
        msgLabel.setFont(Font.font(14));

        // Player rows
        VBox rows = new VBox(10);
        rows.getChildren().addAll(
                playerRow(CurUser.getUserName(), humanMoney, humanBet, humanCards),
                playerRow("Bot 1", bot1Money, bot1Bet, bot1Cards),
                playerRow("Bot 2", bot2Money, bot2Bet, bot2Cards),
                playerRow("Dealer", dealerMoney, dealerBet, dealerCards));

        // Controls
        HBox actionBox = new HBox(10, hitBtn, standBtn);
        actionBox.setAlignment(Pos.CENTER);

        HBox betBox = new HBox(8, new Label("Bet:"), bet1, bet5, bet25, bet50, betClear, betDeal);
        betBox.setAlignment(Pos.CENTER);

        HBox saveLoad = new HBox(10, saveBtn, loadBtn, nextRoundBtn);
        saveLoad.setAlignment(Pos.CENTER);

        // actions
        hitBtn.setOnAction(e -> game.humanHit());
        standBtn.setOnAction(e -> game.humanStand());

        bet1.setOnAction(e -> game.humanBetAdd(1));
        bet5.setOnAction(e -> game.humanBetAdd(5));
        bet25.setOnAction(e -> game.humanBetAdd(25));
        bet50.setOnAction(e -> game.humanBetAdd(50));
        betClear.setOnAction(e -> game.humanBetClear());
        betDeal.setOnAction(e -> {
            if (game.getState().human.getBet() <= 0) {
                Alert a = new Alert(Alert.AlertType.WARNING, "Please place a bet before dealing!", ButtonType.OK);
                a.showAndWait();
            } else {
                game.startRoundIfReady();
            }
        });

        saveBtn.setOnAction(e -> saveGame());
        loadBtn.setOnAction(e -> loadGame());
        nextRoundBtn.setOnAction(e -> {
            int savedMoney = gameManager.getHighScoreManager().getBlackjackScore(CurUser.getUserName());
            game.newGame(CurUser.getUserName(), savedMoney);
        });

        tablePane.getChildren().addAll(
                phaseLabel, turnLabel, msgLabel,
                rows,
                actionBox,
                betBox,
                saveLoad);

        refresh(game.getState(), "Ready.");
    }

    private Node playerRow(String title, Label moneyLbl, Label betLbl, HBox cardsBox) {
        cardsBox.setAlignment(Pos.CENTER_LEFT);
        var name = new Label(title + ": ");
        name.setStyle("-fx-font-weight: bold;");

        var box = new VBox(
                new HBox(10, name, new Label("Money:"), moneyLbl, new Label("Bet:"), betLbl),
                new HBox(10, new Label("Cards:"), cardsBox));
        box.setPadding(new Insets(6));
        box.setStyle(
                "-fx-border-color: #931212ff; -fx-border-radius: 6; -fx-padding: 6; -fx-background-color: #b285ede9;");
        return box;
    }

    // Save state
    private void saveGame() {
        String save = game.save();
        TextArea area = new TextArea(save);
        area.setEditable(false);
        area.setWrapText(true);
        area.setPrefRowCount(8);
        Dialog<Void> dlg = new Dialog<>();
        dlg.setTitle("Save String");
        dlg.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
        dlg.getDialogPane().setContent(area);
        dlg.showAndWait();
    }

    // load state
    private void loadGame() {
        TextArea area = new TextArea();
        area.setWrapText(true);
        area.setPrefRowCount(8);
        Dialog<ButtonType> dlg = new Dialog<>();
        dlg.setTitle("Load Save String");
        dlg.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        dlg.getDialogPane().setContent(area);
        var res = dlg.showAndWait();
        if (res.isPresent() && res.get() == ButtonType.OK) {
            boolean ok = game.load(area.getText().trim());
            if (ok)
                setCenter(tablePane);
        }
    }

    @Override
    public void onStateChanged(GameState state, String message) {
        refresh(state, message);
    }

    @Override
    public void onRoundEnded(GameState state, String message) {
        // to decide Outcome of the rounds
        GameLogic.Outcome humanOutcome = GameLogic.compare(state.human, state.dealer);
        GameLogic.Outcome bot1Outcome = GameLogic.compare(state.bot1, state.dealer);
        GameLogic.Outcome bot2Outcome = GameLogic.compare(state.bot2, state.dealer);

        int humanBet = state.human.getBet();
        int bot1Bet = state.bot1.getBet();
        int bot2Bet = state.bot2.getBet();

        // Calculate winnings/losses based on outcome
        int humanChange = switch (humanOutcome) {
            case WIN -> humanBet;
            case LOSE -> -humanBet;
            case PUSH -> 0;
        };

        int bot1Change = switch (bot1Outcome) {
            case WIN -> bot1Bet;
            case LOSE -> -bot1Bet;
            case PUSH -> 0;
        };

        int bot2Change = switch (bot2Outcome) {
            case WIN -> bot2Bet;
            case LOSE -> -bot2Bet;
            case PUSH -> 0;
        };

        StringBuilder result = new StringBuilder("Round Results: ");

        // Human
        switch (humanOutcome) {
            case WIN -> result.append(CurUser.getUserName())
                    .append(" won $").append(Math.abs(humanChange / 2))
                    .append(" | ");
            case LOSE -> result.append(CurUser.getUserName())
                    .append(" lost $").append(Math.abs(humanBet)).append(" | ");
            case PUSH -> result.append(CurUser.getUserName())
                    .append(" pushed | ");
        }

        // Bot 1
        switch (bot1Outcome) {
            case WIN -> result.append("Bot 1 won $").append(Math.abs(bot1Change)).append(" | ");
            case LOSE -> result.append("Bot 1 lost $").append(Math.abs(bot1Change)).append(" | ");
            case PUSH -> result.append("Bot 1 pushed | ");
        }

        // Bot 2
        switch (bot2Outcome) {
            case WIN -> result.append("Bot 2 won $").append(Math.abs(bot2Change));
            case LOSE -> result.append("Bot 2 lost $").append(Math.abs(bot2Change));
            case PUSH -> result.append("Bot 2 pushed");
        }

        msgLabel.setText(result.toString());
        msgLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: Red;");
        msgLabel.setFont(Font.font(24));

        state.message = result.toString();

        // Update UI money values
        humanMoney.setText("$" + state.human.getMoney());
        bot1Money.setText("$" + state.bot1.getMoney());
        bot2Money.setText("$" + state.bot2.getMoney());

        refresh(game.getState(), result.toString());
        int currentMoney = state.human.getMoney();
        gameManager.getHighScoreManager()
                .updateScore(CurUser.getUserName(), currentMoney, null);
    }

    // Rendering
    private void refresh(GameState gs, String msg) {
        phaseLabel.setText("Phase: " + gs.phase);
        String whose = switch (gs.turnIndex) {
            case 0 -> CurUser.getUserName();
            case 1 -> "Bot 1";
            case 2 -> "Bot 2";
            case 3 -> "Dealer";
            default -> "?";
        };
        turnLabel.setText("Turn: " + whose);
        msgLabel.setText(msg == null ? "" : msg);

        // Balances / bets
        humanMoney.setText("$" + gs.human.getMoney());
        bot1Money.setText("$" + gs.bot1.getMoney());
        bot2Money.setText("$" + gs.bot2.getMoney());
        dealerMoney.setText("$0");

        humanBet.setText("$" + gs.human.getBet());
        bot1Bet.setText("$" + gs.bot1.getBet());
        bot2Bet.setText("$" + gs.bot2.getBet());
        dealerBet.setText("$" + gs.dealer.getBet());

        // Cards
        fillCards(humanCards, gs.human.getHand(), true);
        fillCards(bot1Cards, gs.bot1.getHand(), true);
        fillCards(bot2Cards, gs.bot2.getHand(), true);
        fillDealerCards(dealerCards, gs.dealer.getHand(), gs.revealDealerHole);

        boolean humanTurn = gs.phase == GameState.Phase.DEAL && gs.turnIndex == 0 && !gs.human.isBust();
        hitBtn.setDisable(!humanTurn);
        standBtn.setDisable(!humanTurn);

        boolean betting = gs.phase == GameState.Phase.BETTING;
        bet1.setDisable(!betting);
        bet5.setDisable(!betting);
        bet25.setDisable(!betting);
        bet50.setDisable(!betting);
        betClear.setDisable(!betting);
        betDeal.setDisable(!betting);
    }

    private void fillCards(HBox box, Hand hand, boolean showAll) {
        box.getChildren().clear();
        for (Card c : hand.getCards()) {
            box.getChildren().add(CardImage.createCardImage(c.toString(), !showAll));
        }
        if (hand.getCards().isEmpty()) {
            box.getChildren().add(CardImage.createCardImage("back", true));
        }
    }

    private void fillDealerCards(HBox box, Hand hand, boolean revealHole) {
        box.getChildren().clear();
        var cards = hand.getCards();
        for (int i = 0; i < cards.size(); i++) {
            boolean hidden = (i == 1 && !revealHole);
            box.getChildren().add(CardImage.createCardImage(cards.get(i).toString(), hidden));
        }
        if (cards.isEmpty()) {
            box.getChildren().add(CardImage.createCardImage("back", true));
        }
    }
}
