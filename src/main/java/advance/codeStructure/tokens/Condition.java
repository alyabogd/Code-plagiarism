package advance.codeStructure.tokens;

public class Condition extends Token {

    public Condition(String actualString) {
        super(actualString);
        this.tokenType = TokenType.CONDITION;
    }

    public static boolean isCondition(String word) {
        return word.equals("if");
    }

    @Override
    public int compare(Token token) {
        if (token.tokenType != TokenType.CONDITION) {
            return 0;
        }
        final Condition other = (Condition) token;
        return this.actualString.equals(other.actualString) ? 1 : 0;
    }

    @Override
    public String toString() {
        return "Condition \"" + actualString + "\"";
    }
}
