package advance.codeStructure.tokens;

/**
 * Created by Alina on 5/11/2017.
 */
public class MethodCall extends Token {

    private String methodName;

    public MethodCall(String actualString) {
        super(actualString);
        this.tokenType = TokenType.METHOD_CALL;
        // TODO extract method name/ what for?
    }

    @Override
    public String toString() {
        return "MethodCall {" +
                "methodName='" + methodName + '\'' +
                '}';
    }
}
