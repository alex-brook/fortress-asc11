import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;

public class Main extends Application {
    private static final String MENU_VIEW_PATH = "./view/menu.fxml";
    private static final String TITLE = "Game";

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader ld = new FXMLLoader();
        URL url = getClass().getResource(MENU_VIEW_PATH);
        ld.setLocation(url);
        Parent root = ld.load();

        stage.setScene(new Scene(root));
        stage.show();
    }

    public static void main(final String[] args) {
        launch(args);
    }
}
