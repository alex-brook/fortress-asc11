import java.sql.*;

public class Leaderboard {


    public Leaderboard() {
        createNewDatabase();
        createNewTable();
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

    /**
     * creates the initial table if none allready exists
     */
    private void createNewTable() {
        // SQLite connection string
        String url = "jdbc:sqlite:resources\\Leaderboard.db";

        // SQL statement for creating a new table
        String sql = "CREATE TABLE IF NOT EXISTS LeaderBoardScores (\n"
                + "    name text PRIMARY KEY,\n"
                // + "    name text NOT NULL,\n"
                + "    score real\n"
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
    public void insert(String name, int score) {
        if (getPlayerScore(name) == 0) {
            String sql = "INSERT INTO LeaderboardScores(name,score) VALUES(?,?)";

            try (Connection conn = this.connect();
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, name);
                pstmt.setInt(2, score);
                pstmt.executeUpdate();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
        else if(getPlayerScore(name) < score) {
            update(name, score);
        }
    }

    /**
     * outputs all data as a string sorted by score, this will need to be modified
     * to allow for use in the menu class
      */
    public void selectAll() {
        String sql = "SELECT name, score FROM LeaderboardScores ORDER BY score DESC";

        try (Connection conn = this.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            // loop through the result set
            while (rs.next()) {
                System.out.println(rs.getString("name") + "\t" +
                        rs.getInt("score"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
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
            return 0;
        }
    }

    /**
    @param name takes in the name of the user
    @param score takes in the score of the user
    updates an existing record with a new score value
     */
    private void update(String name, int score) {
        String sql = "UPDATE LeaderboardScores SET score = ? "
                + "WHERE name = ?";
        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // set the corresponding param
            pstmt.setString(2, name);
            pstmt.setInt(1, score);
            // update
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}




