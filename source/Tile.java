import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.Map;

public abstract class Tile {
    private char mapChar;

    private Map<String, Image> images;

    private Tile upNeighbour;
    private Tile downNeighbour;
    private Tile leftNeighbour;
    private Tile rightNeighbour;

    public Tile(final char mapc, final Map<String, Image> img) {
        mapChar = mapc;
        images = img;
    }

    public boolean isPassable(final Player p) {
        return true;
    }

    public boolean isPassable(final Enemy e) {
        // probably easier to assume an enemy can't go on a tile unless
        // told otherwise.
        return false;
    }

    public void playerContact(final Player p) {
        return;
    }

    public void setUpNeighbour(final Tile t) {
        upNeighbour = t;
    }

    public void setDownNeighbour(final Tile t) {
        downNeighbour = t;
    }

    public void setLeftNeighbour(final Tile t) {
        leftNeighbour = t;
    }

    public void setRightNeighbour(final Tile t) {
        rightNeighbour = t;
    }

    public Tile getUpNeighbour() {
        return upNeighbour;
    }

    public Tile getDownNeighbour() {
        return downNeighbour;
    }

    public Tile getLeftNeighbour() {
        return leftNeighbour;
    }

    public Tile getRightNeighbour() {
        return rightNeighbour;
    }


    public final char getMapChar() {
        return mapChar;
    }

    protected final void setMapChar(final char mapc) {
        mapChar = mapc;
    }

    public String getAdditionalInfo() {
        return null;
    }

    public void draw(final GraphicsContext gc, final double x, final double y,
                     final int animationTick) {
        return;
    }

    protected Image getImage(final String name) {
        return images.get(name);
    }

    @Override
    public final String toString() {
        return String.valueOf(getMapChar());
    }
}