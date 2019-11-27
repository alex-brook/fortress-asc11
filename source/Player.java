import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

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
        xPos = x;
        yPos = y;
        tokenCount = tokens;
        inventory = new LinkedList<>(Arrays.asList(inv));
        img = image;
    }

    private static Item[] inventoryFromInfo(final String[] info) {
        Item[] inv = new Item[info.length - 1];
        for (int i = 0; i < inv.length; i++) {
            inv[i] = Item.getItem(info[i + 1].charAt(0));
        }
        return inv;
    }

    public void giveItem(final Item item) {
        inventory.add(item);
    }

    public void takeItem(final Item item) {
        inventory.remove(item);
    }

    public boolean hasItem(final Item item) {
        return inventory.contains(item);
    }

    public void giveToken() {
        tokenCount++;
    }

    public void takeTokens(final int count) {
        tokenCount -= count;
    }

    public int getTokenCount() {
        return tokenCount;
    }

    public void teleportTo(final int newX, final int newY) {
        xPos = newX;
        yPos = newY;
    }

    public void moveLeft() {
        xPos -= 1;
    }

    public void moveRight() {
        xPos += 1;
    }

    public int getXPos() {
        return xPos;
    }

    public void moveUp() {
        yPos -= 1;
    }

    public void moveDown() {
        yPos += 1;
    }

    public int getYPos() {
        return yPos;
    }

    public boolean isDead() {
        return dead;
    }

    public void kill() {
        dead = true;
    }

    public boolean hasWon() {
        return won;
    }

    public void win() {
        won = true;
    }

    public final char getMapChar() {
        return PLAYER;
    }

    public final String getAdditionalInfo() {
        StringBuilder sb = new StringBuilder();
        sb.append(tokenCount);
        for (Item i : inventory) {
            sb.append(GameState.INFO_DELIMITER);
            sb.append(i.getMapChar());
        }
        return sb.toString();
    }

    public void drawInventory(final GraphicsContext gc, final double x,
                              final double y) {

    }

    public void draw(final GraphicsContext gc, final double x, final double y,
                     final int animationTick) {
        final int playerAnims = 6;

        switch (animationTick % playerAnims) {
            case 0:
                gc.drawImage(img.get(PLAYER_0_IMG), x, y);
                break;
            case 1:
                gc.drawImage(img.get(PLAYER_1_IMG), x, y);
                break;
            case 2:
                gc.drawImage(img.get(PLAYER_2_IMG), x, y);
                break;
            case 3:
                gc.drawImage(img.get(PLAYER_3_IMG), x, y);
                break;
            case 4:
                gc.drawImage(img.get(PLAYER_4_IMG), x, y);
                break;
            case 5:
                gc.drawImage(img.get(PLAYER_5_IMG), x, y);
                break;
            default:
                gc.drawImage(img.get(PLAYER_0_IMG), x, y);
        }
    }

    @Override
    public String toString() {
        return String.valueOf(getMapChar());
    }
}
