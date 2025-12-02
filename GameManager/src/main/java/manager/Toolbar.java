package manager;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

import javafx.geometry.Pos;
import javafx.scene.control.Label; 
import javafx.scene.control.Slider;
import javafx.scene.input.KeyEvent;

public class Toolbar {
    private final HBox hbox;

    public Toolbar(GameManager gameManager) {
        hbox = new HBox(10);
        hbox.setPadding(new Insets(10));
        hbox.setStyle("-fx-background-color: #1551ded0;");

        Button mainMenu = new Button("Main Menu");
        mainMenu.setOnAction(e -> {
            gameManager.stopSnakeGame();
            gameManager.showMainMenu();
            gameManager.stopBlackjackGame();
        });

        Button pauseBtn = new Button("Pause");
        pauseBtn.setOnAction(e -> gameManager.pauseGame(pauseBtn));

        Button loggedOut = new Button("Log out");
        loggedOut.setOnAction(e -> {
            gameManager.logout();
            gameManager.stopSnakeGame();
            gameManager.stopBlackjackGame();
        });

        Label musicVolumeLabel = new Label("Music:");
        Slider musicVolumeSlider = new Slider(0.0, 1.0, gameManager.getMusicVolume()); 
        musicVolumeSlider.setPrefWidth(80);
        musicVolumeSlider.setFocusTraversable(false);
        musicVolumeSlider.addEventFilter(KeyEvent.ANY, e -> e.consume());
        musicVolumeSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            gameManager.setMusicVolume(newVal.doubleValue()); 
        });

        // SFX Volume Slider
        Label sfxVolumeLabel = new Label("SFX:");
        Slider sfxVolumeSlider = new Slider(0.0, 1.0, gameManager.getSfxVolume()); 
        sfxVolumeSlider.setPrefWidth(80);
        sfxVolumeSlider.setFocusTraversable(false);
        sfxVolumeSlider.addEventFilter(KeyEvent.ANY, e -> e.consume());
        sfxVolumeSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            gameManager.setSfxVolume(newVal.doubleValue()); 
        });

        hbox.getChildren().addAll(mainMenu, pauseBtn, musicVolumeLabel,
             musicVolumeSlider, sfxVolumeLabel, sfxVolumeSlider, loggedOut);
    }

        //hbox.getChildren().addAll(mainMenu, pauseBtn, loggedOut);

    public HBox getLayout() {
        return hbox;
    }
}
