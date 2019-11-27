import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class GameWindow extends Application {
    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;
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
        //gc.fillText(gs.toString(), MARGIN, MARGIN);

        AnimationTimer at = new AnimationTimer() {
            private long frame = 0;

            @Override
            public void handle(long now) {
                if (frame % 10 == 0
                        && gs.getCurrentState() == GameState.State.RUNNING) {
                    gs.draw(gc, true);
                    frame = 0;
                } else if (gs.getCurrentState() == GameState.State.WIN) {
                    gc.setFill(Color.WHITE);
                    gc.fillText("YOU WON!", MARGIN, MARGIN);
                } else if (gs.getCurrentState() == GameState.State.LOSE) {
                    gc.setFill(Color.RED);
                    gc.fillText("YOU LOST...", MARGIN, MARGIN);
                }
                frame++;
            }
        };
        at.start();

        canvas.setOnKeyPressed(event -> {
            if (gs.getCurrentState() == GameState.State.RUNNING) {
                gs.update(event.getCode());
                gs.draw(gc, false);
            }
        });

        root.getChildren().add(canvas);
        primaryStage.setTitle("Starship ASC11");
        primaryStage.setScene(s);
        primaryStage.show();
    }

    public GameWindow() {
        gs = new GameState(stringFromFile("test/map3.txt"));

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
