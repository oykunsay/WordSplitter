import java.util.HashMap;

public class Splitting {
	HashMap<String, String> wordMap = new HashMap<>();
	Trie dictionary;
	
	public Splitting(Trie trie) {
		dictionary = trie;
	}
	
	public String split(String str) {
		if(dictionary.contains(str)) {
			return (str);
		}else if(wordMap.containsKey(str)) {
			return (wordMap.get(str));
		}else {
			for(int i=0; i < str.length(); i++) {
				String prefix = str.substring(0,i);
				if(dictionary.contains(prefix)) {
					String end = str.substring(i);
					String newEnd = split(end);
					if(newEnd != null) {
						wordMap.put(str, prefix + " " + newEnd);
						return prefix + " " + newEnd;
					}else {
					}
				}
			}
		}
		wordMap.put(str, null);
		return null;
	}
}
