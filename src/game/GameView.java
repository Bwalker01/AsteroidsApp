package game;

import javafx.scene.input.MouseButton;
import javafx.scene.layout.Background;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import menu.MainMenuView;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.animation.AnimationTimer;
import javafx.geometry.Insets;
import javafx.geometry.Pos;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

public class GameView {

    private int WIDTH;
    private int HEIGHT;

    private Map<MouseButton, Boolean> pressedKeys = new HashMap<>();

    public GameView(double WIDTH, double HEIGHT) {
        this.pressedKeys = new HashMap<MouseButton, Boolean>();
        this.WIDTH = (int) WIDTH;
        this.HEIGHT = (int) HEIGHT;
    }

    public Scene getView(Stage window) {
        Pane gamePane = new Pane();
        gamePane.setPrefSize(WIDTH, HEIGHT);
        gamePane.setBackground(Background.EMPTY);

        Text text = new Text(10, 20, "Points: 0");
        text.setFill(Color.WHITE);
        gamePane.getChildren().add(text);

        AtomicInteger points = new AtomicInteger();

        Player player = new Player(WIDTH / 2, HEIGHT / 2);
        gamePane.getChildren().add(player.getShape());

        List<Projectile> projectiles = new ArrayList<>();

        List<Asteroid> asteroids = new ArrayList<>();
        Random rand = new Random();
        int i = 0;
        while (i < 15) {
            Asteroid asteroid = new Asteroid(rand.nextInt(WIDTH - 10) + 5, rand.nextInt(HEIGHT - 10) + 5);
            if (Math.abs(asteroid.getShape().getTranslateX() - player.getShape().getTranslateX()) < 100
                    || Math.abs(asteroid.getShape().getTranslateY() - player.getShape().getTranslateY()) < 100) {
                continue;
            } else {
                asteroid.accelerate(2);
                gamePane.getChildren().add(asteroid.getShape());
                asteroids.add(asteroid);
                i++;
            }
        }

        Scene scene = new Scene(gamePane);
        scene.setFill(Color.BLACK);
        scene.setOnMousePressed(event -> {
            pressedKeys.put(event.getButton(), Boolean.TRUE);
        });
        scene.setOnMouseReleased(event -> {
            pressedKeys.put(event.getButton(), Boolean.FALSE);
        });

        new AnimationTimer() {

            private int shootCooldown = 0;

            @Override
            public void handle(long now) {
                if (pressedKeys.getOrDefault(MouseButton.PRIMARY, false)) {
                    player.accelerate();
                } else {
                    player.deccelerate();
                }

                if (pressedKeys.getOrDefault(MouseButton.SECONDARY, false)) {
                    if (shootCooldown < 1) {
                        Projectile projectile = new Projectile((int) player.getShape().getTranslateX(),
                                (int) player.getShape().getTranslateY());
                        projectile.turnToMouse();
                        projectile.accelerate(6);
                        projectiles.add(projectile);
                        gamePane.getChildren().add(projectile.getShape());
                        shootCooldown = 30;
                    }
                }

                player.turnToMouse();
                player.move();

                // Player Out Of Bounds
                if (player.getShape().getTranslateX() < 0) {
                    player.getShape().setTranslateX(1);
                    player.bounce("left");
                }
                if (player.getShape().getTranslateX() > WIDTH) {
                    player.getShape().setTranslateX(WIDTH - 1);
                    player.bounce("right");
                }
                if (player.getShape().getTranslateY() < 0) {
                    player.getShape().setTranslateY(1);
                    player.bounce("top");
                }
                if (player.getShape().getTranslateY() > HEIGHT) {
                    player.getShape().setTranslateY(HEIGHT - 1);
                    player.bounce("bottom");
                }

                asteroids.forEach(asteroid -> {
                    asteroid.spin();
                    asteroid.move();
                    // Asteroid Out of Bounds
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

                    if (asteroid.collide(player)) {

                        StackPane layout = new StackPane();

                        VBox foreground = new VBox();
                        foreground.setAlignment(Pos.CENTER);

                        Text deathText = new Text("You died.");
                        deathText.setFill(Color.RED);
                        deathText.setFont(Font.font("impact", FontWeight.BOLD, 125));

                        Text scoreText = new Text("Your score: " + points.get());
                        scoreText.setFill(Color.GREY);
                        scoreText.setFont(Font.font("impact", FontWeight.BOLD, 50));

                        Button returnButton = new Button("RETURN");
                        returnButton.setBackground(Background.EMPTY);
                        returnButton.setTextFill(Color.GREY);
                        returnButton.setFont(Font.font("impact", FontWeight.BOLD, 75));
                        returnButton.setPadding(new Insets(10, 10, 10, 10));

                        returnButton.setOnMouseEntered(e -> {
                            returnButton.setTextFill(Color.RED);
                            returnButton.setScaleX(1.20);
                            returnButton.setScaleY(1.20);
                        });
                        returnButton.setOnMouseExited(e -> {
                            returnButton.setTextFill(Color.GREY);
                            returnButton.setScaleX(1);
                            returnButton.setScaleY(1);
                        });
                        returnButton.setOnMouseClicked(e -> {
                            MainMenuView menuView = new MainMenuView(WIDTH, HEIGHT);
                            Scene mainMenu = menuView.getView(window);
                            window.setScene(mainMenu);
                            window.setFullScreen(true);
                        });

                        foreground.getChildren().addAll(deathText, scoreText, returnButton);
                        layout.getChildren().addAll(gamePane, foreground);
                        layout.setBackground(Background.EMPTY);

                        Scene endScene = new Scene(layout);
                        endScene.setFill(Color.BLACK);

                        window.setScene(endScene);
                        window.setFullScreen(true);
                        stop();
                    }

                });

                List<Projectile> toRemoveProjectiles = new ArrayList<>();

                projectiles.forEach(projectile -> {
                    projectile.move();
                    // Projectile Out of Bounds
                    if (projectile.getShape().getTranslateX() < 0
                            || projectile.getShape().getTranslateX() > WIDTH
                            || projectile.getShape().getTranslateY() < 0
                            || projectile.getShape().getTranslateY() > HEIGHT) {
                        toRemoveProjectiles.add(projectile);
                    }
                });

                List<Asteroid> toRemoveAsteroids = new ArrayList<>();
                List<Asteroid> toAddAsteroids = new ArrayList<>();

                asteroids.forEach(asteroid -> {
                    projectiles.forEach(projectile -> {
                        if (asteroid.collide(projectile)) {
                            text.setText("Points: " + points.addAndGet(100));
                            toRemoveProjectiles.add(projectile);
                            toRemoveAsteroids.add(asteroid);
                            List<Asteroid> newAsteroids = split(asteroid);
                            for (Asteroid newAsteroid : newAsteroids) {
                                toAddAsteroids.add(newAsteroid);
                            }
                        }
                    });
                });

                if (Math.random() < 0.005) {
                    Asteroid asteroid = new Asteroid(rand.nextInt(WIDTH - 10) + 5, rand.nextInt(HEIGHT - 10) + 5);
                    if (Math.abs(asteroid.getShape().getTranslateX() - player.getShape().getTranslateX()) > 200
                            && Math
                                    .abs(asteroid.getShape().getTranslateX()
                                            - player.getShape().getTranslateX()) > 200) {
                        toAddAsteroids.add(asteroid);
                    }
                }

                for (Projectile projectile : toRemoveProjectiles) {
                    gamePane.getChildren().remove(projectile.getShape());
                    projectiles.remove(projectile);
                }

                for (Asteroid asteroid : toRemoveAsteroids) {
                    gamePane.getChildren().remove(asteroid.getShape());
                    asteroids.remove(asteroid);
                }

                for (Asteroid asteroid : toAddAsteroids) {
                    gamePane.getChildren().add(asteroid.getShape());
                    Random rand = new Random();
                    asteroid.turn(rand.nextInt(360));
                    asteroid.accelerate(rand.nextInt(3) + 2);
                    asteroids.add(asteroid);
                }

                shootCooldown--;
            }
        }.start();

        return scene;
    }

    private static ArrayList<Asteroid> split(Asteroid asteroid) {
        ArrayList<Asteroid> newAsteroids = new ArrayList<>();
        if (asteroid.getSize().equals("medium")) {
            for (int i = 0; i < 3; i++) {
                Asteroid newAsteroid = new Asteroid((int) asteroid.getShape().getTranslateX(),
                        (int) asteroid.getShape().getTranslateY(), "small");
                newAsteroids.add(newAsteroid);
            }
        }
        if (asteroid.getSize().equals("large")) {
            for (int i = 0; i < 2; i++) {
                Asteroid newAsteroid = new Asteroid((int) asteroid.getShape().getTranslateX(),
                        (int) asteroid.getShape().getTranslateY(), "medium");
                newAsteroids.add(newAsteroid);
            }
        }
        return newAsteroids;
    }
}
