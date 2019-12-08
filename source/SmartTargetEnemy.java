import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * Type of enemy that follows the player taking an optimised route.
 *
 * @author Irfaan
 * @author Stephen
 */
class SmartTargetEnemy extends Enemy {
    private static final String SMART_0_IMG = "goblin_idle_anim_f0.png";
    private static final String SMART_1_IMG = "goblin_idle_anim_f1.png";
    private static final String SMART_2_IMG = "goblin_idle_anim_f2.png";
    private static final String SMART_3_IMG = "goblin_idle_anim_f3.png";
    private static final String SMART_4_IMG = "goblin_idle_anim_f4.png";

    private Random rand;

    SmartTargetEnemy(final int x, final int y, final char mapChar,
                     final Map<String, Image> img) {
        super(x, y, mapChar, img);
        rand = new Random();
    }

    /**
     * Searches for best path to player.
     * @param passable grid of passable tiles
     * @param goal player
     * @return move enemy will make
     */
    private Map<Point, Point> BFS(final boolean[][] passable,
                                  final Point goal) {
        Queue<Point> considering = new LinkedList<>();
        HashSet<Point> discovered = new HashSet<>();
        Map<Point, Point> parent = new HashMap<>();
        Point start = new Point(getXPos(), getYPos());
        discovered.add(start);
        considering.add(start);
        while (considering.size() > 0) {
            Point current = considering.remove();
            if (current.equals(goal)) {
                return parent;
            }
            Point[] neighbours = {new Point(current.x, current.y - 1),
                                  new Point(current.x, current.y + 1),
                                  new Point(current.x - 1, current.y),
                                  new Point(current.x + 1, current.y)};
            for (Point p : neighbours) {
                boolean xInBounds
                        = p.x >= 0 && p.x < passable.length;
                boolean yInBounds
                        = p.y >= 0 && p.y < passable[0].length;
                if (xInBounds && yInBounds && passable[p.x][p.y]
                        && !discovered.contains(p)) {
                    discovered.add(p);
                    parent.put(p, current);
                    considering.add(p);
                }
            }
        }
        return null;
    }

    /**
     * Moves the enemy.
     * @param passable grid of passable tiles
     * @param playerX player's x coordinate
     * @param playerY player's y coordinate
     */
    @Override
    public void move(final boolean[][] passable, final int playerX,
                     final int playerY) {
        Point goal = new Point(playerX, playerY);
        Point current = new Point(getXPos(), getYPos());
        Map<Point, Point> parent = BFS(passable, goal);

        if (current.equals(goal)) {
		    // the player walked into us
            return;
        }

        if (parent == null) {
            // pick random valid direction
            moveInRandomValidDirection(passable);
            return;
        }

        while (!parent.get(goal).equals(current)) {
            goal = parent.get(goal);
        }

        if (pointFromDirection(Direction.UP).equals(goal)) {
            moveUp();
        } else if (pointFromDirection(Direction.DOWN).equals(goal)) {
            moveDown();
        } else if (pointFromDirection(Direction.LEFT).equals(goal)) {
            moveLeft();
        } else if (pointFromDirection(Direction.RIGHT).equals(goal)) {
            moveRight();
        }
    }

    /**
     * Moves enemy randomly if player isn't reachable.
     * @param passable grid of passable tiles
     */
    private void moveInRandomValidDirection(final boolean[][] passable) {
        List<Direction> valid = new LinkedList<>();
        for (Direction d : Direction.values()) {
            Point p = pointFromDirection(d);
            if (p.x >= 0 && p.x < passable.length
                    && p.y >= 0 && p.y < passable[0].length
                    && passable[p.x][p.y]) {
                valid.add(d);
            }
        }
        if (valid.size() == 0) {
            // nowhere to go
            return;
        }
        int randomIndex = rand.nextInt(valid.size());
        switch (valid.get(randomIndex)) {
            case UP:
                moveUp();
                break;
            case DOWN:
                moveDown();
                break;
            case LEFT:
                moveLeft();
                break;
            case RIGHT:
                moveRight();
                break;
            default:
        }
    }

    /**
     * Draws the graphics for a smart target enemy in the scene.
     * @param gc drawable feature of canvas
     * @param x x coordinate
     * @param y y coordinate
     * @param animationTick runtime of animation
     */
    @Override
    public void draw(final GraphicsContext gc, final double x, final double y,
                    final int animationTick) {
        super.draw(gc, x, y, animationTick);
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
            default:
                break;
        }
        gc.restore();
    }
}
