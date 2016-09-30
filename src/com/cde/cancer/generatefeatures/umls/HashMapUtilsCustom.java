package com.cde.cancer.generatefeatures.umls;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * Utilities can be used on HashMap
 * @author raghavendra
 *
 */
public class HashMapUtilsCustom {

	
	/**
	 * Returns the score of the user node.
	 * @param <K>
	 * @param key
	 * @return '1' if new user or else node score
	 */
	public static <K> HashMap<K, Integer> incrementKeyFreqCount(HashMap<K, Integer> map, K key){
		
		if(map==null) {
			map = new HashMap<K,Integer>();
		}
		
		if(map.containsKey(key)){
			int count = map.get(key);
			map.put(key, count+1);
			return map;
		}
		else{
			map.put(key, 1);
			return map;
		}
		
	}
	
	/**
	 * Returns the score of the user node.
	 * @param <K>
	 * @param key
	 * @return '1' if new user or else node score
	 */
	public static <K> HashMap<K, Integer> mergeHashMapKeyFreqCount(HashMap<K, Integer> map1, HashMap<K, Integer> map2){
		
		if(map1==null) {
			map1 = new HashMap<K,Integer>();
		}
		
		for (Entry<K, Integer> entry2 : map2.entrySet()) {
			K key = entry2.getKey();
			int count = map2.get(key);
			if(map1.containsKey(key)){
				count += map1.get(key);
			}
			map1.put(key, count);
		}
		return map1;
	}
	
	
	
	/**
	 * Returns the score of the user node.
	 * @param <K>
	 * @param key
	 * @return '1' if new user or else node score
	 */
	public static <K> TreeMap<K, Integer> incrementKeyFreqCount(TreeMap<K, Integer> map, K key){
		
		if(map.containsKey(key)){
			int count = map.get(key);
			map.put(key, count+1);
			return map;
		}
		else{
			map.put(key, 1);
			return map;
		}
		
	}
	
	
	/**
	 * Stores the word and its count from the given file into a treemap. 
	 * @param file Input text
	 * @return word-count 
	 * @throws IOException
	 */
	public static TreeMap<String, Integer> getTermAndFreqCountFromFile(File file) throws IOException {
		TreeMap<String, Integer> tm = new TreeMap<String, Integer>();
		List<String> lines = FileUtils.readLines(file);
		for(String line : lines) {
			String[] tokens = line.split(" ");
			for(String token : tokens) {
				incrementKeyFreqCount(tm, token);
			}
		}
		return tm;
	}
	
	/**
	 * Returns the key with high value in the map.
	 * @param map
	 * @return key With highest value
	 */
	public static <K> K getKeyWithTopFreq(TreeMap<K, Integer> map){
		
		int max = 0;
		K KMax = null;
		for (Entry<K, Integer> entry : map.entrySet()) {
			if(max < entry.getValue()) {
				max = entry.getValue();
				KMax = entry.getKey();
			}
    	}
		return KMax;
		
	}
	
	/**
	 * Append the value for the specified key in the map.
	 * @param map
	 * @param key
	 * @param value
	 * @return
	 */
	public static <K> HashMap<K, String> appendValueToHM(HashMap<K, String> map, K key, String value){
		
		if(map.containsKey(key)){
			map.put(key, (String) (map.get(key)+" "+value));
			return map;
		}
		else{
			map.put(key, value);
			return map;
		}
		
	}
	
	/**
	 * Adds the value to the specified key in the map
	 * @param map
	 * @param key
	 * @param value
	 * @return
	 */
	public static <K,V> Map<K, List<V>> addValueToListForKeyMap (Map<K, List<V>> map, K key, V value){
		
		List<V> list = null;
		if(map.containsKey(key)){
			list = map.get(key);
		} else {
			list = new ArrayList<V>();
		}
		list.add(value);
		map.put(key, list);
		return map;
	}
	
	  /**
     * Sort the map in accordance with values.
     * @param map
     * @return
     */
    public static <K, V extends Comparable<V>> TreeMap<K, V> sortByValues(final Map<K, V> map) {
		Comparator<K> valueComparator =  new Comparator<K>() {
		    public int compare(K k1, K k2) {
		        int compare = map.get(k2).compareTo(map.get(k1));
		        if (compare == 0) return 1;
		        else return compare;
		    }
		};
		TreeMap<K, V> sortedByValues = new TreeMap<K, V>(valueComparator);
		sortedByValues.putAll(map);
		return sortedByValues;
	}
    
    /**
     * Prints the HashMap
     * @param map
     */
    public static <K, V> void printHashMap (Map<K, V> map) {
    	for (Entry<K, V> entry : map.entrySet()) {
    		System.out.println(entry.getKey() + "\t" + entry.getValue() );
    	}
    }
    
    /**
     * Converts hashMap to a list.
     * @param map
     * @return list
     */
    static <K,V> List<String> convertHMtoList (HashMap<K,V> map) {
    	List<String> list = new ArrayList<String>();
    	for (Entry<K, V> entry : map.entrySet()) {
    		list.add(entry.getKey() + "\t_" + entry.getValue() );
    	}
		return list;
    	
    }
    
    public static HashMap<String, Integer> convertStringArrayToHashMap (String tokens[]) {
    	HashMap<String, Integer> map = new HashMap<String, Integer>();;
    	for (String token : tokens) {
			incrementKeyFreqCount(map, token);
    	}
    	return map;
    }
    
    /**
     * Flush HashMap to a file.
     * @param map
     * @param filename
     */
    public static <K,V> void dumpHashMap (HashMap<K, V> map, String filename) {
    	try {
			FileUtils.writeLines(new File(filename), convertHMtoList(map));
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    

    /**
     * Returns ngrams from the given string array.
     * @param tokens Array of tokens
     * @param len ngram value i.e 1 for unigram, 2 for bigram 
     * @return
     */
	 public static List<String> getNGrams(String[] tokens, int len) {
			List<String> ngrams = new ArrayList<String>();

			for(int i = 0 ; i < tokens.length - len + 1; i++) {
				ngrams.add(concat(tokens, i, i+len));
			}

			return ngrams;
		}
	 
	 /**
	  * Concatenates the tokens to form a string
	  * @param tokens Array of tokens
	  * @param start Start index
	  * @param end end Index
	  * @return
	  */
	 private static String concat(String[] tokens, int start, int end) {
			StringBuilder sb = new StringBuilder();
			for (int i = start; i < end; i++)
				sb.append((i > start ? " " : "") + tokens[i]);
			return sb.toString();
		}

	public static int indexOfIgnorecaseList(List<String> trainDict,
			String token) {

		for (int i=0; i< trainDict.size(); i++) {
			if(StringUtils.equalsIgnoreCase(trainDict.get(i), token)){
				return i;
			}
		}
		return -1;
	}

}
