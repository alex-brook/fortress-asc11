import javafx.scene.image.Image;

import java.util.Map;

class KeyDoor extends Door {
    private Item requiredItem;

    KeyDoor(final char mapChar, final Map<String, Image> img, final Item item) {
        super(mapChar, img);
        requiredItem = item;
    }

    @Override
    public boolean isPassable(final Player p) {
        if (isLocked()) {
            return p.hasItem(requiredItem);
        }
        return true;
    }

    @Override
    public void playerContact(final Player p) {
        if (isLocked()) {
            p.takeItem(requiredItem);
            unlock();
        }
    }

    @Override
    public String getAdditionalInfo() {
        if (isLocked()) {
            return String.format("%c", requiredItem.getMapChar());
        }
        return null;
    }
}
