package advance.codeComprators;

import advance.codeStructure.Method;

import java.util.LinkedList;
import java.util.List;

/**
 * Class represents similarity of 2 source codes
 */
public class CodeSimilarity {

    private List<MethodSimilarity> plagiatedMethods;

    public CodeSimilarity() {
        plagiatedMethods = new LinkedList<>();
    }

    public void addSimilarity(Method first, Method second, double measure) {
        plagiatedMethods.add(new MethodSimilarity(first, second, measure));
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("CodeSimilarity : \n");
        for (MethodSimilarity ms : plagiatedMethods) {
            sb.append("\t").append(ms).append("\n");
        }
        return sb.toString();
    }
}
