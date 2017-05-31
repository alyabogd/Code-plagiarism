package advance.codeStructure.tokens;

public class Identifier extends Token {

    public Identifier(String actualString) {
        super(actualString);
        this.tokenType = TokenType.IDENTIFIER;
    }

    @Override
    public int compare(Token token) {
        return token.tokenType == TokenType.IDENTIFIER ? 1 : 0;
    }

    @Override
    public String toString() {
        return "Identifier  \"" + actualString + "\"";
    }
}
