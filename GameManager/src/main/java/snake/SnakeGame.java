package main.java.snake;

import javafx.geometry.Point2D;

public class SnakeGame {
    private final int width;
    private final int height;
    private final Snake snake;
    private final Food food;
    private int score;
    private boolean gameOver;

    public SnakeGame(int width, int height) {
        this.width = width;
        this.height = height;
        this.snake = new Snake(new Point2D(width / 2, height / 2));
        this.food = new Food(width, height);
    }

    public Snake getSnake() {
        return snake;
    }

    public Food getFood() {
        return food;
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
        food.spawn(width, height);
        score = 0;
        gameOver = false;
    }

    public void update() {
        if (gameOver)
            return;

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
            snake.grow(newHead);
            food.spawn(width, height);
            score += 10;
        } else {
            snake.move(newHead);
        }
    }
}
