package game;

import javafx.geometry.Point2D;
import javafx.scene.shape.Shape;

public class GameObject {
    private Shape shape;
    Point2D movement;

    public GameObject(Shape polygon, int x, int y) {
        this.shape = polygon;
        this.shape.setTranslateX(x);
        this.shape.setTranslateY(y);

        this.movement = new Point2D(0, 0);
    }

    public Shape getShape() {
        return shape;
    }

    public Point2D getMovement() {
        return movement;
    }

    public void setMovement(Point2D movement) {
        this.movement = movement;
    }

    public void turn(double degrees) {
        shape.setRotate(degrees);
    }

    public void move() {
        shape.setTranslateX(shape.getTranslateX() + movement.getX());
        shape.setTranslateY(shape.getTranslateY() + movement.getY());
    }

    public void accelerate() {
        double changeX = Math.cos(Math.toRadians(shape.getRotate()));
        double changeY = Math.sin(Math.toRadians(shape.getRotate()));

        changeX *= 0.05;
        changeY *= 0.05;

        if (movement.getX() < 10 && movement.getY() < 10) {
            movement = movement.add(changeX, changeY);
        } else if (movement.getX() < 10) {
            movement = movement.add(changeX, 0);
        } else if (movement.getY() < 10) {
            movement = movement.add(0, changeY);
        }
    }

    public void deccelerate() {
        double changeX = movement.getX() / 2;
        if (changeX < 1) {
            changeX = 0;
        }
        double changeY = movement.getY() / 2;
        if (changeY < 1) {
            changeY = 0;
        }

        movement.add(0 - changeX, 0 - changeY);
    }

    public void bounce(String side) {
        double xMovement = movement.getX();
        double yMovement = movement.getY();

        switch (side) {
            case "top":
            case "bottom":
                yMovement = 0 - yMovement;
                break;
            case "left":
            case "right":
                xMovement = 0 - xMovement;
                break;
            default:
                throw new IllegalArgumentException("Something went wrong.");
        }
        movement = new Point2D(xMovement, yMovement);
    }

    public boolean collide(GameObject other) {
        Shape collisionArea = Shape.intersect(shape, other.getShape());
        return collisionArea.getBoundsInLocal().getWidth() != -1;
    }
}
