import javafx.scene.input.KeyCode;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

class GameState {
    private final class Cell {
        public static final char WALL = '#';
        public static final char GROUND = ' ';
        public static final char WATER = '~';
        public static final char FIRE = '&';
        public static final char GOAL = '!';
        public static final char TOKEN = 'O';
        public static final char FLIPPER = 'Y';
        public static final char FIRE_BOOTS = '"';
        public static final char WALL_FOLLOW_ENEMY = 'W';
        public static final char DUMB_TARGET_ENEMY = 'D';
        public static final char SMART_TARGET_ENEMY = 'S';
        public static final char COMPLEX = '?';

        private Cell() {
        }
    }
    private final class ComplexEntity {
        public static final String PLAYER = "Player";
        public static final String KEY = "Key";
        public static final String KEY_DOOR = "KeyDoor";
        public static final String SCORE_DOOR = "ScoreDoor";
        public static final String TELEPORTER = "Teleporter";
        public static final String STRAIGHT_LINE_ENEMY = "StraightLineEnemy";

        private ComplexEntity() {
        }
    }

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

    // parse a string representing the map, and load all values into their
    // respective instance variables.
    private void load(final String map) {
        enemies = new LinkedList<>();
        // Split the level file into its logical components
        String[] mapParts = map.split("DESCRIPTION" + System.lineSeparator());
        String[] tiles = mapParts[0].split(System.lineSeparator());
        String[] entities = mapParts[1].split(System.lineSeparator());
        grid = new Tile[tiles[0].length()][tiles.length];
        Queue<String[]> entityQueue = new LinkedList<>();
        for (String entity : entities) {
            entityQueue.add(entity.split(", "));
        }
        // For each char in the level file
        for (int y = 0; y < tiles.length; y++) {
            for (int x = 0; x < tiles[y].length(); x++) {
                char current = tiles[y].charAt(x);
                entityQueue = loadCell(current, x, y, entityQueue);
            }
        }
    }
    // instantiate a cell in the map file depending on what it is.
    private Queue<String[]> loadCell(final char current,
                                     final int x, final int y,
                                     Queue<String[]> entities) {
        switch (current) {
            case Cell.WALL:
                grid[x][y] = new Wall();
                break;
            case Cell.GROUND:
                grid[x][y] = new Ground();
                break;
            case Cell.WATER:
                // required item flippers
                grid[x][y] = new Hazard();
                break;
            case Cell.FIRE:
                // required item fire boots
                grid[x][y] = new Hazard();
                break;
            case Cell.GOAL:
                grid[x][y] = new Goal();
                break;
            case Cell.TOKEN:
                // contains item token
                grid[x][y] = new Ground(Item.TOKEN);
                break;
            case Cell.FLIPPER:
                // contains item flipper
                grid[x][y] = new Ground(Item.FLIPPERS);
                break;
            case Cell.FIRE_BOOTS:
                // contains item fire boots
                grid[x][y] = new Ground(Item.FIRE_BOOTS);
                break;
            case Cell.WALL_FOLLOW_ENEMY:
                grid[x][y] = new Ground();
                // will need to know its x and y
                enemies.add(new EnemyWallFollow());
                break;
            case Cell.DUMB_TARGET_ENEMY:
                grid[x][y] = new Ground();
                // will need to know its x and y
                enemies.add(new EnemyDumbTarget());
                break;
            case Cell.SMART_TARGET_ENEMY:
                grid[x][y] = new Ground();
                // will need to know its x and y
                enemies.add(new EnemySmartTarget());
                break;
            case Cell.COMPLEX:
                loadComplexEntity(entities.remove(), x, y);
                break;
            default:
                throw new IllegalArgumentException("Unexpected character");
        }
        return entities;
    }

    // initialise a cell with additional information.
    private void loadComplexEntity(final String[] entity,
                                   final int x, final int y) {
        switch (entity[0]) {
            case ComplexEntity.KEY:
                grid[x][y] = new Ground(keyFromColorString(entity[1]));
                break;
            case ComplexEntity.KEY_DOOR:
                grid[x][y] = new KeyDoor(keyFromColorString(entity[1]));
                break;
            case ComplexEntity.SCORE_DOOR:
                grid[x][y] = new ScoreDoor();
                break;
            case ComplexEntity.TELEPORTER:
                grid[x][y] = new Teleporter();
                break;
            case ComplexEntity.STRAIGHT_LINE_ENEMY:
                grid[x][y] = new Ground();
                enemies.add(new EnemyStraightLine());
                break;
            case ComplexEntity.PLAYER:
                player = new Player();
                break;
            default:
                throw new IllegalArgumentException("Malformed complex entity");
        }
    }

    private Item keyFromColorString(final String color) {
        switch (color) {
            case "Red":
                return Item.RED_KEY;
            case "Blue":
                return Item.BLUE_KEY;
            case "Green":
                return Item.GREEN_KEY;
            default:
                throw new IllegalArgumentException("Invalid key color");
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
