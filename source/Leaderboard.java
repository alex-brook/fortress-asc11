import java.sql.*;
import java.util.LinkedList;
import java.util.List;

/**
 * Controls the leader board
 * Javadoc comments added by Stephen
 *
 * @author
 */
public class Leaderboard {

    private static final String LEADERBOARD_SCORE_TABLE = "leaderboardScore";
    private static final String USER_PROFILE_TABLE = "UserProfiles";
    private static final String DATABASE_PATH = "jdbc:sqlite:resources\\Leaderboard.db";

    public Leaderboard() {
        createNewDatabase();
        createScoreTable();
        createUserTable();
    }

    /**
     * Creates the new database
     */
    public void createNewDatabase() {
        Connection c = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:resources\\Leaderboard.db");
            System.out.println("Database Connected");
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /**
     *
     */
    private void createUserTable() {
        // SQLite connection string
        String url = "jdbc:sqlite:resources\\Leaderboard.db";

        // SQL statement for creating a new table
        String sql = "CREATE TABLE IF NOT EXISTS " + USER_PROFILE_TABLE + " (\n"
                + "    name text PRIMARY KEY NOT NULL,\n"
                + "    password text,\n"
                + "    gamesPlayed real,\n"
                + "    highestLevel text\n"
                + ");";

        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement()) {
            // create a new table
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Creates the initial table if none already exists
     */
    private void createScoreTable() {
        // SQLite connection string
        String url = "jdbc:sqlite:resources\\Leaderboard.db";

        // SQL statement for creating a new table
        String sql = "CREATE TABLE IF NOT EXISTS " + LEADERBOARD_SCORE_TABLE + " (\n"
                + "    name text,\n"
                + "    score real,\n"
                + "    map text,\n"
                + "    PRIMARY KEY(name, map, score)\n"
                + ");";

        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement()) {
            // create a new table
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * How connecting to the database is handled
     *
     * @return the connect object used by all other methods
     */
    private Connection getConnection() {
        // SQLite connection string
        String url = "jdbc:sqlite:resources\\Leaderboard.db";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    /**
     *
     * @param name
     * @param password
     */
    public void newAccount(String name, String password) {
        String sql = "INSERT INTO " + USER_PROFILE_TABLE + "(name,password,gamesPlayed,highestLevel) VALUES(?,?,0,0)";
        try (Connection conn = this.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setString(2, password);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());

        }
    }

    /**
     *
     * @param name
     * @return
     */
    public boolean isAccount(String name) {
        String sql = "SELECT name "
                + "FROM " + USER_PROFILE_TABLE + " WHERE name = ?";

        try (Connection conn = this.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // set the value
            pstmt.setString(1, name);
            //
            ResultSet rs = pstmt.executeQuery();

            // return result

            if ((rs.getString("name")).equals("")) {
                return false;
            } else {
                return true;
            }
        } catch (SQLException e) {
            return false;
        }
    }

    /**
     *
     * @param name
     * @return
     */
    public String getUserPassword(String name) {
        String sql = "SELECT password "
                + "FROM " + USER_PROFILE_TABLE + " WHERE name = ?";

        try (Connection conn = this.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // set the value
            pstmt.setString(1, name);
            //
            ResultSet rs = pstmt.executeQuery();

            // return result

            return (rs.getString("password"));
        } catch (SQLException e) {
            return "";
        }
    }

    /**
     *
     * @return
     */
    public String selectAllUsers() {
        String sql = "SELECT name, password, gamesPlayed, highestLevel FROM " + USER_PROFILE_TABLE + " ORDER BY name ASC";
        String result = "";
        try (Connection conn = this.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            // loop through the result set
            while (rs.next()) {
                result += (rs.getString("name") + "\t" +
                        rs.getString("password") + "\t" +
                        rs.getInt("gamesPlayed") + "\t" +
                        rs.getString("highestLevel") + "\n");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return result;
    }

    /**
     *
     * @return
     */
    public List<String> selectAllUsernames() {
        String sql = "SELECT name FROM " + USER_PROFILE_TABLE + " ORDER BY name ASC";
        List<String> result = new LinkedList<>();
        try (Connection conn = this.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            // loop through the result set
            while (rs.next()) {
                result.add(rs.getString("name"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return result;
    }



    /**
     * Outputs all data as a string sorted by score, this will need to be modified
     * to allow for use in the menu class
     */
    public String selectAllScores() {
        String sql = "SELECT name, score, map FROM " + LEADERBOARD_SCORE_TABLE + " ORDER BY score ASC";
        String result = "";
        try (Connection conn = this.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            // loop through the result set

            while (rs.next()) {
                result += (rs.getString("name") + "\t" +
                        rs.getInt("score")) + "\t" +
                        rs.getString("map") + "\n";
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return result;
    }

    /**
     *
     * @param map
     * @return
     */
    public UserScoreRecord[] selectMapScores(String map) {
        UserScoreRecord[] result = new UserScoreRecord[3];
        String sql = "SELECT name, score, map FROM " + LEADERBOARD_SCORE_TABLE + " WHERE map = ? ORDER BY score ASC";
        try (Connection conn = this.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            //set value
            pstmt.setString(1, map);

            ResultSet rs = pstmt.executeQuery();

            // loop through the result set

            int i = 0;
            String name = "";
            String score = "";
            while (rs.next() && i <= 2) {
                name = (rs.getString("name"));
                score = Integer.toString((rs.getInt("score")));
                result[i] = new UserScoreRecord(name, score);
                i++;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return result;
    }

    /**
     *
     * @param name
     * @param map
     * @return
     */
    private int getPlayerScore(String name, String map) {
        String sql = "SELECT score "
                + "FROM " + LEADERBOARD_SCORE_TABLE + " WHERE name = ? AND map = ?";

        try (Connection conn = this.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // set the value
            pstmt.setString(1, name);
            pstmt.setString(2, map);
            //
            ResultSet rs = pstmt.executeQuery();

            // loop through the result set

            return (rs.getInt("score"));
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return 0;
        }
    }

    /**
     *
     * @param name
     * @return
     */
    public int getPlayedGamesOfPlayer(String name) {
        String sql = "SELECT gamesPlayed "
                + "FROM " + USER_PROFILE_TABLE + " WHERE name = ?";

        try (Connection conn = this.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // set the value
            pstmt.setString(1, name);
            //
            ResultSet rs = pstmt.executeQuery();

            // loop through the result set

            return (rs.getInt("gamesPlayed"));
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return 0;
        }
    }

    /**
     *
     * @param name
     * @return
     */
    public boolean isNewPlayer(String name) {
        return (getPlayedGamesOfPlayer(name) == 0);
    }

    /**
     *
     * @param name takes in the name of the player to add or update within the
     *             database
     * @param score takes in the score of the player to add or update within
     *              the database
     * @param map
     */
    public void insertNewScore(String name, long score, String map) {
        System.out.println("updating " + name + " with score " + score + " on map " + map);

        String sql = "INSERT INTO " + LEADERBOARD_SCORE_TABLE + "(name,score,map) VALUES(?,?,?)";

        try (Connection conn = this.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setLong(2, score);
            pstmt.setString(3, map);
            pstmt.executeUpdate();
            updatehighestLevel(name, map);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     *
     * @param name takes in the name of the user
     */
    public void updatehighestLevel(String name, String map) {

        String sql = "UPDATE " + USER_PROFILE_TABLE + " SET highestLevel = ? "
                + "WHERE name = ?";
        if (Integer.parseInt(map) > getHighestMap(name)) {

            try (Connection conn = this.getConnection();
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {

                // set the corresponding param
                pstmt.setString(2, name);
                pstmt.setString(1, map);
                // update
                pstmt.executeUpdate();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }

    }

    /**
     *
     * @param name
     * @return
     */
    private int getHighestMap(String name) {
        String sql = "SELECT highestLevel "
                + "FROM " + USER_PROFILE_TABLE + " WHERE name = ?";

        try (Connection conn = this.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // set the value
            pstmt.setString(1, name);
            //
            ResultSet rs = pstmt.executeQuery();

            // return result

            return Integer.parseInt((rs.getString("password")));
        } catch (SQLException e) {
            return 0;
        }
    }

    /**
     * 
     * @param name
     */
    public void incrementGamesPlayed(String name) {
        String sql = "UPDATE " + USER_PROFILE_TABLE + " SET gamesPlayed = " + (getPlayedGamesOfPlayer(name) + 1)
                + " WHERE name = ?";
        try (Connection conn = this.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // set the corresponding param
            pstmt.setString(1, name);
            // update
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}




