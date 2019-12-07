import javafx.application.Platform;
import javafx.beans.property.ReadOnlyStringProperty;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

public class MenuController {
    public static final String FILE_END = ".txt";
    private static final String MOTD_ERR =
            "Error retrieving message of the day";
    private static final String CS130_WEBSITE_URL =
            "http://cswebcat.swan.ac.uk";
    private static final String CS130_WEBSITE_PUZZLE_PATH
            = "/puzzle";
    private static final String CS130_WEBSITE_SOLUTION_PATH
            = "/message?solution=";
    public static final String MAP_PATH = "./resources/map";
    public static final String SAVES_PATH = "./resources/saves";

    @FXML
    private ComboBox<String> mapSelector;
    @FXML
    private Label motdLabel;
    @FXML
    private Label loginStatus;
    @FXML
    private Label leaderboardLabel;
    @FXML
    private Label selectMapLabel;
    @FXML
    private Label usernameLabel;
    @FXML
    private Label passwordLabel;
    @FXML
    private TableView<UserScoreRecord> leaderBoardTable;
    @FXML
    private TableColumn<UserScoreRecord, String> usernameColumn;
    @FXML
    private TableColumn<UserScoreRecord, String> timeColumn;
    @FXML
    private TableView<String> profilesTable;
    @FXML
    private TableColumn<String, String> profileNameColumn;
    @FXML
    private TextField usernameInput;
    @FXML
    private PasswordField passwordInput;
    @FXML
    private Button logoutButton;
    @FXML
    private Button newgameButton;
    @FXML
    private Button continueButton;
    @FXML
    private Button loginButton;
    @FXML
    private Button createProfileButton;


    private String currentMap;
    private Stage stage;
    private Leaderboard ld;

    @FXML
    public void initialize() {
        try {
            motdLabel.setText(getMOTD());
        } catch (IOException e) {
            motdLabel.setText(MOTD_ERR);
        }
        mapSelector.setItems(getMapNames());
        currentMap = mapSelector.getItems().get(0);
        mapSelector.setValue(currentMap);
        ld = new Leaderboard();
        usernameColumn.setCellValueFactory(new PropertyValueFactory<UserScoreRecord, String>("user"));
        timeColumn.setCellValueFactory(new PropertyValueFactory<UserScoreRecord, String>("score"));
        profileNameColumn.setCellValueFactory(field -> new ReadOnlyStringWrapper(field.getValue()));
        ObservableList<UserScoreRecord> userData = getUserScoreData();
        ObservableList<String> profileNames = getUsernamesData();
        leaderBoardTable.setItems(userData);
        profilesTable.setItems(profileNames);
        setVisibleGameControls(false);
        setVisibleLoginControls(true);

    }

    private ObservableList<UserScoreRecord> getUserScoreData() {
        return FXCollections.observableList(Arrays.asList(ld.selectMapScores(currentMap)));
    }

    private ObservableList<String> getUsernamesData() {
        return FXCollections.observableList(ld.selectAllUsernames());
    }

    @FXML
    public void handleLogout(){
        Main.setUsername(null);
        usernameInput.setText(null);
        passwordInput.setText(null);
        setVisibleGameControls(false);
        setVisibleLoginControls(true);
        loginStatus.setText("Logged out");
    }

    @FXML
    public void handleCreateProfile(){
        Leaderboard lb = new Leaderboard();
        String username = usernameInput.getText();
        String password = passwordInput.getText();
        if (!lb.isAccount(username)){
            lb.newAccount(username, password);
            profilesTable.setItems(getUsernamesData());
            loginStatus.setText("New User profile created");
        } else {
            loginStatus.setText("This user already exists, please use login");
        }
    }
    @FXML
    public void handleLogin(){
        Leaderboard lb = new Leaderboard();
        String username = usernameInput.getText();
        String password = passwordInput.getText();
        if (lb.isAccount(username)){
            if (password.equals(lb.getUserPassword(username))){
                loginStatus.setText("Successfully logged in as " + username);
                setVisibleGameControls(true);
                setVisibleLoginControls(false);
                Main.setUsername(username);
                toggleContinue();
            } else {
                loginStatus.setText("Incorrect password for " + username + ", please try again");
            }
        } else {
            loginStatus.setText("This account does not exist, please create a new account");
        }
    }
    
    private void setVisibleGameControls(boolean state){
        mapSelector.setVisible(state);
        mapSelector.setDisable(!state);
        leaderBoardTable.setVisible(state);
        leaderBoardTable.setDisable(!state);
        leaderboardLabel.setVisible(state);
        selectMapLabel.setVisible(state);
        newgameButton.setVisible(state);
        newgameButton.setDisable(!state);
        continueButton.setVisible(state);
        logoutButton.setVisible(state);
        logoutButton.setDisable(!state);
    }

    private void setVisibleLoginControls(boolean state) {
        usernameInput.setVisible(state);
        usernameInput.setDisable(!state);
        passwordInput.setVisible(state);
        passwordInput.setDisable(!state);
        usernameLabel.setVisible(state);
        usernameLabel.setDisable(!state);
        passwordLabel.setVisible(state);
        passwordLabel.setDisable(!state);
        createProfileButton.setVisible(state);
        createProfileButton.setDisable(!state);
        loginButton.setVisible(state);
        loginButton.setDisable(!state);
        profilesTable.setVisible(state);
        profilesTable.setDisable(!state);
    }

    @FXML
    public void handleDropDownSelect(){
        currentMap = mapSelector.getValue();
        Leaderboard dataSet = new Leaderboard();
        ObservableList<UserScoreRecord> userData = getUserScoreData();
        leaderBoardTable.setItems(userData);
        toggleContinue();
    }

    private void toggleContinue() {
        continueButton.setDisable(!checkForSave());
    }

    @FXML
    public void handleNewGameButtonAction() {
        Main.getGameController().loadMap(mapSelector.getValue()+FILE_END);
        stage.setScene(Main.getGameScene());
        stage.show();
    }

    @FXML
    public void handleContinueButton() {
        Main.getGameController().loadSave(getSavegameFilename()+FILE_END);
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
        File mapDir = new File(MAP_PATH);
        for (File f : Objects.requireNonNull(mapDir.listFiles())) {
            fnames.add(f.getName().replaceFirst(".txt",""));
        }
        return fnames;
    }

    private ArrayList<String> getSaveFiles() {
        ArrayList<String> fnames =
                new ArrayList<>();
        File saveDir = new File(SAVES_PATH);
        for (File f : Objects.requireNonNull(saveDir.listFiles())) {
            fnames.add(f.getName().replaceFirst(".txt",""));
        }
        return fnames;
    }

    private String getSavegameFilename() {
        return String.format("%s_%s",Main.getUsername(),currentMap);
    }

    private boolean checkForSave() {
        return getSaveFiles().contains(getSavegameFilename());
    }
}
