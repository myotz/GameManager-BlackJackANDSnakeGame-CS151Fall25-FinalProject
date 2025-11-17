package manager;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import manager.models.HighScoreManager;
import manager.models.User;

import java.util.List;
import java.util.Map;

public class MainMenu {
    private final HBox hbox;

    public MainMenu(GameManager gameManager, HighScoreManager highScoreManager, User curUser) {
<<<<<<< HEAD
        hbox = new HBox(80);
        hbox.setPadding(new Insets(20));
        hbox.setAlignment(Pos.CENTER);
        hbox.setStyle("-fx-background-color: #bad137e7;");
=======
        hbox = new HBox(50);
        hbox.setPadding(new Insets(20));
        hbox.setAlignment(Pos.CENTER);
>>>>>>> chan

        VBox scoresBox = new VBox(20);
        scoresBox.setAlignment(Pos.TOP_LEFT);

        VBox blackjackBox = new VBox(5);
        blackjackBox.setAlignment(Pos.TOP_LEFT);

        Label bjLabel = new Label("Top 5 Blackjack Scores:");
        bjLabel.setFont(Font.font(16));

        List<Map.Entry<String, int[]>> bjScores = highScoreManager.getTop5Blackjack();
        for (Map.Entry<String, int[]> entry : bjScores) {
            int blackjackScore = entry.getValue()[0];
            blackjackBox.getChildren().add(new Label(entry.getKey() + " - " + blackjackScore));
        }

        VBox snakeBox = new VBox(5);
        snakeBox.setAlignment(Pos.TOP_LEFT);

        Label snakeLabel = new Label("Top 5 Snake Scores:");
        snakeLabel.setFont(Font.font(16));

        List<Map.Entry<String, int[]>> snakeScores = highScoreManager.getTop5SnakeGame();
        for (Map.Entry<String, int[]> entry : snakeScores) {
            int snakeScore = entry.getValue()[1];
            snakeBox.getChildren().add(new Label(entry.getKey() + " - " + snakeScore));
        }

        scoresBox.getChildren().addAll(bjLabel, blackjackBox, new Separator(), snakeLabel, snakeBox);

        VBox gameButtons = new VBox(25);
        gameButtons.setAlignment(Pos.TOP_RIGHT);

        Label welcome = new Label("Welcome " + curUser.getProfileName());
        welcome.setFont(Font.font(16));

        Button playBJ = new Button("Play Blackjack");
        Button playSnake = new Button("Play Snake");
        Button logout = new Button("Log out");

        playBJ.setOnAction(e -> gameManager.openBlackjackGame());
        playSnake.setOnAction(e -> gameManager.openSnakeGame());
        logout.setOnAction(e -> gameManager.logout());

        gameButtons.getChildren().addAll(welcome, playBJ, playSnake, logout);

        hbox.getChildren().addAll(scoresBox, new Separator(), gameButtons);
    }

    public HBox getLayout() {
        return hbox;
    }
}
