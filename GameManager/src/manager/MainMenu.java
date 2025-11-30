package manager;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import manager.models.HighScoreManager;
import manager.models.User;
import javafx.scene.image.ImageView;

import java.util.List;
import java.util.Map;

public class MainMenu {
    private final BorderPane root;

    public MainMenu(GameManager gameManager, HighScoreManager highScoreManager, User curUser) {
        root = new BorderPane();
        root.setStyle("-fx-background-color: #18a52fc1;");

        VBox scoreSection = createScoreSection(highScoreManager);
        root.setCenter(scoreSection);

        HBox gameCards = createGameCards(gameManager, curUser);
        root.setBottom(gameCards);
    }

    private VBox createScoreSection(HighScoreManager highScoreManager) {
        BackgroundImage bgImage = new BackgroundImage(
                new Image(getClass().getResource("/assets/trophy.jpg").toExternalForm(),
                        1200, 400, false, true),
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(100, 100, true, true, false, true));

        VBox backgroundBox = new VBox();
        backgroundBox.setBackground(new Background(bgImage));
        backgroundBox.setPadding(new Insets(40, 60, 40, 60));
        backgroundBox.setAlignment(Pos.CENTER);

        String textStyle = "-fx-text-fill: white; -fx-font-weight: bold;";

        VBox blackjackBox = new VBox(8);
        blackjackBox.setAlignment(Pos.TOP_LEFT);

        Label bjTitle = new Label("Top 5 Blackjack Scores:");
        bjTitle.setFont(Font.font(32));
        bjTitle.setStyle(textStyle);
        blackjackBox.getChildren().add(bjTitle);

        List<Map.Entry<String, int[]>> bjScores = highScoreManager.getTop5Blackjack();
        for (Map.Entry<String, int[]> entry : bjScores) {
            Label scoreLabel = new Label(entry.getKey() + " - " + entry.getValue()[0]);
            scoreLabel.setStyle(textStyle);
            scoreLabel.setFont(Font.font(26));
            blackjackBox.getChildren().add(scoreLabel);
        }

        VBox snakeBox = new VBox(8);
        snakeBox.setAlignment(Pos.TOP_RIGHT);

        Label snTitle = new Label("Top 5 Snake Scores:");
        snTitle.setFont(Font.font(32));
        snTitle.setStyle(textStyle);
        snakeBox.getChildren().add(snTitle);

        List<Map.Entry<String, int[]>> snakeScores = highScoreManager.getTop5SnakeGame();
        for (Map.Entry<String, int[]> entry : snakeScores) {
            Label scoreLabel = new Label(entry.getKey() + " - " + entry.getValue()[1]);
            scoreLabel.setStyle(textStyle);
            scoreLabel.setFont(Font.font(26));
            snakeBox.getChildren().add(scoreLabel);
        }

        HBox scoreRow = new HBox(80, blackjackBox, snakeBox);
        scoreRow.setAlignment(Pos.CENTER);

        backgroundBox.getChildren().add(scoreRow);

        return backgroundBox;
    }

    private HBox createGameCards(GameManager gameManager, User curUser) {
        VBox blackjackCard = createGameCard(
                "Blackjack",
                "/assets/blackjack.jpg",
                () -> gameManager.openBlackjackGame());

        VBox snakeCard = createGameCard(
                "Snake",
                "/assets/snake.jpg",
                () -> gameManager.openSnakeGame());

        VBox vbox = new VBox(30);
        Button playBtn = new Button("Play Mario");
        Button logoutBtn = new Button("Log out");
        logoutBtn.setOnAction(e -> gameManager.logout());
        vbox.getChildren().addAll(playBtn, logoutBtn);

        HBox hbox = new HBox(80, blackjackCard, snakeCard, vbox);
        hbox.setAlignment(Pos.CENTER);
        hbox.setPadding(new Insets(25));

        return hbox;
    }

    private VBox createGameCard(String title, String imagePath, Runnable action) {
        Label titleLabel = new Label(title);
        titleLabel.setFont(Font.font(20));

        Image img = new Image(getClass().getResource(imagePath).toExternalForm());

        ImageView imgView = new ImageView(img);
        imgView.setFitWidth(220);
        imgView.setFitHeight(150);
        imgView.setPreserveRatio(true);

        Button playBtn = new Button("Play " + title);
        playBtn.setOnAction(e -> action.run());

        VBox card = new VBox(15, imgView, playBtn);
        card.setAlignment(Pos.CENTER);
        card.setPadding(new Insets(10));
        card.setStyle("-fx-background-color: transparent;");

        return card;
    }

    public BorderPane getLayout() {
        return root;
    }
}
