import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * The Main.
 *
 * @author Alex
 * @author Stephen Colegrove
 */
public class Main extends Application {
    private static final String VIEW_PATH = "./resources";
    private static final String MENU_VIEW_PATH = "/view/menu.fxml";
    private static final String GAME_VIEW_PATH = "/view/game.fxml";
    private static final String FAIL_VIEW_PATH = "/view/levelFail.fxml";
    private static final String WIN_VIEW_PATH = "/view/levelWin.fxml";
    private static final String SOUNDS_PATH = "./resources/sounds/";
    private static final String TITLE = "FORTRESS ASC11";

    private static Scene menuScene;
    private static MenuController menuController;

    private static Scene gameScene;
    private static GameController gameController;

    private static Scene winScene;
    private static WinController winController;

    private static Scene failScene;
    private static FailController failController;

    private static String currentUser = null;
    private static Leaderboard lb = new Leaderboard();

    /**
     * Prepares each scene of the game.
     * @param stage primary stage for project
     * @throws IOException No file found
     */
    @Override
    public void start(final Stage stage) throws IOException {
        FXMLLoader menuLoader = new FXMLLoader();
        URL url = new File(VIEW_PATH + MENU_VIEW_PATH).toURI().toURL();
        menuLoader.setLocation(url);
        Parent menuRoot = menuLoader.load();
        menuScene = new Scene(menuRoot);
        menuController = (MenuController) menuLoader.getController();
        menuController.setStage(stage);

        FXMLLoader gameLoader = new FXMLLoader();
        URL gameUrl = new File(VIEW_PATH + GAME_VIEW_PATH).toURI().toURL();
        gameLoader.setLocation(gameUrl);
        Pane gameRoot = gameLoader.load();
        gameScene = new Scene(gameRoot);
        gameController = gameLoader.getController();
        gameController.setStage(stage);

        FXMLLoader failLoader = new FXMLLoader();
        URL failUrl = new File(VIEW_PATH + FAIL_VIEW_PATH).toURI().toURL();
        failLoader.setLocation(failUrl);
        Pane failRoot = failLoader.load();
        failScene = new Scene(failRoot);
        failController = failLoader.getController();
        failController.setStage(stage);

        FXMLLoader winLoader = new FXMLLoader();
        URL winUrl = new File(VIEW_PATH + WIN_VIEW_PATH).toURI().toURL();
        winLoader.setLocation(winUrl);
        Parent winRoot = winLoader.load();
        winScene = new Scene(winRoot);
        winController = winLoader.getController();
        winController.setStage(stage);

        stage.setScene(menuScene);
        stage.setTitle(TITLE);
        stage.show();

    }

    /**
     * Setter for profile username.
     * @param username username to be set
     */
    public static void setUsername(final String username) {
        currentUser = username;
    }

    /**
     * Getter for profile username.
     * @return profile username
     */
    public static String getUsername() {
        return currentUser;
    }

    /**
     * Getter for leader board.
     * @return leader board
     */
    public static Leaderboard getLb() {
        return lb;
    }

    /**
     * Getter for main menu controller.
     * @return main menu controller
     */
    public static MenuController getMenuController() {
        return menuController;
    }

    /**
     * Getter for game level scene controller.
     * @return game scene controller
     */
    public static GameController getGameController() {
        return gameController;
    }

    /**
     * Getter for win menu controller.
     * @return win menu scene controller
     */
    public static WinController getWinController() {
        return winController;
    }

    /**
     * Getter for fail menu controller.
     * @return fail menu scene controller
     */
    public static FailController getFailController() {
        return failController;
    }

    /**
     * Getter for main menu.
     * @return main menu scene
     */
    public static Scene getMenuScene() {
        return menuScene;
    }

    /**
     * Getter for game level scene.
     * @return game level scene
     */
    public static Scene getGameScene() {
        return gameScene;
    }

    /**
     * Getter for level won menu.
     * @return win menu scene
     */
    public static Scene getWinScene() {
        return winScene;
    }

    /**
     * Getter for level failed menu.
     * @return fail menu scene
     */
    public static Scene getFailScene() {
        return failScene;
    }

    /**
     * Plays sound file with given name.
     * @param sound Name of sound file
     */
    public static void playSound(final String sound) {
        try {
            File f = new File(SOUNDS_PATH + sound);
            URL url = f.toURI().toURL();
            final Media media = new Media(url.toString());
            final MediaPlayer mediaPlayer = new MediaPlayer(media);
            mediaPlayer.play();
        } catch (MalformedURLException e) {
            System.err.println("Failed to play sound!");
            System.err.println(e);
        }
    }

    /**
     * Its a P.S.V.M. Yay.
     * @param args you know what this does
     */
    public static void main(final String[] args) {
        launch(args);
    }
}
