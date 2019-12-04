import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class GameController {
    @FXML
    private Canvas gameCanvas;
    @FXML
    private Pane gamePane;

    private GraphicsContext gc;
    private GameState gs;
    private AnimationTimer at;
    private Stage stage;
    private String currentMapName;


    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    public void initialize() {
        gameCanvas.setFocusTraversable(true);
        gameCanvas.requestFocus();
        gc = gameCanvas.getGraphicsContext2D();

        at = new AnimationTimer() {
            private long frame = 0;

            @Override
            public void handle(long now) {
                if (frame == 8) {
                    stateCheck();
                    frame = 0;
                }
                frame++;
            }
        };
    }

    @FXML
    private void keyPress(final KeyEvent e) {
        if (e.getCode() == KeyCode.ESCAPE) {
            //testing save functionality
            gs = new GameState(gs.save());
        } else {
            gs.update(e.getCode());
            gs.drawRadius(gc, false);
        }
    }

    public void loadGame(final String mapName) {
        currentMapName = mapName;
        gs = new GameState(stringFromMapName(mapName));
        at.start();
    }

    public void restartGame() {
        loadGame(currentMapName);
    }

    private void switchToFail() {
        at.stop();
        stage.setScene(Main.getFailScene());
        stage.show();
    }

    private void switchToWin() {
        at.stop();
        stage.setScene(Main.getWinScene());
        stage.show();
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

    private String stringFromMapName(final String fileName) {
        try {
            File file = new File(getClass()
                    .getResource("./map/" + fileName).getPath());
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
