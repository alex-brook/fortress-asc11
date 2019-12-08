import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import java.util.Map;

/**
 * Type of tile that is impassable by both the player and enemies. Graphic
 * drawn is dependent on what type of wall it is. This is decided based
 * on where the wall is drawn in relation to other wall tiles around it.
 *
 * @author Alex
 * @author Stephen
 */
class Wall extends Tile {
    private static final String WALL_BOTTOM_MID_IMG = "wall_bottom_mid.png";
    private static final String WALL_CONCAVE_BOTTOM_LEFT_IMG =
            "wall_concave_bottom_left.png";
    private static final String WALL_CONCAVE_BOTTOM_RIGHT_IMG =
            "wall_concave_bottom_right.png";
    private static final String WALL_CONCAVE_TOP_LEFT_IMG =
            "wall_concave_top_left.png";
    private static final String WALL_CONCAVE_TOP_RIGHT_IMG =
            "wall_concave_top_right.png";
    private static final String WALL_CONVEX_BOTTOM_LEFT_IMG =
            "wall_convex_bottom_left.png";
    private static final String WALL_CONVEX_BOTTOM_RIGHT_IMG =
            "wall_convex_bottom_right.png";
    private static final String WALL_CONVEX_TOP_LEFT_IMG =
            "wall_convex_top_left.png";
    private static final String WALL_CONVEX_TOP_RIGHT_IMG =
            "wall_convex_top_right.png";
    private static final String WALL_LEFT_MID_IMG =
            "wall_left_mid.png";
    private static final String WALL_RIGHT_MID_IMG = "wall_right_mid.png";
    private static final String WALL_TOP_MID_IMG = "wall_top_mid.png";
    private static final String WALL_BLOCK = "block.png";

    Wall(final char mapChar, final Map<String, Image> img) {
        super(mapChar, img);
    }

    /**
     * Checks if a wall is an internal wall (surrounded by other
     * walls).
     * @return true if it is an internal wall
     */
    public boolean isInternal() {
        return getLeftNeighbour() != null
                && getRightNeighbour() != null
                && getDownNeighbour() != null
                && getUpNeighbour() != null
                && getLeftNeighbour().getDownNeighbour() != null
                && getRightNeighbour().getDownNeighbour() != null
                && getLeftNeighbour().getUpNeighbour() != null
                && getRightNeighbour().getUpNeighbour() != null;
    }

    /**
     * Checks if a wall is in the bottom middle of a wall structure.
     * @return true if it is
     */
    public boolean isBottomMid() {
        return getDownNeighbour() == null
                && getLeftNeighbour() != null
                && getLeftNeighbour().getMapChar() == getMapChar()
                && getRightNeighbour() != null
                && getRightNeighbour().getMapChar() == getMapChar();
    }

    /**
     * Checks if a wall is in the top middle of a wall structure.
     * @return true if it is
     */
    public boolean isTopMid() {
        return getUpNeighbour() == null
                && getLeftNeighbour() != null
                && getLeftNeighbour().getMapChar() == getMapChar()
                && getRightNeighbour() != null
                && getRightNeighbour().getMapChar() == getMapChar();
    }

    /**
     * Checks if a wall is in the left middle of a wall structure.
     * @return true if it is
     */
    public boolean isLeftMid() {
        return getLeftNeighbour() == null
                && getUpNeighbour() != null
                && getUpNeighbour().getMapChar() == getMapChar()
                && getDownNeighbour() != null
                && getDownNeighbour().getMapChar() == getMapChar();
    }

    /**
     * Checks if a wall is in the right middle of a wall structure.
     * @return true if it is
     */
    public boolean isRightMid() {
        return getRightNeighbour() == null
                && getUpNeighbour() != null
                && getUpNeighbour().getMapChar() == getMapChar()
                && getDownNeighbour() != null
                && getDownNeighbour().getMapChar() == getMapChar();
    }

    /**
     * Checks if a wall is concave and in the bottom left of a wall structure.
     * @return true if it is
     */
    public boolean isConcaveBottomLeft() {
        return getLeftNeighbour() == null
                && getDownNeighbour() == null
                && getUpNeighbour() != null
                && getUpNeighbour().getMapChar() == getMapChar()
                && getRightNeighbour() != null
                && getRightNeighbour().getMapChar() == getMapChar();
    }

    /**
     * Checks if a wall is concave and in the bottom right of a wall structure.
     * @return true if it is
     */
    public boolean isConcaveBottomRight() {
        return getRightNeighbour() == null
                && getDownNeighbour() == null
                && getUpNeighbour() != null
                && getUpNeighbour().getMapChar() == getMapChar()
                && getLeftNeighbour() != null
                && getLeftNeighbour().getMapChar() == getMapChar();
    }

    /**
     * Checks if a wall is concave and in the top left of a wall structure.
     * @return true if it is
     */
    public boolean isConcaveTopLeft() {
        return getLeftNeighbour() == null
                && getUpNeighbour() == null
                && getDownNeighbour() != null
                && getDownNeighbour().getMapChar() == getMapChar()
                && getRightNeighbour() != null
                && getRightNeighbour().getMapChar() == getMapChar();
    }

    /**
     * Checks if a wall is concave and in the top right of a wall structure.
     * @return true if it is
     */
    public boolean isConcaveTopRight() {
        return getRightNeighbour() == null
                && getUpNeighbour() == null
                && getDownNeighbour() != null
                && getDownNeighbour().getMapChar() == getMapChar()
                && getLeftNeighbour() != null
                && getLeftNeighbour().getMapChar() == getMapChar();
    }

    /**
     * Checks if a wall is convex and in the top right of a wall structure.
     * @return true if it is
     */
    public boolean isConvexTopRight() {
        return !isInternal()
                && getUpNeighbour() != null
                && getUpNeighbour().getMapChar() == getMapChar()
                && getRightNeighbour() != null
                && getRightNeighbour().getMapChar() == getMapChar()
                && getLeftNeighbour() != null
                && getDownNeighbour() != null
                && getRightNeighbour().getUpNeighbour() == null;
    }

    /**
     * Checks if a wall is convex and in the bottom right of a wall structure.
     * @return true if it is
     */
    public boolean isConvexBottomRight() {
        return !isInternal()
                && getDownNeighbour() != null
                && getDownNeighbour().getMapChar() == getMapChar()
                && getRightNeighbour() != null
                && getRightNeighbour().getMapChar() == getMapChar()
                && getLeftNeighbour() != null
                && getUpNeighbour() != null
                && getRightNeighbour().getDownNeighbour() == null;
    }

    /**
     * Checks if a wall is convex and in the bottom left of a wall structure.
     * @return true if it is
     */
    public boolean isConvexBottomLeft() {
        return !isInternal()
                && getDownNeighbour() != null
                && getDownNeighbour().getMapChar() == getMapChar()
                && getLeftNeighbour() != null
                && getLeftNeighbour().getMapChar() == getMapChar()
                && getRightNeighbour() != null
                && getUpNeighbour() != null
                && getLeftNeighbour().getDownNeighbour() == null;
    }

    /**
     * Checks if a wall is convex and in the top left of a wall structure.
     * @return true if it is
     */
    public boolean isConvexTopLeft() {
        return !isInternal()
                && getUpNeighbour() != null
                && getUpNeighbour().getMapChar() == getMapChar()
                && getLeftNeighbour() != null
                && getLeftNeighbour().getMapChar() == getMapChar()
                && getRightNeighbour() != null
                && getDownNeighbour() != null
                && getLeftNeighbour().getUpNeighbour() == null;
    }

    /**
     * Dictates if the tile is passable by the player.
     * @param p instance of player
     * @return true if the player can walk on teh tile
     */
    @Override
    public boolean isPassable(final Player p) {
        return false;
    }

    /**
     * Getter for the tile colour on the minimap.
     * @return
     */
    @Override
    public Color getMinimapColor() {
        return Color.GREY;
    }

    /**
     * Draws the graphics for a wall tile in the scene.
     * @param gc drawable feature of canvas
     * @param x x coordinate
     * @param y y coordinate
     * @param animationTick runtime of animation
     */
    @Override
    public void draw(final GraphicsContext gc, final double x, final double y,
                     final int animationTick) {
        // the order these are checked in matters

        if (isBottomMid()) {
            gc.drawImage(getImage(WALL_BOTTOM_MID_IMG), x, y);
        } else if (isTopMid()) {
            gc.drawImage(getImage(WALL_TOP_MID_IMG), x, y);
        } else if (isLeftMid()) {
            gc.drawImage(getImage(WALL_LEFT_MID_IMG), x, y);
        } else if (isRightMid()) {
            gc.drawImage(getImage(WALL_RIGHT_MID_IMG), x, y);
        } else if (isConcaveBottomLeft()) {
            gc.drawImage(getImage(WALL_CONCAVE_BOTTOM_LEFT_IMG), x, y);
        } else if (isConcaveBottomRight()) {
            gc.drawImage(getImage(WALL_CONCAVE_BOTTOM_RIGHT_IMG), x, y);
        } else if (isConcaveTopLeft()) {
            gc.drawImage(getImage(WALL_CONCAVE_TOP_LEFT_IMG), x, y);
        } else if (isConcaveTopRight()) {
            gc.drawImage(getImage(WALL_CONCAVE_TOP_RIGHT_IMG), x, y);
        } else if (isConvexTopRight()) {
            gc.drawImage(getImage(WALL_CONVEX_TOP_RIGHT_IMG), x, y);
        } else if (isConvexBottomRight()) {
            gc.drawImage(getImage(WALL_CONVEX_BOTTOM_RIGHT_IMG), x, y);
        } else if (isConvexBottomLeft()) {
            gc.drawImage(getImage(WALL_CONVEX_BOTTOM_LEFT_IMG), x, y);
        } else if (isConvexTopLeft()) {
            gc.drawImage(getImage(WALL_CONVEX_TOP_LEFT_IMG), x, y);
        } else {
            gc.drawImage(getImage(WALL_BLOCK), x, y);
        }
    }
}
