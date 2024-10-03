import java.util.HashSet;
import java.util.Set;

// CS108 HW1 -- String static methods

public class StringCode {

	/**
	 * Given a string, returns the length of the largest run.
	 * A a run is a series of adajcent chars that are the same.
	 * @param str
	 * @return max run length
	 */
	public static int maxRun(String str) {
		if(str.length() == 0){
			return 0;
		}
		int max = 1;
		int curMax = 1;
		char prev = str.charAt(0);
		for(int i = 1; i < str.length(); i ++){
			char cur = str.charAt(i);
			if(cur == prev){
				curMax ++;
			}else{
				max = Math.max(max, curMax);
				curMax = 1;
			}
			prev = cur;
		}
		return max;
	}

	
	/**
	 * Given a string, for each digit in the original string,
	 * replaces the digit with that many occurrences of the character
	 * following. So the string "a3tx2z" yields "attttxzzz".
	 * @param str
	 * @return blown up string
	 */
	public static String blowup(String str) {
		if(str.length() == 0){
			return "";
		}
		StringBuffer blownUpString = new StringBuffer();
		for(int i = 0; i < str.length() - 1; i ++){
			char cur = str.charAt(i);
			if(!Character.isDigit(cur)){
				blownUpString.append(cur);
			}else{
				char next = str.charAt(i + 1);
				int num = Integer.parseInt(String.valueOf(cur));
				for(int j = 0; j < num; j ++){
					blownUpString.append(next);
				}
			}
		}
		if(!Character.isDigit(str.charAt(str.length() - 1))) {
			blownUpString.append(str.charAt(str.length() - 1));
		}
		return blownUpString.toString();
	}
	
	/**
	 * Given 2 strings, consider all the substrings within them
	 * of length len. Returns true if there are any such substrings
	 * which appear in both strings.
	 * Compute this in linear time using a HashSet. Len will be 1 or more.
	 */
	public static boolean stringIntersect(String a, String b, int len) {
		if(a.length() == 0 || b.length() == 0){
			return false;
		}
		if(len > a.length() || len > b.length()){
			return false;
		}
		if(a == b){
			return true;
		}
		Set<String> substrings1 = new HashSet<>();
		for(int i = 0; i <= a.length() - len; i ++){
			substrings1.add(a.substring(i,i + len));
		}
		for(int i = 0; i <= b.length() - len; i ++){
			if(substrings1.contains(b.substring(i, i + len))){
				return true;
			}
		}
		return false;
	}
}
