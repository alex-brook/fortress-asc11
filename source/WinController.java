import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class WinController {

    private Scene menuScene;
    private Scene gameScene;

    private Stage stage;

    @FXML
    public void switchToMenu() {
        stage.setScene(Main.getMenuScene());
        stage.show();
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setMenuScene(Scene scene) {
        this.menuScene = scene;
    }

    public void setGameScene(Scene scene) {
        this.gameScene = scene;
    }
}
