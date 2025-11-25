package main.java.manager;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

public class Toolbar {
    private final HBox hbox;

    public Toolbar(GameManager gameManager) {
        hbox = new HBox(10);
        hbox.setPadding(new Insets(10));
        hbox.setStyle("-fx-background-color: #1f9acfe7;");

        Button mainMenu = new Button("Main Menu");
        mainMenu.setOnAction(e -> {
            gameManager.stopSnakeGame();
            gameManager.showMainMenu();
        });

        Button pauseBtn = new Button("Pause");
        pauseBtn.setOnAction(e -> gameManager.pauseGame(pauseBtn));

        Button loggedOut = new Button("Log out");
        loggedOut.setOnAction(e -> {
            gameManager.logout();
            gameManager.stopSnakeGame();
        });
        hbox.getChildren().addAll(mainMenu, pauseBtn, loggedOut);
    }

    public HBox getLayout() {
        return hbox;
    }
}
