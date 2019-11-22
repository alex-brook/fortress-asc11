import javafx.scene.input.KeyCode;

import java.util.LinkedList;
import java.util.List;

class GameState {
    public static final String TILE_DESC_DELIMITER = "TILES";
    public static final String ENEMY_DESC_DELIMITER = "ENEMIES";
    public static final String PLAYER_DESC_DELIMITER = "PLAYER";
    public static final String ID_DELIMITER = ":";
    public static final String INFO_DELIMITER = ",";

    public enum State {
        WIN,
        LOSE,
        RUNNING;
    }

    private State currentState;
    private Tile[][] grid;
    private Player player;
    private List<Enemy> enemies;
    private long startTime;
    private long endTime;

    GameState(final String map) {
        load(map);
        currentState = State.RUNNING;
    }

    private void load(final String map) {
        String[] parts = getMapComponents(map);
        loadTiles(parts[0], parts[1]);
        loadEnemies(parts[2]);
        loadPlayer(parts[3]);
    }

    private void loadTiles(final String tileMap, final String tileDesc) {
        TileFactory tf = new TileFactory();
        String[] rows = tileMap.split(System.lineSeparator());
        grid = new Tile[rows[0].length()][rows.length];
        String[] tiles =
                tileDesc.split(System.lineSeparator());
        int descCounter = 0;
        String[] currentDesc = tiles[descCounter].split(ID_DELIMITER);
        for (int y = 0; y < rows.length; y++) {
            for (int x = 0; x < rows[0].length(); x++) {
                char cur = rows[y].charAt(x);
                boolean hasDesc = cur == currentDesc[0].charAt(0);

                if (hasDesc) {
                    grid[x][y] = tf.getTile(cur, currentDesc[1]);
                } else {
                    grid[x][y] = tf.getTile(cur);
                }

                boolean updateDesc = hasDesc
                        && descCounter < tiles.length - 1;
                if (updateDesc) {
                    descCounter++;
                    currentDesc = tiles[descCounter].split(ID_DELIMITER);
                }
            }
        }
    }

    private void loadEnemies(final String enemyDesc) {
        final int numMandatoryInfo = 3;
        enemies = new LinkedList<>();
        EnemyFactory ef = new EnemyFactory();
        String[] enemyDescriptions = enemyDesc.split(System.lineSeparator());
        for (String desc : enemyDescriptions) {
            String splitRegex = String.format("%s|%s", ID_DELIMITER,
                    INFO_DELIMITER);
            String[] vals = desc.split(splitRegex);
            char cur = vals[0].charAt(0);
            int x = Integer.parseInt(vals[1]);
            int y = Integer.parseInt(vals[2]);

            int numAdditionalInfo = vals.length - numMandatoryInfo;
            if (numAdditionalInfo > 0) {
                String[] addInfo = new String[numAdditionalInfo];
                System.arraycopy(vals, numMandatoryInfo,
                        addInfo, 0, numAdditionalInfo);
                enemies.add(ef.getEnemy(cur, x, y, addInfo));
            } else {
                enemies.add(ef.getEnemy(cur, x, y));
            }
        }
    }

    private void loadPlayer(final String desc) {
        final int numMandatoryInfo = 3;
        String splitRegex = String.format("%s|%s", ID_DELIMITER,
                INFO_DELIMITER);
        String[] vals = desc.split(splitRegex);
        int x = Integer.parseInt(vals[1]);
        int y = Integer.parseInt(vals[2]);

        int numAdditionalInfo = vals.length - numMandatoryInfo;
        String[] addInfo = new String[numAdditionalInfo];
        System.arraycopy(vals, numMandatoryInfo,
                    addInfo, 0, numAdditionalInfo);
        player = new Player(x, y, addInfo);
    }

    private String[] getMapComponents(final String map) {
        String splitRegex = String.format("%s%s|%s%s|%s%s",
                TILE_DESC_DELIMITER, System.lineSeparator(),
                ENEMY_DESC_DELIMITER, System.lineSeparator(),
                PLAYER_DESC_DELIMITER, System.lineSeparator()
        );
        String[] parts = map.split(splitRegex);
        for (int i = 0; i < parts.length; i++) {
            parts[i] = parts[i].trim();
        }
        return parts;
    }

    public void update(final KeyCode kc) {
        updatePlayer(kc);
        updateEnemies();
        if (player.isDead()) {
            currentState = State.LOSE;
        } else if (player.hasWon()) {
            currentState = State.WIN;
        }
    }

    private void updatePlayer(final KeyCode kc) {
        int playerX = player.getXPos();
        int playerY = player.getYPos();
        switch (kc) {
            case A:
                int leftX = playerX > 0 ? playerX - 1 : playerX;
                if (grid[leftX][playerY].isPassable(player)) {
                    player.moveLeft();
                }
                getTileAtPlayer(player).playerContact(player);
                break;
            case D:
                int rightX = playerX < grid.length ? playerX + 1 : playerX;
                if (grid[rightX][playerY].isPassable(player)) {
                    player.moveRight();
                }
                getTileAtPlayer(player).playerContact(player);
                break;
            case W:
                int upY = playerY > 0 ? playerY - 1 : playerY;
                if (grid[playerX][upY].isPassable(player)) {
                    player.moveUp();
                }
                getTileAtPlayer(player).playerContact(player);
                break;
            case S:
                int downY = playerY < grid[0].length ? playerY + 1 : playerY;
                if (grid[playerX][downY].isPassable(player)) {
                    player.moveDown();
                }
                getTileAtPlayer(player).playerContact(player);
                break;
            default:
                System.err.println("[GameState] Invalid key " + kc.getName());
        }
    }

    private Tile getTileAtPlayer(final Player p) {
        return grid[p.getXPos()][p.getYPos()];
    }

    private void updateEnemies() {

    }

    public State getCurrentState() {
        return currentState;
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
        sbMap.append(TILE_DESC_DELIMITER);
        sbMap.append(System.lineSeparator());
        sbMap.append(sbInfo.toString());
        return sbMap.toString();
    }
}
