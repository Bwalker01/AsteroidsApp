package game;

import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.robot.Robot;
import javafx.scene.shape.Polygon;

public class Player extends GameObject {

    public Player(int x, int y) {
        super(new Polygon(-5, -5, 10, 0, -5, 5), x, y);
        getShape().setFill(Color.RED);
    }

    public void turnToMouse() {
        Robot robot = new Robot();
        Point2D mousePosition = robot.getMousePosition();

        double diffX = mousePosition.getX() - getShape().getTranslateX();
        double diffY = mousePosition.getY() - getShape().getTranslateY();

        turn(Math.toDegrees(Math.atan2(diffY, diffX)));
    }

    public void bounce(String side) {
        setMovement(new Point2D(getMovement().getX() * 0.5, getMovement().getY() * 0.5));
        super.bounce(side);
    }
}
