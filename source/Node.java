
public class Node {

	private int xCoord;
	private int yCoord;
	private int heuristic;
	private int distanceFromStart;
	private int totalDistance;
	private Node parent;
	
	public Node(int x, int y, int h, int g, int f, Node n) {
		setXCoord(x);
		setYCoord(y);
		heuristic = h;
		distanceFromStart = g;
		totalDistance = f;
		parent = n;
	}
	
	public int getXCoord() {
		return xCoord;
	}
	
	public void setXCoord(int xCoord) {
		this.xCoord = xCoord;
	}
	
	public int getYCoord() {
		return yCoord;
	}
	
	public void setYCoord(int yCoord) {
		this.yCoord = yCoord;
	}
	
	public int getDownY() {
		return yCoord + 1;
	}
	
	public int getRightX() {
		return xCoord + 1;
	}
	
	public int getUpY() {
		return yCoord - 1;
	}
	
	public int getLeftX() {
		return xCoord - 1;
	}

	public int getHeuristic() {
		return heuristic;
	}

	public void setHeuristic(int x, int y, int targetX, int targetY) {
		this.heuristic = Math.abs(Math.abs(x - targetX) + Math.abs(y - targetY));
	}

	public int getDistanceFromStart() {
		return distanceFromStart;
	}

	public void setDistanceFromStart(int x, int y, int startX, int startY) {
		this.distanceFromStart = Math.abs(Math.abs(x - startX) + Math.abs(y - startY));
	}

	public int getTotalDistance() {
		return totalDistance;
	}

	public void setTotalDistance() {
		this.totalDistance = heuristic + distanceFromStart;
	}

	public Node getParent() {
		return parent;
	}

	public void setParent(Node parent) {
		this.parent = parent;
	}
	
}
