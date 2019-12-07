import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

/**
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


    @Override
    public void start(final Stage stage) throws IOException {
        // database stuff
        Leaderboard ld = new Leaderboard();
        System.out.println(ld.selectAllUsers());


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
    public static void setUsername(String username){
        currentUser = username;
    }
    public static String getUsername(){
        return currentUser;
    }
    public static Leaderboard getLb(){
        return lb;
    }

    public static MenuController getMenuController() {
        return menuController;
    }

    public static GameController getGameController() {
        return gameController;
    }

    public static WinController getWinController() {
        return winController;
    }

    public static FailController getFailController() {
        return failController;
    }

    public static Scene getMenuScene() {
        return menuScene;
    }

    public static Scene getGameScene() {
        return gameScene;
    }

    public static Scene getWinScene() {
        return winScene;
    }

    public static Scene getFailScene() {
        return failScene;
    }

    public static void playSound(String sound) {
        try {
            File f = new File(SOUNDS_PATH + sound);
            URL url = f.toURI().toURL();
            final Media media = new Media(url.toString());
            final MediaPlayer mediaPlayer = new MediaPlayer(media);
            mediaPlayer.play();
        } catch (MalformedURLException e){
            System.err.println("Failed to play sound!");
            System.err.println(e);
        }
    }

    public static void main(final String[] args) {
        launch(args);
    }
}
