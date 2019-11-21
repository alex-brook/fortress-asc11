import javafx.scene.input.KeyCode;
import java.util.LinkedList;
import java.util.List;

class GameState {
    public static final String ADDITIONAL_INFO_DELIMITER = "DESCRIPTION";
    public static final String ID_DELIMITER = ":";
    public static final String INFO_DELIMITER = ",";

    private Tile[][] grid;
    private Player player;
    private List<Enemy> enemies;
    private long startTime;
    private long endTime;

    GameState(final String map) {
        load(map);
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
                    asEnemy = ef.getEnemy(current, x, y, info[1]);
                } else {
                    asTile = tf.getTile(current);
                    asEnemy = ef.getEnemy(current, x, y);
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
                    // it it is neither, the only thing it can be is the player
                    grid[x][y] = tf.getTile(TileFactory.MapChars.GROUND);
                    player = new Player(x, y, info[1].split(INFO_DELIMITER));
                    // this could cause bugs as every unknown character is
                    // assumed to be the player.
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
        updatePlayer(kc);
    }

    private void updatePlayer(final KeyCode kc) {
        switch (kc) {
            case A:
                player.moveLeft();
                break;
            case D:
                player.moveRight();
                break;
            case W:
                player.moveUp();
                break;
            case S:
                player.moveDown();
                break;
            default:
                throw new IllegalArgumentException("Invalid key!");
        }
    }

    private void updateEnemies() {

    }

    public long currentTime() {
        return startTime - System.currentTimeMillis();
    }

    @Override
    public String toString() {
        StringBuilder sbInfo = new StringBuilder();
        StringBuilder sbMap = new StringBuilder();
        for (int y = 0; y < grid[0].length; y++) {
            for (int x = 0; x < grid.length; x++) {
                Player playerAtLocation =
                        (player.getXPos() == x && player.getYPos() == y)
                        ? player : null;
                Enemy enemyAtLocation = null;
                for (Enemy e: enemies) {
                    if (e.getXPos() == x && e.getYPos() == y) {
                        enemyAtLocation = e;
                    }
                }
                String info;
                char mapCell;
                if (playerAtLocation != null) {
                    mapCell = player.getMapChar();
                    info = player.getAdditionalInfo();
                } else if (enemyAtLocation != null) {
                    mapCell = enemyAtLocation.getMapChar();
                    info = enemyAtLocation.getAdditionalInfo();
                } else {
                    mapCell = grid[x][y].getMapChar();
                    info = grid[x][y].getAdditionalInfo();
                }
                sbMap.append(mapCell);
                if (info != null) {
                    sbInfo.append(mapCell);
                    sbInfo.append(ID_DELIMITER);
                    sbInfo.append(info);
                    sbInfo.append(System.lineSeparator());
                }
            }
            sbMap.append(System.lineSeparator());
        }
        sbMap.append(ADDITIONAL_INFO_DELIMITER);
        sbMap.append(System.lineSeparator());
        sbMap.append(sbInfo.toString());
        return sbMap.toString();
    }
}
