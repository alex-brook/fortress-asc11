import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.Map;

public abstract class Tile {
    private char mapChar;

    private Tile upNeighbour;
    private Tile downNeighbour;
    private Tile leftNeighbour;
    private Tile rightNeighbour;

    public Tile(final char mapc) {
        mapChar = mapc;
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

    public boolean sameUpNeighbour() {
        return upNeighbour != null && upNeighbour.getMapChar() == getMapChar();
    }

    public boolean sameDownNeighbour() {
        return downNeighbour != null && downNeighbour.getMapChar() == getMapChar();
    }

    public boolean sameLeftNeighbour() {
        return leftNeighbour != null && leftNeighbour.getMapChar() == getMapChar();
    }

    public boolean sameRightNeighbour() {
        return rightNeighbour != null && rightNeighbour.getMapChar() == getMapChar();
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

    public void draw(final GraphicsContext gc, final int x, final int y,
                     final Map<String, Image> images, final int animationTick) {
        return;
    }

    @Override
    public final String toString() {
        return String.valueOf(getMapChar());
    }
}