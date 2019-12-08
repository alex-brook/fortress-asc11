import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


import java.io.*;

/**
 * Controls the fxml code for the game scene.
 *
 * @author Stephen
 * @author Alex
 */
public class GameController {
    //private final String SAVE_LOCATION = "./saves/";

    @FXML
    private Canvas gameCanvas;
    @FXML
    private Pane gamePane;

    private GraphicsContext gc;
    private GameState gs;
    private AnimationTimer at;
    private Stage stage;
    private String currentMapName;
    private boolean continuedGame;

    /**
     * Setter for stage.
     * @param currStage javafx stage
     */
    public void setStage(final Stage currStage) {
        this.stage = currStage;
    }

    /**
     * Runs code on game scene startup , controls canvas and checks game
     * state every tick.
     */
    @FXML
    public void initialize() {
        gameCanvas.setFocusTraversable(true);
        gameCanvas.requestFocus();
        gc = gameCanvas.getGraphicsContext2D();

        at = new AnimationTimer() {
            private long frame = 0;

            @Override
            public void handle(final long now) {
                if (frame == 8) {
                    stateCheck();
                    frame = 0;
                }
                frame++;
            }
        };
    }

    /**
     * Saves game when esc is pressed otherwise redraws scene after action.
     * @param e Key pressed event
     */
    @FXML
    private void keyPress(final KeyEvent e) {
        if (e.getCode() == KeyCode.ESCAPE) {
            //testing save functionality
            savePlayerLevel(gs.save());

        } else {
            gs.update(e.getCode());
            gs.drawRadius(gc, false);
        }
    }

    /**
     * Saves the current game scene to selected profile's save file.
     * @param saveGame name of map save file
     */
    private void savePlayerLevel(final String saveGame) {
        try {
            String path = MenuController.SAVES_PATH;

            String filename = currentMapName;
            if (!continuedGame) {
                filename = Main.getUsername() + "_" + currentMapName;
            }
            String filepath = String.format("%s/%s", path, filename);
            System.out.println(filepath);
            FileWriter fileWriter = new FileWriter(filepath);
            BufferedWriter fileSaver = new BufferedWriter(fileWriter);
            fileSaver.write(saveGame);
            fileSaver.close();
        } catch (IOException e) {
            System.out.println("IOException when saving game.");
            System.out.println(e.getMessage());
        }
    }

    /**
     * Returns the original name of a level map.
     * @param str name of profile save file
     * @return original file name
     */
    private String getOriginalMapName(final String str) {
        return currentMapName.replaceFirst(Main.getUsername() + "_", "");
    }

    /**
     * Builds the game scene using information from input map.
     * @param mapName name of map save file
     */
    public void loadMap(final String mapName) {
        currentMapName = mapName;
        gs = new GameState(stringFromMapName(mapName, MenuController.MAP_PATH));
        continuedGame = false;
        at.start();
    }

    /**
     * Builds the game scene using information from save file.
     * @param mapName name of map save file
     */
    public void loadSave(final String mapName) {
        currentMapName = mapName;
        gs = new GameState(stringFromMapName(mapName,
                MenuController.SAVES_PATH));
        continuedGame = true;
        at.start();
    }

    /**
     * Rebuilds game scene from beginning of level.
     */
    public void restartGame() {
        gs.restart();
        at.start();
    }

    /**
     * Switches current scene to level failed menu.
     */
    private void switchToFail() {
        at.stop();
        stage.setScene(Main.getFailScene());
        Main.getLb().incrementGamesPlayed(Main.getUsername());
        stage.show();
    }

    /**
     * Switches current game scene to level won menu.
     */
    private void switchToWin() {
        at.stop();
        String mapName = continuedGame
                ? getOriginalMapName(currentMapName)
                : currentMapName;
        stage.setScene(Main.getWinScene());
        Main.getLb().incrementGamesPlayed(Main.getUsername());
        Main.getLb().insertNewScore(Main.getUsername(), (gs.getSessionTime()),
                mapName.replaceFirst(MenuController.FILE_END, ""));
        stage.show();
    }

    /**
     * Checks if the game is running and if the game is failed or won.
     */
    private void stateCheck() {
        if (gs.getCurrentState() == GameState.State.RUNNING) {
            gs.drawRadius(gc, true);
        } else if (gs.getCurrentState() == GameState.State.LOSE) {
            switchToFail();
        } else if (gs.getCurrentState() == GameState.State.WIN) {
            switchToWin();
        }
    }

    /**
     * Finds location of map file.
     * @param fileName name of the map file
     * @param dir directory path
     * @return file path for map
     */
    private String stringFromMapName(final String fileName, final String dir) {
        try {
            File file = new File(String.format("%s/%s", dir, fileName));
            FileInputStream fis = new FileInputStream(file);
            byte[] data = new byte[(int) file.length()];
            fis.read(data);
            fis.close();
            String result = new String(data, "UTF-8");
            System.out.printf("The map contents: %s", result);
            return result;
        } catch (IOException e) {
            return null;
        }
    }

}
