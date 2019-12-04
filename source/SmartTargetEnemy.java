import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

/**
 * Type of enemy that follows the player taking an optimised route
 *
 * @author Irfaan
 */
class SmartTargetEnemy extends Enemy {
    private static final String SMART_0_IMG = "red_goblin_idle_anim_f0.png";
    private static final String SMART_1_IMG = "red_goblin_idle_anim_f1.png";
    private static final String SMART_2_IMG = "red_goblin_idle_anim_f2.png";
    private static final String SMART_3_IMG = "red_goblin_idle_anim_f3.png";
    private static final String SMART_4_IMG = "red_goblin_idle_anim_f4.png";

    private List<Node> openNodes = new ArrayList<Node>();
    private List<Node> closedNodes = new ArrayList<Node>();
    private Node current = new Node(getXPos(), getYPos(), 0, 0, 0, null);
    private Node previousCycle = new Node(0, 0, 0, 0, 0, null);

	Comparator<Node> byTotalDistance = Comparator.comparing(Node::getTotalDistance);
    
    SmartTargetEnemy(final int x, final int y, final char mapChar,
                     final Map<String, Image> img) {
        super(x, y, mapChar, img);
    }

    /**
     *
     * @param passableGrid
     * @param playerX
     * @param playerY
     */
    @Override
    public void move(boolean[][] passableGrid, int playerX, int playerY) {
    	openNodes.clear();
    	closedNodes.clear();
    	current.setXCoord(getXPos());
    	current.setYCoord(getYPos());
    	current.setHeuristic(getXPos(), getYPos(), playerX, playerY);
    	current.setDistanceFromStart(getXPos(), getYPos(), current.getXCoord(), current.getYCoord());
    	current.setTotalDistance();
    	closedNodes.add(current);
    	createNeighbours(current, passableGrid, playerX, playerY);
    	
    	while (current.getXCoord() != playerX || current.getYCoord() != playerY) {
    		if (openNodes.isEmpty()) {
    			break;
    		}
    		current = openNodes.get(0);
    		openNodes.remove(0);
    		closedNodes.add(current);
    		createNeighbours(current, passableGrid, playerX, playerY);
    	}
    	
    	move2();
    	previousCycle = closedNodes.get(0);
    }
    
    private void move2() {
    	//This part might be necessary for certain positions of walls not entirely sure.
    	if (closedNodes.get(1).getXCoord() == previousCycle.getXCoord()
    			&& closedNodes.get(1).getYCoord() == previousCycle.getYCoord()) {
    		if (getXPos() != closedNodes.get(2).getXCoord()) {
            	if (getXPos() < closedNodes.get(2).getXCoord()) {
            		moveRight();
            	} else {
            		moveLeft();
            	}
            }else if (getYPos() != closedNodes.get(2).getYCoord()) {
            	if (getYPos() < closedNodes.get(2).getYCoord()) {
            		moveDown();
            	} else {
            		moveUp();
            	}
            }
    	} else { 
    		if (getXPos() != closedNodes.get(1).getXCoord()) {
            	if (getXPos() < closedNodes.get(1).getXCoord()) {
            		moveRight();
            	} else {
            		moveLeft();
            	}
            }else if (getYPos() != closedNodes.get(1).getYCoord()) {
            	if (getYPos() < closedNodes.get(1).getYCoord()) {
            		moveDown();
            	} else {
            		moveUp();
            	}
            }
    	}
    }
    
    private void createNeighbours(Node n, boolean[][] passableGrid, int playerX, int playerY) {
    	//Node above current
		if (passableGrid[current.getXCoord()][current.getUpY()] == true) {
			Node neighbourUp = new Node(current.getXCoord(),current.getUpY(), 0, 0, 0, current);
			neighbourUp.setHeuristic(neighbourUp.getXCoord(), neighbourUp.getYCoord(), playerX, playerY);
			neighbourUp.setDistanceFromStart(getXPos(), getYPos(), 
					neighbourUp.getXCoord(), neighbourUp.getYCoord());
			neighbourUp.setTotalDistance();
			if (!findNode(openNodes, neighbourUp) && !findNode(closedNodes, neighbourUp)) {
				openNodes.add(neighbourUp);
			}
		}
		//Node below current
		if (passableGrid[current.getXCoord()][current.getDownY()] == true) {
			Node neighbourDown = new Node(current.getXCoord(),current.getDownY(), 0, 0, 0, current);
			neighbourDown.setHeuristic(neighbourDown.getXCoord(), neighbourDown.getYCoord(), playerX, playerY);
			neighbourDown.setDistanceFromStart(getXPos(), getYPos(), 
					neighbourDown.getXCoord(), neighbourDown.getYCoord());
			neighbourDown.setTotalDistance();
			if (!findNode(openNodes, neighbourDown) && !findNode(closedNodes, neighbourDown)) {
				openNodes.add(neighbourDown);
			}
		}
		//Node to the right of current
		if (passableGrid[current.getRightX()][current.getYCoord()] == true) {
			Node neighbourRight = new Node(current.getRightX(),current.getYCoord(), 0, 0, 0, current);
			neighbourRight.setHeuristic(neighbourRight.getXCoord(), neighbourRight.getYCoord(), playerX, playerY);
			neighbourRight.setDistanceFromStart(getXPos(), getYPos(), 
					neighbourRight.getXCoord(), neighbourRight.getYCoord());
			neighbourRight.setTotalDistance();
			if (!findNode(openNodes, neighbourRight) && !findNode(closedNodes, neighbourRight)) {
				openNodes.add(neighbourRight);
			}
		}
		//Node to the left of current
		if (passableGrid[current.getLeftX()][current.getYCoord()] == true) {
			Node neighbourLeft = new Node(current.getLeftX(),current.getYCoord(), 0, 0, 0, current);
			neighbourLeft.setHeuristic(neighbourLeft.getXCoord(), neighbourLeft.getYCoord(), playerX, playerY);
			neighbourLeft.setDistanceFromStart(getXPos(), getYPos(), 
					neighbourLeft.getXCoord(), neighbourLeft.getYCoord());
			neighbourLeft.setTotalDistance();
			if (!findNode(openNodes, neighbourLeft) && !findNode(closedNodes, neighbourLeft)) {
				openNodes.add(neighbourLeft);
			}
		}
		Collections.sort(openNodes, byTotalDistance);
	}

	private boolean findNode(List<Node> list, Node a) {
		return list.stream().anyMatch(b -> (b.getXCoord() == a.getXCoord()
				&& b.getYCoord() == a.getYCoord()));
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
        }
        gc.restore();
    }
}