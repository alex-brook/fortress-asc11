import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class GameWindow extends Application {
    private static final int WIDTH = 640;
    private static final int HEIGHT = 480;
    private static final int MARGIN = 20;

    private GameState gs;

    @Override
    public void start(Stage primaryStage) {
        Group root = new Group();
        Scene s = new Scene(root, WIDTH, HEIGHT, Color.BLACK);
        final Canvas canvas = new Canvas(WIDTH - MARGIN, HEIGHT - MARGIN);
        canvas.setFocusTraversable(true);
        canvas.requestFocus();
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setFont(new Font("Courier New", 24));

        gc.setFill(Color.GREEN);
        gc.fillText(gs.toString(), MARGIN, MARGIN);

        canvas.setOnKeyPressed(event -> {
            gc.setFill(Color.BLACK);
            gc.fillRect(0, 0, WIDTH - MARGIN, HEIGHT - MARGIN);
            if (gs.getCurrentState() == GameState.State.RUNNING) {
                gs.update(event.getCode());
                gc.setFill(Color.GREEN);
                gc.fillText(gs.toString(), MARGIN, MARGIN);
            } else if (gs.getCurrentState() == GameState.State.WIN) {
                gc.setFill(Color.WHITE);
                gc.fillText("YOU WON!", MARGIN, MARGIN);
                System.out.println(gs.save());
            } else if (gs.getCurrentState() == GameState.State.LOSE) {
                gc.setFill(Color.RED);
                gc.fillText("YOU LOST...", MARGIN, MARGIN);
            }
        });

        root.getChildren().add(canvas);
        primaryStage.setTitle("Starship ASC11");
        primaryStage.setScene(s);
        primaryStage.show();
    }

    public GameWindow() {
        gs = new GameState(stringFromFile("test/map.txt"));
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

    public static void main(String[] args) {
        launch(args);
    }

}
