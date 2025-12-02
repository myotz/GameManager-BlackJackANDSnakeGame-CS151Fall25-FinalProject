package manager;

import javafx.application.Application;
import javafx.stage.Stage;

public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        GameManager gameManager = new GameManager(primaryStage);
        gameManager.showLoginScreen();
        primaryStage.setTitle("CS 151 Game Manager");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
