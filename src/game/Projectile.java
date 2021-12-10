package game;

import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.robot.Robot;
import javafx.scene.shape.Circle;

public class Projectile extends GameObject {
    public Projectile(int x, int y) {
        super(new Circle(3, Color.RED), x, y);
        Robot robot = new Robot();
        Point2D mousePosition = robot.getMousePosition();

        double diffX = mousePosition.getX() - getShape().getTranslateX();
        double diffY = mousePosition.getY() - getShape().getTranslateY();

        turn(Math.toDegrees(Math.atan2(diffY, diffX)));
    }

    public void accelerate(double multiplier) {
        double changeX = Math.cos(Math.toRadians(getShape().getRotate()));
        double changeY = Math.sin(Math.toRadians(getShape().getRotate()));

        changeX *= multiplier;
        changeY *= multiplier;

        this.movement = this.movement.add(changeX, changeY);
    }

    public void turnToMouse() {
        Robot robot = new Robot();
        Point2D mousePosition = robot.getMousePosition();

        double diffX = mousePosition.getX() - getShape().getTranslateX();
        double diffY = mousePosition.getY() - getShape().getTranslateY();

        turn(Math.toDegrees(Math.atan2(diffY, diffX)));
    }
}
