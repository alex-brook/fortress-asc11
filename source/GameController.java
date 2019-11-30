import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class GameController {

    private static final double GAME_WIDTH = ((GameState.VIEW_RADIUS * 2) * (GameState.TILE_RES)) - GameState.TILE_RES * 2;
    private static final double GAME_HEIGHT = ((GameState.VIEW_RADIUS + 2) * (GameState.TILE_RES * 2));
    private static final int GAME_MARGIN = 0;

    private Scene menuScene;
    private Scene winScene;
    private Scene failScene;
    private GraphicsContext gc;

    @FXML
    private Canvas gameCanvas;

    private GameState gs;

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

    @FXML
    public void initialize() {
        gs = new GameState(stringFromFile(getClass().getResource("./map/map3.txt").getPath()));
        gameCanvas.setFocusTraversable(true);
        gameCanvas.requestFocus();
        gc = gameCanvas.getGraphicsContext2D();
        gameCanvas.setHeight(GAME_HEIGHT);
        gameCanvas.setWidth(GAME_WIDTH);

        AnimationTimer at = new AnimationTimer() {
            private long frame = 0;

            @Override
            public void handle(long now) {
                if (frame % 10 == 0) {
                    stateCheck();
                    frame = 0;
                }
                frame++;
            }
        };
        at.start();
    }

    public void switchToFail() {
        stage.setScene(failScene);
        stage.show();
    }

    public void switchToWin() {
        stage.setScene(winScene);
        stage.show();
    }

    @FXML
    private void keyPress(KeyEvent e) {
        updateGameOnKeyPress(e);
    }

    private void updateGameOnKeyPress(KeyEvent e) {
        gs.update(e.getCode());
        gs.drawRadius(gc, false);
    }

    private void stateCheck() {
        if (gs.getCurrentState() == GameState.State.RUNNING) {
            gs.drawRadius(gc, true);
        } else if (gs.getCurrentState() == GameState.State.LOSE) {
            switchToFail();
        } else if (gs.getCurrentState() == GameState.State.WIN) {
            switchToWin();
        }
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
