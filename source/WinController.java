
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Controls the fxml code for the level won menu.
 *
 * @author Stephen
 * @author Alex
 */
public class WinController {

    private Scene menuScene;
    private Scene gameScene;

    private Stage stage;

    /**
     * Switches current scene to main menu.
     */
    @FXML
    public void switchToMenu() {
        stage.setScene(Main.getMenuScene());
        Main.getMenuController().handleDropDownSelect();
        stage.show();
    }

    /**
     * Setter for stage.
     * @param currStage current stage
     */
    public void setStage(final Stage currStage) {
        this.stage = currStage;
    }

    /**
     * Setter for main menu scene.
     * @param scene main menu scene
     */
    public void setMenuScene(final Scene scene) {
        this.menuScene = scene;
    }

    /**
     * Setter for game scene.
     * @param scene game scene
     */
    public void setGameScene(final Scene scene) {
        this.gameScene = scene;
    }
}
