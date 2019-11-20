class ScoreDoor extends Door{
    private int tokensNeeded;

    ScoreDoor(final char mapChar, final int tokens) {
        super(mapChar);
        tokensNeeded = tokens;
    }
}