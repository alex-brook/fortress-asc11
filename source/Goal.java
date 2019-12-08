import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import java.util.Map;

/**
 * Tile that ends the level when the player comes into contact with it
 * Javadoc comments added by Stephen
 *
 * @author Alex
 */

class Goal extends Tile {
    private static final String GOAL_IMG = "stair_nextlevel.png";

    Goal(final char mapChar, final Map<String, Image> img) {
        super(mapChar, img);
    }

    /**
     * Draws the goal tile
     * @param gc drawable feature of canvas
     * @param x x coordinate of tile
     * @param y y coordinate of tile
     * @param animationTick runtime of animation
     */
    public void draw(final GraphicsContext gc, final double x, final double y,
                     final int animationTick) {
        gc.drawImage(getImage(GOAL_IMG), x, y);
    }

    /**
     * Make the player win when the walk on an instance of goal
     * @param p instance of player
     */
    @Override
    public void playerContact(final Player p) {
        p.win();
    }
}