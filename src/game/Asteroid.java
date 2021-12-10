package game;

import java.util.Random;

import javafx.scene.transform.Scale;

public class Asteroid extends GameObject {
    private int spin_factor;
    private String size;

    public Asteroid(int x, int y) {
        super(new PolygonFactory().createPolygon(), x, y);
        Random rand = new Random();
        Scale scale = new Scale();
        int scaleSetter = rand.nextInt(3);
        double scaleSize;
        switch (scaleSetter) {
            case 0:
                scaleSize = 0.5;
                this.size = "small";
                break;
            case 1:
                scaleSize = 0.75;
                this.size = "medium";
                break;
            case 2:
                scaleSize = 1.5;
                this.size = "large";
                break;
            default:
                scaleSize = 0.75;
                this.size = "medium";
        }
        scale.setX(scaleSize);
        scale.setY(scaleSize);
        getShape().getTransforms().addAll(scale);
        this.spin_factor = rand.nextInt(4) + 1;
    }

    public Asteroid(int x, int y, String size) {
        super(new PolygonFactory().createPolygon(), x, y);
        double scaleSize;
        switch (size) {
            case "small":
                scaleSize = 0.5;
                break;
            case "medium":
                scaleSize = 0.75;
                break;
            case "large":
                scaleSize = 1.5;
                break;
            default:
                size = "medium";
                scaleSize = 0.75;
        }
        this.size = size;
        Random rand = new Random();
        Scale scale = new Scale();
        scale.setX(scaleSize);
        scale.setY(scaleSize);
        getShape().getTransforms().addAll(scale);
        this.spin_factor = rand.nextInt(4) + 1;
    }

    public void spin() {
        turn(getShape().getRotate() + spin_factor);
    }

    public void accelerate(double multiplier) {
        Random rand = new Random();
        turn(rand.nextInt(360));
        double changeX = Math.cos(Math.toRadians(getShape().getRotate()));
        double changeY = Math.sin(Math.toRadians(getShape().getRotate()));

        changeX *= multiplier;
        changeY *= multiplier;

        this.movement = this.movement.add(changeX, changeY);
    }

    public String getSize() {
        return size;
    }
}
