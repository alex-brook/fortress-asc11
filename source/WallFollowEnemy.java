import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import java.util.Map;

 /**
 * Type of enemy that moves by following alongside wall tiles
 *
 * @author Irfaan
 */

class WallFollowEnemy extends Enemy {
    private static String WALL_FOLLOW_0_IMG = "slime_idle_anim_f0.png";
    private static String WALL_FOLLOW_1_IMG = "slime_idle_anim_f1.png";
    private static String WALL_FOLLOW_2_IMG = "slime_idle_anim_f2.png";
    private static String WALL_FOLLOW_3_IMG = "slime_idle_anim_f3.png";
    private static String WALL_FOLLOW_4_IMG = "slime_idle_anim_f4.png";
    private static String WALL_FOLLOW_5_IMG = "slime_idle_anim_f5.png";
    
    private String direction;
    private String wallTracker;

    WallFollowEnemy(final int x, final int y, final char mapChar,
                    final Map<String, Image> img) {
        super(x, y, mapChar, img);
    }

    @Override
	public void getMove(boolean[][] passableGrid, int playerX, int playerY) {
	    	switch (direction) {
    			case "up":
    				switch (wallTracker) {
    					case "left":
    						if (passableGrid[getXPos()][getUpY()] == true) {
    							moveUp();
    						} else if (passableGrid[getLeftX()][getYPos()] == true){
    							moveLeft();
    							direction = "left";
    							wallTracker = "down";
    						} else {
    							moveDown();
    							direction = "down";
    						}
    					case "right":
    						if (passableGrid[getXPos()][getUpY()] == true) {
    							moveUp();
    						} else if (passableGrid[getRightX()][getYPos()] == true){
    							moveRight();
    							direction = "right";
    							wallTracker = "down";
    						} else {
    							moveDown();
    							direction = "down";
    						}		
    				}
    			case "down":
    				switch (wallTracker) {
    					case "left":
    						if (passableGrid[getXPos()][getDownY()] == true) {
    							moveDown();
    						} else if (passableGrid[getLeftX()][getYPos()] == true){
    							moveLeft();
    							direction = "left";
    							wallTracker = "up";
    						} else {
    							moveUp();
    							direction = "up";
    						}
    					case "right":
    						if (passableGrid[getXPos()][getDownY()] == true) {
    							moveDown();
    						} else if (passableGrid[getRightX()][getYPos()] == true){
    							moveRight();
    							direction = "right";
    							wallTracker = "up";
    						} else {
    							moveUp();
    							direction = "up";
    						}
    				}
    			case "left":
    				switch (wallTracker) {
    					case "up":
    						if (passableGrid[getLeftX()][getYPos()] == true) {
    							moveLeft();
    						} else if (passableGrid[getXPos()][getUpY()] == true){
    							moveUp();
    							direction = "up";
    							wallTracker = "right";
    						} else {
    							moveRight();
    							direction = "right";
    						}
    					case "down":
    						if (passableGrid[getLeftX()][getYPos()] == true) {
    							moveLeft();
    						} else if (passableGrid[getXPos()][getDownY()] == true){
    							moveDown();
    							direction = "down";
    							wallTracker = "right";
    						} else {
    							moveRight();
    							direction = "right";
    						}
    				}
    			case "right":
    				switch (wallTracker) {
    					case "up":
    						if (passableGrid[getRightX()][getYPos()] == true) {
    							moveRight();
    						} else if (passableGrid[getXPos()][getUpY()] == true){
    							moveUp();
    							direction = "up";
    							wallTracker = "left";
    						} else {
    							moveLeft();
    							direction = "left";
    						}
    					case "down":
    						if (passableGrid[getLeftX()][getYPos()] == true) {
    							moveRight();
    						} else if (passableGrid[getXPos()][getDownY()] == true){
    							moveDown();
    							direction = "down";
    							wallTracker = "left";
    						} else {
    							moveLeft();
    							direction = "left";
    						}
    				}
		}
	}
    
    @Override
    public void draw(final GraphicsContext gc, final double x, final double y,
                     final int animationTick) {
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
        }
    }
}