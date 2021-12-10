package menu;

import game.GameView;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class MainMenuView {
    private int WIDTH;
    private int HEIGHT;

    public MainMenuView(double WIDTH, double HEIGHT) {
        this.WIDTH = (int) WIDTH;
        this.HEIGHT = (int) HEIGHT;
    }

    public Scene getView(Stage window) {
        BackgroundView background = new BackgroundView(WIDTH, HEIGHT);

        Text header = new Text(0, 0, "ASTEROIDS");
        header.setFill(Color.RED);
        header.setFont(Font.font("impact", FontWeight.BOLD, 125));
        header.setStroke(Color.GRAY);
        header.setStrokeWidth(2);

        Button playButton = new Button("PLAY");
        playButton.setBackground(Background.EMPTY);
        playButton.setTextFill(Color.GREY);
        playButton.setFont(Font.font("impact", FontWeight.BOLD, 75));
        playButton.setPadding(Insets.EMPTY);

        playButton.setOnMouseEntered(e -> {
            playButton.setTextFill(Color.RED);
            playButton.setScaleX(1.20);
            playButton.setScaleY(1.20);
        });
        playButton.setOnMouseExited(e -> {
            playButton.setTextFill(Color.GREY);
            playButton.setScaleX(1);
            playButton.setScaleY(1);
        });
        playButton.setOnMouseClicked(e -> {
            GameView gameView = new GameView(WIDTH, HEIGHT);
            Scene playGame = gameView.getView(window);
            window.setScene(playGame);
            window.setFullScreen(true);
        });

        Button highscoresButton = new Button("HIGHSCORES");
        highscoresButton.setBackground(Background.EMPTY);
        highscoresButton.setTextFill(Color.GREY);
        highscoresButton.setFont(Font.font("impact", FontWeight.BOLD, 75));
        highscoresButton.setPadding(Insets.EMPTY);

        highscoresButton.setOnMouseEntered(e -> {
            highscoresButton.setTextFill(Color.RED);
            highscoresButton.setScaleX(1.25);
            highscoresButton.setScaleY(1.25);
        });
        highscoresButton.setOnMouseExited(e -> {
            highscoresButton.setTextFill(Color.GREY);
            highscoresButton.setScaleX(1);
            highscoresButton.setScaleY(1);
        });
        highscoresButton.setOnMouseClicked(e -> {
            HighscoresView hsView = new HighscoresView();
            Scene highscores = hsView.getView(window, background, WIDTH, HEIGHT);
            window.setScene(highscores);
            window.setFullScreen(true);
        });

        Button settingsButton = new Button("SETTINGS");
        settingsButton.setBackground(Background.EMPTY);
        settingsButton.setTextFill(Color.GREY);
        settingsButton.setFont(Font.font("impact", FontWeight.BOLD, 75));
        settingsButton.setPadding(Insets.EMPTY);

        settingsButton.setOnMouseEntered(e -> {
            settingsButton.setTextFill(Color.RED);
            settingsButton.setScaleX(1.20);
            settingsButton.setScaleY(1.20);
        });
        settingsButton.setOnMouseExited(e -> {
            settingsButton.setTextFill(Color.GREY);
            settingsButton.setScaleX(1);
            settingsButton.setScaleY(1);
        });

        Button quitButton = new Button("QUIT");
        quitButton.setBackground(Background.EMPTY);
        quitButton.setTextFill(Color.GREY);
        quitButton.setFont(Font.font("impact", FontWeight.BOLD, 75));
        quitButton.setPadding(Insets.EMPTY);

        quitButton.setOnMouseEntered(e -> {
            quitButton.setTextFill(Color.RED);
            quitButton.setScaleX(1.20);
            quitButton.setScaleY(1.20);
        });
        quitButton.setOnMouseExited(e -> {
            quitButton.setTextFill(Color.GREY);
            quitButton.setScaleX(1);
            quitButton.setScaleY(1);
        });
        quitButton.setOnMouseClicked(e -> {
            window.close();
        });

        VBox foreground = new VBox();

        foreground.setAlignment(Pos.CENTER);

        foreground.getChildren().addAll(header, playButton, highscoresButton, settingsButton,
                quitButton);

        StackPane layout = new StackPane();
        layout.setBackground(Background.EMPTY);
        layout.setPrefSize(WIDTH, HEIGHT);
        layout.getChildren().addAll(background.getBackground(), foreground);
        Scene scene = new Scene(layout);
        scene.setFill(Color.BLACK);

        return scene;
    }
}
