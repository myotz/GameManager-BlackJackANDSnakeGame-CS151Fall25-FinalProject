package main.java.manager;

import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import main.java.manager.models.AccountManager;
import main.java.manager.models.HighScoreManager;
import main.java.manager.models.User;
import javafx.scene.control.Button;

public class GameManager {

    private final Stage primaryStage;
    private final AccountManager accountManager;
    private final HighScoreManager highScoreManager;
    private User currentUser;
    private boolean gamePaused = false;
    private main.java.snake.SnakeUI activeSnakeUI;
    private double musicVolume = 0.5; 
    private double sfxVolume = 0.5; 

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

    public double getMusicVolume() {
        return musicVolume;
    }

    public void setMusicVolume(double volume) {
        this.musicVolume = volume;
    }
    
    public double getSfxVolume() {
        return sfxVolume;
    }

    public void setSfxVolume(double volume) {
        this.sfxVolume = volume;
    }

    public void openBlackjackGame() {
        System.out.println("Launching Blackjack Game...");
        main.java.blackjack.BlackjackUI bj = new main.java.blackjack.BlackjackUI(this);

        BorderPane root = new BorderPane();
        root.setTop(new Toolbar(this).getLayout());
        root.setCenter(bj);

        Scene scene = new Scene(root, 1000, 700);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Blackjack");
    }

    public void openSnakeGame() {
        System.out.println("Launching Snake Game...");
        if (activeSnakeUI != null) {
            activeSnakeUI.stopGame();
            activeSnakeUI = null;
        }
        main.java.snake.SnakeUI snakeUI = new main.java.snake.SnakeUI(this);
        activeSnakeUI = snakeUI;

        BorderPane root = new BorderPane();
        root.setTop(new Toolbar(this).getLayout());
        root.setCenter(snakeUI);

        Scene scene = new Scene(root, 1000, 700);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Snake Game");
    }

    public void pauseGame(Button pauseButton) {
        if (activeSnakeUI != null) {
            if (!gamePaused) {
                activeSnakeUI.pauseGame();
                pauseButton.setText("Resume");
            } else {
                activeSnakeUI.resumeGame();
                pauseButton.setText("Pause");
            }
            gamePaused = !gamePaused;
        }
    }

    public void stopSnakeGame() {
        if (activeSnakeUI != null) {
            activeSnakeUI.stopGame(); 
            activeSnakeUI = null; 
        }
    }

}
