package snake;

import javafx.geometry.Point2D;

public class SnakeGame {
    private final int width;
    private final int height;
    private final Snake snake;
    private final Food food;
    private int score;
    private boolean gameOver;
    private boolean bananaEaten;

    public SnakeGame(int width, int height) {
        this.width = width;
        this.height = height;
        this.snake = new Snake(new Point2D(width / 2, height / 2));
        this.food = new Food(width, height);
        food.spawn(width, height, snake);
    }

    public Snake getSnake() {
        return snake;
    }

    public Food getFood() {
        return food;
    }

    public boolean wasBananaEaten() { 
        return bananaEaten;
    }

    public int getScore() {
        return score;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public void resetGame() {
        snake.getBody().clear();
        snake.getBody().add(new Point2D(width / 2, height / 2)); // start in center
        food.spawn(width, height, snake);
        score = 0;
        gameOver = false;
        bananaEaten = false;
    }

    public void update() {
        if (gameOver)
            return;
        bananaEaten = false;

        Point2D head = snake.getHead();
        Point2D newHead = switch (snake.getDirection()) {
            case UP -> head.add(0, -1);
            case DOWN -> head.add(0, 1);
            case LEFT -> head.add(-1, 0);
            case RIGHT -> head.add(1, 0);
        };

        // wall collision
        if (newHead.getX() < 0 || newHead.getY() < 0 ||
                newHead.getX() >= width || newHead.getY() >= height) {
            gameOver = true;
            return;
        }

        // self collision
        if (snake.collision(newHead)) {
            gameOver = true;
            return;
        }

        // food or move
        if (newHead.equals(food.getPosition())) {
            FoodType consumedType = food.getType(); 
            score += consumedType.getScoreValue(); 
            snake.grow(newHead);
        
            if (consumedType == FoodType.BANANAPEEL) {
                bananaEaten = true; 
            }
            
            if (score < 0) 
                score = 0; 
            
            food.spawn(width, height, snake);
        } else {
            snake.move(newHead);
        }
    
    }
}
