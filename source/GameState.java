import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.PixelWriter;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;

import java.io.File;
import java.net.URL;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Reads in data from a save file uses that to create the level layout, then
 * controls and coordinates all of the game elements while the game is
 * being run. Saves the current level layout to a separate file
 *
 * @author Alex
 */

class GameState {
    private static final String BACKGROUND_IMG = "background1.png";

    public static final String MAP_DESC_DELIMITER = "MAP";
    public static final String TILE_DESC_DELIMITER = "TILES";
    public static final String ENEMY_DESC_DELIMITER = "ENEMIES";
    public static final String PLAYER_DESC_DELIMITER = "PLAYER";
    public static final String ID_DELIMITER = ":";
    public static final String INFO_DELIMITER = ",";

    public static final double TILE_RES = 32;
    public static final int VIEW_RADIUS = 7;
    public static final double MINIMAP_PIXEL_SIZE = 7;

    public enum State {
        WIN,
        LOSE,
        RUNNING;
    }

    private Map<String, Image> img;
    private String map;
    private State currentState;
    private Tile[][] grid;
    private Player player;
    private List<Enemy> enemies;
    private long timeElapsed;
    private long startTime;
    private int animationTick = 0;

    GameState(final String mapStr) {
        map = mapStr;
        String path = getClass().getClassLoader().getResource("./assets").getPath();
        loadImages(path);
        load(map);
        currentState = State.RUNNING;
    }

    /**
     * Puts all .png files at designated filepath into a hash map
     * @param path filepath
     */
    private void loadImages(final String path) {
        final String extension = ".png";
        System.out.println("[LOADING] Loading images...");

        img = new HashMap<>();
        File directory = new File(path);
        for (final File f : directory.listFiles()) {
            if (f.isDirectory()) {
                loadImages(f.getPath());
            } else if (f.getName().contains(extension) && !f.getName().equals(BACKGROUND_IMG)) {
                Image image = new Image(f.toURI().toString(),
                        TILE_RES, TILE_RES, false, false);
                img.put(f.getName(), image);
            } else if (f.getName().equals(BACKGROUND_IMG)) {
                Image image = new Image(f.toURI().toString());
                img.put(f.getName(), image);
            }
        }
        System.out.println("[LOADING] Finished loading images.");
    }

    /**
     * Creates a level from designated map
     * @param map name of the map file to be drawn
     */
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

    /**
     * Creates framework to save time spent on level
     * @param time milliseconds spent on the level
     */
    private void loadTime(final String time) {
        timeElapsed = Long.parseLong(time.trim());
        startTime = System.currentTimeMillis();
        System.out.printf("[INFO]: time starting from %d\n", timeElapsed);
    }

    /**
     * Creates and loads the tiles into the level scene from a save file
     * @param tileMap map file text
     * @param tileDesc extra info for certain tiles
     */
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

    /**
     * Creates and loads the enemies into the level scene
     * @param enemyDesc extra info for certain enemy types
     */
    private void loadEnemies(final String enemyDesc) {
        final int numMandatoryInfo = 3;
        enemies = new LinkedList<>();
        EnemyFactory ef = new EnemyFactory(img);
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

    /**
     * Creates and loads the player into the level scene
     * @param desc Extra info for player
     */
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

    /**
     * Returns the components of a level map from the save file
     * @param map map filename
     * @return Array of logical components of the map file
     */
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

    /**
     * Changes the current game state from running to lose or win
     * @param kc key pressed
     */
    public void update(final KeyCode kc) {
        updatePlayer(kc);
        updateEnemies();
        if (player.isDead()) {
            currentState = State.LOSE;
        } else if (player.hasWon()) {
            currentState = State.WIN;
        }
    }

    /**
     * Moves the player in the designated direction from player input
     * @param kc key pressed
     */
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

    /**
     * returns the tile at the player's current x,y coordinates
     * @param p player instance to be used
     * @return tile at current player position
     */
    private Tile getTileAtPlayer(final Player p) {
        return grid[p.getXPos()][p.getYPos()];
    }

    /**
     * Checks specified coordinates for an instance of enemy
     * @param x x coordinate being checked
     * @param y y coordinate being checked
     * @return enemy at specified coordinates or null
     */
    private Enemy getEnemyAtLocation(final int x, final int y) {
        for (Enemy e : enemies) {
            if (e.getXPos() == x && e.getYPos() == y) {
                return e;
            }
        }
        return null;
    }

    /**
     * Checks every tile in the scene for if the instance of enemy can move there
     * @param e instance of enemy
     * @return 2D array of booleans that dictate if the enemy can move there or not
     */
    private boolean[][] getPassableGrid(final Enemy e) {
        boolean[][] passableGrid = new boolean[grid.length][grid[0].length];
        for (int y = 0; y < grid[0].length; y++) {
            for (int x = 0; x < grid.length; x++) {
                if (grid[x][y] != null) {
                    boolean anotherEnemyHere = getEnemyAtLocation(x, y) != null
                            && getEnemyAtLocation(x, y) != e;
                    boolean tileIsPassable = grid[x][y].isPassable(e);
                    passableGrid[x][y] = tileIsPassable && !anotherEnemyHere;
                }
            }
        }
        return passableGrid;
    }

    /**
     * Moves enemy
     */
    private void updateEnemies() {
        for (Enemy e : enemies) {
            e.move(getPassableGrid(e), player.getXPos(), player.getYPos());
            if (getEnemyAtLocation(player.getXPos(), player.getYPos())
                    != null) {
                player.kill();
            }
        }
    }

    /**
     * Gets the current state of the level (running, lose, win)
     * @return current state of the level
     */
    public State getCurrentState() {
        return currentState;
    }

    /**
     * Converts the current game state to a string
     * @return game state as a string
     */
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
                if (grid[x][y] != null) {
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

    /**
     *
     */
    public void restart() {
        load(map);
        currentState = State.RUNNING;
    }

    /**
     *
     * @param gc
     * @param tick
     */
    public void drawRadius(final GraphicsContext gc, final boolean tick) {
        final double viewOffset = VIEW_RADIUS * TILE_RES;

        int playerX = player.getXPos();
        int playerY = player.getYPos();

        int xMin = playerX - VIEW_RADIUS + 1;
        int xMax = playerX + VIEW_RADIUS + 1;
        int yMin = playerY - VIEW_RADIUS - 1;
        int yMax = playerY + VIEW_RADIUS - 1;

        gc.drawImage(img.get(BACKGROUND_IMG), 0, 0);

        for (int y = yMin; y < yMax; y++) {
            for (int x = xMin; x < xMax; x++) {
                boolean xInBounds = x >= 0 && x < grid.length;
                boolean yInBounds = y >= 0 && y < grid[0].length;
                double drawX = (x - xMin) * TILE_RES;
                double drawY = (y - yMin) * TILE_RES;
                if (xInBounds && yInBounds && grid[x][y] != null) {
                    grid[x][y].draw(gc, drawX, drawY, animationTick);
                }
                Enemy enemyHere = getEnemyAtLocation(x, y);
                if (enemyHere != null) {
                    enemyHere.draw(gc, drawX, drawY, animationTick);
                }
                if (playerX == x && playerY == y) {
                    player.draw(gc, drawX, drawY, animationTick);
                }
            }
        }
        player.drawInventory(gc, 0, (VIEW_RADIUS * TILE_RES) * 2 );
        drawMinimap(gc, 0, 0);
        if (tick) {
            animationTick++;
        }
    }

    /**
     * Draws all components of a level
     * @param gc canvas it draws on
     * @param tick flag for if you want to update the animation
     */
    public void draw(final GraphicsContext gc, final boolean tick) {
        gc.drawImage(img.get(BACKGROUND_IMG), 0, 0);
        //tiles
        int x = 0;
        int y = 0;
        for (y = 0; y < grid[0].length; y++) {
            // only draw walls
            for (x = 0; x < grid.length; x++) {
                if (grid[x][y] != null && grid[x][y].getMapChar() == TileFactory.MapChars.WALL) {
                    grid[x][y].draw(gc, x * TILE_RES, y * TILE_RES, animationTick);
                }
            }
            // draw everything apart from walls
            for (x = 0; x < grid.length; x++) {
                if (grid[x][y] != null && grid[x][y].getMapChar() != TileFactory.MapChars.WALL) {
                    grid[x][y].draw(gc, x * TILE_RES, y * TILE_RES, animationTick);
                }
            }
        }
        //enemies
        for (Enemy e : enemies) {
            e.draw(gc, e.getXPos() * TILE_RES, e.getYPos() * TILE_RES, animationTick);
        }
        //player
        player.draw(gc, player.getXPos() * TILE_RES,
                player.getYPos() * TILE_RES, animationTick);

        if (tick) {
            animationTick++;
        }
    }

    private void drawMinimap(final GraphicsContext gc,
                             final double xOrigin, final double yOrigin) {
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, (grid.length + 1) * MINIMAP_PIXEL_SIZE,
                (grid[0].length + 1) * MINIMAP_PIXEL_SIZE);

        for (int y = 0; y < grid[0].length; y++) {
            for (int x = 0; x < grid.length; x++) {
                if (player.getXPos() == x && player.getYPos() == y) {
                    gc.setFill(Color.WHITE);
                    gc.fillRect(xOrigin + (x * MINIMAP_PIXEL_SIZE)
                            , yOrigin + (y * MINIMAP_PIXEL_SIZE)
                            , MINIMAP_PIXEL_SIZE, MINIMAP_PIXEL_SIZE);
                } else if (getEnemyAtLocation(x, y) != null) {
                    gc.setFill(Color.RED);
                    gc.fillRect(xOrigin + (x * MINIMAP_PIXEL_SIZE)
                            , yOrigin + (y * MINIMAP_PIXEL_SIZE)
                            , MINIMAP_PIXEL_SIZE, MINIMAP_PIXEL_SIZE);
                } else if (grid[x][y] != null) {
                    gc.setFill(grid[x][y].getMinimapColor());
                    gc.fillRect(xOrigin + (x * MINIMAP_PIXEL_SIZE)
                            , yOrigin + (y * MINIMAP_PIXEL_SIZE)
                            , MINIMAP_PIXEL_SIZE, MINIMAP_PIXEL_SIZE);
                }
            }
        }
    }

    /**
     * Standard toString override
     * @return new toString override
     */
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
