package menu;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class HighscoresView {

    public Scene getView(Stage window, BackgroundView background, int WIDTH, int HEIGHT) {
        Text header = new Text(0, 0, "SCORES");
        header.setFill(Color.RED);
        header.setFont(Font.font("impact", FontWeight.BOLD, 125));

        StackPane score1 = new ScoreBox().getDisplay(1);
        score1.setPadding(new Insets(10, 10, 10, 10));
        StackPane score2 = new ScoreBox().getDisplay(2);
        score2.setPadding(new Insets(10, 10, 10, 10));
        StackPane score3 = new ScoreBox().getDisplay(3);
        score3.setPadding(new Insets(10, 10, 10, 10));

        HBox scoreLayout = new HBox();
        scoreLayout.setAlignment(Pos.CENTER);
        scoreLayout.getChildren().addAll(score1, score2, score3);
        scoreLayout.setPadding(new Insets(30, 30, 30, 30));

        Button returnButton = new Button("RETURN");
        returnButton.setBackground(Background.EMPTY);
        returnButton.setTextFill(Color.GREY);
        returnButton.setFont(Font.font("impact", FontWeight.BOLD, 75));
        returnButton.setPadding(Insets.EMPTY);

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

        VBox foreground = new VBox();
        foreground.setAlignment(Pos.CENTER);
        foreground.getChildren().addAll(header, scoreLayout, returnButton);

        StackPane layout = new StackPane();
        layout.setBackground(Background.EMPTY);
        layout.setPrefSize(WIDTH, HEIGHT);
        layout.getChildren().addAll(background.getBackground(), foreground);
        Scene scene = new Scene(layout);
        scene.setFill(Color.BLACK);

        return scene;
    }
}
