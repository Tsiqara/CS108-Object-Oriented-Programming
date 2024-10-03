
/*
 HW1 Taboo problem class.
 Taboo encapsulates some rules about what objects
 may not follow other objects.
 (See handout).
*/

import java.util.*;

public class Taboo<T> {
	private Map<Integer, Set<T> > map;
	
	/**
	 * Constructs a new Taboo using the given rules (see handout.)
	 * @param rules rules for new Taboo
	 */
	public Taboo(List<T> rules) {
		map = new HashMap<>();
		for(int i = 0; i < rules.size() - 1; i ++){
			if(rules.get(i) != null) {
				int key = rules.get(i).hashCode();
				if (map.containsKey(key)) {
					Set<T> tmp = map.get(key);
					if(rules.get(i+1) != null) {
						tmp.add(rules.get(i + 1));
						map.put(key, tmp);
					}
				} else {
					Set<T> tmp = new HashSet<>();
					if(rules.get(i+1) != null) {
						tmp.add(rules.get(i + 1));
						map.put(key, tmp);
					}
				}
			}
		}
	}
	
	/**
	 * Returns the set of elements which should not follow
	 * the given element.
	 * @param elem
	 * @return elements which should not follow the given element
	 */
	public Set<T> noFollow(T elem) {
		int key = elem.hashCode();
		if(map.containsKey(key)){
			return map.get(key);
		}
		return Collections.emptySet();
	}
	
	/**
	 * Removes elements from the given list that
	 * violate the rules (see handout).
	 * @param list collection to reduce
	 */
	public void reduce(List<T> list) {
		if(list.isEmpty() || map.isEmpty()){
			return;
		}
		for(int i = 0; i < list.size() - 1; i ++){
			if(list.get(i) != null) {
				int key = list.get(i).hashCode();
				if (map.containsKey(key) && (list.get(i + 1) != null) && map.get(key).contains(list.get(i + 1))) {
					list.remove(i + 1);
					i--;
				}
			}
		}
	}
}
