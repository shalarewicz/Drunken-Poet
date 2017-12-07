/* Copyright (c) 2015-2017 MIT 6.005/6.031 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package poet;

import static org.junit.Assert.*;

import org.junit.Test;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Scanner;

/**
 * Tests for GraphPoet.
 */
public class GraphPoetTest {
    // These two corpuses take a while to test and therefore are not used. 
	private final File CORPUS = new File("test/poet/shakespeare.txt");
	private final File THRONES_CORPUS = new File("test/poet/gameOfThrones.txt");
	
	private final File SHORT_CORPUS = new File("test/poet/test.txt");
	private final String TEST = "Out of the frying pan and into the fire";
	private final int TEST_LENGTH = 9;
	private final String EMPTY = "";
	private final String CASES = "OUT OF THE FRYING PAN AND INTO THE FIRE";
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
    
    
    private int wordLength(String a, int index, int length) {
    	if (index == -1) {
    		return length;
    	}
    	else {
    		int nextSpace = a.indexOf(" ");
    		return wordLength(a.substring(nextSpace + 1), nextSpace, length + 1);
    	}
    	
    	
    	
    }
    
    private int wordLength(String a) {
    	return wordLength(a, 0, 0);
    }
    
    
    @Test
    // Test to make sure word length of generated poem is between n and (n-1) inclusive
    public void testPoemLength() {
    	try {
    		GraphPoet TEST_GRAPH = new GraphPoet(SHORT_CORPUS);
    		assertEquals("expected length of TEST ", TEST_LENGTH, wordLength(TEST));
    		
    		final String poem = TEST_GRAPH.poem(TEST);
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
    		Scanner in = new Scanner(poem);
    		
    		String previousWord = in.next().toLowerCase();
    		String currentWord = "";
    		
    		while (in.hasNext()) {
    			currentWord = in.next().toLowerCase();
    			try {
    				Map<String, Integer> firstLayer = TEST_GRAPH.getLayer(previousWord);
    				if (!firstLayer.isEmpty()) {
    					assertTrue("expected \"" + currentWord + "\" in first or secondLayer of \"" + previousWord + "\" ", firstLayer.containsKey(currentWord) ||
    							TEST.contains(previousWord + " " + currentWord));
    				}
    				else {
    					assertTrue("expected input to contain phrase \"" + previousWord + " " + currentWord + "\"", TEST.contains(previousWord + " " + currentWord ));
    				}
    				
    			} catch (Exception e) {
    				assertTrue("expected input to contain phrase \"" + previousWord + " " + currentWord + "\"", TEST.contains(previousWord + " " + currentWord));
    			}
    			finally {
    				previousWord = currentWord;
    			}
    		}
    		in.close();
    		
    	}
    	catch (IOException e) {
    		assertTrue("Encountered exception " + e, false);
    	}
    	
    	
    	
    }
    @Test
    // Test that gaphPoet is insenstive to case of the input
    public void testCaseInsensitive() {
    	try {
    		GraphPoet TEST_GRAPH = new GraphPoet(SHORT_CORPUS);	
    		
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
    		GraphPoet TEST_GRAPH = new GraphPoet(SHORT_CORPUS);	
    		
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
    		GraphPoet TEST_GRAPH = new GraphPoet(SHORT_CORPUS);	
    		
    		final String poem = TEST_GRAPH.poem(EMPTY);
    		
    		assertEquals(" expected poem to be empty ", poem, EMPTY);
    	}
    	catch (IOException e) {
    		assertTrue("Encountered exception " + e, false);
    	}
    }
    
   
    // {Source=[Target: weight, Target: Weight, ...], Source=[targets],...} where sources in alphabetical order and targets in descending order by weight
    // GraphPoet - Empty, 1, n
    // 
    // Don't need to test if targets in descending order
    // Test if souces are alphabetical
    @Test
    public void testToStringEmptyGraph() {
    	try {
    		GraphPoet TEST_GRAPH = new GraphPoet(SHORT_CORPUS);	
    		
    		assertTrue(" expected {Source=[Target: weight, Target: Weight, ...], Source=[targets],...} ", TEST_GRAPH.toString().matches("\\{(.*=\\[.*[\\], |\\]])*\\}"));
    	}
    	catch (IOException e) {
    		assertTrue("Encountered exception " + e, false);
    	}
    }
    
    @Test
    public void testToStringOneElement() {
    	try {
    		GraphPoet TEST_GRAPH = new GraphPoet(SHORT_CORPUS);	
    		
    		assertTrue(" expected {Source=[Target: weight, Target: Weight, ...], Source=[targets],...} ", TEST_GRAPH.toString().matches("\\{(.*=\\[.*[\\], |\\]])*\\}"));
    	}
    	catch (IOException e) {
    		assertTrue("Encountered exception " + e, false);
    	}
    }
    
    @Test
    public void testToStringMAny() {
    	try {
    		GraphPoet TEST_GRAPH = new GraphPoet(SHORT_CORPUS);	
    		assertTrue(" expected {Source=[Target: weight, Target: Weight, ...], Source=[targets],...} ", TEST_GRAPH.toString().matches("\\{(.*=\\[.*[\\], |\\]])*\\}"));
    	}
    	catch (IOException e) {
    		assertTrue("Encountered exception " + e, false);
    	}
    }
    
    @Test
    // Tests that toString lists souces in alphabetical order
    public void testToStringSourcesAlphabetical() {
    	try {
    		GraphPoet TEST_GRAPH = new GraphPoet(SHORT_CORPUS);	
    		String test = TEST_GRAPH.toString();
    		
    		Pattern pattern = Pattern.compile(".+=");
    		Matcher matcher = pattern.matcher(test.substring(1));
    		
    		
    		if (matcher.find()) {
    			String previous = matcher.group();
    		
	    		while (matcher.find()) {
	    			String current = matcher.group();
	    			assertTrue("expected alphabetical order", previous.compareTo(current) <= 0);
	    			previous = current;
	    		}
    		}
    	}
    	catch (IOException e) {
    		assertTrue("Encountered exception " + e, false);
    	}
    }
    
    @Test
    // Test that toString list targets in descending order by weight
    public void testToStringTargetsDescending() {
    	try {
    		GraphPoet TEST_GRAPH = new GraphPoet(SHORT_CORPUS);	
    		String test = TEST_GRAPH.toString();
    		
    		Pattern pattern = Pattern.compile("\\[^=\\]");
    		Matcher matcher = pattern.matcher(test.substring(1));
    		
    		
    		while (matcher.find()) {
    			String targetList = matcher.group();
    			Pattern numbers = Pattern.compile(": [0-9]+");
    			Matcher targets = numbers.matcher(targetList);
    			
    			List<Integer> weights = new ArrayList<Integer>();
    			
    			while (targets.find()) {
    				String match = targets.group();
    	    		int number = Integer.parseInt(match.substring(2)); 
    	    		weights.add(number);
    			}
    			System.out.println(targetList);
    			for (int i = 1; i < weights.size(); i++) {
    				System.out.println(weights.get(i - 1));
    				System.out.println(weights.get(i));
    	    		assertTrue("expected weight to be >= previous weight ", weights.get(i - 1) >= weights.get(i));
    	    	}
    			
    		}
    	}
    	catch (IOException e) {
    		assertTrue("Encountered exception " + e, false);
    	}
    }
    
}
