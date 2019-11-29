import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import java.util.Map;

/**
 * Door is a tile that acts as a wall unless the player has the correct
 * condition to open it
 *
 * @author Alex
 */
public abstract class Door extends Tile {
    private static final String DOOR_DOWN_2_IMG = "door_bottom_2.png";
    private static final String DOOR_DOWN_1_IMG = "door_bottom_1.png";
    private static final String DOOR_DOWN_3_IMG = "door_bottom_3.png";
    private static final String DOOR_LEFT_2_IMG = "door_left_2.png";
    private static final String DOOR_LEFT_1_IMG = "door_left_1.png";
    private static final String DOOR_LEFT_3_IMG = "door_left_3.png";
    private static final String DOOR_RIGHT_2_IMG = "door_right_2.png";
    private static final String DOOR_RIGHT_1_IMG = "door_right_1.png";
    private static final String DOOR_RIGHT_3_IMG = "door_right_3.png";
    private static final String DOOR_UP_2_IMG = "door_top_2.png";
    private static final String DOOR_UP_1_IMG = "door_top_1.png";
    private static final String DOOR_UP_3_IMG = "door_top_3.png";


    static boolean drewLeftDoor = false;
    private boolean locked;

    /**
     *
     * @param mapChar
     * @param img
     */
    public Door(final char mapChar, final Map<String, Image> img) {
        super(mapChar,  img);
        locked = true;
    }

    protected final boolean isLocked() {
        return locked;
    }
    protected final void unlock() {
        locked = false;
        setMapChar(TileFactory.MapChars.GROUND);
    }

    protected final boolean isLeft() {
        Tile up = getUpNeighbour();
        Tile down = getDownNeighbour();

        return up != null && up.getMapChar() == TileFactory.MapChars.WALL
                && (((Wall) up).isTopMid() || ((Wall) up).isConvexTopLeft()
                    || ((Wall) up).isInternal())
                && down != null && down.getMapChar() == TileFactory.MapChars.WALL
                && (((Wall) down).isBottomMid() || ((Wall) down).isConvexBottomLeft()
                    || ((Wall) down).isInternal());
    }

    protected final boolean isRight() {
        Tile up = getUpNeighbour();
        Tile down = getDownNeighbour();

        return up != null && up.getMapChar() == TileFactory.MapChars.WALL
                && (((Wall) up).isTopMid() || ((Wall) up).isConvexTopRight()
                    || ((Wall) up).isInternal())
                && down != null && down.getMapChar() == TileFactory.MapChars.WALL
                && (((Wall) down).isBottomMid() || ((Wall) down).isConvexBottomRight()
                    || ((Wall) down).isInternal());
    }

    protected final boolean isUp() {
        Tile left = getLeftNeighbour();
        Tile right = getRightNeighbour();

        return left != null && left.getMapChar() == TileFactory.MapChars.WALL
                && (((Wall) left).isLeftMid() || ((Wall) left).isConvexTopLeft()
                    || ((Wall) left).isInternal())
                && right != null && right.getMapChar() == TileFactory.MapChars.WALL
                && (((Wall) right).isRightMid() || ((Wall) right).isConvexTopRight()
                    || ((Wall) right).isInternal());
    }

    protected final boolean isDown() {
        Tile left = getLeftNeighbour();
        Tile right = getRightNeighbour();

        return left != null && left.getMapChar() == TileFactory.MapChars.WALL
                && (((Wall) left).isLeftMid() || ((Wall) left).isConvexBottomLeft()
                    || ((Wall) left).isInternal())
                && right != null && right.getMapChar() == TileFactory.MapChars.WALL
                && (((Wall) right).isRightMid() || ((Wall) right).isConvexBottomRight()
                    || ((Wall) right).isInternal());
    }

    public boolean isPassable(final Enemy e) {
    	if (isLocked() == true) {
    		return false;
    	}else {
    		return true;
    	}
    }
    
    @Override
    public void draw(final GraphicsContext gc, final double x, final double y,
                     final int animationTick) {
        if (isLeft()) {
            gc.drawImage(getImage(GROUND_IMG), x, y);
            gc.drawImage(getImage(DOOR_LEFT_1_IMG), x, y - GameState.TILE_RES);
            gc.drawImage(getImage(DOOR_LEFT_2_IMG), x, y);
            gc.drawImage(getImage(DOOR_LEFT_3_IMG), x, y + GameState.TILE_RES);
        } else if (isRight()) {
            gc.drawImage(getImage(GROUND_IMG), x, y);
            gc.drawImage(getImage(DOOR_RIGHT_1_IMG), x, y - GameState.TILE_RES);
            gc.drawImage(getImage(DOOR_RIGHT_2_IMG), x, y);
            gc.drawImage(getImage(DOOR_RIGHT_3_IMG), x, y + GameState.TILE_RES);
        } else if (isUp()) {
            gc.drawImage(getImage(GROUND_IMG), x, y);
            gc.drawImage(getImage(DOOR_UP_1_IMG), x - GameState.TILE_RES, y);
            gc.drawImage(getImage(DOOR_UP_2_IMG), x, y);
            gc.drawImage(getImage(DOOR_UP_3_IMG), x + GameState.TILE_RES, y);
        } else if (isDown()) {
            gc.drawImage(getImage(GROUND_IMG), x, y);
            gc.drawImage(getImage(DOOR_DOWN_1_IMG), x - GameState.TILE_RES, y);
            gc.drawImage(getImage(DOOR_DOWN_2_IMG), x, y);
            gc.drawImage(getImage(DOOR_DOWN_3_IMG), x + GameState.TILE_RES, y);
        }
    }
}
