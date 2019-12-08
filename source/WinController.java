import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Controls the fxml code for the level won menu
 * Javadoc comments added by Stephen
 *
 * @author Stephen
 * @author Alex
 */
public class WinController {

    private Scene menuScene;
    private Scene gameScene;

    private Stage stage;

    /**
     *
     */
    @FXML
    public void switchToMenu() {
        stage.setScene(Main.getMenuScene());
        Main.getMenuController().handleDropDownSelect();
        stage.show();
    }

    /**
     *
     * @param stage
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    /**
     *
     * @param scene
     */
    public void setMenuScene(Scene scene) {
        this.menuScene = scene;
    }

    /**
     * 
     * @param scene
     */
    public void setGameScene(Scene scene) {
        this.gameScene = scene;
    }
}
