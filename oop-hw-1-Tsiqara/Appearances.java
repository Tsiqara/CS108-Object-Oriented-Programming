import java.util.*;

public class Appearances {
	
	/**
	 * Returns the number of elements that appear the same number
	 * of times in both collections. Static method. (see handout).
	 * @return number of same-appearance elements
	 */
	public static <T> int sameCount(Collection<T> a, Collection<T> b) {
		if(a.size() == 0 || b.size() == 0){
			return 0;
		}
		int count = 0;
		Map<Integer,Integer> occurrences1 = numOccurrences(a);
		Map<Integer,Integer> occurrences2 = numOccurrences(b);
		for (int key :occurrences1.keySet()){
			if(occurrences2.containsKey(key) && occurrences2.get(key) == occurrences1.get(key)){
				count ++;
			}
		}
		return count;
	}

	private static <T> Map<Integer,Integer> numOccurrences(Collection<T> c){
		Map<Integer,Integer> map = new HashMap<>();
		for(T elem : c) {
			Integer key = elem.hashCode();
			if(map.containsKey(key)){
				int value = map.get(key);
				map.put(key, value + 1);
			}else{
				map.put(key, 1);
			}
		}
		return map;
	}
	
}
