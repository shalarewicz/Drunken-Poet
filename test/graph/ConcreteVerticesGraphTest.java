/* Copyright (c) 2015-2017 MIT 6.005/6.031 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package graph;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.List;
import java.util.ArrayList;

import org.junit.Test;

/**
 * Tests for ConcreteVerticesGraph<String>.
 * 
 * This class runs the GraphInstanceTest tests against ConcreteVerticesGraph<String>, as
 * well as tests for that particular implementation.
 * 
 * Tests against the Graph spec should be in GraphInstanceTest.
 */
public class ConcreteVerticesGraphTest extends GraphInstanceTest {
    
    /*
     * Provide a ConcreteVerticesGraph<String> for tests in GraphInstanceTest.
     */
    @Override public Graph<String> emptyInstance() {
        return new ConcreteVerticesGraph<String>();
    }
    
    /*
     * Testing ConcreteVerticesGraph<String>...
     */
    
    // Testing strategy for ConcreteVerticesGraph<String>.toString()
    //  Don't need to test vertex since that has it's owen tests. Can assume it's right
    //  Empty Graph, Size one, Sinze n
    /// Use RegEx
    
    @Test
    //Tests empty graph
    public void testGraphToStringEmpty() {
    	assertEquals("expected \"{}\" ", "{}", emptyInstance().toString());
    }
    
    @Test
    // Tests graph with one vertex
    public void testGraphOne() {
    	Graph<String> test = emptyInstance();
    	test.add("Test1");
    	assertEquals("expected \"{Test1=[]}\" ", "{Test1=[]}", test.toString());
    }
    
    @Test
    // Tests graph with n vertices and n edges using regual expression
    public void testGraphMany() {
    	Graph<String> test = emptyInstance();
    	test.set("Test1", "Test2", 100);
    	test.set("Test1", "Test3", 2);
    	test.set("Test2", "Test4", 5);
    	test.set("Test3", "Test2", 76);
    	test.set("Test1", "Test4", 100);
    	assertTrue("expected 5", test.toString().matches("\\{(.*=\\[.*[\\], |\\]])*\\}"));
    }
    /*
     * Testing Vertex<String>...
     */
    
    // Testing strategy for Vertex<String>
   /*
    * setName - current name has length 0 and max length?
    * setTarget - vertex has 0, n targets
    * 			- Weight is changed, not changed. 
    * getName - current name has length 0 and max length?
    * getTargets - vertex has 0, 1, n targets
    * toString - vertex has 0, 1, n targets
    * getWeight - Weight 0, n
    */
    
    
    // Test Vertices
    
    private final Vertex<String> V1 = new Vertex<String>("Test1");
    private final Vertex<String> V2 = new Vertex<String>("Test2");
    private final Vertex<String> V3 = new Vertex<String>("Test3");
    private final Vertex<String> V4 = new Vertex<String>("Test4");
    private final int WEIGHT5 = 5;
    private final int WEIGHT75 = 75;
    private final int WEIGHT4 = 4;
    private final int WEIGHT0 = 0;
    private final int WEIGHT1 = 1;
    
    @Test
    // test setName
    public void testSetName() {
    	String expected = "Apple 84 &";
    	V1.setName(expected);
    	assertEquals("expected vertex with name ", expected, V1.getName());
    }
    
    @Test
    // tests setTarget when 0, n existing targets
    // tests getTargets when 1, n targets
    public void testSetNewTarget() {
    	assertEquals("expected weight ", WEIGHT0, V1.setTarget(V2, WEIGHT5));
    	assertEquals("expected to have targets ", Collections.singleton(V2), V1.getTargets());
    	V1.setTarget(V3, WEIGHT4);
    	Set<Vertex<String>> expected = new HashSet<Vertex<String>>(Arrays.asList(V2, V3));
    	assertEquals("expected to have targets ", expected, V1.getTargets());
    }
    @Test
    // test setTarget when weight is changed
    // tests getWeight when weight = 0, n
    public void testSetTargetChangeWeight() {
    	assertEquals("expected weight ", WEIGHT0, V1.getWeight(V2));
    	assertEquals("expected weight ", WEIGHT0, V1.setTarget(V2, WEIGHT5));
    	assertEquals("expected weight ", WEIGHT5, V1.setTarget(V2, WEIGHT4));
    	assertEquals("expected weight ", WEIGHT4, V1.getWeight(V2));
    }
    
    @Test
    // Tests setTarget when edge to be created has weight 0;
    // Tests getTargets when no targets exist
    public void testSetTargetWeightZero() {
    	V1.setTarget(V4, WEIGHT5);
    	V1.setTarget(V4, WEIGHT0);
    	assertEquals("expected to have no targets", Collections.EMPTY_SET, V1.getTargets());
    }
    
    @Test
    // Tests getName
    public void testgetName() {
    	assertEquals("expected name ", "Test1", V1.getName());
    }
    
    @Test
    // Tests Vertex.toString when the vertex has zero targets
    public void testToStringNoTargers() {
    	assertTrue("expected string ", V1.toString().matches(".+=\\[(.+: [0-9]+[\\], .+: [0-9]+]*)*\\]"));
    }
    
    @Test
    // tests toString when 1 target
    public void testToStringOneTarget() {
    	V1.setTarget(V4, WEIGHT5);
    	System.out.println(V1.toString());
    	assertTrue("expected string ", V1.toString().matches(".+=\\[(.+: [0-9]+[\\], .+: [0-9]+]*)*\\]"));
    }
    
    @Test
    // Tests Vertex.toString  using regex
    public void testVertexToStringManyTargets() {
    	V1.setTarget(V4, WEIGHT5);
    	V1.setTarget(V3, WEIGHT4);
    	V1.setTarget(V2, WEIGHT5);
    	V1.setTarget(V1, WEIGHT4);
    	System.out.println(V1.toString());
    	assertTrue("expected V1 to be of pattern Source=[target: weight, target, weight]", V1.toString().matches(
    			".+=\\[(.+: [0-9]+[\\], .+: [0-9]+]*)*\\]"));
    }
    
    @Test
    public void testVertexToStringDescendingWeight() {
    	V1.setTarget(V4, WEIGHT75);
    	V1.setTarget(V3, WEIGHT4);
    	V1.setTarget(V2, WEIGHT5);
    	V1.setTarget(V1, WEIGHT1);
    	
    	String test = V1.toString();
    	Pattern pattern = Pattern.compile(": [0-9]+");
    	Matcher matcher = pattern.matcher(test);
    	
    	List<Integer> weights = new ArrayList<Integer>();
    	
    	while (matcher.find()) {
    		String match = matcher.group();
    		int number = Integer.parseInt(match.substring(2)); 
    		weights.add(number);
    	}
    	
    	for (int i=1; i < weights.size(); i++) {
    		assertTrue("expected weight to be >= previous weight ", weights.get(i - 1) >= weights.get(i));
    	}
    }
    
}
