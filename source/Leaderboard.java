import java.sql.*;

public class Leaderboard {


    public Leaderboard() {
        createNewDatabase();
        createScoreTable();
        createUserTable();
    }

    /**
     * creates the new database
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

    private void createUserTable() {
        // SQLite connection string
        String url = "jdbc:sqlite:resources\\Leaderboard.db";

        // SQL statement for creating a new table
        String sql = "CREATE TABLE IF NOT EXISTS UserProfiles (\n"
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
     * creates the initial table if none allready exists
     */
    private void createScoreTable() {
        // SQLite connection string
        String url = "jdbc:sqlite:resources\\Leaderboard.db";

        // SQL statement for creating a new table
        String sql = "CREATE TABLE IF NOT EXISTS LeaderBoardScores (\n"
                + "    name text,\n"
                + "    score real,\n"
                + "    map text,\n"
                + "    PRIMARY KEY(name, map)\n"
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
     * how connecting to the database is handled
     *
     * @return the connect object used by all other methods
     */
    private Connection connect() {
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
     * @param name  takes in the name of the player to add or update within the database
     * @param score takes in the score of the player to add or update within the database
     */
    public void insertNewScore(String name, int score, String map) {
        if (getPlayerScore(name, map) == 0) {
            String sql = "INSERT INTO LeaderboardScores(name,score,map) VALUES(?,?,?)";

            try (Connection conn = this.connect();
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, name);
                pstmt.setInt(2, score);
                pstmt.setString(3, map);
                pstmt.executeUpdate();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        } else if (getPlayerScore(name, map) < score) {
            updateScore(name, score, map);
        }
    }

    public void newAccount(String name, String password) {
        String sql = "INSERT INTO UserProfiles(name,password,gamesPlayed,highestLevel) VALUES(?,?,0,0)";
        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setString(2, password);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());

        }
    }

    public boolean isAccount(String name) {
        String sql = "SELECT name "
                + "FROM UserProfiles WHERE name = ?";

        try (Connection conn = this.connect();
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

    public String getUserPassword(String name) {
        String sql = "SELECT password "
                + "FROM UserProfiles WHERE name = ?";

        try (Connection conn = this.connect();
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

    public String selectAllUsers() {
        String sql = "SELECT name, password, gamesPlayed, highestLevel FROM UserProfiles ORDER BY name ASC";
        String result = "";
        try (Connection conn = this.connect();
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
     * outputs all data as a string sorted by score, this will need to be modified
     * to allow for use in the menu class
     */
    public String selectAllScores() {
        String sql = "SELECT name, score, map FROM LeaderboardScores ORDER BY score ASC";
        String result = "";
        try (Connection conn = this.connect();
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

    public UserScoreRecord[] selectMapScores(String map) {
        UserScoreRecord[] result = new UserScoreRecord[3];
        String sql = "SELECT name, score, map FROM LeaderboardScores WHERE map = ? ORDER BY score ASC";
        try (Connection conn = this.connect();
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
     * @param name takes in the name to find the corresponding score
     */
    private int getPlayerScore(String name, String map) {
        String sql = "SELECT score "
                + "FROM LeaderboardScores WHERE name = ? AND map = ?";

        try (Connection conn = this.connect();
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

    public int getPlayedGamesOfPlayer(String name) {
        String sql = "SELECT gamesPlayed "
                + "FROM UserProfiles WHERE name = ?";

        try (Connection conn = this.connect();
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

    public boolean isNewPlayer(String name) {
        return (getPlayedGamesOfPlayer(name) == 0);
    }

    /**
     * @param name  takes in the name of the user
     * @param score takes in the score of the user
     *              updates an existing record with a new score value
     */
    public void updateScore(String name, long score, String map) {
        String sql = "UPDATE LeaderboardScores SET score = ? "
                + "WHERE name = ? AND map = ?";
        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // set the corresponding param
            pstmt.setString(2, name);
            pstmt.setLong(1, score);
            pstmt.setString(3, map);
            // update
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        sql = "UPDATE UserProfiles SET highestLevel = ? "
                + "WHERE name = ?";
        if(Integer.parseInt(map) > getHighestMap(name)) {

            try (Connection conn = this.connect();
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

    private int getHighestMap(String name) {
        String sql = "SELECT highestLevel "
                + "FROM UserProfiles WHERE name = ?";

        try (Connection conn = this.connect();
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

    public void incrementGamesPlayed(String name) {
        String sql = "UPDATE userProfiles SET gamesPlayed = " + (getPlayedGamesOfPlayer(name) + 1)
                + " WHERE name = ?";
        try (Connection conn = this.connect();
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




