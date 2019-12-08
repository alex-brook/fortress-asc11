import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Contains the behaviors of the player character and its interactions
 * Javadoc comments added by Stephen
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

    private static final String FOOTSTEP_SOUND = "walk.wav";
    private static final String DEATH_SOUND = "death.wav";
    private static final String TOKEN_SOUND = "coin.wav";
    private static final String ITEM_SOUND = "metal-ringing.wav";


    private Map<String, Image> img;

    private int xPos;
    private int yPos;
    private boolean lookingRight;
    private Direction direction;

    private List<Item> inventory;
    private int tokenCount;

    private boolean won;
    private boolean dead;
    private String sound;

    Player(final int x, final int y, final String[] addInfo,
           final Map<String, Image> image) {
        this(x, y, Integer.parseInt(addInfo[0]), inventoryFromInfo(addInfo), image);
        sound = null;
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
     * Player's inventory from additional information ina  save file
     * @param info additional information
     * @return players inventory
     */
    private static Item[] inventoryFromInfo(final String[] info) {
        Item[] inv = new Item[info.length - 1];
        for (int i = 0; i < inv.length; i++) {
            inv[i] = Item.getItem(info[i + 1].charAt(0));
        }
        return inv;
    }

    /**
     * Gives an item to the player
     * @param item item to be given to the player
     */
    public void giveItem(final Item item) {
        inventory.add(item);
        sound = ITEM_SOUND;
    }

    /**
     * Tkaes the requested item form the player
     * @param item item to be removed from the player
     */
    public void takeItem(final Item item) {
        inventory.remove(item);
    }

    /**
     * Checks if the player has the item requested
     * @param item item to be checked for
     * @return if player has the item or not
     */
    public boolean hasItem(final Item item) {
        return inventory.contains(item);
    }

    /**
     * Increases the players number of tokens by one
     */
    public void giveToken() {
        tokenCount++;
        sound = TOKEN_SOUND;
    }

    /**
     * Removes a number of tokens from the player
     * @param count number of tokens to be removed
     */
    public void takeTokens(final int count) {
        tokenCount -= count;
    }

    /**
     * Getter for number of tokens player has
     * @return number of tokens
     */
    public int getTokenCount() {
        return tokenCount;
    }

    /**
     * Getter for the direction player is facing in
     * @return direction
     */
    public Direction getDirection() {
        return direction;
    }

    /**
     * Moves the player to new position
     * @param newX new x coordinate
     * @param newY new y coordinate
     */
    public void teleportTo(final int newX, final int newY) {
        xPos = newX;
        yPos = newY;
    }

    /**
     * Moves the instance of player to the left one space
     */
    public void moveLeft() {
        xPos -= 1;
        direction = Direction.LEFT;
        lookingRight = false;
        sound = FOOTSTEP_SOUND;
    }

    /**
     * Moves the instance of player to the right one space
     */
    public void moveRight() {
        xPos += 1;
        direction = Direction.RIGHT;
        lookingRight = true;
        sound = FOOTSTEP_SOUND;
    }

    /**
     * Getter for player's x coordinate
     * @return x coordinate
     */
    public int getXPos() {
        return xPos;
    }

    /**
     * Moves the instance of player up one space
     */
    public void moveUp() {
        yPos -= 1;
        direction = Direction.UP;
        sound = FOOTSTEP_SOUND;
    }

    /**
     * Moves the instance of player down one space
     */
    public void moveDown() {
        yPos += 1;
        direction = Direction.DOWN;
        sound = FOOTSTEP_SOUND;
    }

    /**
     * Getter for players y coordinate
     * @return y coordinate
     */
    public int getYPos() {
        return yPos;
    }

    /**
     * Returns if the player is dead or not
     * @return dead boolean
     */
    public boolean isDead() {
        return dead;
    }

    /**
     * Kills the player
     */
    public void kill() {
        dead = true;
        sound = DEATH_SOUND;
    }

    /**
     * Returns if the player has won or not
     * @return won boolean
     */
    public boolean hasWon() {
        return won;
    }

    /**
     * Sets won to true
     */
    public void win() {
        won = true;
    }

    /**
     *
     * @return name of sound
     */
    public String consumeSound() {
        String snd = sound;
        sound = null;
        return snd;
    }

    /**
     * Getter for player's character in a save file
     * @return player
     */
    public final char getMapChar() {
        return PLAYER;
    }

    /**
     * Getter for additional information for an instance of player from
     * save file
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
     * Draws the graphics for the player's inventory one screen
     * @param gc drawable feature of canvas
     * @param x x coordinate
     * @param y y coordinate
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
     * Draws the graphics for an instance of player
     * @param gc drawable feature of canvas
     * @param x x coordinate
     * @param y y coordinate
     * @param animationTick runtime of animation
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
     * Generic toString override
     * @return correct output
     */
    @Override
    public String toString() {
        return String.valueOf(getMapChar());
    }
}
