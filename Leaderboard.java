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
                + "    name text PRIMARY KEY,\n"
                + "    password text,\n"
                + "    gamesPlayed real\n"
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
     *
     * @param name takes in the name of the player to add or update within the database
     * @param score takes in the score of the player to add or update within the database
     */
    public void insertNewScore(String name, int score, String map) {
        if (getPlayerScore(name) == 0) {
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
        }
        else if(getPlayerScore(name) < score) {
            update(name, score, map);
        }
    }

    public void newAccount(String name, String password) {
        if (getUserPassword(name) == "") {
            String sql = "INSERT INTO UserProfiles(name,password,gamesPlayed) VALUES(?,?,0)";
            try (Connection conn = this.connect();
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, name);
                pstmt.setString(2, password);
                pstmt.executeUpdate();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
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

            return(rs.getString("password"));
        } catch (SQLException e) {
            return "";
        }
    }

    public String selectAllUsers() {
        String sql = "SELECT name, password, gamesPlayed FROM UserProfiles ORDER BY name ASC";
        String result = "";
        try (Connection conn = this.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            // loop through the result set
            while (rs.next()) {
                result += (rs.getString("name") + "\t" +
                        rs.getString("password") + "\t" +
                        rs.getInt("gamesPlayed")) + "\n";
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
        String sql = "SELECT name, score, map FROM LeaderboardScores ORDER BY score DESC";
        String result = "";
        try (Connection conn = this.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            // loop through the result set

            while (rs.next()) {
                result += (rs.getString("name") + "\t" +
                        rs.getInt("score")) +  "\t" +
                        rs.getString("map") + "\n";
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return result;
    }

    public String selectMapScores(String map) {
        String sql = "SELECT name, score, map FROM LeaderboardScores WHERE map = ? ORDER BY score DESC";
        String result = "";
        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            //set value
            pstmt.setString(1, map);

            ResultSet rs = pstmt.executeQuery();

            // loop through the result set

            while (rs.next()) {
                result += (rs.getString("name") + "\t" +
                        rs.getInt("score")) +  "\n";
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return result;
    }

    /**
    @param name takes in the name to find to corresponding score
     */
    private int getPlayerScore(String name) {
        String sql = "SELECT score "
                + "FROM LeaderboardScores WHERE name = ?";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // set the value
            pstmt.setString(1, name);
            //
            ResultSet rs = pstmt.executeQuery();

            // loop through the result set

                return(rs.getInt("score"));
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return 0;
        }
    }

    public boolean isNewPlayer(String name){
        String sql = "SELECT gamesPlayed "
                + "FROM UserProfiles WHERE name = ?";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // set the value
            pstmt.setString(1, name);
            //
            ResultSet rs = pstmt.executeQuery();

            // loop through the result set

            return (rs.getInt("gamesPlayed")) == 0;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    /**
    @param name takes in the name of the user
    @param score takes in the score of the user
    updates an existing record with a new score value
     */
    private void update(String name, int score, String map) {
        String sql = "UPDATE LeaderboardScores SET score = ? "
                + "WHERE name = ? AND map = ?";
        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // set the corresponding param
            pstmt.setString(2, name);
            pstmt.setInt(1, score);
            pstmt.setString(3, map);
            // update
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}




