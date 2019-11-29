import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.Map;

/**
 * Type of enemy that walks in a straight line until it reaches an impassable
 * tile, where it turns and moves in the opposite direction
 *
 * @author Irfaan
 */

class StraightLineEnemy extends Enemy {
    private static final String STRAIGHT_0_IMG = "fly_anim_f0.png";
    private static final String STRAIGHT_1_IMG = "fly_anim_f1.png";
    private static final String STRAIGHT_2_IMG = "fly_anim_f2.png";
    private static final String STRAIGHT_3_IMG = "fly_anim_f3.png";

    private Direction direction;

    StraightLineEnemy(final int x, final int y, final char mapChar,
                      final Map<String, Image> img, final Direction dir) {
        super(x, y, mapChar, img);
        direction = dir;
    }

    @Override
    public void move(final boolean[][] passable, final int playerX,
                     final int playerY) {
        switch (direction) {
            case UP:
                if (passable[getXPos()][getUpY()]) {
                    moveUp();
                } else {
                    direction = Direction.DOWN;
                    moveDown();
                }
                break;
            case DOWN:
                if (passable[getXPos()][getDownY()]) {
                    moveDown();
                } else {
                    direction = Direction.UP;
                    moveUp();
                }
                break;
            case LEFT:
                if (passable[getLeftX()][getYPos()]) {
                    moveLeft();
                } else {
                    direction = Direction.RIGHT;
                    moveRight();
                }
                break;
            case RIGHT:
                if (passable[getRightX()][getYPos()]) {
                    moveRight();
                } else {
                    direction = Direction.LEFT;
                    moveLeft();
                }
                break;
        }
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