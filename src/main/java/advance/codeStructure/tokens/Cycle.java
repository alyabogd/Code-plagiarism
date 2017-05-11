package advance.codeStructure.tokens;


public class Cycle extends Token {

    public Cycle(String actualString) {
        super(actualString);
        this.tokenType = TokenType.CYCLE;
    }
}
