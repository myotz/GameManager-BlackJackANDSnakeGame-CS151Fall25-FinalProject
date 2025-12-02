package snake;

import javafx.geometry.Point2D;
import java.util.LinkedList;
import java.util.List;

public class Snake {
    private LinkedList<Point2D> body = new LinkedList<>();
    private Direction direction = Direction.RIGHT;

    public Snake(Point2D startPosition) {
        body.add(startPosition);
    }

    public List<Point2D> getBody() {
        return body;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public void move(Point2D direction) {
        body.addFirst(direction);
        body.removeLast();
    }

    public void grow(Point2D direction) {
        body.addFirst(direction);
    }

    public boolean collision(Point2D direction) {
        return body.contains(direction);
    }

    public Point2D getHead() {
        return body.getFirst();
    }
}
