package manager;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

public class Toolbar {
    private final HBox hbox;

    public Toolbar(GameManager gameManager) {
        hbox = new HBox(10);
        hbox.setPadding(new Insets(10));
<<<<<<< HEAD
        hbox.setStyle("-fx-background-color: #1f9acfe7;");
=======
>>>>>>> chan

        Button mainMenu = new Button("Main Menu");
        mainMenu.setOnAction(e -> gameManager.showMainMenu());

<<<<<<< HEAD
        Button pauseBtn = new Button("Pause");
        pauseBtn.setOnAction(e -> gameManager.pauseGame(pauseBtn));

        Button loggedOut = new Button("Log out");
        loggedOut.setOnAction(e -> gameManager.logout());
        hbox.getChildren().addAll(mainMenu, pauseBtn, loggedOut);
=======
        Button loggedOut = new Button("Log out");
        loggedOut.setOnAction(e -> gameManager.logout());
        hbox.getChildren().addAll(mainMenu, loggedOut);
>>>>>>> chan
    }

    public HBox getLayout() {
        return hbox;
    }
}