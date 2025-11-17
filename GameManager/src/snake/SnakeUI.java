package snake;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Duration;
import manager.GameManager;
import manager.models.HighScoreManager;
import manager.models.User;
import javafx.scene.image.Image;

public class SnakeUI extends BorderPane {
    private final int cellSize = 20;
    private final int gridWidth = 40;
    private final int gridHeight = 30;

    private final SnakeGame game;
    private final Canvas canvas;
    private final Timeline timeline;

    private final HighScoreManager highScoreManager;
    private final User currentUser;
    private final AudioManager audio;

    private Direction pendingDirection = null;
    private boolean firstInput = true;
    private boolean gamePaused = false;
    private int highestSnakeScore;
    private Image backgroundImage;
    private double gameSpeed = 200;

    public SnakeUI(GameManager gameManager) {
        // Initialize core objects
        this.highScoreManager = gameManager.getHighScoreManager();
        this.currentUser = gameManager.getCurrentUser();
        this.game = new SnakeGame(gridWidth, gridHeight);
        this.canvas = new Canvas(gridWidth * cellSize, gridHeight * cellSize);
        this.timeline = new Timeline();
        setCenter(canvas);

        
        audio = new AudioManager(gameManager.getMusicVolume(), gameManager.getSfxVolume());
        

        try {
            backgroundImage = new Image(getClass().getResource("/assets/snakebackground.jpg").toExternalForm());
        } catch (Exception e) {
            System.out.println("Could not load background image: " + e.getMessage());
        }

        sceneProperty().addListener((obs, oldScene, newScene) -> {
            if (oldScene != null)
                oldScene.removeEventFilter(KeyEvent.KEY_PRESSED, this::handleKey);
            if (newScene != null)
                newScene.addEventFilter(KeyEvent.KEY_PRESSED, this::handleKey);
        });

        KeyFrame frame = new KeyFrame(Duration.millis(gameSpeed), e -> gameLoop());
        timeline.getKeyFrames().add(frame);
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.pause();
        render();
    }

    private void handleKey(KeyEvent e) {
        if (game.isGameOver()) {
            restartGame();
            e.consume();
            return;
        }

        Direction newDir = switch (e.getCode()) {
            case UP -> Direction.UP;
            case DOWN -> Direction.DOWN;
            case LEFT -> Direction.LEFT;
            case RIGHT -> Direction.RIGHT;
            default -> null;
        };
        if (newDir == null)
            return;

        if (firstInput) {
            game.getSnake().setDirection(newDir);
            timeline.play();
            firstInput = false;
            e.consume();
            return;
        }

        Direction current = game.getSnake().getDirection();
        boolean ok = (current == Direction.UP && newDir != Direction.DOWN) ||
                (current == Direction.DOWN && newDir != Direction.UP) ||
                (current == Direction.LEFT && newDir != Direction.RIGHT) ||
                (current == Direction.RIGHT && newDir != Direction.LEFT);

        if (ok)
            pendingDirection = newDir;
        e.consume();
    }

    private void render() {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        final double CANVAS_WIDTH = canvas.getWidth();
        if (backgroundImage != null) {
            gc.drawImage(backgroundImage, 0, 0, canvas.getWidth(), canvas.getHeight());
        } else {
            gc.setFill(Color.BLACK); 
            gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        }

        gc.setFill(Color.RED);
        Point2D food = game.getFood().getPosition();
        gc.fillOval(food.getX() * cellSize, food.getY() * cellSize, cellSize, cellSize);

        gc.setFill(Color.LIGHTCYAN);
        for (Point2D p : game.getSnake().getBody()) {
            gc.fillRect(p.getX() * cellSize, p.getY() * cellSize, cellSize - 1, cellSize - 1);
        }

        gc.setFill(Color.WHITE);
        gc.setFont(Font.font(18));
        highestSnakeScore = highScoreManager.getSnakeScore(currentUser.getUserName());
        gc.fillText("Current: " + game.getScore(), 10, 20);
        gc.fillText("Highest: " + highestSnakeScore, 10, 40);

        if (game.isGameOver()) {
            gc.setFill(Color.RED);
            String gameOverText = "GAME OVER";
            gc.setFont(Font.font(50));
            double gameOverWidth = calculateTextWidth(gameOverText, gc.getFont());
            //x coordinate where the "GAME OVER"
            double gameOverXCoordinate = (CANVAS_WIDTH / 2) - (gameOverWidth / 2);

            gc.fillText(gameOverText, gameOverXCoordinate, 260); //y: 260

            String restartText = "Press any key to restart";
            gc.setFont(Font.font(22));
            double restartWidth = calculateTextWidth(restartText, gc.getFont());
            double restartXCoordinate = (CANVAS_WIDTH / 2) - (restartWidth / 2);
            gc.fillText(restartText, restartXCoordinate, 300); //y:300

            if (game.getScore() > highestSnakeScore) {
                gc.setFill(Color.GOLD);
                gc.setFont(Font.font(18));
                gc.fillText("New Highest " + game.getScore(), 160, 280);
            }
        }

        if (gamePaused) {
            gc.setFill(Color.RED);
            gc.setFont(Font.font(36));
            gc.fillText("GAME PAUSED", 150, 200);
        }
    }

    //helps center texts based on its length and the size of the screen
    private double calculateTextWidth(String text, Font font) {
        javafx.scene.text.Text tempText = new javafx.scene.text.Text(text);
        tempText.setFont(font);
        //returning the measured width of text
        return tempText.getBoundsInLocal().getWidth();
    }

    public void pauseGame() {
        if (timeline != null && timeline.getStatus() == Timeline.Status.RUNNING) {
            timeline.pause();
            audio.stopBackground();
            gamePaused = true;
            render();
        }
    }

    public void resumeGame() {
        if (timeline != null && timeline.getStatus() == Timeline.Status.PAUSED) {
            timeline.play();
            audio.playBackground();
            gamePaused = false;
            render();
        }
    }

    private void restartGame() {
        System.out.println("Restarting Snake Game...");
        game.resetGame();
        timeline.setRate(1.0);
        timeline.stop();
        timeline.playFromStart();
        // restart music and redraw
        audio.playBackground();
        render();
    }

    //Increase speed every time score hits a multiple of 50
    private void adjustSpeed() {
        if (game.getScore() % 50 == 0) {
            double currentSpeed = timeline.getRate();
            double newSpeed = Math.min(currentSpeed + 0.5, 3.0); // don't go below 50ms
            System.out.println("Speed increased! New frame time: " + newSpeed + " ms");
            timeline.setRate(newSpeed);

        }
    }

    private void gameLoop() {
        if (pendingDirection != null) {
            game.getSnake().setDirection(pendingDirection);
            pendingDirection = null;
        }

        int prevScore = game.getScore();
        game.update();
        render();

        if (game.getScore() > prevScore) {
            audio.playEat();
            adjustSpeed();
        }

        if (game.isGameOver()) {
            audio.playCrash();
            audio.stopBackground();
            timeline.stop();
            highScoreManager.updateScore(currentUser.getUserName(), null, game.getScore());
        }
    }

}
