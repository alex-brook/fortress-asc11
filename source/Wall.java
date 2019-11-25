import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import java.util.Map;

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

    Wall(final char mapChar, final Map<String, Image> img) {
        super(mapChar, img);
    }

    // there is loads of dope duplication in the draw logic
    // should be refactored at some point
    private boolean isBottomMid() {
        return getDownNeighbour() == null
                && getLeftNeighbour() != null
                && getLeftNeighbour().getMapChar() == getMapChar()
                && getRightNeighbour() != null
                && getRightNeighbour().getMapChar() == getMapChar();
    }

    private boolean isTopMid() {
        return getUpNeighbour() == null
                && getLeftNeighbour() != null
                && getLeftNeighbour().getMapChar() == getMapChar()
                && getRightNeighbour() != null
                && getRightNeighbour().getMapChar() == getMapChar()
                && getDownNeighbour() != null
                && getDownNeighbour().getMapChar() != getMapChar();
    }

    private boolean isLeftMid() {
        return getLeftNeighbour() == null
                && getUpNeighbour() != null
                && getUpNeighbour().getMapChar() == getMapChar()
                && getDownNeighbour() != null
                && getDownNeighbour().getMapChar() == getMapChar();
    }

    private boolean isRightMid() {
        return getRightNeighbour() == null
                && getUpNeighbour() != null
                && getUpNeighbour().getMapChar() == getMapChar()
                && getDownNeighbour() != null
                && getDownNeighbour().getMapChar() == getMapChar();
    }

    private boolean isConcaveBottomLeft() {
        return getLeftNeighbour() == null
                && getDownNeighbour() == null
                && getUpNeighbour() != null
                && getUpNeighbour().getMapChar() == getMapChar()
                && getRightNeighbour() != null
                && getRightNeighbour().getMapChar() == getMapChar();
    }

    private boolean isConcaveBottomRight() {
        return getRightNeighbour() == null
                && getDownNeighbour() == null
                && getUpNeighbour() != null
                && getUpNeighbour().getMapChar() == getMapChar()
                && getLeftNeighbour() != null
                && getLeftNeighbour().getMapChar() == getMapChar();
    }

    private boolean isConcaveTopLeft() {
        return getLeftNeighbour() == null
                && getUpNeighbour() == null
                && getDownNeighbour() != null
                && getDownNeighbour().getMapChar() == getMapChar()
                && getRightNeighbour() != null
                && getRightNeighbour().getMapChar() == getMapChar();
    }

    private boolean isConcaveTopRight() {
        return getRightNeighbour() == null
                && getUpNeighbour() == null
                && getDownNeighbour() != null
                && getDownNeighbour().getMapChar() == getMapChar()
                && getLeftNeighbour() != null
                && getLeftNeighbour().getMapChar() == getMapChar();
    }

    private boolean isConvexTopRight() {
        return getUpNeighbour() != null
                && getUpNeighbour().getMapChar() == getMapChar()
                && getRightNeighbour() != null
                && getRightNeighbour().getMapChar() == getMapChar()
                && getLeftNeighbour() != null
                && getLeftNeighbour().getMapChar() != getMapChar()
                && getDownNeighbour() != null
                && getDownNeighbour().getMapChar() != getMapChar();
    }

    private boolean isConvexBottomRight() {
        return getDownNeighbour() != null
                && getDownNeighbour().getMapChar() == getMapChar()
                && getRightNeighbour() != null
                && getRightNeighbour().getMapChar() == getMapChar()
                && getLeftNeighbour() != null
                && getLeftNeighbour().getMapChar() != getMapChar()
                && getUpNeighbour() != null
                && getUpNeighbour().getMapChar() != getMapChar();
    }

    private boolean isConvexBottomLeft() {
        return getDownNeighbour() != null
                && getDownNeighbour().getMapChar() == getMapChar()
                && getLeftNeighbour() != null
                && getLeftNeighbour().getMapChar() == getMapChar()
                && getRightNeighbour() != null
                && getRightNeighbour().getMapChar() != getMapChar()
                && getUpNeighbour() != null
                && getUpNeighbour().getMapChar() != getMapChar();
    }

    private boolean isConvexTopLeft() {
        return getUpNeighbour() != null
                && getUpNeighbour().getMapChar() == getMapChar()
                && getLeftNeighbour() != null
                && getLeftNeighbour().getMapChar() == getMapChar()
                && getRightNeighbour() != null
                && getRightNeighbour().getMapChar() != getMapChar()
                && getDownNeighbour() != null
                && getDownNeighbour().getMapChar() != getMapChar();
    }


    @Override
    public boolean isPassable(final Player p) {
        return false;
    }

    @Override
    public void draw(final GraphicsContext gc, final double x, final double y,
                     final int animationTick) {
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
        }
    }
}
