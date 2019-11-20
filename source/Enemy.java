public abstract class Enemy {
    private char mapChar;
    private int xPos;
    private int yPos;

    public Enemy(final int x, final int y, final char mapc) {
        xPos = x;
        yPos = y;
        mapChar = mapc;
    }

    public final int getXPos() {
        return xPos;
    }

    public final int getYPos() {
        return yPos;
    }

    public final char getMapChar() {
        return mapChar;
    }

    public String getAdditionalInfo() {
        return null;
    }

    @Override
    public final String toString() {
        return String.valueOf(getMapChar());
    }
}