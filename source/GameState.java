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
        enemies = new LinkedList<>();
        TileFactory tf = new TileFactory();
        EnemyFactory ef = new EnemyFactory();
        // maps are assumed to have the correct information for each cell
        // in the correct order when read left to right, up to down
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
                char current = rows[y].charAt(x);
                boolean complex = current == info[0].charAt(0);
                //cells fall into 5 groups,
                if (Tile.isTile(current) && complex) {
                    grid[x][y] = tf.getTile(current, info[1]);
                } else if (Tile.isTile(current)) {
                    grid[x][y] = tf.getTile(current);
                } else if (Enemy.isEnemy(current) && complex) {
                    enemies.add(ef.getEnemy(current, info[1]));
                } else if (Enemy.isEnemy(current)) {
                    enemies.add(ef.getEnemy(current));
                }

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
