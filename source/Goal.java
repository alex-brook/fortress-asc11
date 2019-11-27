import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import java.util.Map;

class Goal extends Tile {
    private static final String GOAL_IMG = "stair_nextlevel.png";

    Goal(final char mapChar, final Map<String, Image> img) {
        super(mapChar, img);
    }

    public void draw(final GraphicsContext gc, final double x, final double y,
                     final int animationTick) {
        gc.drawImage(getImage(GOAL_IMG), x, y);
    }

    @Override
    public void playerContact(final Player p) {
        p.win();
    }
}