import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.Map;

class StraightLineEnemy extends Enemy {
    private static final String STRAIGHT_0_IMG = "fly_anim_f0.png";
    private static final String STRAIGHT_1_IMG = "fly_anim_f1.png";
    private static final String STRAIGHT_2_IMG = "fly_anim_f2.png";
    private static final String STRAIGHT_3_IMG = "fly_anim_f3.png";


    private String direction;

    StraightLineEnemy(final int x, final int y, final char mapChar,
                      final Map<String, Image> img, final String dir) {
        super(x, y, mapChar, img);
        direction = dir;
    }

    @Override
    public void draw(final GraphicsContext gc, final double x, final double y,
                     final int animationTick) {
        final int anims = 4;
        switch (animationTick % anims) {
            case 0:
                gc.drawImage(getImage(STRAIGHT_0_IMG), x, y);
                break;
            case 1:
                gc.drawImage(getImage(STRAIGHT_1_IMG), x, y);
                break;
            case 2:
                gc.drawImage(getImage(STRAIGHT_2_IMG), x, y);
                break;
            case 3:
                gc.drawImage(getImage(STRAIGHT_3_IMG), x, y);
                break;
        }
    }

    @Override
    public String getAdditionalInfo() {
        return String.format("%s", direction);
    }

}