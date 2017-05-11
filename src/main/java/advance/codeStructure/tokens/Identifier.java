package advance.codeStructure.tokens;

public class Identifier extends Token {

    public Identifier(String actualString) {
        super(actualString);
        this.tokenType = TokenType.IDENTIFIER;
    }
}
