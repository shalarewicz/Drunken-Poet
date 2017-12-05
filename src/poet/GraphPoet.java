/* Copyright (c) 2015-2017 MIT 6.005/6.031 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package poet;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Set;

import graph.Graph;

/**
 * A graph-based poetry generator.
 * 
 * <p>GraphPoet is initialized with a corpus of text, which it uses to derive a
 * word affinity graph.
 * Vertices in the graph are words. Words are defined as non-empty
 * case-insensitive strings of non-space non-newline characters. They are
 * delimited in the corpus by spaces, newlines, or the ends of the file.
 * Edges in the graph count adjacencies: the number of times "w1" is followed by
 * "w2" in the corpus is the weight of the edge from w1 to w2.
 * 
 * <p>For example, given this corpus:
 * <pre>    Hello, HELLO, hello, goodbye!    </pre>
 * <p>the graph would contain two edges:
 * <ul><li> ("hello,") -> ("hello,")   with weight 2
 *     <li> ("hello,") -> ("goodbye!") with weight 1 </ul>
 * <p>where the vertices represent case-insensitive {@code "hello,"} and
 * {@code "goodbye!"}.
 * 
 * <p>Given an input string, GraphPoet generates a poem by attempting to
 * insert a bridge word between every adjacent pair of words in the input.
 * Words are defined and delimited as in the corpus.
 * The bridge word between input words "w1" and "w2" will be some "b" such that
 * w1 -> b -> w2 is a two-edge-long path with maximum-weight weight among all
 * the two-edge-long paths from w1 to w2 in this poet's affinity graph.
 * If there are no such paths, no bridge word is inserted.
 * In the output poem, input words retain their original case, while bridge
 * words are lower case. The whitespace between every word in the poem is a
 * single space, with no whitespace at the beginning or end.
 * 
 * <p>For example, given this corpus:
 * <pre>    This is a test of the Mugar Omni Theater sound system.    </pre>
 * <p>on this input:
 * <pre>    Test the system.    </pre>
 * <p>the output poem would be:
 * <pre>    Test of the system.    </pre>
 * 
 * <p>PS2 instructions: this is a required ADT class, and you MUST NOT weaken
 * the required specifications. However, you MAY strengthen the specifications
 * and you MAY add additional methods.
 * You MUST use Graph in your rep, but otherwise the implementation of this
 * class is up to you.
 */
public class GraphPoet {
    
    private final Graph<String> graph = Graph.empty();
    
    // Abstraction function:
    //   Graph poet represents an adjacency graph where each edge represents that word1 is followed by word2
    // 		the weight of that edge times. 
    // Representation invariant:
    //   Each edge occurs weight times in the input. 
    // 		i.e. Total weight = number words - 1;
	//   Each word is in corpus
    // Safety from rep exposure:
    //   TODO
    
    /**
     * Create a new poet with the graph from corpus (as described above).
     * 
     * @param corpus text file from which to derive the poet's affinity graph
     * @throws IOException if the corpus file cannot be found or read
     */
    public GraphPoet(File corpus) throws IOException {
    	/*Periods should stop edges from being formed. are they allowed in the input?
    	 * unless they are a part of the word. 
    	 * Shakespeare.text separates "," and "." but we can just add them to the end of the previous word
    	 * since we have to grab the next word anyway
    	 */
    	
    	// IF a comma or period is found by itself it will be appended to the end of the previous word. 
    	final String COMMA = ",";
    	final String PERIOD = ".";
    	
    	Scanner input = new Scanner(corpus);
    	// Breaks if length < 2;
    	// Grab the next two words
    	String previousWord = input.next().toLowerCase();
    	String currentWord = input.next().toLowerCase();
    	boolean last = false;
    	while (input.hasNext() || !last) {
    			// Account for periods or commas
    			if (currentWord.equals(COMMA) || currentWord.equals(PERIOD)) {
    				previousWord = previousWord + currentWord;
    				currentWord = input.next().toLowerCase();
    			}
    			
    			// Add the edge to the graph
    			int oldWeight = graph.set(previousWord, currentWord, 1);
    			
    			if (oldWeight != 0) {
    				graph.set(previousWord, currentWord, oldWeight + 1);
    			}
    			if (!input.hasNext()) {
    				last = true;
    			}
    			else {
    				last = false;
    				previousWord = currentWord;
    				currentWord = input.next().toLowerCase();
    			}
    		
    	}
    	
    	input.close();
    }
    
    // TODO checkRep
    
    /**
     * Generate a poem.
     * 
     * @param input string from which to create the poem
     * @return poem (as described above)
     */
    public String poem(String input) {
    	// When adding a bridge word, check to see if prior word has period then capitalize.
    	// New line ever 10 words?
    	
    	Scanner in = new Scanner(input);
    	StringBuilder result = new StringBuilder();
    	
    	String currentWord = in.next().toLowerCase();
    	// TODO: Will break with string length < 2
    	String toMatch = in.next();
    	boolean last = false;
    	while (in.hasNext() || !last) {
    		System.out.println("Looking for " + toMatch);
    		String toAdd = "";
    		int maxWeight = 0;
    		if (graph.vertices().contains(currentWord)) {
	    		Map<String, Integer> firstLayer = graph.targets(currentWord);
	    		for (String s : firstLayer.keySet()) {
	    			System.out.println("Current first layer " + s);
	    			int weightS = firstLayer.get(s);
	    			Map<String, Integer> secondLayer = graph.targets(s);
	    			
	    			for (String bridge : secondLayer.keySet()) {
	    				System.out.println("Current second layer " + bridge);
	    				if (!toMatch.equals(bridge)) {
	    					System.out.println("We skipped it");
	    					continue;
	    				}
	    				int weightBridge = secondLayer.get(bridge) + weightS;
	
	    				if (weightBridge < maxWeight) {
	    					continue;
	    				}
	    				else if (weightBridge > maxWeight) {
	    					maxWeight = weightBridge;
	    					toAdd = s;
	    				}
	    				else if (bridge.compareTo(toAdd) < 0) {
	    					toAdd = s;
	    				}
	    				
	    			}
	    		}
	    	
	    	
    		}
    		result.append(currentWord + " ");
    		if (toAdd != "") {
    			result.append(toAdd + " ");
    		}
    		if (!in.hasNext()) {
    			last = true;	
    		}
    		else {
    			last = false;
    			currentWord = toMatch;
    			toMatch = in.next();
    		}
    	}
    	
    	in.close();
    	result.append(toMatch).toString();
    	System.out.println(result);
    	return result.toString();
    }
    
    public Map<String, Integer> getSecondLayer(String s){
    	throw new RuntimeException("Not implemented");
    }
    
    public Map<String, Integer> getFirstLayer(String s){
    	return this.graph.targets(s);
    }
    // TODO toString()
    // try to have it re-create the source text. basically a depth first search in reverse. start with nodes with the highest weight
    
}
