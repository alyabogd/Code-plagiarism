package advance.codeStructure.tokens;

public abstract class Token {

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

    /**
     * Method performs tokens comparisons.
     * Each type of token should be compared differently (include it's own fields)
     *
     * Method returns 0 if objects are different and 1 otherwise
     */
    public abstract int compare(Token token);

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Token token = (Token) o;

        if (tokenType != token.tokenType) return false;
        return actualString.equals(token.actualString);
    }

    @Override
    public int hashCode() {
        int result = tokenType.hashCode();
        result = 31 * result + actualString.hashCode();
        return result;
    }

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
}
