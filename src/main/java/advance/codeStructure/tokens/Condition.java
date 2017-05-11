package advance.codeStructure.tokens;

/**
 * Created by Alina on 5/11/2017.
 */
public class Condition extends Token {

    public Condition(String actualString) {
        super(actualString);
        this.tokenType = TokenType.CONDITION;
    }

}
