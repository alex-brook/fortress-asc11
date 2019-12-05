import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;

/**
 * @author Alex
 * @author Stephen Colegrove
 */
public class Main extends Application {
    private static final String MENU_VIEW_PATH = "./view/menu.fxml";
    private static final String GAME_VIEW_PATH = "./view/game.fxml";
    private static final String FAIL_VIEW_PATH = "./view/levelFail.fxml";
    private static final String WIN_VIEW_PATH = "./view/levelWin.fxml";
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

        FXMLLoader menuLoader = new FXMLLoader();
        URL url = getClass().getResource(MENU_VIEW_PATH);
        menuLoader.setLocation(url);
        Parent menuRoot = menuLoader.load();
        menuScene = new Scene(menuRoot);
        menuController = (MenuController) menuLoader.getController();
        menuController.setStage(stage);

        FXMLLoader gameLoader = new FXMLLoader();
        URL gameUrl = getClass().getResource(GAME_VIEW_PATH);
        gameLoader.setLocation(gameUrl);
        Pane gameRoot = gameLoader.load();
        gameScene = new Scene(gameRoot);
        gameController = gameLoader.getController();
        gameController.setStage(stage);

        FXMLLoader failLoader = new FXMLLoader();
        URL failUrl = getClass().getResource(FAIL_VIEW_PATH);
        failLoader.setLocation(failUrl);
        Pane failRoot = failLoader.load();
        failScene = new Scene(failRoot);
        failController = failLoader.getController();
        failController.setStage(stage);

        FXMLLoader winLoader = new FXMLLoader();
        URL winUrl = getClass().getResource(WIN_VIEW_PATH);
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


    public static void main(final String[] args) {
        launch(args);
    }
}
