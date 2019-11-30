import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import java.util.Map;

/**
 * Type of tile that is impassable by both the player and enemies
 *
 * @author Alex
 */
class Wall extends Tile {
    private static final String WALL_BOTTOM_MID_IMG = "wall_bottom_mid.png";
    private static final String WALL_CONCAVE_BOTTOM_LEFT_IMG = "wall_concave_bottom_left.png";
    private static final String WALL_CONCAVE_BOTTOM_RIGHT_IMG = "wall_concave_bottom_right.png";
    private static final String WALL_CONCAVE_TOP_LEFT_IMG = "wall_concave_top_left.png";
    private static final String WALL_CONCAVE_TOP_RIGHT_IMG = "wall_concave_top_right.png";
    private static final String WALL_CONVEX_BOTTOM_LEFT_IMG = "wall_convex_bottom_left.png";
    private static final String WALL_CONVEX_BOTTOM_RIGHT_IMG = "wall_convex_bottom_right.png";
    private static final String WALL_CONVEX_TOP_LEFT_IMG = "wall_convex_top_left.png";
    private static final String WALL_CONVEX_TOP_RIGHT_IMG = "wall_convex_top_right.png";
    private static final String WALL_LEFT_MID_IMG = "wall_left_mid.png";
    private static final String WALL_RIGHT_MID_IMG = "wall_right_mid.png";
    private static final String WALL_TOP_MID_IMG = "wall_top_mid.png";
    private static final String WALL_BLOCK = "block.png";

    Wall(final char mapChar, final Map<String, Image> img) {
        super(mapChar, img);
    }

    // there is loads of dope duplication in the draw logic
    // should be refactored at some point
    /**
     *
     * @return
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
     *
     * @return
     */
    public boolean isBottomMid() {
        return getDownNeighbour() == null
                && getLeftNeighbour() != null
                && getLeftNeighbour().getMapChar() == getMapChar()
                && getRightNeighbour() != null
                && getRightNeighbour().getMapChar() == getMapChar();
    }

    /**
     *
     * @return
     */
    public boolean isTopMid() {
        return getUpNeighbour() == null
                && getLeftNeighbour() != null
                && getLeftNeighbour().getMapChar() == getMapChar()
                && getRightNeighbour() != null
                && getRightNeighbour().getMapChar() == getMapChar();
    }

    /**
     *
     * @return
     */
    public boolean isLeftMid() {
        return getLeftNeighbour() == null
                && getUpNeighbour() != null
                && getUpNeighbour().getMapChar() == getMapChar()
                && getDownNeighbour() != null
                && getDownNeighbour().getMapChar() == getMapChar();
    }

    /**
     *
     * @return
     */
    public boolean isRightMid() {
        return getRightNeighbour() == null
                && getUpNeighbour() != null
                && getUpNeighbour().getMapChar() == getMapChar()
                && getDownNeighbour() != null
                && getDownNeighbour().getMapChar() == getMapChar();
    }

    /**
     *
     * @return
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
     *
     * @return
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
     *
     * @return
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
     *
     * @return
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
     *
     * @return
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
     *
     * @return
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
     *
     * @return
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
     *
     * @return
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
     *
     * @param p
     * @return
     */
    @Override
    public boolean isPassable(final Player p) {
        return false;
    }

    /**
     *
     * @param gc
     * @param x
     * @param y
     * @param animationTick
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
