// StringCodeTest
// Some test code is provided for the early HW1 problems,
// and much is left for you to add.

import junit.framework.TestCase;

public class StringCodeTest extends TestCase {
	//
	// blowup
	//
	public void testBlowup1() {
		// basic cases
		assertEquals("xxaaaabb", StringCode.blowup("xx3abb"));
		assertEquals("xxxZZZZ", StringCode.blowup("2x3Z"));
	}
	
	public void testBlowup2() {
		// things with digits
		
		// digit at end
		assertEquals("axxx", StringCode.blowup("a2x3"));
		
		// digits next to each other
		assertEquals("a33111", StringCode.blowup("a231"));
		
		// try a 0
		assertEquals("aabb", StringCode.blowup("aa0bb"));
	}
	
	public void testBlowup3() {
		// weird chars, empty string
		assertEquals("AB&&,- ab", StringCode.blowup("AB&&,- ab"));
		assertEquals("", StringCode.blowup(""));
		
		// string with only digits
		assertEquals("", StringCode.blowup("2"));
		assertEquals("33", StringCode.blowup("23"));
	}
	
	
	//
	// maxRun
	//
	public void testRun1() {
		assertEquals(2, StringCode.maxRun("hoopla"));
		assertEquals(3, StringCode.maxRun("hoopllla"));
	}
	
	public void testRun2() {
		assertEquals(3, StringCode.maxRun("abbcccddbbbxx"));
		assertEquals(0, StringCode.maxRun(""));
		assertEquals(3, StringCode.maxRun("hhhooppoo"));
	}
	
	public void testRun3() {
		// "evolve" technique -- make a series of test cases
		// where each is change from the one above.
		assertEquals(1, StringCode.maxRun("123"));
		assertEquals(2, StringCode.maxRun("1223"));
		assertEquals(2, StringCode.maxRun("112233"));
		assertEquals(3, StringCode.maxRun("1112233"));
	}

	// Need test cases for stringIntersect
	public void testIntersectEmpty(){
		//one of strings is empty
		assertEquals(false, StringCode.stringIntersect("","aab",1));
		assertEquals(false, StringCode.stringIntersect("cd","",1));
		assertEquals(false, StringCode.stringIntersect("","",1));
	}

	public void testIntersectSubstring() {
		//one string is substring of second
		assertEquals(true, StringCode.stringIntersect("aabcd", "ab", 2));
		assertEquals(true, StringCode.stringIntersect("ab", "aabcd",2));
		assertEquals(true, StringCode.stringIntersect("bc", "bbbced", 2));
		assertEquals(true, StringCode.stringIntersect("abcd", "abcd", 4));
	}
	public void testIntersectBasic() {
		//basic cases
		assertEquals(false, StringCode.stringIntersect("ab","cd",1));
		assertEquals(true, StringCode.stringIntersect("abc","cdef",1));
		assertEquals(true, StringCode.stringIntersect("abcd","cdef",1));
		assertEquals(false, StringCode.stringIntersect("abbced","cdef",2));
		assertEquals(false, StringCode.stringIntersect("cdef","abbced",2));
		assertEquals(true, StringCode.stringIntersect("aabcdeef","cdef",3));
		assertEquals(true, StringCode.stringIntersect("cdef","aabcdeef",3));
	}
	public void testIntersectLonger() {
		//len is greater than length of string
		assertEquals(false, StringCode.stringIntersect("aabcd", "ab", 3));
		assertEquals(false, StringCode.stringIntersect("bc", "bbbced", 3));
		assertEquals(false, StringCode.stringIntersect("abcd", "bbbced", 7));
		assertEquals(false, StringCode.stringIntersect("abcd", "abcd", 5));
	}
}
