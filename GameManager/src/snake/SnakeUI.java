package snake;

import java.util.List;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.util.Duration;
import manager.GameManager;
import manager.models.HighScoreManager;
import manager.models.User;
import javafx.scene.image.Image;
import javafx.scene.control.Label;

public class SnakeUI extends BorderPane {
    private final int cellSize = 20;
    private final int gridWidth = 40;
    private final int gridHeight = 30;

    private final SnakeGame game;
    private final Canvas canvas;
    private final Timeline timeline;

    private final VBox gameTextBox = new VBox(10);
    private final Label messageLabel = new Label();
    private final Label subMessageLabel = new Label();
    private final Label scoreLabel = new Label();

    private final HighScoreManager highScoreManager;
    private final User currentUser;
    private final AudioManager audio;

    private Direction pendingDirection = null;
    private boolean firstInput = true;
    private boolean gamePaused = false;
    private int highestSnakeScore;
    private Image backgroundImage;
    private double gameSpeed = 200;

    private Image snakeBodyHorizontalImage;
    private Image snakeBodyVerticalImage;
    private Image bodyTopRightImage;
    private Image bodyTopLeftImage;
    private Image bodyBottomRightImage;
    private Image bodyBottomLeftImage;
    private Image headUpImage; 
    private Image headDownImage;
    private Image headLeftImage;
    private Image headRightImage;
    private Image tailUpImage;
    private Image tailDownImage;
    private Image tailLeftImage;
    private Image tailRightImage;
    private Image foodImage;

    public SnakeUI(GameManager gameManager) {
        // Initialize core objects
        this.highScoreManager = gameManager.getHighScoreManager();
        this.currentUser = gameManager.getCurrentUser();
        this.game = new SnakeGame(gridWidth, gridHeight);
        this.canvas = new Canvas(gridWidth * cellSize, gridHeight * cellSize);
        this.timeline = new Timeline();

        audio = new AudioManager(gameManager.getMusicVolume(), gameManager.getSfxVolume());

        try {
            backgroundImage = new Image(getClass().getResource("/assets/snakebackground.jpg").toExternalForm());

            headUpImage = new Image(getClass().getResource("/assets/head_up.png").toExternalForm());
            headDownImage = new Image(getClass().getResource("/assets/head_down.png").toExternalForm());
            headLeftImage = new Image(getClass().getResource("/assets/head_left.png").toExternalForm());
            headRightImage = new Image(getClass().getResource("/assets/head_right.png").toExternalForm());

            snakeBodyHorizontalImage = new Image(getClass().getResource("/assets/body_horizontal.png").toExternalForm());
            snakeBodyVerticalImage = new Image(getClass().getResource("/assets/body_vertical.png").toExternalForm());
            bodyTopRightImage = new Image(getClass().getResource("/assets/body_topright.png").toExternalForm());
            bodyTopLeftImage = new Image(getClass().getResource("/assets/body_topleft.png").toExternalForm());
            bodyBottomRightImage = new Image(getClass().getResource("/assets/body_bottomright.png").toExternalForm());
            bodyBottomLeftImage = new Image(getClass().getResource("/assets/body_bottomleft.png").toExternalForm());

            tailUpImage = new Image(getClass().getResource("/assets/tail_up.png").toExternalForm());
            tailDownImage = new Image(getClass().getResource("/assets/tail_down.png").toExternalForm());
            tailLeftImage = new Image(getClass().getResource("/assets/tail_left.png").toExternalForm());
            tailRightImage = new Image(getClass().getResource("/assets/tail_right.png").toExternalForm());

            foodImage = new Image(getClass().getResource("/assets/apple.png").toExternalForm());
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

        gameTextBox.setAlignment(Pos.CENTER);
        messageLabel.setFont(Font.font("Arial", FontWeight.BOLD, 36));
        messageLabel.setTextFill(Color.WHITE);
        subMessageLabel.setFont(Font.font("Arial", 18));
        subMessageLabel.setTextFill(Color.WHITE);
        scoreLabel.setFont(Font.font("Arial", 30));
        scoreLabel.setTextFill(Color.GOLD);
        gameTextBox.getChildren().addAll(messageLabel, subMessageLabel, scoreLabel);
        gameTextBox.setVisible(false);

        StackPane stackPane = new StackPane(canvas, gameTextBox);
        stackPane.setAlignment(Pos.CENTER);
        setCenter(stackPane);

        render();
        // timeline.pause();
    }

    private void handleKey(KeyEvent e) {
        switch (e.getCode()) {
            case ESCAPE -> {
                if (gamePaused) {
                    resumeGame();
                } else {
                    pauseGame();
                }
                e.consume();
                return;
            }
            default -> {
            }
        }
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
            // e.consume();
            return;
        }

        Direction current = game.getSnake().getDirection();
        boolean ok = (current == Direction.UP && newDir != Direction.DOWN) ||
                (current == Direction.DOWN && newDir != Direction.UP) ||
                (current == Direction.LEFT && newDir != Direction.RIGHT) ||
                (current == Direction.RIGHT && newDir != Direction.LEFT);

        if (ok)
            pendingDirection = newDir;
        // e.consume();
    }

    private void render() {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        if (backgroundImage != null) {
            gc.drawImage(backgroundImage, 0, 0, canvas.getWidth(), canvas.getHeight());
        } else {
            gc.setFill(Color.BLACK);
            gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        }

        gc.setStroke(Color.web("#f3f71ee2"));
        gc.setLineWidth(0.5);

        for (int x = 0; x <= gridWidth; x++) {
            gc.strokeLine(x * cellSize, 0, x * cellSize, gridHeight * cellSize);
        }
        for (int y = 0; y <= gridHeight; y++) {
            gc.strokeLine(0, y * cellSize, gridWidth * cellSize, y * cellSize);
        }

        //apple
        Point2D food = game.getFood().getPosition();
        if (foodImage != null) {
            gc.drawImage(foodImage, 
                        food.getX() * cellSize, 
                        food.getY() * cellSize, 
                        cellSize, cellSize);
        } else { //if drawing fails
            gc.setFill(Color.RED);
            gc.fillOval(food.getX() * cellSize, food.getY() * cellSize, cellSize, cellSize);
        }

        //snake
        List<Point2D> body = game.getSnake().getBody();

        if (!body.isEmpty()) {
            // body and corners
            for (int i = 1; i < body.size() -1; i++) {
                Point2D current = body.get(i);
                Point2D prev = body.get(i - 1); 
                Point2D next;
                if (i + 1 < body.size()) {
                    next = body.get(i + 1);
                } else {
                    next = null;
                }

                Image segmentImage = null;
                if (next == null || prev.getX() == next.getX()) {
                    //vertical segment
                    segmentImage = snakeBodyVerticalImage;
                } else if (next == null || prev.getY() == next.getY()) {
                    //horizontal segment
                    segmentImage = snakeBodyHorizontalImage;
                } 
                //corners
                else {
                    //moving from up to left or right to down
                    if (prev.getX() < current.getX() && next.getY() > current.getY() || 
                        prev.getY() > current.getY() && next.getX() < current.getX()) {  
                        segmentImage = bodyBottomLeftImage;
                    }
                    //mving from up to right or left to down
                    else if (prev.getX() > current.getX() && next.getY() > current.getY() || 
                            prev.getY() > current.getY() && next.getX() > current.getX()) {  
                        segmentImage = bodyBottomRightImage;
                    }
                    //moving from down to left or right to up
                    else if (prev.getX() < current.getX() && next.getY() < current.getY() || 
                            prev.getY() < current.getY() && next.getX() < current.getX()) {  
                        segmentImage = bodyTopLeftImage;
                    }
                    //moving from down to right or left to up :(? is this right omg
                    else if (prev.getX() > current.getX() && next.getY() < current.getY() || 
                            prev.getY() < current.getY() && next.getX() > current.getX()) {  
                        segmentImage = bodyTopRightImage;
                    }
                } 
                //image
                if (segmentImage != null) {
                    gc.drawImage(segmentImage, current.getX() * cellSize, current.getY() * cellSize, cellSize, cellSize);
                }
            }
            //head
            Point2D head = body.get(0);
            Direction direction = game.getSnake().getDirection();
            Image headImage = switch (direction) {
                case UP -> headUpImage;
                case DOWN -> headDownImage;
                case LEFT -> headLeftImage;
                case RIGHT -> headRightImage;
            };
            if (headImage != null) {
                gc.drawImage(headImage, head.getX() * cellSize,  head.getY() * cellSize, cellSize, cellSize);
            }
        }

        //tail
        if (body.size() > 1) {
            Point2D tail = body.get(body.size() - 1); //last slot - tail
            Point2D prev = body.get(body.size() - 2); //slot before the tail
            
            Image tailImage = null;

            //direction the tail
            if (tail.getX() < prev.getX()) {
                tailImage = tailLeftImage;
            } else if (tail.getX() > prev.getX()) {
                tailImage = tailRightImage;
            } else if (tail.getY() < prev.getY()) {
                tailImage = tailUpImage;
            } else if (tail.getY() > prev.getY()) {
                tailImage = tailDownImage;
            }
            if (tailImage != null) {
                gc.drawImage(tailImage, 
                            tail.getX() * cellSize, 
                            tail.getY() * cellSize, 
                            cellSize, cellSize);
            }
        }

        gc.setFill(Color.WHITE);
        gc.setFont(Font.font(18));
        highestSnakeScore = highScoreManager.getSnakeScore(currentUser.getUserName());
        gc.fillText("Current: " + game.getScore(), 10, 20);
        gc.fillText("Highest: " + highestSnakeScore, 10, 40);

        gameTextBox.setVisible(false);
        String state = getGameState();

        switch (state) {
            case "GAME_OVER" -> {
                gameTextBox.setVisible(true);
                messageLabel.setText("GAME OVER");
                subMessageLabel.setText("Press any key to restart");
                if (game.getScore() > highestSnakeScore) {
                    scoreLabel.setText("New Highest: " + game.getScore());
                    scoreLabel.setTextFill(Color.GOLD);
                } else {
                    scoreLabel.setText("Your Score: " + game.getScore());
                    scoreLabel.setTextFill(Color.WHITE);
                }
            }

            case "PAUSED" -> {
                gameTextBox.setVisible(true);
                messageLabel.setText("GAME PAUSED");
                subMessageLabel.setText("");
                scoreLabel.setText("");
            }

            default -> gameTextBox.setVisible(false);
        }
    }

    public void pauseGame() {
        if (timeline != null && timeline.getStatus() == Timeline.Status.RUNNING) {
            timeline.pause();
            audio.stopBackground();
            gamePaused = true;
            render();
        }
    }

    public void stopGame() {
        if (timeline != null) {
            timeline.stop();
        }
        audio.stopBackground();
        gamePaused = true;
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
        timeline.setRate(1.0); // restore to default snake speed
        timeline.stop();
        timeline.playFromStart();
        // restart music and redraw
        audio.playBackground();
        render();
    }

    // Increase speed every time score hits a multiple of 50
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

        int prevScore = game.getScore(); // starts with zero
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

    private String getGameState() {
        if (game.isGameOver())
            return "GAME_OVER";
        if (gamePaused)
            return "PAUSED";
        return "RUNNING";
    }

}
