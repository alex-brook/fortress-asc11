import javafx.scene.image.Image;

import java.util.Map;

class Hazard extends Tile {
    private Item requiredItem;

    Hazard(final char mapChar, final Map<String, Image> img, final Item item) {
        super(mapChar, img);
        requiredItem = item;
    }

    @Override
    public void playerContact(final Player p) {
        if (!p.hasItem(requiredItem)) {
            p.kill();
        }
    }
}
