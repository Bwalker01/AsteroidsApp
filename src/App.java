import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.KeyCodeCombination;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import menu.MainMenuView;
import javafx.scene.input.KeyCode;

public class App extends Application {
    private static Stage window;

    public static void main(String[] args) throws Exception {
        launch(App.class);
    }

    public void start(Stage window) {
        App.window = window;
        double HEIGHT = Screen.getPrimary().getBounds().getHeight();
        double WIDTH = Screen.getPrimary().getBounds().getWidth();

        System.out.println("Height: " + HEIGHT);
        System.out.println("Width: " + WIDTH);

        MainMenuView mainMenuView = new MainMenuView(WIDTH, HEIGHT);

        Scene mainMenu = mainMenuView.getView(window);

        window.setScene(mainMenu);
        window.setFullScreenExitKeyCombination(new KeyCodeCombination(KeyCode.X,
                KeyCodeCombination.CONTROL_DOWN));
        window.setFullScreenExitHint("");
        window.initStyle(StageStyle.UNDECORATED);
        window.setFullScreen(true);
        window.show();
    }

    public static void closeWindow() {
        window.close();
    }
}
