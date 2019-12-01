import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Contains the behaviors of the player character and its interactions
 *
 * @author Alex
 */

class Player {
    public static final char PLAYER = 'P';

    public static final String PLAYER_0_IMG = "knight_idle_anim_f0.png";
    public static final String PLAYER_1_IMG = "knight_idle_anim_f1.png";
    public static final String PLAYER_2_IMG = "knight_idle_anim_f2.png";
    public static final String PLAYER_3_IMG = "knight_idle_anim_f3.png";
    public static final String PLAYER_4_IMG = "knight_idle_anim_f4.png";
    public static final String PLAYER_5_IMG = "knight_idle_anim_f5.png";


    private Map<String, Image> img;

    private int xPos;
    private int yPos;
    private boolean lookingRight;
    private Direction direction;

    private List<Item> inventory;
    private int tokenCount;

    private boolean won;
    private boolean dead;

    Player(final int x, final int y, final String[] addInfo,
           final Map<String, Image> image) {
        this(x, y, Integer.parseInt(addInfo[0]), inventoryFromInfo(addInfo), image);
    }

    private Player(final int x, final int y, final int tokens, final Item[] inv,
                   final Map<String, Image> image) {
        lookingRight = true;
        direction = Direction.RIGHT;
        xPos = x;
        yPos = y;
        tokenCount = tokens;
        inventory = new LinkedList<>(Arrays.asList(inv));
        img = image;
    }

    /**
     *
     * @param info
     * @return
     */
    private static Item[] inventoryFromInfo(final String[] info) {
        Item[] inv = new Item[info.length - 1];
        for (int i = 0; i < inv.length; i++) {
            inv[i] = Item.getItem(info[i + 1].charAt(0));
        }
        return inv;
    }

    /**
     *
     * @param item
     */
    public void giveItem(final Item item) {
        inventory.add(item);
    }

    /**
     *
     * @param item
     */
    public void takeItem(final Item item) {
        inventory.remove(item);
    }

    /**
     *
     * @param item
     * @return
     */
    public boolean hasItem(final Item item) {
        return inventory.contains(item);
    }

    /**
     *
     */
    public void giveToken() {
        tokenCount++;
    }

    /**
     *
     * @param count
     */
    public void takeTokens(final int count) {
        tokenCount -= count;
    }

    /**
     *
     * @return
     */
    public int getTokenCount() {
        return tokenCount;
    }

    /**
     *
     * @return
     */
    public Direction getDirection() {
        return direction;
    }

    /**
     *
     * @param newX
     * @param newY
     */
    public void teleportTo(final int newX, final int newY) {
        xPos = newX;
        yPos = newY;
    }

    /**
     *
     */
    public void moveLeft() {
        xPos -= 1;
        direction = Direction.LEFT;
        lookingRight = false;
    }

    /**
     *
     */
    public void moveRight() {
        xPos += 1;
        direction = Direction.RIGHT;
        lookingRight = true;
    }

    /**
     *
     * @return
     */
    public int getXPos() {
        return xPos;
    }

    /**
     *
     */
    public void moveUp() {
        yPos -= 1;
        direction = Direction.UP;
    }

    /**
     *
     */
    public void moveDown() {
        yPos += 1;
        direction = Direction.DOWN;
    }

    /**
     *
     * @return
     */
    public int getYPos() {
        return yPos;
    }

    /**
     *
     * @return
     */
    public boolean isDead() {
        return dead;
    }

    /**
     *
     */
    public void kill() {
        dead = true;
    }

    /**
     *
     * @return
     */
    public boolean hasWon() {
        return won;
    }

    /**
     *
     */
    public void win() {
        won = true;
    }

    /**
     *
     * @return
     */
    public final char getMapChar() {
        return PLAYER;
    }

    /**
     *
     * @return
     */
    public final String getAdditionalInfo() {
        StringBuilder sb = new StringBuilder();
        sb.append(tokenCount);
        for (Item i : inventory) {
            sb.append(GameState.INFO_DELIMITER);
            sb.append(i.getMapChar());
        }
        return sb.toString();
    }

    /**
     *
     * @param gc
     * @param x
     * @param y
     */
    public double drawInventory(final GraphicsContext gc, final double x,
                              final double y) {
        final double leanDegrees = 90;
        final double rows = 3;
        final double arcWidth = 20;
        final double arcHeight = 10;
        final double offset = GameState.TILE_RES;
        final double cointsHeightOffset = 1.7;
        final double textOffset = 0.6;
        gc.save();
        gc.setFill(Color.DARKSLATEGREY);
        gc.fillRoundRect(x, y, Math.max(inventory.size(), 2) * GameState.TILE_RES,
                rows * GameState.TILE_RES, arcWidth, arcHeight);
        gc.setFill(Color.WHITE);
        String playerText = isDead() ? "x 0" : "x 1";
        gc.fillText(playerText, x + GameState.TILE_RES,
                y + (GameState.TILE_RES * textOffset));
        if (isDead()) {
            gc.translate(x + GameState.TILE_RES, y);
            gc.rotate(leanDegrees);
            gc.translate(-x, -y);
        }
        gc.drawImage(img.get(PLAYER_0_IMG), x,  y);
        gc.restore();
        gc.save();

        gc.setFill(Color.GOLD);
        gc.drawImage(img.get(Item.TOKEN.getImageName()), x,  y + offset);
        gc.fillText(String.format("x %d", tokenCount), x + offset,
                y + (offset * cointsHeightOffset));
        int i;
        for (i = 0; i < inventory.size(); i++) {
            gc.drawImage(img.get(inventory.get(i).getImageName()),
                    x + (i * offset),
                    y + (2 * offset));
        }
        gc.restore();
        return x + (i * offset);
    }

    /**
     *
     * @param gc
     * @param x
     * @param y
     * @param animationTick
     */
    public void draw(final GraphicsContext gc, final double x, final double y,
                     final int animationTick) {
        if (isDead()) {
            return;
        }

        final int playerAnims = 6;
        final int leanDegrees = 10;
        Image image;

        gc.save();

        if (!lookingRight) {
            gc.translate(x, y);
            gc.scale(-1, 1);
            gc.translate(-x - GameState.TILE_RES, -y);
        }

        if (direction == Direction.UP) {
            gc.translate(x, y);
            gc.rotate(-leanDegrees);
            gc.translate(-x, -y);
        } else if (direction == Direction.DOWN) {
            gc.translate(x, y);
            gc.rotate(leanDegrees);
            gc.translate(-x, -y);
        }

        switch (animationTick % playerAnims) {
            case 0:
                image = img.get(PLAYER_0_IMG);
                gc.drawImage(image, x, y);
                break;
            case 1:
                image = img.get(PLAYER_1_IMG);
                gc.drawImage(image, x, y);
                break;
            case 2:
                image = img.get(PLAYER_2_IMG);
                gc.drawImage(image, x, y);
                break;
            case 3:
                image = img.get(PLAYER_3_IMG);
                gc.drawImage(image, x, y);
                break;
            case 4:
                image = img.get(PLAYER_4_IMG);
                gc.drawImage(image, x, y);
                break;
            case 5:
                image = img.get(PLAYER_5_IMG);
                gc.drawImage(image, x, y);
                break;
            default:
                break;
        }
        gc.restore();
    }

    /**
     * 
     * @return
     */
    @Override
    public String toString() {
        return String.valueOf(getMapChar());
    }
}
