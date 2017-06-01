package advance.codeComprators;

import advance.codeStructure.Method;
import advance.codeStructure.SourceCode;
import advance.codeStructure.tokens.Token;

import java.util.List;

/**
 * Class performs code comparison by cost of their mutual alignment
 * (Levenshtain)
 */
public class AlignmentCostCodeComparator implements CodeComparator {

    private static final int COINCIDENCE_COST = -2;
    private static final int MISS_COST = 1;

    // TODO come up with rule (plagiarism or not)

    private int getAlignmentCost(List<Token> first, List<Token> second) {
        if (first.size() < second.size()) {
            List<Token> temp = first;
            first = second;
            second = temp;
        }

        final Token[] firstMethod = first.toArray(new Token[0]);
        final Token[] secondMethod = second.toArray(new Token[0]);

        int[][] costs = new int[2][second.size() + 1];

        for (int i = 0; i < firstMethod.length; ++i) {
            costs[1][0] = 0;
            for (int j = 1; j <= secondMethod.length; ++j) {
                costs[0][j] = costs[1][j];
                int equalityMeasure = firstMethod[i].compare(secondMethod[j - 1]);
                if (equalityMeasure == 2) {
                    // tokens are equal
                    costs[1][j] = costs[0][j - 1] + COINCIDENCE_COST;
                } else {
                    final int swapCost = costs[0][j - 1] + 2 - equalityMeasure;
                    final int missCost = Math.min(costs[0][j], costs[1][j - 1]) + MISS_COST;
                    costs[1][j] = Math.min(swapCost, missCost);
                }
            }
        }
        return costs[1][second.size() - 1];
    }

    @Override
    public CodeSimilarity compare(SourceCode first, SourceCode second) {
        CodeSimilarity codeSimilarity = new CodeSimilarity();
        for (Method methodFirst : first.getMethods()) {
            for (Method methodSecond : second.getMethods()) {
                final int alignmentCost = getAlignmentCost(methodFirst.getStructure(), methodSecond.getStructure());
                final int minLength = Math.min(methodFirst.getNumberOfTokens(), methodSecond.getNumberOfTokens());
                //if (alignmentCost > minLength / 2) {
                codeSimilarity.addSimilarity(methodFirst, methodSecond, alignmentCost);
                //}
            }
        }
        return codeSimilarity;
    }
}
