package manager;

import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import manager.models.AccountManager;
import manager.models.HighScoreManager;
import manager.models.User;
import javafx.scene.control.Button;

public class GameManager {

    private final Stage primaryStage;
    private final AccountManager accountManager;
    private final HighScoreManager highScoreManager;
    private User currentUser;
    private boolean gamePaused = false;
    private snake.SnakeUI activeSnakeUI;
    private blackjack.BlackjackUI activeblackjackUI;
    private double musicVolume = 0.5;
    private double sfxVolume = 0.5;

    public GameManager(Stage stage) {
        this.primaryStage = stage;
        this.accountManager = new AccountManager(this);
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

    public double getMusicVolume() {
        return musicVolume;
    }

    public void setMusicVolume(double volume) {
        this.musicVolume = volume;

        if (activeSnakeUI != null)
            activeSnakeUI.updateVolumes(volume, sfxVolume);

        if (activeblackjackUI != null)
            activeblackjackUI.updateVolumes(volume, sfxVolume);
    }

    public double getSfxVolume() {
        return sfxVolume;
    }

    public void setSfxVolume(double volume) {
        this.sfxVolume = volume;

        if (activeSnakeUI != null)
            activeSnakeUI.updateVolumes(musicVolume, volume);

        if (activeblackjackUI != null)
            activeblackjackUI.updateVolumes(musicVolume, volume);
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
        if (activeblackjackUI != null) {
            activeblackjackUI.stopGame();
            activeblackjackUI = null;
        }
        blackjack.BlackjackUI bj = new blackjack.BlackjackUI(this);
        activeblackjackUI = bj;
        BorderPane root = new BorderPane();
        root.setTop(new Toolbar(this).getLayout());
        root.setCenter(bj);

        Scene scene = new Scene(root, 1200, 900);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Blackjack");
    }

    public void openSnakeGame() {
        System.out.println("Launching Snake Game...");
        if (activeSnakeUI != null) {
            activeSnakeUI.stopGame();
            activeSnakeUI = null;
        }
        snake.SnakeUI snakeUI = new snake.SnakeUI(this);
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

    public void stopBlackjackGame() {
        if (activeblackjackUI != null) {
            activeblackjackUI.stopGame();
            activeblackjackUI = null;
        }
    }

}
