package advance.codeStructure.tokens;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Represents variable types in source code
 */
public class DataType extends Token {

    private static Set<String> dataTypes;

    /**
     * PRIMITIVE
     * CONTAINER
     * OTHER
     * ?????????????????
     */
    public enum Type {
        PRIMITIVE,
        CONTAINER,
        OTHER
    }

    public static void setDataTypes(Set<String> dataTypes) {
        DataType.dataTypes = dataTypes;
    }

    public static void setDefaultDataTypes() {
        dataTypes = new HashSet<>();

        String[] integerTypes = new String[] {"byte", "short", "int", "long"};
        String[] doubleTypes = new String[] {"float", "double"};
        String[] miscTypes = new String[] {"bool", "string", "char", "unsigned"};

        dataTypes.addAll(Arrays.asList(integerTypes));
        dataTypes.addAll(Arrays.asList(doubleTypes));
        dataTypes.addAll(Arrays.asList(miscTypes));
    }

    // TODO ????????????????????????

    public DataType(String actualString) {
        super(actualString);
        this.tokenType = TokenType.DATA_TYPE;
    }
}
