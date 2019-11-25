import javafx.scene.image.Image;
import java.util.Map;

public abstract class Door extends Tile {
    private boolean locked;

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
}
