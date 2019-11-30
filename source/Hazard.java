import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.Map;

/**
 * Tile that if the player comes into contact with loses them the game, unless
 * they have the required safe item
 *
 * @author Alex
 */
class Hazard extends Tile {
    private static final String LAVA_0_IMG = "l0.png";
    private static final String LAVA_1_IMG = "l1.png";
    private static final String LAVA_2_IMG = "l2.png";
    private static final String LAVA_3_IMG = "l3.png";
    private static final String LAVA_4_IMG = "l4.png";

    private static final String WATER_0_IMG = "water-0.png";
    private static final String WATER_1_IMG = "water-1.png";
    private static final String WATER_2_IMG = "water-2.png";
    private static final String WATER_3_IMG = "water-3.png";
    private static final String WATER_4_IMG = "water-4.png";
    private static final String WATER_5_IMG = "water-5.png";
    private static final String WATER_6_IMG = "water-6.png";
    private static final String WATER_7_IMG = "water-7.png";
    private static final String WATER_8_IMG = "water-8.png";
    private static final String WATER_9_IMG = "water-9.png";
    private static final String WATER_10_IMG = "water-10.png";
    private static final String WATER_11_IMG = "water-11.png";
    private static final String WATER_12_IMG = "water-12.png";
    private static final String WATER_13_IMG = "water-13.png";
    private static final String WATER_14_IMG = "water-14.png";
    private static final String WATER_15_IMG = "water-15.png";



    private Item requiredItem;

    Hazard(final char mapChar, final Map<String, Image> img, final Item item) {
        super(mapChar, img);
        requiredItem = item;
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
        final int lavaAnims = 5;
        final int waterAnims = 16;

        if (requiredItem == Item.FIRE_BOOTS) {
            switch (animationTick % lavaAnims) {
                case 0:
                    gc.drawImage(getImage(LAVA_0_IMG), x, y);
                    break;
                case 1:
                    gc.drawImage(getImage(LAVA_1_IMG), x, y);
                    break;
                case 2:
                    gc.drawImage(getImage(LAVA_2_IMG), x, y);
                    break;
                case 3:
                    gc.drawImage(getImage(LAVA_3_IMG), x, y);
                    break;
                case 4:
                    gc.drawImage(getImage(LAVA_4_IMG), x, y);
                    break;
                default:
                    break;
            }
        } else if (requiredItem == Item.FLIPPERS) {
            switch (animationTick % waterAnims) {
                case 0:
                    gc.drawImage(getImage(WATER_0_IMG), x, y);
                    break;
                case 1:
                    gc.drawImage(getImage(WATER_1_IMG), x, y);
                    break;
                case 2:
                    gc.drawImage(getImage(WATER_2_IMG), x, y);
                    break;
                case 3:
                    gc.drawImage(getImage(WATER_3_IMG), x, y);
                    break;
                case 4:
                    gc.drawImage(getImage(WATER_4_IMG), x, y);
                    break;
                case 5:
                    gc.drawImage(getImage(WATER_5_IMG), x, y);
                    break;
                case 6:
                    gc.drawImage(getImage(WATER_6_IMG), x, y);
                    break;
                case 7:
                    gc.drawImage(getImage(WATER_7_IMG), x, y);
                    break;
                case 8:
                    gc.drawImage(getImage(WATER_8_IMG), x, y);
                    break;
                case 9:
                    gc.drawImage(getImage(WATER_9_IMG), x, y);
                    break;
                case 10:
                    gc.drawImage(getImage(WATER_10_IMG), x, y);
                    break;
                case 11:
                    gc.drawImage(getImage(WATER_11_IMG), x, y);
                    break;
                case 12:
                    gc.drawImage(getImage(WATER_12_IMG), x, y);
                    break;
                case 13:
                    gc.drawImage(getImage(WATER_13_IMG), x, y);
                    break;
                case 14:
                    gc.drawImage(getImage(WATER_14_IMG), x, y);
                    break;
                case 15:
                    gc.drawImage(getImage(WATER_15_IMG), x, y);
                    break;
                default:
                    break;
            }
        }
    }

    /**
     *
     * @param p
     */
    @Override
    public void playerContact(final Player p) {
        if (!p.hasItem(requiredItem)) {
            p.kill();
        }
    }
}
