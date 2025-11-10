package manager;

import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import manager.models.AccountManager;
import manager.models.HighScoreManager;
import manager.models.User;

public class GameManager {

    private final Stage primaryStage;
    private final AccountManager accountManager;
    private final HighScoreManager highScoreManager;
    private User currentUser;

    public GameManager(Stage stage) {
        this.primaryStage = stage;
        this.accountManager = new AccountManager();
        this.highScoreManager = new HighScoreManager();
    }
    
    // Getters
    public AccountManager getAccountManager() {
        return accountManager;
    }

    public HighScoreManager getHighScoreManager() {
        return highScoreManager;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User user) {
        this.currentUser = user;
    }

    public void showLoginScreen() {
        LoginScreen screen = new LoginScreen(this, accountManager);
        Scene scene = new Scene(screen.getLayout(), 600, 400);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Game Manager - Login");
        primaryStage.show();
    }

    public void showMainMenu() {
        BorderPane root = new BorderPane();
        Toolbar toolbar = new Toolbar(this);
        MainMenu mainMenu = new MainMenu(this, highScoreManager, currentUser);

        root.setTop(toolbar.getLayout());
        root.setCenter(mainMenu.getLayout());

        Scene scene = new Scene(root, 900, 600);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Game Manager - Main Menu");
    }

    public void logout() {
        currentUser = null;
        showLoginScreen();
    }

    public void openBlackjackGame() {
        System.out.println("Launching Blackjack Game...");
        // TODO: Replace with BlackjackUI when ready
    }

    public void openSnakeGame() {
        System.out.println("Launching Snake Game...");
        // TODO: Replace with your SnakeUI when ready
    }
}