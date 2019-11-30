import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class FailController {

    private Scene menuScene;
    private Scene gameScene;

    private Stage stage;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setMenuScene(Scene scene) {
        this.menuScene = scene;
    }

    public void setGameScene(Scene scene) {
        this.gameScene = scene;
    }

    @FXML
    public void switchToMenu() {
        stage.setScene(menuScene);
        stage.show();
    }
}
