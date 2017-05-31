package advance.codeStructure.tokens;

public class MethodCall extends Token {

    private String methodName;

    public MethodCall(String actualString) {
        super(actualString);
        this.tokenType = TokenType.METHOD_CALL;

    }

    @Override
    public int compare(Token token) {
        if (token.getTokenType() != TokenType.METHOD_CALL) {
            return 0;
        }
        return 1;
    }

    @Override
    public String toString() {
        return "MethodCall {" +
                "methodName='" + methodName + '\'' +
                '}';
    }
}
