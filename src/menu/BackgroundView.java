package menu;

import java.util.ArrayList;
import java.util.Random;

import game.Asteroid;

import javafx.animation.AnimationTimer;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;

public class BackgroundView {
    private int WIDTH;
    private int HEIGHT;
    private ArrayList<Asteroid> asteroids;

    public BackgroundView(double WIDTH, double HEIGHT) {
        this.WIDTH = (int) WIDTH;
        this.HEIGHT = (int) HEIGHT;
    }

    public Parent getBackground() {
        Pane background = new Pane();
        this.asteroids = new ArrayList<>();
        Random rand = new Random();
        for (int i = 0; i < 15; i++) {
            Asteroid asteroid = new Asteroid(rand.nextInt(WIDTH - 10) + 5, rand.nextInt(HEIGHT - 10) + 5);
            asteroid.accelerate(rand.nextInt(2) + 1);
            background.getChildren().add(asteroid.getShape());
            asteroids.add(asteroid);
        }

        new AnimationTimer() {
            public void handle(long now) {
                asteroids.forEach(asteroid -> {
                    asteroid.move();
                    asteroid.spin();

                    if (asteroid.getShape().getTranslateX() < 0) {
                        asteroid.bounce("left");
                    }
                    if (asteroid.getShape().getTranslateX() > WIDTH) {
                        asteroid.bounce("right");
                    }
                    if (asteroid.getShape().getTranslateY() < 0) {
                        asteroid.bounce("top");
                    }
                    if (asteroid.getShape().getTranslateY() > HEIGHT) {
                        asteroid.bounce("bottom");
                    }
                });
            }
        }.start();

        return background;
    }
}
