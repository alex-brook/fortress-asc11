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

    private String direction;

    StraightLineEnemy(final int x, final int y, final char mapChar,
                      final Map<String, Image> img, final String dir) {
        super(x, y, mapChar, img);
        direction = dir;
    }

    @Override
	public void getMove(boolean[][] passableGrid, int playerX, int playerY) {
		switch (direction) {
			case "up":
				if (passableGrid[getXPos()][getUpY()] == false) {
					direction = "down";
					moveDown();
				} else if (passableGrid[getXPos()][getUpY()] == true) {
					moveUp();
				}
				break;
			case "down":
				if (passableGrid[getXPos()][getDownY()] == false) {
					direction = "up";
					moveUp();
				} else if (passableGrid[getXPos()][getDownY()] == true) {
					moveDown();
				}
				break;
			case "left":
				if (passableGrid[getLeftX()][getYPos()] == false) {
					direction = "right";
					moveRight();
				} else if (passableGrid[getLeftX()][getYPos()] == true) {
					moveLeft();
				}
				break;
			case "right":
				if (passableGrid[getRightX()][getYPos()] == false) {
					direction = "left";
					moveLeft();
				} else if (passableGrid[getRightX()][getYPos()] == true) {
					moveRight();
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