package advance.codeStructure.tokens;

public abstract class Token {

    public enum TokenType {
        OTHER,
        OPERATOR,
        IDENTIFIER, // ??
        CYCLE,
        CONDITION,
        METHOD_CALL,
        LITERAL,
        KEYWORD,
        BORDER
    }

    protected TokenType tokenType;
    protected String actualString;

    public Token(String actualString) {
        this.actualString = actualString;
    }

    public TokenType getTokenType() {
        return tokenType;
    }

    public String getActualString() {
        return actualString;
    }
}
