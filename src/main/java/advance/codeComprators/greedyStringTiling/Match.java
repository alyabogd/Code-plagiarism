package advance.codeComprators.greedyStringTiling;

/**
 * Class is used to store matched tiles found in Greedy String Tiling algorithm
 */
public class Match {
    private final int patternPosition;
    private final int textPosition;
    private final int lenght;

    public Match(int patternPosition, int textPosition, int lenght) {
        this.patternPosition = patternPosition;
        this.textPosition = textPosition;
        this.lenght = lenght;
    }

    public int getPatternPosition() {
        return patternPosition;
    }

    public int getTextPosition() {
        return textPosition;
    }

    public int getLenght() {
        return lenght;
    }

    @Override
    public String toString() {
        return "Match{" +
                "patternPosition=" + patternPosition +
                ", textPosition=" + textPosition +
                ", lenght=" + lenght +
                '}';
    }
}
