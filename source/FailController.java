import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Controls the fxml code for the level failed menu.
 *
 * @author Stephen
 * @author Alex
 */
public class FailController {

    private Scene menuScene;
    private Scene gameScene;

    private Stage stage;

    /**
     * Setter for stage.
     * @param currStage javafx stage
     */
    public void setStage(final Stage currStage) {
        this.stage = currStage;
    }

    /**
     * Setter for main menu scene.
     * @param scene main menu's scene
     */
    public void setMenuScene(final Scene scene) {
        this.menuScene = scene;
    }

    /**
     * Setter for game scene.
     * @param scene game's scene
     */
    public void setGameScene(final Scene scene) {
        this.gameScene = scene;
    }

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
     * Switches current scene to game level.
     */
    @FXML
    public void switchToGame() {
        Main.getGameController().restartGame();
        stage.setScene(Main.getGameScene());
        stage.show();
    }
}
