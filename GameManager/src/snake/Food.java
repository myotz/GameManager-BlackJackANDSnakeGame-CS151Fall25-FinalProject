package snake;

import javafx.geometry.Point2D;
import java.util.Random;

public class Food {
    private Point2D position;

    public Food(int x, int y) {
        spawn(x,y);
    }

    public void spawn(int width, int height) {
        Random random = new Random();
        int x = random.nextInt(width);
        int y = random.nextInt(height);
        position = new Point2D(x, y);
    }

    public Point2D getPosition() {
        return position;
    }
}