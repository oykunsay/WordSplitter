import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

public class Trie {
	public static class TrieNode {
		private HashMap<Character, TrieNode> charMap = new HashMap<>();
		public char c;
		public boolean endOfWord;
		
		public void insert(String s) {
		//	
		}
		
		public boolean contains(String s) {
			return true;
		}
	}
	
	public TrieNode root;
	
	public Trie() {
		root = new TrieNode();
	}
	
	public void insert(String s) {
		TrieNode current = root; 
		for(char c: s.toCharArray()) {
			if(!current.charMap.containsKey(c)) {
				TrieNode node = new TrieNode();
				node.c = c;
				current.charMap.put(c, node);
			}
			current = current.charMap.get(c);
		}
		current.endOfWord = true;
	}
	
	public boolean contains(String s) {
		TrieNode current = root;
		for(char c: s.toCharArray()) {
			if(!current.charMap.containsKey(c)) {
				return false;
			}
			current = current.charMap.get(c);
		}
		return current.endOfWord;
	}
	
	public void insertDictionary(String filename) throws FileNotFoundException {
		File file = new File(filename);
		try (Scanner sc = new Scanner(file)) {
			while (sc.hasNextLine()) {
				insert(sc.nextLine());
			}
		}
	}
		public void insertDictionary(File file) throws FileNotFoundException {
			try (Scanner sc = new Scanner(file)) {
				while (sc.hasNextLine()) {
					insert(sc.nextLine());
				}
			}
		
	}

}
