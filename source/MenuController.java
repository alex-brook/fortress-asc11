import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;

public class MenuController {

    private static final double GAME_WIDTH = ((GameState.VIEW_RADIUS * 2) * (GameState.TILE_RES)) - GameState.TILE_RES * 2;
    private static final double GAME_HEIGHT = ((GameState.VIEW_RADIUS + 2) * (GameState.TILE_RES * 2));
    private static final int GAME_MARGIN = 0;
    private static final String FAIL_VIEW_PATH = "./view/levelFail.fxml";
    private static final String MENU_VIEW_PATH = "./view/menu.fxml";

    private GameState gs;
    private Scene scene;
    private Stage mainStage = new Stage();

    public void test() {
        //click on the window
        System.out.println("it works!");
    }

    /**
     *
     */
    public void ngButton() {
        loadLevel();
    }

    private void loadLevel() {
        gs = new GameState(stringFromFile(getClass().getResource("./map/map3.txt").getPath()));
       BorderPane root = new BorderPane();
       scene = new Scene(root, GAME_WIDTH, GAME_HEIGHT, Color.BLACK);

       final Canvas canvas = new Canvas(GAME_WIDTH - GAME_MARGIN, GAME_HEIGHT - GAME_MARGIN);
       mainStage.setResizable(false);
        canvas.setFocusTraversable(true);
        canvas.requestFocus();
        GraphicsContext gc = canvas.getGraphicsContext2D();

        AnimationTimer at = new AnimationTimer() {
            private long frame = 0;

            @Override
            public void handle(long now) {
                if (frame % 10 == 0
                        && gs.getCurrentState() == GameState.State.RUNNING) {
                    gs.drawRadius(gc, true);
                    frame = 0;
                }
                frame++;
            }
        };
        at.start();

        canvas.setOnKeyPressed(event -> {
            if (gs.getCurrentState() == GameState.State.RUNNING) {
                gs.update(event.getCode());
                gs.drawRadius(gc, false);
            } else if (gs.getCurrentState() == GameState.State.WIN) {
                if (event.getCode() == KeyCode.ESCAPE) {
                    mainStage.setScene(scene);
                } else {
                    gs.restart();
                }
            } else if (gs.getCurrentState() == GameState.State.LOSE) {
                try {
                    levelFailMenu();
                } catch (IOException e) {

                }
            }
        });

        root.getChildren().add(canvas);
        mainStage.setTitle("Starship ASC11");
        mainStage.setScene(scene);
        mainStage.show();
    }



    /**
     *
     * @param fileName
     * @return
     */
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

    public void levelFailMenu() throws IOException {
        Stage stage = new Stage();
        FXMLLoader ld = new FXMLLoader();
        URL url = getClass().getResource(FAIL_VIEW_PATH);
        ld.setLocation(url);
        Parent root = ld.load();

        stage.setScene(new Scene(root));
        stage.show();
    }

    public void mainMenu() throws IOException {
        Stage stage = new Stage();
        FXMLLoader ld = new FXMLLoader();
        URL url = getClass().getResource(MENU_VIEW_PATH);
        ld.setLocation(url);
        Parent root = ld.load();

        stage.setScene(new Scene(root));
        stage.show();
    }

    public void quitGame() {
        Platform.exit();
    }
}
