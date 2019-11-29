import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 * Main menu of game
 *
 * @author Stephen Colegrove
 *
 */
public class Menu extends Application {
    // The size of the window
    private static final int WINDOW_WIDTH = 400;
    private static final int WINDOW_HEIGHT = 400;

    // The size of the canvas
    private static final int CANVAS_WIDTH = 400;
    private static final int CANVAS_HEIGHT = 400;

    private Canvas canvas;

    public void start(Stage primaryStage) {
        // Build the GUI and scene
        Pane root = buildMmGUI();

        Scene scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);

        // Display the scene on the stage
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setTitle("Starship ASC11");
    }

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

        //Set button behaviours
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

    private Pane buildNgGUI() {
        BorderPane root = new BorderPane();

        canvas = new Canvas(CANVAS_WIDTH, CANVAS_HEIGHT);
        root.setCenter(canvas);

        VBox sidebar = new VBox();
        sidebar.setSpacing(20);
        sidebar.setPadding(new Insets(10, 10, 10, 10));
        root.setLeft(sidebar);

        Label title = new Label("Starship ASC11");
        title.setFont(new Font(60));
        Label m1 = new Label("Level 1");
        m1.setFont(new Font(20));

        sidebar.getChildren().addAll(title, m1);

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

    /*
     * Creates a new game on chosen profile
     */
    private void loadNewGame() {
        Stage secondaryStage = new Stage();
        Pane root = buildNgGUI();

        Scene sceneNG = new Scene(root, WINDOW_WIDTH - 50, WINDOW_HEIGHT - 50);
        secondaryStage.setScene(sceneNG);
        secondaryStage.show();
        secondaryStage.setTitle("Level 1");
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
