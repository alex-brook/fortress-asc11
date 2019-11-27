import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;

import java.io.File;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

class GameState {
    private static final String BACKGROUND_IMG = "background1.png";

    public static final String MAP_DESC_DELIMITER = "MAP";
    public static final String TILE_DESC_DELIMITER = "TILES";
    public static final String ENEMY_DESC_DELIMITER = "ENEMIES";
    public static final String PLAYER_DESC_DELIMITER = "PLAYER";
    public static final String ID_DELIMITER = ":";
    public static final String INFO_DELIMITER = ",";

    public static final double TILE_RES = 32;

    public enum State {
        WIN,
        LOSE,
        RUNNING;
    }

    private Map<String, Image> img;
    private State currentState;
    private Tile[][] grid;
    private Player player;
    private List<Enemy> enemies;
    private long timeElapsed;
    private long startTime;
    private int animationTick = 0;

    GameState(final String map) {
        loadImages("assets/");
        load(map);
        currentState = State.RUNNING;
    }

    private void loadImages(final String path) {
        final String extension = ".png";
        System.out.println("[LOADING] Loading images...");

        img = new HashMap<>();
        File directory = new File(path);
        for (final File f : directory.listFiles()) {
            if (f.isDirectory()) {
                loadImages(f.getPath());
            } else if (f.getName().contains(extension)) {
                Image image = new Image(f.toURI().toString(),
                        TILE_RES, TILE_RES, false, false);
                img.put(f.getName(), image);
            }
        }
        System.out.println("[LOADING] Finished loading images.");
    }

    private void load(final String map) {
        final int timeIndex = 0;
        final int mapIndex = 1;
        final int tilesIndex = 2;
        final int enemiesIndex = 3;
        final int playerIndex = 4;

        String[] parts = getMapComponents(map);
        loadTime(parts[timeIndex]);
        loadTiles(parts[mapIndex], parts[tilesIndex]);
        loadEnemies(parts[enemiesIndex]);
        loadPlayer(parts[playerIndex]);
    }

    private void loadTime(final String time) {
        timeElapsed = Long.parseLong(time.trim());
        startTime = System.currentTimeMillis();
        System.out.printf("[INFO]: time starting from %d\n", timeElapsed);
    }

    private void loadTiles(final String tileMap, final String tileDesc) {
        TileFactory tf = new TileFactory(img);
        String[] rows = tileMap.split(System.lineSeparator());
        grid = new Tile[rows[0].length()][rows.length];
        String[] tiles =
                tileDesc.split(System.lineSeparator());
        int descCounter = 0;
        String[] currentDesc = tiles[descCounter].split(ID_DELIMITER);
        for (int y = 0; y < rows.length; y++) {
            for (int x = 0; x < rows[0].length(); x++) {
                char cur = rows[y].charAt(x);
                boolean hasDesc = currentDesc[0].length() >= 1
                    && cur == currentDesc[0].charAt(0);

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

        for (int y = 0; y < grid[0].length; y++) {
            for (int x = 0; x < grid.length; x++) {
                if (grid[x][y] != null) {
                    boolean bottom = y == (grid[0].length - 1);
                    boolean top = y == 0;
                    boolean left = x == 0;
                    boolean right = x == (grid.length - 1);

                    if (!top) {
                        grid[x][y].setUpNeighbour(grid[x][y - 1]);
                        if (grid[x][y - 1] != null) {
                            grid[x][y - 1].setDownNeighbour(grid[x][y]);
                        }
                    }
                    if (!left) {
                        grid[x][y].setLeftNeighbour(grid[x - 1][y]);
                        if (grid[x - 1][y] != null) {
                            grid[x - 1][y].setRightNeighbour(grid[x][y]);
                        }
                    }
                }
            }
        }
    }

    private void loadEnemies(final String enemyDesc) {
        final int numMandatoryInfo = 3;
        enemies = new LinkedList<>();
        EnemyFactory ef = new EnemyFactory();
        String[] enemyDescriptions = enemyDesc.split(System.lineSeparator());

        if (enemyDescriptions[0].length() == 0) {
            return;
        }

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
        player = new Player(x, y, addInfo, img);
    }

    private String[] getMapComponents(final String map) {
        String splitRegex = String.format("%s%s|%s%s|%s%s|%s%s",
                MAP_DESC_DELIMITER, System.lineSeparator(),
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

    private Enemy getEnemyAtLocation(final int x, final int y) {
        for (Enemy e : enemies) {
            if (e.getXPos() == x && e.getYPos() == y) {
                return e;
            }
        }
        return null;
    }

    private boolean[][] getPassableGrid(final Enemy e) {
        boolean[][] passableGrid = new boolean[grid[0].length][grid.length];
        for (int y = 0; y < grid[0].length; y++) {
            for (int x = 0; x < grid.length; x++) {
                boolean anotherEnemyHere = getEnemyAtLocation(x, y) != null
                        && getEnemyAtLocation(x, y) != e;
                boolean tileIsPassable = grid[x][y].isPassable(e);

                passableGrid[x][y] = tileIsPassable && !anotherEnemyHere;
            }
        }
        return passableGrid;
    }

    private void updateEnemies() {
        /*
        for each enemy {
            find out the move it wants to do
            is it valid? {
                do the move
            }
        }
         */
    }

    public State getCurrentState() {
        return currentState;
    }

    public String save() {
        StringBuilder sbTime = new StringBuilder();
        StringBuilder sbMap = new StringBuilder();
        StringBuilder sbTiles = new StringBuilder();
        StringBuilder sbEnemies = new StringBuilder();
        StringBuilder sbPlayer = new StringBuilder();
        //work out time
        long timeCurrentSession = (System.currentTimeMillis() - startTime)
                + timeElapsed;
        sbTime.append(timeCurrentSession);
        sbTime.append(System.lineSeparator());
        // get every map tile
        for (int y = 0; y < grid[0].length; y++) {
            for (int x = 0; x < grid.length; x++) {
                char current = grid[x][y].getMapChar();
                sbMap.append(current);
                // if this tile needs a description
                String info = grid[x][y].getAdditionalInfo();
                if (info != null) {
                    sbTiles.append(current);
                    sbTiles.append(ID_DELIMITER);
                    sbTiles.append(info);
                    sbTiles.append(System.lineSeparator());
                }
            }
            sbMap.append(System.lineSeparator());
        }
        // get every enemy
        for (Enemy e : enemies) {
            sbEnemies.append(e.getMapChar());
            sbEnemies.append(ID_DELIMITER);
            sbEnemies.append(e.getXPos());
            sbEnemies.append(INFO_DELIMITER);
            sbEnemies.append(e.getYPos());
            String info = e.getAdditionalInfo();
            if (info != null) {
                sbEnemies.append(INFO_DELIMITER);
                sbEnemies.append(info);
            }
            sbEnemies.append(System.lineSeparator());
        }
        sbPlayer.append(player.getMapChar());
        sbPlayer.append(ID_DELIMITER);
        sbPlayer.append(player.getXPos());
        sbPlayer.append(INFO_DELIMITER);
        sbPlayer.append(player.getYPos());
        sbPlayer.append(INFO_DELIMITER);
        sbPlayer.append(player.getAdditionalInfo());
        sbPlayer.append(System.lineSeparator());

        return sbTime.toString() + MAP_DESC_DELIMITER + System.lineSeparator()
                + sbMap.toString() + TILE_DESC_DELIMITER + System.lineSeparator()
                + sbTiles.toString() + ENEMY_DESC_DELIMITER + System.lineSeparator()
                + sbEnemies.toString() + PLAYER_DESC_DELIMITER + System.lineSeparator()
                + sbPlayer.toString();
    }

    public void draw(final GraphicsContext gc) {
        gc.drawImage(img.get(BACKGROUND_IMG), 0, 0);
        //tiles
        for (int y = 0; y < grid[0].length; y++) {
            // only draw walls
            for (int x = 0; x < grid.length; x++) {
                if (grid[x][y] != null && grid[x][y].getMapChar() == TileFactory.MapChars.WALL) {
                    grid[x][y].draw(gc, x * TILE_RES, y * TILE_RES, 0);
                }
            }
            for (int x = 0; x < grid.length; x++) {
                if (grid[x][y] != null && grid[x][y].getMapChar() != TileFactory.MapChars.WALL) {
                    grid[x][y].draw(gc, x * TILE_RES, y * TILE_RES, 0);
                }
            }
        }
        //player
        player.draw(gc, player.getXPos() * TILE_RES,
                player.getYPos() * TILE_RES, animationTick);

        animationTick = (animationTick + 1) % 5;
    }

    @Override
    public String toString() {
        // pretty output until we get a tileset
        StringBuilder out = new StringBuilder();
        for (int y = 0; y < grid[0].length; y++) {
            for (int x = 0; x < grid.length; x++) {
                if (grid[x][y] != null) {
                    boolean playerHere = (player.getXPos() == x
                            && player.getYPos() == y);
                    boolean enemyHere = getEnemyAtLocation(x, y) != null;

                    if (playerHere) {
                        out.append(player.getMapChar());
                    } else if (enemyHere) {
                        out.append(getEnemyAtLocation(x, y).getMapChar());
                    } else {
                        out.append(grid[x][y].getMapChar());
                    }
                } else {
                    out.append(' ');
                }
            }
            out.append(System.lineSeparator());
        }
        out.append(player.getAdditionalInfo());
        return out.toString();
    }
}
