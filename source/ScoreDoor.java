class ScoreDoor extends Door {
    private int tokensNeeded;

    ScoreDoor(final char mapChar, final int tokens) {
        super(mapChar);
        tokensNeeded = tokens;
    }
    @Override
    public boolean isPassable(final Player p) {
        if (isLocked()) {
            return p.getTokenCount() >= tokensNeeded;
        }
        return true;
    }

    @Override
    public void playerContact(final Player p) {
        if (isLocked()) {
            p.takeTokens(tokensNeeded);
            unlock();
        }
    }

    @Override
    public String getAdditionalInfo() {
        if (isLocked()) {
            return String.format("%d", tokensNeeded);
        } else {
            return null;
        }
    }
}