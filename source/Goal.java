import javafx.scene.image.Image;
import java.util.Map;

class Goal extends Tile {
    Goal(final char mapChar, final Map<String, Image> img) {
        super(mapChar, img);
    }

    @Override
    public void playerContact(final Player p) {
        p.win();
    }
}