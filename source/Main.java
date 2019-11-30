import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;

public class Main extends Application {
    private static final String MENU_VIEW_PATH = "./view/menu.fxml";
    private static final String GAME_VIEW_PATH = "./view/game.fxml";
    private static final String FAIL_VIEW_PATH = "./view/levelFail.fxml";
    private static final String WIN_VIEW_PATH = "./view/levelWin.fxml";
    private static final String TITLE = "Game";
    private Scene menuScene;
    private Scene gameScene;
    private Scene winScene;
    private Scene failScene;


    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader menuLoader = new FXMLLoader();
        URL url = getClass().getResource(MENU_VIEW_PATH);
        menuLoader.setLocation(url);
        Parent menuRoot = menuLoader.load();
        menuScene = new Scene(menuRoot);
        MenuController menuController = (MenuController)menuLoader.getController();
        menuController.setStage(stage);

        FXMLLoader gameLoader = new FXMLLoader();
        URL gameUrl = getClass().getResource(GAME_VIEW_PATH);
        gameLoader.setLocation(gameUrl);
        Pane gameRoot = (Pane) gameLoader.load();
        gameScene = new Scene(gameRoot);
        GameController gameController = (GameController)gameLoader.getController();
        gameController.setStage(stage);

        FXMLLoader failLoader = new FXMLLoader();
        URL failUrl = getClass().getResource(FAIL_VIEW_PATH);
        failLoader.setLocation(failUrl);
        Pane failRoot = (Pane) failLoader.load();
        failScene = new Scene(failRoot);
        FailController failController = (FailController)failLoader.getController();
        failController.setStage(stage);

        FXMLLoader winLoader = new FXMLLoader();
        URL winUrl = getClass().getResource(WIN_VIEW_PATH);
        winLoader.setLocation(winUrl);
        Parent winRoot = winLoader.load();
        winScene = new Scene(winRoot);
        WinController winController = (WinController)winLoader.getController();
        winController.setStage(stage);

        menuController.setGameScene(gameScene);
        gameController.setFailScene(failScene);
        gameController.setMenuScene(menuScene);
        gameController.setWinScene(winScene);
        failController.setGameScene(gameScene);
        failController.setMenuScene(menuScene);
        winController.setGameScene(gameScene);
        winController.setMenuScene(menuScene);

        stage.setScene(gameScene);
        stage.show();

    }

    public static void main(final String[] args) {
        launch(args);
    }
}
