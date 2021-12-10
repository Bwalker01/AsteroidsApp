package menu;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class ScoreBox {
    private int score;
    private Shape box;
    private String time;

    public StackPane getDisplay(int num) {
        score = 100;
        box = new Polygon(-180, 120, 140, 120, 180, 80, 180, -120, -140, -120, -180, -80);
        time = "yesterday";
        Text scoreText = new Text(0, 0, score + "");
        scoreText.setFill(Color.WHITE);
        scoreText.setFont(Font.font("impact", FontWeight.BOLD, 75));
        scoreText.setTranslateY(-20);

        box.setFill(Color.BLACK);
        box.setStroke(Color.RED);
        box.setStrokeWidth(5);

        Text timeText = new Text(0, 0, time);
        timeText.setFill(Color.GREY);
        timeText.setFont(Font.font("impact", FontPosture.ITALIC, 30));
        timeText.setTranslateX(20);
        timeText.setTranslateY(-20);

        BorderPane content = new BorderPane();
        content.setPrefSize(box.getBoundsInLocal().getWidth(), box.getBoundsInLocal().getHeight());
        Text numberText = new Text(0, 0, num + "");
        numberText.setFill(Color.BLACK);
        numberText.setStroke(Color.RED);
        numberText.setStrokeWidth(5);
        numberText.setFont(Font.font("impact", 75));
        numberText.setTranslateX(50);
        numberText.setTranslateY(-45);
        content.setTop(numberText);
        content.setCenter(scoreText);
        content.setBottom(timeText);

        StackPane layout = new StackPane();
        layout.getChildren().addAll(box, content);
        layout.setOpacity(0.7);

        return layout;
    }
}
