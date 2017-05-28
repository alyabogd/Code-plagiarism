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
    public String toString() {
        return "Condition \"" + actualString + "\"";
    }
}
