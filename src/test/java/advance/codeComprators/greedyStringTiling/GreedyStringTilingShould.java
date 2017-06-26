package advance.codeComprators.greedyStringTiling;

import advance.codeStructure.tokens.*;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;


public class GreedyStringTilingShould {

    private static List<Token> tokens;
    private static List<Token> other;

    private static GreedyStringTiling gst;

    @BeforeClass
    public static void setUp() {
        tokens = new ArrayList<>();
        tokens.addAll(Arrays.asList(
                new Border("{"),
                new Cycle("for"),
                new Operator("="),
                new Literal("i"),
                new Operator("<"),
                new Operator("++"),
                new Border("{"),
                new Condition("if"),
                new Operator(">="),
                new Operator("+"),
                new Operator("="),
                new Border("}"),
                new Border("}"),
                new Operator("*"),
                new Border("}")
        ));

        other = new ArrayList<>();
        gst = new GreedyStringTiling();
    }

    @Test
    public void markAllTokensForEqualSequences() {
        other = new ArrayList<>();
        other.addAll(tokens);
        int tokensMarked = gst.getStringTiling(tokens, other).getLengthOfTokensTiled();
        assertEquals(tokens.size(), tokensMarked);
        tokensMarked = gst.getStringTiling(other, tokens).getLengthOfTokensTiled();
        assertEquals(tokens.size(), tokensMarked);
    }

    @Test
    public void markTokensForAlmostEqualSequences() {
        other = new ArrayList<>(tokens);
        other.set(0, new Cycle("while"));
        int tokensMarked = gst.getStringTiling(tokens, other).getLengthOfTokensTiled();
        assertEquals(tokens.size() - 1, tokensMarked);

        other = new ArrayList<>(tokens);
        other.set(8, new Cycle("while"));
        tokensMarked = gst.getStringTiling(tokens, other).getLengthOfTokensTiled();
        assertEquals(tokens.size() - 1, tokensMarked);

        other = new ArrayList<>(tokens);
        other.set(14, new Cycle("while"));
        tokensMarked = gst.getStringTiling(tokens, other).getLengthOfTokensTiled();
        assertEquals(tokens.size() - 1, tokensMarked);
    }

    @Test
    public void ignorePartsLessThenMinMatchLength() {
        int tokensMarked;

        other = new ArrayList<>(tokens);
        for (int i = 0; i < other.size(); i += 3) {
            other.set(i, new Cycle("while"));
        }
        tokensMarked = gst.getStringTiling(tokens, other).getLengthOfTokensTiled();
        assertEquals(0, tokensMarked);

        other = new ArrayList<>(tokens);
        for (int i = 0; i < other.size(); i += 4) {
            other.set(i, new Cycle("while"));
        }
        tokensMarked = gst.getStringTiling(tokens, other).getLengthOfTokensTiled();
        assertEquals(0, tokensMarked);

        other = new ArrayList<>(tokens);
        for (int i = 0; i < other.size(); i += 5) {
            other.set(i, new Cycle("while"));
        }
        tokensMarked = gst.getStringTiling(tokens, other).getLengthOfTokensTiled();
        assertEquals(0, tokensMarked);

    }
}