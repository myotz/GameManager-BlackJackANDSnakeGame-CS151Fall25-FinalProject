package manager;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

public class Toolbar {
    private final HBox hbox;

    public Toolbar(GameManager gameManager) {
        hbox = new HBox(10);
        hbox.setPadding(new Insets(10));

        Button mainMenu = new Button("Main Menu");
        mainMenu.setOnAction(e -> gameManager.showMainMenu());

        Button loggedOut = new Button("Log out");
        loggedOut.setOnAction(e -> gameManager.logout());
        hbox.getChildren().addAll(mainMenu, loggedOut);
    }

    public HBox getLayout() {
        return hbox;
    }
}