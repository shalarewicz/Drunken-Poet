/* Copyright (c) 2015-2017 MIT 6.005/6.031 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package poet;

import static org.junit.Assert.*;

import org.junit.Test;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Set;

/**
 * Tests for GraphPoet.
 */
public class GraphPoetTest {
    
	private final File CORPUS = new File("test/poet/shakespeare.txt");
	private final File SHORT_CORPUS = new File("test/poet/test.txt");
	private final String TEST = "Out of the frying pan and into the fire";
	private final int TEST_LENGTH = 9;
	private final String EMPTY = "";
	private final String CASES = "OUT OF THE FRYING PAN AND INTO THE FIRE";
	private final String SPECIAL = "OUT-OF THE frying pan, and into the fire."; // Don't need this
	private final String SINGLE = "Singleton";
	
	
    // Testing strategy
	// Poet
    //   Case Insensitive
	//   Selected path with max weight
	//   	- tied weight defaults to max weight of first edge then which ever bridge word comes alphabetically first
	//   Max word length is 2n - 1 words where n is length of input string. Min length is n.
	//
	// Graph Poet
	// 	tested by checkRep() in implementation
    
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }
    
    // TODO tests
    
    private int wordLength(String a) {
    	int length = 0;
    	
    	int currentIndex = 0;
    	 // TODO: Will return one for empty string. make this a recursive call instead or just...
    	if (a.equals("")) { return length;}
    	// Double spaces will count as a word
    	while (currentIndex != -1) {
    		length++;
    		currentIndex = a.indexOf(" ");
    	}
    	
    	return length;
    }
    
    
    @Test
    // Test to make sure word length of generated poem is between n and (n-1) inclusive
    public void testPoemLenght() {
    	try {
    		GraphPoet TEST_GRAPH = new GraphPoet(CORPUS);
    		assertEquals("expected length of TEST ", TEST_LENGTH, wordLength(TEST));
    		
    		final String poem = TEST_GRAPH.poem("TEST");
    		int poemLength = wordLength(poem);
    		
    		assertTrue("expected poem length between input length, n, and 2n - 1 ", TEST_LENGTH <= poemLength && poemLength <= 2 * TEST_LENGTH -1);
    		
    	}
    	catch (IOException e) {
    		assertTrue("Encountered exception " + e, false);
    	}
    }
    
    @Test
    // Test that poet selects bridge words with max weight or if weights equal the first word in alphabetical order
    // Tests that every word is followed either by the next word in the input or by a bridge word with the above
    // criteria
    // 
    public void testGraphPoet() {
    	try {
    		GraphPoet TEST_GRAPH = new GraphPoet(SHORT_CORPUS);	
    		
    		final String poem = TEST_GRAPH.poem(TEST);
    		
    		// For every word in test find all length 2 paths. make sure word with max weight chosen
    		for (int i = 0; i != -1;) {
    			int startNext = poem.substring(i + 1).indexOf(" ");
    			String currentWord = poem.substring(i, startNext);
    			int endNext = poem.substring(startNext + 1).indexOf(" ");
    			String nextWord = poem.substring(startNext + 1, endNext);
    
    			Map<String, Integer> firstLayer = TEST_GRAPH.getFirstLayer(currentWord);
    			Map<String, Integer> secondLayer = TEST_GRAPH.getSecondLayer(currentWord);
    			
    			assertTrue("expected " + nextWord + " in first or secondLayer " + currentWord, firstLayer.containsKey(nextWord) || secondLayer.containsKey(nextWord));
    			
    			// Test that the appropriate bridge word is chosen. 
    			if (secondLayer.containsKey(nextWord)) {
    				int weight = secondLayer.get(nextWord);
    				Set<String> otherKeys = secondLayer.keySet();
    				otherKeys.remove(nextWord);
    				for (String key : otherKeys) {
    					assertTrue("expected word with max weight to be choses or word with highest alphabetical order ", 
    							weight > secondLayer.get(key) || nextWord.compareTo(key) < 0);
    				}
    			}
    			
    			if (i != -1) i = startNext + 1;
    		}
    	}
    	catch (IOException e) {
    		assertTrue("Encountered exception " + e, false);
    	}
    	
    	
    }
    @Test
    // Test that gaphPoet is insenstive to case of the input
    public void testCaseInsensitive() {
    	try {
    		GraphPoet TEST_GRAPH = new GraphPoet(CORPUS);	
    		
    		final String poem = TEST_GRAPH.poem(TEST);
    		final String poem2 = TEST_GRAPH.poem(CASES);
    		
    		assertTrue(" expected poems to be equal ", poem.equals(poem2));
    	}
    	catch (IOException e) {
    		assertTrue("Encountered exception " + e, false);
    	}
    }
    
    @Test
    // Test when poem is given one word input
    public void testPoemOneWord() {
    	try {
    		GraphPoet TEST_GRAPH = new GraphPoet(CORPUS);	
    		
    		final String poem = TEST_GRAPH.poem(SINGLE);
    		
    		assertEquals(" expected poems to be equal to \"Singleton\" ", poem, SINGLE);
    	}
    	catch (IOException e) {
    		assertTrue("Encountered exception " + e, false);
    	}
    }
    
    @Test
    // Test when poem is given empty string as input
    public void testPoemEmpty() {
    	try {
    		GraphPoet TEST_GRAPH = new GraphPoet(CORPUS);	
    		
    		final String poem = TEST_GRAPH.poem(EMPTY);
    		
    		assertEquals(" expected poem to be empty ", poem, EMPTY);
    	}
    	catch (IOException e) {
    		assertTrue("Encountered exception " + e, false);
    	}
    }
    
}
