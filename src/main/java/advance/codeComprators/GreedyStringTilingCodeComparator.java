package advance.codeComprators;

import advance.codeComprators.greedyStringTiling.GreedyStringTiling;
import advance.codeComprators.greedyStringTiling.TiledMatches;
import advance.codeStructure.Method;
import advance.codeStructure.SourceCode;

public class GreedyStringTilingCodeComparator implements CodeComparator {

    private GreedyStringTiling greedyStringTiling;

    public GreedyStringTilingCodeComparator() {
        greedyStringTiling = new GreedyStringTiling();
    }

    @Override
    public CodeSimilarity compare(SourceCode first, SourceCode second) {
        CodeSimilarity codeSimilarity = new CodeSimilarity();
        for (Method methodFromFirst : first.getMethods()) {
            for (Method methodFromSecond : second.getMethods()) {
                TiledMatches matches = greedyStringTiling.getStringTiling(
                        methodFromFirst.getStructure(), methodFromSecond.getStructure());
                if (matches.getLengthOfTokensTiled() >
                        Math.min(methodFromFirst.getNumberOfTokens(), methodFromSecond.getNumberOfTokens()) / 2) {
                    codeSimilarity.addSimilarity(methodFromFirst, methodFromSecond, matches.getLengthOfTokensTiled());
                }
            }
        }
        return codeSimilarity;
    }
}
