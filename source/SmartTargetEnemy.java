import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.Map;

class SmartTargetEnemy extends Enemy {
    private static final String SMART_0_IMG = "goblin_idle_anim_f0.png";
    private static final String SMART_1_IMG = "goblin_idle_anim_f1.png";
    private static final String SMART_2_IMG = "goblin_idle_anim_f2.png";
    private static final String SMART_3_IMG = "goblin_idle_anim_f3.png";
    private static final String SMART_4_IMG = "goblin_idle_anim_f4.png";

    SmartTargetEnemy(final int x, final int y, final char mapChar,
                     final Map<String, Image> img) {
        super(x, y, mapChar, img);
    }

    @Override
    public void draw(final GraphicsContext gc, final double x, final double y,
                    final int animationTick) {
        final int anims = 5;
        switch (animationTick % anims) {
            case 0:
                gc.drawImage(getImage(SMART_0_IMG), x, y);
                break;
            case 1:
                gc.drawImage(getImage(SMART_1_IMG), x, y);
                break;
            case 2:
                gc.drawImage(getImage(SMART_2_IMG), x, y);
                break;
            case 3:
                gc.drawImage(getImage(SMART_3_IMG), x, y);
                break;
            case 4:
                gc.drawImage(getImage(SMART_4_IMG), x, y);
                break;
        }
    }
}