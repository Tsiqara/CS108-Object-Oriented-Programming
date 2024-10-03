// TabooTest.java
// Taboo class tests -- nothing provided.

import java.util.*;

import junit.framework.TestCase;

public class TabooTest extends TestCase {

    public void testNoFollow1(){
        Taboo<String> t = new Taboo<>(Arrays.asList("a", "c", "a", "b"));
        Set<String> s = new HashSet<>(Arrays.asList("c", "b"));
        assertEquals(s, t.noFollow("a"));
        assertEquals(Collections.emptySet(), t.noFollow("b"));
        s =  new HashSet<>(Arrays.asList("a"));
        assertEquals(s, t.noFollow("c"));

        List<Integer> list = Arrays.asList(1,2,4,5,1,7,1);
        Taboo<Integer> ta = new Taboo<>(list);
        Set<Integer> se = new HashSet<>(Arrays.asList(2, 7));
        assertEquals(se, ta.noFollow(1));
    }

    public void testNoFollowWithNull(){
        Taboo<Character> t = new Taboo<>(Arrays.asList('a', 'b', null, 'c', 'd'));
        Set<Character> s = new HashSet<>(Arrays.asList('b'));
        assertEquals(s, t.noFollow('a'));
        s = new HashSet<>(Arrays.asList('d'));
        assertEquals(s, t.noFollow('c'));
        assertEquals(Collections.emptySet(), t.noFollow('b'));
    }

    public void testNoFollowEmpty(){
        Taboo<String> t = new Taboo<>(Arrays.asList());
        assertEquals(Collections.emptySet(), t.noFollow("a"));
        assertEquals(Collections.emptySet(), t.noFollow("123"));
    }
    public void testNoFollowOneElem(){
        Taboo<String> t = new Taboo<>(Arrays.asList("234"));
        assertEquals(Collections.emptySet(), t.noFollow("a"));
        assertEquals(Collections.emptySet(), t.noFollow("123"));
    }

    public void testReduce1(){
        List<String> l = new ArrayList<>(Arrays.asList("a", "c", "b", "x", "c", "a"));
        Taboo<String> t = new Taboo<>(Arrays.asList("a", "c", "a", "b"));
        List<String> modified = Arrays.asList("a", "x", "c");
        t.reduce(l);
        assertEquals(modified, l);
    }

    public void testReduce2(){
        List<Character> l = new ArrayList<>(Arrays.asList('a', 'b', 'a', 'c', 'c', 'd'));
        Taboo<Character> t = new Taboo<>(Arrays.asList('a', 'b', null, 'c', 'd'));
        List<Character> modified = Arrays.asList('a', 'a', 'c', 'c');
        t.reduce(l);
        assertEquals(modified, l);
    }

    public void testReduceNoRules(){
        List<Integer> l = Arrays.asList(1, 2, 5, 6, 34, 12);
        Taboo<Integer> t = new Taboo<>(Arrays.asList());
        List<Integer> modified = l;
        t.reduce(l);
        assertEquals(modified, l);

        l = Arrays.asList(1, 2, 5, 6, 34, 12);
        t = new Taboo<>(Arrays.asList(1, 7, null, 2, null, 5, null, 6));
        modified = l;
        t.reduce(l);
        assertEquals(modified, l);

        l = Arrays.asList(1, 2, 5, 6, 34, 12);
        t = new Taboo<>(Arrays.asList( null, null, null, null, null));
        modified = l;
        t.reduce(l);
        assertEquals(modified, l);
    }

    public void testReduceOneElem(){
        List<Character> l = Arrays.asList('M');
        Taboo<Character> t = new Taboo<>(Arrays.asList('m', 'a', '-'));
        List<Character> modified = l;
        t.reduce(l);
        assertEquals(modified, l);
    }

    public void testReduceEmpty(){
        List<String> l = Arrays.asList();
        Taboo<String > t = new Taboo<>(Arrays.asList("a", "c", "a", "b"));
        List<String> modified = l;
        t.reduce(l);
        assertEquals(modified, l);
    }
}
