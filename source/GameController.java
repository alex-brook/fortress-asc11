import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class GameController {

    private Scene menuScene;
    private Scene winScene;
    private Scene failScene;

    private Stage stage;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setMenuScene(Scene scene) {
        this.menuScene = scene;
    }

    public void setFailScene(Scene scene) {
        this.failScene = scene;
    }

    public void setWinScene(Scene scene) {
        this.winScene = scene;
    }

    private String stringFromFile(final String fileName) {
        try {
            File file = new File(fileName);
            FileInputStream fis = new FileInputStream(file);
            byte[] data = new byte[(int) file.length()];
            fis.read(data);
            fis.close();
            return new String(data, "UTF-8");
        } catch (IOException e) {
            return null;
        }
    }
}
