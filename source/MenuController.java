import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;

import java.io.File;
import java.util.LinkedList;

public class MenuController {
    @FXML private ComboBox<String> mapSelector;
    private Stage stage;

    @FXML
    public void initialize() {
        mapSelector.setItems(getMapNames());
        mapSelector.setValue(mapSelector.getItems().get(0));
    }

    @FXML
    public void handleNewGameButtonAction(final ActionEvent e) {
        Main.getGameController().loadGame(mapSelector.getValue());
        stage.setScene(Main.getGameScene());
        stage.show();
    }

    @FXML
    public void handleQuitButtonAction(final ActionEvent e) {
        Platform.exit();
    }

    public void setStage(final Stage stage) {
        this.stage = stage;
    }

    private ObservableList<String> getMapNames() {
        ObservableList<String> fnames =
                FXCollections.observableList(new LinkedList<>());
        File mapDir = new File(getClass().getResource("./map").getPath());
        for (File f : mapDir.listFiles()) {
            fnames.add(f.getName());
        }
        return fnames;
    }
}
