import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import java.util.Map;

 /**
 * Type of enemy that moves by following alongside wall tiles
 *
 * @author Irfaan
 */

class WallFollowEnemy extends Enemy {
    private static String WALL_FOLLOW_0_IMG = "slime_idle_anim_f0.png";
    private static String WALL_FOLLOW_1_IMG = "slime_idle_anim_f1.png";
    private static String WALL_FOLLOW_2_IMG = "slime_idle_anim_f2.png";
    private static String WALL_FOLLOW_3_IMG = "slime_idle_anim_f3.png";
    private static String WALL_FOLLOW_4_IMG = "slime_idle_anim_f4.png";
    private static String WALL_FOLLOW_5_IMG = "slime_idle_anim_f5.png";
    
    private String direction;

    WallFollowEnemy(final int x, final int y, final char mapChar,
                    final Map<String, Image> img) {
        super(x, y, mapChar, img);
    }

     /**
      *
      * @param passableGrid
      * @param playerX
      * @param playerY
      */
    @Override
    public void move(boolean[][] passableGrid, int playerX, int playerY) {
    }

     /**
      *
      * @param gc
      * @param x
      * @param y
      * @param animationTick
      */
    @Override
    public void draw(final GraphicsContext gc, final double x, final double y,
                     final int animationTick) {
        super.draw(gc, x, y, animationTick);
        final int anims = 6;
        switch (animationTick % anims) {
            case 0:
                gc.drawImage(getImage(WALL_FOLLOW_0_IMG), x, y);
                break;
            case 1:
                gc.drawImage(getImage(WALL_FOLLOW_1_IMG), x, y);
                break;
            case 2:
                gc.drawImage(getImage(WALL_FOLLOW_2_IMG), x, y);
                break;
            case 3:
                gc.drawImage(getImage(WALL_FOLLOW_3_IMG), x, y);
                break;
            case 4:
                gc.drawImage(getImage(WALL_FOLLOW_4_IMG), x, y);
                break;
            case 5:
                gc.drawImage(getImage(WALL_FOLLOW_5_IMG), x, y);
                break;
        }
        gc.restore();
    }
}