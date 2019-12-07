import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


import java.io.*;

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
            savePlayerLevel(gs.save());

        } else {
            gs.update(e.getCode());
            gs.drawRadius(gc, false);
        }
    }

    private void savePlayerLevel(String saveGame){
        try{
            String path = MenuController.SAVES_PATH;
            String filename = Main.getUsername() + "_" + currentMapName;
            FileWriter fileWriter = new FileWriter(String.format("%s/%s",path,filename));
            BufferedWriter fileSaver = new BufferedWriter(fileWriter);
            fileSaver.write(saveGame);
            fileSaver.close();
        }catch(IOException e){
            System.out.println("IOException when saving game.");
            System.out.println(e.getMessage());
        }
    }

    public void loadMap(final String mapName) {
        currentMapName = mapName;
        gs = new GameState(stringFromMapName(mapName, MenuController.MAP_PATH));
        continuedGame = false;
        at.start();
    }

    public void loadSave(final String mapName) {
        currentMapName = mapName;
        gs = new GameState(stringFromMapName(mapName, MenuController.SAVES_PATH));
        continuedGame = true;
        at.start();
    }

    public void restartGame() {
        gs.restart();
        at.start();
    }

    private void switchToFail() {
        at.stop();
        stage.setScene(Main.getFailScene());
        Main.getLb().incrementGamesPlayed(Main.getUsername());
        stage.show();
    }

    private void switchToWin() {
        at.stop();
        String mapName = continuedGame
                ? currentMapName.replaceAll("[^\\d]","")
                : currentMapName;
        stage.setScene(Main.getWinScene());
        Main.getLb().incrementGamesPlayed(Main.getUsername());
        Main.getLb().insertNewScore(Main.getUsername(), (gs.getSessionTime()),
                mapName.replaceFirst(".txt",""));
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

    private String stringFromMapName(final String fileName, final String dir) {
        try {
            File file = new File(String.format("%s/%s",dir,fileName));
            FileInputStream fis = new FileInputStream(file);
            byte[] data = new byte[(int) file.length()];
            fis.read(data);
            fis.close();
            String result = new String(data, "UTF-8");
            System.out.printf("The map contents: %s",result);
            return result;
        } catch (IOException e) {
            return null;
        }
    }

}
