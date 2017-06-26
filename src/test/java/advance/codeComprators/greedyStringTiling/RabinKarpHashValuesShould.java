package advance.codeComprators.greedyStringTiling;

import advance.codeStructure.tokens.*;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;


public class RabinKarpHashValuesShould {

    private static List<Token> first;
    private static List<Token> second;

    @BeforeClass
    public static void setUp() {
        first = new ArrayList<>();
        first.addAll(Arrays.asList(
                new Border("}"),
                new Cycle("for"),
                new Operator("-"),
                new Operator("++"),
                new Condition("if"),
                new Border("{")
        ));

        second = new ArrayList<>();
        second.addAll(Arrays.asList(
                new Border("}"),
                new Cycle("for"),
                new Operator("-"),
                new Operator("++"),
                new Condition("if"),
                new Border("{")
        ));

    }

    @Test
    public void giveEqualHashForEqualTokens() {
        RabinKarpHashValues hashValues_f = new RabinKarpHashValues(first);
        RabinKarpHashValues hashValues_s = new RabinKarpHashValues(second);

        for (int l = 0; l < first.size(); ++l) {
            for (int r = l + 1; r < first.size(); ++r) {
                assertEquals(hashValues_f.getHash(l, r), hashValues_s.getHash(l, r));
            }
        }
    }

    @Test
    public void giveEqualHashesForSingleElements() {
        RabinKarpHashValues hashValues_f = new RabinKarpHashValues(first);
        RabinKarpHashValues hashValues_s = new RabinKarpHashValues(second);

        for (int l = 0; l < first.size(); ++l) {
            assertEquals(hashValues_f.getHash(l, l), hashValues_s.getHash(l, l));
        }
    }

    @Test
    public void giveDifferentHashedForDifferentTokens() {
        RabinKarpHashValues hashValues_f = new RabinKarpHashValues(first);
        RabinKarpHashValues hashValues_s = new RabinKarpHashValues(second);

        for (int l_f = 0; l_f < first.size(); ++l_f) {
            for(int r_f = l_f; r_f < first.size(); ++r_f) {
                for(int l_s = 0; l_s < first.size(); ++l_s) {
                    for(int r_s = l_s; r_s < first.size(); ++r_s) {
                        if (l_s == l_f && r_s == r_f) {
                            continue;
                        }
                        assertNotEquals(l_f + "-" + r_f + "  " + l_s + "-" + r_s,
                                hashValues_f.getHash(l_f, r_f), hashValues_s.getHash(l_s, r_s));
                    }
                }
            }
        }

    }

    @Test
    public void giveEqualHashesForEqualSequences() {

        first.clear();;
        first.addAll(Arrays.asList(
                new Border("}"),
                new Cycle("for"),
                new Operator("-"),
                new Operator("++"),
                new Condition("if"),
                new Border("{")
        ));

        List<Token> list = new ArrayList<>();
        list.addAll(Arrays.asList(
                new Operator("+"),
                new Cycle("while"),
                new Border("}"),
                new Cycle("for"),
                new Operator("-"),
                new Operator("++")
        ));

        RabinKarpHashValues hashValues_f = new RabinKarpHashValues(first);
        RabinKarpHashValues hashValues_s = new RabinKarpHashValues(list);

        assertEquals(hashValues_f.getHash(0, 2), hashValues_s.getHash(2,4));
        assertEquals(hashValues_f.getHash(1, 2), hashValues_s.getHash(3,4));
        assertEquals(hashValues_f.getHash(0, 1), hashValues_s.getHash(2,3));

        assertEquals(hashValues_f.getHash(2, 3), hashValues_s.getHash(4,5));

        assertNotEquals(hashValues_f.getHash(1, 4), hashValues_s.getHash(1, 4));
        assertNotEquals(hashValues_f.getHash(1, 3), hashValues_s.getHash(2, 4));
        assertNotEquals(hashValues_f.getHash(1, 5), hashValues_s.getHash(2, 3));
    }

}