import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.awt.*;
import java.util.Map;

/**
 * Type of enemy that moves by following alongside wall tiles.
 *
 * @author Irfaan
 * @author Stephen
 */
class WallFollowEnemy extends Enemy {
    private static final String WALL_FOLLOW_0_IMG = "slime_idle_anim_f0.png";
    private static final String WALL_FOLLOW_1_IMG = "slime_idle_anim_f1.png";
    private static final String WALL_FOLLOW_2_IMG = "slime_idle_anim_f2.png";
    private static final String WALL_FOLLOW_3_IMG = "slime_idle_anim_f3.png";
    private static final String WALL_FOLLOW_4_IMG = "slime_idle_anim_f4.png";
    private static final String WALL_FOLLOW_5_IMG = "slime_idle_anim_f5.png";

    WallFollowEnemy(final int x, final int y, final char mapChar,
                    final Map<String, Image> img, final Direction dir) {
        super(x, y, mapChar, img);
        direction = dir;
    }

     /**
      * Moves the enemy.
      * @param passable grid that shows tiles passable by enemy
      * @param playerX player's x coordinate
      * @param playerY player's y coordinate
      */
    @Override
    public void move(final boolean[][] passable, final int playerX,
                     final int playerY) {
        //System.out.println(String.format("My direction is %s.",direction));
        Point current = new Point(getXPos(), getYPos());
        Point cellOnMyLeft = pointFromDirection(current, direction.turnleft());
        Point cellOnMyRight = pointFromDirection(current,
                direction.turnright());
        Point cellForwards = pointFromDirection(current, direction);
        Point cellBackLeft = pointFromDirection(cellOnMyLeft,
                direction.turnleft().turnleft());

        boolean leftPassable = passable[cellOnMyLeft.x][cellOnMyLeft.y];
        boolean forwardsPassable = passable[cellForwards.x][cellForwards.y];
        boolean rightPassable = passable[cellOnMyRight.x][cellForwards.y];
        // 'r' corner
        boolean inRightCorner = !passable[cellOnMyLeft.x][cellOnMyLeft.y]
                && !passable[cellForwards.x][cellForwards.y]
                && passable[cellOnMyRight.x][cellOnMyRight.y];
        boolean inUCorner = !passable[cellOnMyLeft.x][cellOnMyLeft.y]
                && !passable[cellForwards.x][cellForwards.y]
                && !passable[cellOnMyRight.x][cellOnMyRight.y];
        boolean inStraight = !passable[cellOnMyLeft.x][cellOnMyLeft.y]
                && passable[cellForwards.x][cellForwards.y];
        boolean inLeftCorner = passable[cellOnMyLeft.x][cellOnMyLeft.y]
                && !passable[cellBackLeft.x][cellBackLeft.y];

        if (inRightCorner) {
            direction = direction.turnright();
        } else if (inUCorner) {
            direction = direction.opposite();
        } else if (inLeftCorner) {
            direction = direction.turnleft();
        }
        moveInCurrentDirection();
    }

     /**
      *
      * @return
      */
     @Override
     public String getAdditionalInfo() {
         return String.format("%s", direction);
     }

     /**
      * Draws the graphics for a wall follow enemy in the scene.
      * @param gc drawable feature of canvas
      * @param x x coordinate
      * @param y y coordinate
      * @param animationTick runtime of animation
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
            default:
                break;
        }
        gc.restore();
    }
}
