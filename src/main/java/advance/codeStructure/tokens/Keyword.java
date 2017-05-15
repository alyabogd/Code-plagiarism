package advance.codeStructure.tokens;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Keyword extends Token {

    private static Map<String, KeywordType> rules;

    private KeywordType keywordType;

    public Keyword(String actualString) {
        super(actualString);
        this.tokenType = TokenType.KEYWORD;
        this.keywordType = determineKeywordType(actualString);
    }

    public static void setRules(Map<String, KeywordType> rules) {
        Keyword.rules = rules;
    }

    public static void setDefaultRules() {
        String[] dataTypes = new String[]{"byte", "short", "int", "long",
                "float", "double", "bool", "string", "char", "unsigned", "signed", "const"};

        String[] structural = new String[]{"new", "delete", "class", "struct", "namespace",
                "public", "private", "protected", "template", "static", "enum"};

        String[] controls = new String[]{"for", "do", "while", "break", "continue",
                "if", "else", "switch", "case", "default", "try", "catch", "throw"};

        String[] defines = new String[]{"define", "typedef"};

        rules = new HashMap<>();
        Arrays.stream(dataTypes)
                .forEach(w -> rules.put(w, KeywordType.DATA_TYPE));
        Arrays.stream(structural)
                .forEach(w -> rules.put(w, KeywordType.STRUCTURAL));
        Arrays.stream(controls)
                .forEach(w -> rules.put(w, KeywordType.CONTROL));
        Arrays.stream(defines)
                .forEach(w -> rules.put(w, KeywordType.DEFINE));
    }

    public static KeywordType determineKeywordType(String keyword) {
        if (rules == null) {
            setDefaultRules();
        }
        return rules.getOrDefault(keyword, KeywordType.OTHER);
    }

    public enum KeywordType {
        OTHER,
        DATA_TYPE,
        STRUCTURAL,
        CONTROL,
        DEFINE
    }

}