import javafx.scene.image.Image;

import java.util.Map;

class DumbTargetEnemy extends Enemy {

    DumbTargetEnemy(final int x, final int y, final char mapChar,
                    final Map<String, Image> img) {
        super(x, y, mapChar, img);
    }
}