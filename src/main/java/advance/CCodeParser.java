package advance;

import advance.codeStructure.Method;
import advance.codeStructure.SourceCode;
import advance.codeStructure.tokens.Border;
import advance.codeStructure.tokens.Token;
import advance.fileParcers.FileUtil;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * C++ code parser
 */
public class CCodeParser implements CodeParser {

    /**
     * Detects beginning of method inside string
     * <p>
     * First group (.*) stands for all keywords before brackets. It also
     * includes method name as last 'word'
     * <p>
     * There might be a space between method name and round brackets
     * <p>
     * There might be arguments inside round brackets. Legal characters
     * are: 'word' characters (\\w), space and comma
     * <p>
     * It is necessary for round brackets to be followed by opening curly
     * bracket. There might be one or more spaces between them.
     */
    private static final Pattern methodBeginPattern = Pattern.compile("(.*)\\s?\\([\\w ,]*\\)\\s*\\{");

    public static void main(String[] args) {
        String text = FileUtil.getText("test.txt", "UTF8");
        CodeParser parser = new CCodeParser();
        parser.parse(text);
    }

    private boolean isMethodBegin(String line) {
        return line.matches(methodBeginPattern.toString());
    }

    private List<Token> parseStatement(String statement) {
        List<Token> tokens = new LinkedList<>();
        //System.out.println("st: " + statement);
        for(Character c : statement.toCharArray()) {
            if (c == '}' || c == '{'){
                tokens.add(new Border(String.valueOf(c)));
            }
        }

        // TODO implement somehow
        return tokens;
    }

    private Method extractMethod(String[] lines, int lineBegin) {
        final Matcher methodBeginMatcher = methodBeginPattern.matcher(lines[lineBegin]);
        if (!methodBeginMatcher.find()) {
            return null;
        }
        final String[] methodSignature = methodBeginMatcher.group(1).split("\\s+");
        final String methodName = methodSignature[methodSignature.length - 1];

        final List<Token> methodTokens = new LinkedList<>();
        final int numberOfLines = lines.length;
        int currentLine = lineBegin + 1;

        Deque<Character> bracketsStack = new ArrayDeque<>();
        bracketsStack.push('{');
        methodTokens.add(new Border("{"));
        while (!bracketsStack.isEmpty() && currentLine < numberOfLines) {
            // parse content
            final String[] statements = lines[currentLine].split(";");
            final List<Token> lineTokens = new LinkedList<>();
            for (String statement : statements) {
                lineTokens.addAll(parseStatement(statement));
            }
            for (Token token : lineTokens) {
                if (token.getTokenType() == Token.TokenType.BORDER) {
                    Border border = (Border) token;
                    if (border.getBorderType() == Border.BorderType.OPENING) {
                        bracketsStack.push('{');
                    } else {
                        bracketsStack.pop();
                    }
                }
            }
            methodTokens.addAll(lineTokens);
            currentLine++;
        }

        return new Method(methodName, methodTokens, lineBegin, currentLine);
    }

    @Override
    public SourceCode parse(String text) {
        List<Method> methods = new LinkedList<>();
        final String[] lines = text.split(System.lineSeparator());
        for (int i = 0; i < lines.length; ++i) {
            if (isMethodBegin(lines[i])) {
                final Method method = extractMethod(lines, i);
                i = method.getEndLine();
                methods.add(method);
            }
        }

        for(Method method : methods) {
            System.out.println(method);
        }
        return null;
    }


}