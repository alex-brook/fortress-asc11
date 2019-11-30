import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Main menu of game
 *
 * @author Stephen Colegrove
 */
public class MenuOld extends Application {
    // The size of the window
    private static final int WINDOW_WIDTH = 400;
    private static final int WINDOW_HEIGHT = 400;

    // The size of the canvas
    private static final int CANVAS_WIDTH = 400;
    private static final int CANVAS_HEIGHT = 400;

    // The size of game window
    private static final double GAME_WIDTH = ((GameState.VIEW_RADIUS * 2) * (GameState.TILE_RES)) - GameState.TILE_RES * 2;
    private static final double GAME_HEIGHT = ((GameState.VIEW_RADIUS + 2) * (GameState.TILE_RES * 2));
    private static final int GAME_MARGIN = 0;

    private Canvas canvas;
    private GameState gs;
    private Scene menuScene;
    private Scene scene;
    private Stage mainStage = new Stage();

    /**
     * Builds the scene and displays
     */
    @Override
    public void start(Stage stage) {
        mainStage = stage;
        Pane root = buildMmGUI();

        menuScene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);

        scene = menuScene;
        mainStage.setScene(scene);
        mainStage.show();
        mainStage.setTitle("Starship ASC11");
    }

    /**
     * It's a main, yay
     * @param args
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Create the Main Menu GUI.
     *
     * @return The panel that contains the created GUI.
     */
    private Pane buildMmGUI() {
        BorderPane root = new BorderPane();

        canvas = new Canvas(CANVAS_WIDTH, CANVAS_HEIGHT);
        root.setCenter(canvas);

        VBox sidebar = new VBox();
        sidebar.setSpacing(20);
        sidebar.setPadding(new Insets(10, 10, 10, 10));
        root.setLeft(sidebar);

        Label title = new Label("Starship ASC11");
        title.setFont(new Font(50));
        Label dayMessage = new Label("Wooo Code");
        dayMessage.setFont(new Font(20));

        Button ngButton = new Button("New Game");
        Button loadButton = new Button("Load Level");
        Button profileButton = new Button("Load Profile");
        Button leaderboardButton = new Button("Leaderboard");
        Button quitButton = new Button("Quit Game");
        sidebar.getChildren().addAll(title, dayMessage, ngButton, loadButton, profileButton, leaderboardButton,
                quitButton);

        ngButton.setMaxWidth(Double.MAX_VALUE);
        loadButton.setMaxWidth(Double.MAX_VALUE);
        profileButton.setMaxWidth(Double.MAX_VALUE);
        leaderboardButton.setMaxWidth(Double.MAX_VALUE);
        quitButton.setMaxWidth(Double.MAX_VALUE);

        //Sets button behaviours
        ngButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                loadNewGame();
            }
        });

        loadButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                loadLevel();
            }
        });

        profileButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                loadProfile();
            }
        });

        leaderboardButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                loadLeaderboard();
            }
        });

        quitButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                quitGame();
            }
        });

        return root;
    }


    private Pane buildLgGUI() {
        BorderPane root = new BorderPane();

        canvas = new Canvas(CANVAS_WIDTH, CANVAS_HEIGHT);
        root.setCenter(canvas);

        VBox sidebar = new VBox();
        sidebar.setSpacing(20);
        sidebar.setPadding(new Insets(10, 10, 10, 10));
        root.setLeft(sidebar);

        Label title = new Label("Starship ASC11");
        title.setFont(new Font(50));
        Label m1 = new Label("Level X");
        m1.setFont(new Font(20));

        sidebar.getChildren().addAll(title, m1);

        return root;

    }

    private Pane buildPrGUI() {
        BorderPane root = new BorderPane();

        canvas = new Canvas(CANVAS_WIDTH, CANVAS_HEIGHT);
        root.setCenter(canvas);

        VBox sidebar = new VBox();
        sidebar.setSpacing(20);
        sidebar.setPadding(new Insets(10, 10, 10, 10));
        root.setLeft(sidebar);

        Label title = new Label("Starship ASC11");
        title.setFont(new Font(50));
        Label m1 = new Label("Profiles");
        m1.setFont(new Font(20));

        sidebar.getChildren().addAll(title, m1);

        return root;
    }

    private Pane buildLbGUI() {
        BorderPane root = new BorderPane();

        canvas = new Canvas(CANVAS_WIDTH, CANVAS_HEIGHT);
        root.setCenter(canvas);

        VBox sidebar = new VBox();
        sidebar.setSpacing(20);
        sidebar.setPadding(new Insets(10, 10, 10, 10));
        root.setLeft(sidebar);

        Label title = new Label("Starship ASC11");
        title.setFont(new Font(50));
        Label m1 = new Label("Leaderboard");
        m1.setFont(new Font(20));

        sidebar.getChildren().addAll(title, m1);

        return root;
    }

    /**
     * Creates a new game on chosen profile
     */
    private void loadNewGame() {
        gs = new GameState(stringFromFile(getClass().getResource("./map/map3.txt").getPath()));
        BorderPane root = new BorderPane();
        scene = new Scene(root, GAME_WIDTH, GAME_HEIGHT, Color.BLACK);

        final Canvas canvas = new Canvas(GAME_WIDTH - GAME_MARGIN, GAME_HEIGHT - GAME_MARGIN);
        //mainStage.setResizable(false);
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
            } else if (gs.getCurrentState() == GameState.State.LOSE
                    || gs.getCurrentState() == GameState.State.WIN) {
                if (event.getCode() == KeyCode.ESCAPE) {
                    mainStage.setScene(menuScene);
                } else {
                    gs.restart();
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

    public void hideMenu(Stage primaryStage) {
        primaryStage.hide();
    }

    /**
     * Loads save of chosen profile
     */
    private void loadLevel() {
        Stage secondaryStage = new Stage();
        Pane root = buildLgGUI();

        Scene sceneLG = new Scene(root, WINDOW_WIDTH - 50, WINDOW_HEIGHT - 50);
        secondaryStage.setScene(sceneLG);
        secondaryStage.show();
        secondaryStage.setTitle("Level X");
    }

    /**
     * Loads chosen profile
     */
    private void loadProfile() {
        Stage secondaryStage = new Stage();
        Pane root = buildPrGUI();

        Scene scenePr = new Scene(root, WINDOW_WIDTH - 50, WINDOW_HEIGHT - 50);
        secondaryStage.setScene(scenePr);
        secondaryStage.show();
        secondaryStage.setTitle("Profile");
    }

    /**
     * Opens leaderboard window
     */
    private void loadLeaderboard() {
        Stage secondaryStage = new Stage();
        Pane root = buildLbGUI();

        Scene sceneLb = new Scene(root, WINDOW_WIDTH - 50, WINDOW_HEIGHT - 50);
        secondaryStage.setScene(sceneLb);
        secondaryStage.show();
        secondaryStage.setTitle("Profile");
    }

    /**
     * Closes game
     */
    private void quitGame() {
        Platform.exit();
    }
}
