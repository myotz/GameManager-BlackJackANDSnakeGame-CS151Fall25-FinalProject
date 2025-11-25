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
        hbox = new HBox(80);
        hbox.setPadding(new Insets(20));
        hbox.setAlignment(Pos.CENTER);
        hbox.setStyle("-fx-background-color: #bad137e7;");

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

        // volume box
        VBox settingsBox = new VBox(15);
        settingsBox.setAlignment(Pos.CENTER_LEFT);

        // slider + initalizing vol
        Label musicVolumeLabel = new Label("Snake Music Volume:");
        musicVolumeLabel.setFont(Font.font(14));
        Slider musicVolumeSlider = new Slider(0.0, 1.0, gameManager.getMusicVolume());
        musicVolumeSlider.setShowTickMarks(true);
        musicVolumeSlider.setShowTickLabels(true);
        musicVolumeSlider.setPrefWidth(150);

        //// whenever slider is changed, update the actual vol value
        musicVolumeSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            gameManager.setMusicVolume(newVal.doubleValue());
        });

        Label sfxVolumeLabel = new Label("Snake SFX Volume:");
        sfxVolumeLabel.setFont(Font.font(14));
        Slider sfxVolumeSlider = new Slider(0.0, 1.0, gameManager.getSfxVolume());
        sfxVolumeSlider.setShowTickMarks(true);
        sfxVolumeSlider.setShowTickLabels(true);
        sfxVolumeSlider.setPrefWidth(150);

        sfxVolumeSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            gameManager.setSfxVolume(newVal.doubleValue());
        });
        // add both sets of controls to the settings box
        settingsBox.getChildren().addAll(
                musicVolumeLabel,
                musicVolumeSlider,
                new Separator(),
                sfxVolumeLabel,
                sfxVolumeSlider);

        gameButtons.getChildren().addAll(welcome, playBJ, playSnake, settingsBox, logout);

        hbox.getChildren().addAll(scoresBox, new Separator(), gameButtons);
    }

    public HBox getLayout() {
        return hbox;
    }
}
