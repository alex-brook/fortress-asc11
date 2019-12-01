import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.LinkedList;
import java.util.Objects;

public class MenuController {
    private static final String MOTD_ERR =
            "Error retrieving message of the day";
    private static final String CS130_WEBSITE_URL =
            "http://cswebcat.swan.ac.uk";
    private static final String CS130_WEBSITE_PUZZLE_PATH
            = "/puzzle";
    private static final String CS130_WEBSITE_SOLUTION_PATH
            = "/message?solution=";

    @FXML
    private ComboBox<String> mapSelector;
    @FXML
    private Label motdLabel;
    private Stage stage;

    @FXML
    public void initialize() {
        try {
            motdLabel.setText(getMOTD());
        } catch (IOException e) {
            motdLabel.setText(MOTD_ERR);
        }
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

    private String getMOTD() throws IOException {
        String puzzleUrlString = CS130_WEBSITE_URL + CS130_WEBSITE_PUZZLE_PATH;
        return stringFromGET(CS130_WEBSITE_URL + CS130_WEBSITE_SOLUTION_PATH
                + solvePuzzle(stringFromGET(puzzleUrlString)));
    }

    private String stringFromGET(final String urlString) throws IOException {
        StringBuilder sb = new StringBuilder();
        URL url = new URL(urlString);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        if (con.getResponseCode() == HttpURLConnection.HTTP_OK) {
            BufferedReader br = new BufferedReader(new InputStreamReader(
                    con.getInputStream()
            ));
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            br.close();
        }
        return sb.toString();
    }

    private String solvePuzzle(final String puz) {
        StringBuilder solution = new StringBuilder();
        char[] puzzleChars = puz.toCharArray();
        for (int i = 0; i < puzzleChars.length; i++) {
            char cur;
            if ((i + 1) % 2 == 0) {
                cur = (char) (puzzleChars[i] - 1);
            } else {
                cur = (char) (puzzleChars[i] + 1);
            }

            if (cur < 'A') {
                cur = 'Z';
            } else if (cur > 'Z') {
                cur = 'A';
            }
            solution.append(cur);
        }
        return solution.toString();
    }

    private ObservableList<String> getMapNames() {
        ObservableList<String> fnames =
                FXCollections.observableList(new LinkedList<>());
        File mapDir = new File(getClass().getResource("./map").getPath());
        for (File f : Objects.requireNonNull(mapDir.listFiles())) {
            fnames.add(f.getName());
        }
        return fnames;
    }
}
