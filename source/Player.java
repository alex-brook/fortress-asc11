class Player {
    public static final char PLAYER = 'P';
    private int xPos;
    private int yPos;

    Player(final int x, final int y) {
        xPos = x;
        yPos = y;
    }

    public final int getXPos() {
        return xPos;
    }

    public final int getYPos() {
        return yPos;
    }

    public final char getMapChar() {
        return PLAYER;
    }
    public final String[] getAdditionalInfo() {
        return null;
    }
    @Override
    public String toString() {
        return String.valueOf(getMapChar());
    }
}
