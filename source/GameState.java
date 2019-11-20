import javafx.scene.input.KeyCode;
import javafx.util.Pair;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

class GameState {
    private static final String ADDITIONAL_INFO_DELIMITER = "DESCRIPTION";
    private static final String ID_DELIMITER = ":";

    private Tile[][] grid;
    private Player player;
    private List<Enemy> enemies;
    private long startTime;
    private long endTime;

    GameState(final String map) {
        load(map);
        System.out.println("Loaded game");
        startTime = System.currentTimeMillis();
    }

    private void load(final String map) {
        // maps are assumed to have the correct additional information for each
        // cell in the correct order - when read left to right, up to down.
        enemies = new LinkedList<>();
        TileFactory tf = new TileFactory();
        EnemyFactory ef = new EnemyFactory();
        // just some string manipulation to get the bits of the file we need.
        String[] mapParts = map.split(ADDITIONAL_INFO_DELIMITER
                + System.lineSeparator());
        String[] additionalInfo = mapParts[1].split(System.lineSeparator());
        String[] rows = mapParts[0].split(System.lineSeparator());
        grid = new Tile[rows[0].length()][rows.length];
        int infoCounter =  0;
        String[] info = additionalInfo[infoCounter].split(ID_DELIMITER);
        // for each cell in the map file
        for (int y = 0; y < rows.length; y++) {
            for (int x = 0; x < rows[0].length(); x++) {
                // we look at the current cell at (x,y)
                char current = rows[y].charAt(x);
                // if it matches the next entry in the additional information
                // section, we call it complex.
                boolean complex = current == info[0].charAt(0);
                Tile asTile;
                Enemy asEnemy;
                // we try and instantiate both and enemy and a tile from this
                // cell
                if (complex) {
                    asTile = tf.getTile(current, info[1]);
                    asEnemy = ef.getEnemy(current, info[1]);
                } else {
                    asTile = tf.getTile(current);
                    asEnemy = ef.getEnemy(current);
                }
                if (asTile != null) {
                    // if the TileFactory returned anything but null, it must
                    // be a tile.
                    grid[x][y] = asTile;
                } else if (asEnemy != null) {
                    // if the EnemyFactory returned anything but null, it must
                    // be an enemy. BUT - we know that enemies must be standing
                    // on ground.
                    grid[x][y] = tf.getTile(TileFactory.MapChars.GROUND);
                    enemies.add(asEnemy);
                } else {
                    throw new NullPointerException("Trying to load null.");
                }
                // if we used the current additional information entry,
                // move onto the next one.
                if (complex && infoCounter < additionalInfo.length - 1) {
                    infoCounter++;
                    info = additionalInfo[infoCounter].split(ID_DELIMITER);
                }
            }
        }
    }
    public void update(final KeyCode kc) {
    }

    private void updatePlayer(final KeyCode kc) {
    }

    private void updateEnemies() {

    }

    public long currentTime() {
        return startTime - System.currentTimeMillis();
    }
}
