package snake;

import javafx.geometry.Point2D;

import java.util.List;
import java.util.Random;
import java.util.List;

public class Food {
    private Point2D position;
    private FoodType type;

    public Food(int x, int y) {
    }

    public void spawn(int width, int height, Snake snake) {
        Random random = new Random();
        Point2D newPosition = null;
        while (newPosition == null || snake.getBody().contains(newPosition)) {
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            newPosition = new Point2D(x, y);
        }
        position = newPosition;

        double chance = random.nextDouble();
        
        if (chance < 0.70) { // 70% chance 
            this.type = FoodType.APPLE;
        } else if (chance < 0.90) { // 20% chance bonus
            this.type = FoodType.PEAR;
        } else {
            this.type = FoodType.BANANAPEEL;
        }
    }

    public Point2D getPosition() {
        return position;
    }

    public FoodType getType() { 
        return type;
    }
}
