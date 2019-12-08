import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.Map;

/**
 * An enemy type which follows the player taking an unoptimised route
 * Javadoc comments added by Stephen
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
     * Moves the enemy in the direction of the player
     * @param passableGrid collection of booleans which show if a tile is passable by an enemy or not
     * @param playerX player's x coordinates
     * @param playerY player's y coordinates
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
     * Difference in player's x coordinate and the instance of enemies x coordinate
     * @param playerX player's x coordinate
     */
    public void compareX(int playerX) {
        xDiff =  playerX - getXPos();
    }

    /**
     * Difference in player's y coordinate and the instance of enemies y coordinate
     * @param playerY player's y coordinate
     */
    public void compareY(int playerY) {
        yDiff = playerY - getYPos();
    }

    /**
     * Draws the graphic for an instance of this enemy type
     * @param gc drawable feature of canvas
     * @param x x coordinate of tile
     * @param y y coordinate of tile
     * @param animationTick runtime of animation
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