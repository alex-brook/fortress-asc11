import java.sql.*;
import java.util.LinkedList;
import java.util.List;

/**
 * Controls the leader board.
 *
 * @author Tom
 * @author Stephen
 */
public class Leaderboard {

    private static final String LEADERBOARD_SCORE_TABLE = "leaderboardScore";
    private static final String USER_PROFILE_TABLE = "UserProfiles";
    private static final String DATABASE_PATH
            = "jdbc:sqlite:resources\\Leaderboard.db";

    /**
     * Leaderboard constructor.
     */
    public Leaderboard() {
        createNewDatabase();
        createScoreTable();
        createUserTable();
    }

    /**
     * Creates the new database.
     */
    public void createNewDatabase() {
        Connection c = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection(
                    "jdbc:sqlite:resources\\Leaderboard.db");
            System.out.println("Database Connected");
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /**
     * Creates the user profile table.
     */
    private void createUserTable() {
        // SQLite connection string
        String url = "jdbc:sqlite:resources\\Leaderboard.db";

        // SQL statement for creating a new table
        String sql = "CREATE TABLE IF NOT EXISTS " + USER_PROFILE_TABLE
                + " (\n"
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
     * Creates the initial table if none already exists.
     */
    private void createScoreTable() {
        // SQLite connection string
        String url = "jdbc:sqlite:resources\\Leaderboard.db";

        // SQL statement for creating a new table
        String sql = "CREATE TABLE IF NOT EXISTS "
                + LEADERBOARD_SCORE_TABLE + " (\n"
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
     * Handles connecting to the database.
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
     * Creates a new account in user profile table using given input.
     * @param name username of new profile
     * @param password password for new profile
     */
    public void newAccount(final String name, final String password) {
        String sql = "INSERT INTO " + USER_PROFILE_TABLE
                + "(name,password,gamesPlayed,highestLevel) VALUES(?,?,0,0)";
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
     * Checks to see if an account with given username exists in the table.
     * @param name username being checked
     * @return true if username is in the table
     */
    public boolean isAccount(final String name) {
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
     * Getter for profiles password.
     * @param name username of profile
     * @return password for profile
     */
    public String getUserPassword(final String name) {
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
     * Selects all profiles in the table.
     * @return all rpofiles in the table
     */
    public String selectAllUsers() {
        String sql = "SELECT name, password, gamesPlayed, highestLevel FROM "
                + USER_PROFILE_TABLE + " ORDER BY name ASC";
        String result = "";
        try (Connection conn = this.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            // loop through the result set
            while (rs.next()) {
                result += (rs.getString("name") + "\t"
                        + rs.getString("password") + "\t"
                        + rs.getInt("gamesPlayed") + "\t"
                        + rs.getString("highestLevel") + "\n");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return result;
    }

    /**
     * Selects all usernames in the profile table.
     * @return usernames in table
     */
    public List<String> selectAllUsernames() {
        String sql = "SELECT name FROM " + USER_PROFILE_TABLE
                + " ORDER BY name ASC";
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
     * Outputs all data as a string sorted by score, this will need to be
     * modified to allow for use in the menu class.
     * @return all scores in table
     */
    public String selectAllScores() {
        String sql = "SELECT name, score, map FROM " + LEADERBOARD_SCORE_TABLE
        + " ORDER BY score ASC";
        String result = "";
        try (Connection conn = this.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            // loop through the result set

            while (rs.next()) {
                result += (rs.getString("name") + "\t"
                        + rs.getInt("score")) + "\t"
                        + rs.getString("map") + "\n";
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return result;
    }

    /**
     * Selects all scores for requested level.
     * @param map level which scores are requested
     * @return scores for the level
     */
    public UserScoreRecord[] selectMapScores(final String map) {
        UserScoreRecord[] result = new UserScoreRecord[3];
        String sql = "SELECT name, score, map FROM " + LEADERBOARD_SCORE_TABLE
                + " WHERE map = ? ORDER BY score ASC";
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
     * Getter for player's score in a level.
     * @param name profile's username
     * @param map level name
     * @return score
     */
    private int getPlayerScore(final String name, final String map) {
        String sql = "SELECT score "
                + "FROM " + LEADERBOARD_SCORE_TABLE
                + " WHERE name = ? AND map = ?";

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
     * Checks table to see which levels the profile has played.
     * @param name name of profile
     * @return games played
     */
    public int getPlayedGamesOfPlayer(final String name) {
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
     * Gets the highest level the profile has played.
     * @param name name of profile
     * @return level number
     */
    public int getHighestPlayedLevel(final String name) {
        String sql = "SELECT highestLevel "
                + "FROM " + USER_PROFILE_TABLE + " WHERE name = ?";

        try (Connection conn = this.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // set the value
            pstmt.setString(1, name);
            //
            ResultSet rs = pstmt.executeQuery();

            // loop through the result set

            return (rs.getInt("highestLevel"));
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return 0;
        }
    }

    /**
     * checks if the profile has played any levels or not.
     * @param name name of profile
     * @return true if profile has not played a level
     */
    public boolean isNewPlayer(final String name) {
        return (getPlayedGamesOfPlayer(name) == 0);
    }

    /**
     * Adds a new score to table.
     * @param name takes in the name of the player to add or update within the
     *             database
     * @param score takes in the score of the player to add or update within
     *              the database
     * @param map name of level
     */
    public void insertNewScore(final String name, final long score,
                               final String map) {
        System.out.println("updating " + name + " with score " + score
                + " on map " + map);

        String sql = "INSERT INTO " + LEADERBOARD_SCORE_TABLE
                + "(name,score,map) VALUES(?,?,?)";

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
     * Changes which is the profiles highest level played.
     * @param name takes in the name of the user
     * @param map name of level
     */
    public void updatehighestLevel(final String name, final String map) {

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
     * Getter for highest level played.
     * @param name profile name
     * @return level number for highest level played
     */
    private int getHighestMap(final String name) {
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
     * Updates which levels have been played by profile.
     * @param name name of profile
     */
    public void incrementGamesPlayed(final String name) {
        String sql = "UPDATE " + USER_PROFILE_TABLE + " SET gamesPlayed = "
                + (getPlayedGamesOfPlayer(name) + 1)
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

    /**
     * Deletes profile with chosen name.
     * @param name name of profile
     */
    public void deleteUserProfile(final String name) {
        String sql = "DELETE FROM " + USER_PROFILE_TABLE + " WHERE name = ?";

        try (Connection conn = this.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // set the value
            pstmt.setString(1, name);
            // delete the username
            pstmt.executeUpdate();


        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out.println("nope");
        }
    }
}




