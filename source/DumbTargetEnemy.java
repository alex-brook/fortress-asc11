import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.Map;

/**
 * An enemy type which follows the player taking an unoptimised route
 *
 * @author Irfaan
 */

class DumbTargetEnemy extends Enemy {
    private static final String DUMB_0_IMG = "red_goblin_idle_anim_f0.png";
    private static final String DUMB_1_IMG = "red_goblin_idle_anim_f1.png";
    private static final String DUMB_2_IMG = "red_goblin_idle_anim_f2.png";
    private static final String DUMB_3_IMG = "red_goblin_idle_anim_f3.png";
    private static final String DUMB_4_IMG = "red_goblin_idle_anim_f4.png";
    
    private double xDiff;
    private double yDiff;

    DumbTargetEnemy(final int x, final int y, final char mapChar,
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
        compareX(playerX);
        compareY(playerY);

        if (xDiff == 0) {
            if (yDiff == 0) {
                //kill player
            } else if (yDiff < 0) {
                if (passableGrid[getXPos()][getUpY()] == true) {
                    moveUp();
                }
            } else if (yDiff > 0) {
                if (passableGrid[getXPos()][getDownY()] == true) {
                    moveDown();
                }
            }
        } else if (yDiff == 0) {
            if (xDiff == 0) {
                //kill player
            } else if (xDiff < 0) {
                if (passableGrid[getLeftX()][getYPos()] == true) {
                    moveLeft();
                }
            } else if (xDiff > 0) {
                if (passableGrid[getRightX()][getYPos()] == true) {
                    moveRight();
                }
            }
        } else if (Math.abs(xDiff) > Math.abs(yDiff)) {
            if (yDiff < 0) {
                if (passableGrid[getXPos()][getUpY()] == true) {
                    moveUp();
                }
            } else if (yDiff > 0) {
                if (passableGrid[getXPos()][getDownY()] == true) {
                    moveDown();
                }
            }
        } else {
            if (xDiff < 0) {
                if (passableGrid[getLeftX()][getYPos()] == true) {
                    moveLeft();
                }
            } else if (xDiff > 0) {
                if (passableGrid[getRightX()][getYPos()] == true) {
                    moveRight();
                }
            }
        }
    }

    /**
     *
     * @param playerX
     */
    public void compareX(int playerX) {
        xDiff =  playerX - getXPos();
    }

    /**
     *
     * @param playerY
     */
    public void compareY(int playerY) {
        yDiff = playerY - getYPos();
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
        final int anims = 5;
        switch (animationTick % anims) {
            case 0:
                gc.drawImage(getImage(DUMB_0_IMG), x, y);
                break;
            case 1:
                gc.drawImage(getImage(DUMB_1_IMG), x, y);
                break;
            case 2:
                gc.drawImage(getImage(DUMB_2_IMG), x, y);
                break;
            case 3:
                gc.drawImage(getImage(DUMB_3_IMG), x, y);
                break;
            case 4:
                gc.drawImage(getImage(DUMB_4_IMG), x, y);
                break;
        }
        gc.restore();
    }
}