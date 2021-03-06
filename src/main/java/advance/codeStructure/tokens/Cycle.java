package advance.codeStructure.tokens;

import java.util.Arrays;
import java.util.List;

public class Cycle extends Token {

    private static final List<String> cycles = Arrays.asList("for", "while", "do");

    public Cycle(String actualString) {
        super(actualString);
        this.tokenType = TokenType.CYCLE;
    }

    public static boolean isCycle(String word) {
        return cycles.contains(word);
    }

    @Override
    public int compare(Token token) {
        if (token.tokenType != TokenType.CYCLE) {
            return 0;
        }
        final Cycle other = (Cycle) token;
        return this.actualString.equals(other.actualString) ? 2 : 1;
    }

    @Override
    public String toString() {
        return "Cycle \"" + actualString + "\"";
    }
}
