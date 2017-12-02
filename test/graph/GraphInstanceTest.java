/* Copyright (c) 2015-2017 MIT 6.005/6.031 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package graph;

import static org.junit.Assert.*;

import java.util.Collections;
import java.util.Set;
import java.util.HashSet;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

/**
 * Tests for instance methods of Graph.
 * 
 * <p>PS2 instructions: you MUST NOT add constructors, fields, or non-@Test
 * methods to this class, or change the spec of {@link #emptyInstance()}.
 * Your tests MUST only obtain Graph instances by calling emptyInstance().
 * Your tests MUST NOT refer to specific concrete implementations.
 */
public abstract class GraphInstanceTest {
    
    // Testing strategy
    //   TODO
	//	 add 
	//  	Graph has size 0, 1, n checked
	//  	Graph contains a vertex with same label checked
	//			graph has targets, sources checked
	// 		Graph does not contain vertex with same label checkec
	//   remove 
	// 		Graph size = 0, 1, n
	//  	Removed vertex has 0, n targets or sources
	// 		TODO removed edges from source to removed vertex in implementation
	//   set
	//  	weight = 0 , n checked
	//  	Graph contains source, target
	// 			weight updated CHECKED 
	// 			weight not modified CHECKED
	//   vertices
	// 		size = 0, n checked
	//  		If iteration used to test/implement this do I have to implement Iterator or 
	//			is it inherited
	//   targets
	// 		source has 0, n targets 
	//   sources
	//		target has 0, n sources
    
    /**
     * Overridden by implementation-specific test classes.
     * 
     * @return a new empty graph of the particular implementation being tested
     */
    public abstract Graph<String> emptyInstance();
    
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }
    
    @Test
    public void testInitialVerticesEmpty() {
        // TODO you may use, change, or remove this test
        assertEquals("expected new graph to have no vertices",
                Collections.emptySet(), emptyInstance().vertices());
    }
    
    // TODO other tests for instance methods of Graph
    
    private static final String VERTEX1 = "Test1";
    private static final String VERTEX2 = "Test2";
    private static final String VERTEX3 = "Test3";
    private static final String VERTEX4 = "Test4";
    private final int WEIGHT1 = 1;
    private final int WEIGHT5 = 5;
    
    //TODO: Test edge with weight 0;
    private final int WEIGHT0 = 0;
    
    
    
    private Graph<String> makeGraph(){
    	Graph<String> test = emptyInstance();
    	test.add(VERTEX1);
    	test.add(VERTEX2);
    	test.add(VERTEX3);
    	test.add(VERTEX4);
    	test.set(VERTEX1, VERTEX2, WEIGHT1);  // VERTEX1 --1--> VERTEX2
    	test.set(VERTEX2, VERTEX3, WEIGHT5);  // VERTEX2 --5--> VERTEX3
    	//test.set(VERTEX3, VERTEX1, WEIGHT0);  // No edge created
    	
    	return test;
    }
    
    @Test
    // Tests cases when size = 0, 1, n
    // Graph doesn't contain vertex;
    // Tests to make sure edges not modified;
    // Tests set size 0, n 
    public void testAdd() {
    	Graph<String> test = makeGraph();
    	Set<String> expected = new HashSet<String>();
    	expected.add(VERTEX1);
    	expected.add(VERTEX2);
    	expected.add(VERTEX3);
    	expected.add(VERTEX4);
    	assertEquals("expected graph to have 4 vertices", expected, test.vertices()); 
    	assertEquals("expected set to have 4 elements", expected.size(), test.vertices().size());
    	
    }
    
    @Test
    //Tests when a duplicate vertex is added to the graph
    // tests sources and targets with n sources / targets
    public void testAddDuplicate() {
    	Graph<String> test = makeGraph();
    	assertFalse("expected dup vertex to not be added", test.add(VERTEX2));
    	
    	// Make sure edges weren't changed.
    	Map<String, Integer> expectedSources = new HashMap<String, Integer>();
    	expectedSources.put(VERTEX1, WEIGHT1);
    	assert expectedSources.equals(test.sources(VERTEX2));
    	
    	Map<String, Integer> expectedTargets = new HashMap<String, Integer>();
    	expectedTargets.put(VERTEX3, WEIGHT5);
    	assert expectedTargets.equals(test.targets(VERTEX2));
    }
    
    @Test
    // Tests sources when target has no sources
    public void testSourcesNoSources() {
    	Graph<String> test = makeGraph();
    	assertEquals("expected empty map", Collections.EMPTY_MAP, test.sources(VERTEX4));
    }
    
    @Test
    // Tests sources when target has n sources
    public void testSourcesNSources() {
    	Graph<String> test = makeGraph();
    	test.set(VERTEX4, VERTEX3, WEIGHT1);
    	
    	Map<String, Integer> expected = new HashMap<String, Integer>();
    	expected.put(VERTEX2, WEIGHT5);
    	expected.put(VERTEX4, WEIGHT1);
    	
    	assertEquals("expected map", expected, test.sources(VERTEX3));
    	assertEquals("expected map to have size ", expected.size(), test.sources(VERTEX3).size());
    }
    
    @Test
    // tests targets when source has no targets
    public void testTargetsNoTargets() {
    	Graph<String> test = makeGraph();
    	assertEquals("expected empty map", Collections.EMPTY_MAP, test.targets(VERTEX4));
    }
    
    @Test
    // tests targets when source has n targets
    public void testTargetsNTargets() {
    	Graph<String> test = makeGraph();
    	test.set(VERTEX2, VERTEX4, WEIGHT1);
    	
    	Map<String, Integer> expected = new HashMap<String, Integer>();
    	expected.put(VERTEX3, WEIGHT5);
    	expected.put(VERTEX4, WEIGHT1);
    	
    	assertEquals("expected map", expected, test.targets(VERTEX2));
    	assertEquals("expected map to have size ", expected.size(), test.targets(VERTEX2).size());
    }
    
    @Test
    // Test set when weight is updated
    public void testSetUpdateWeight() {
    	Graph<String> test = makeGraph();
    	test.set(VERTEX1, VERTEX2, WEIGHT5);
    	
    	Map<String, Integer> expectedTargets = new HashMap<String, Integer>();
    	expectedTargets.put(VERTEX1, WEIGHT5);
    	assert expectedTargets.equals(test.sources(VERTEX2));
    	
    }
    
    @Test
    // Tests set when weight is zero. Edges with weight 0 violate pre-condition od Edge class
    public void testSetWeightZero() {
    	Graph<String> test = makeGraph();
    	test.set(VERTEX2, VERTEX3, WEIGHT0);
    	assertEquals("expected empty map", Collections.EMPTY_MAP, test.sources(VERTEX3));
    }
    
    @Test 
    // Test vertices when graph empty
    public void testVerticesEmpty() {
    	assertEquals("expected no vertices in empty graphy", Collections.EMPTY_SET, emptyInstance().vertices());
    }
    
    @Test
    public void testVerticesSizeOne() {
    	Graph<String> test = emptyInstance();
    	test.add(VERTEX1);
    	Set<String> expected = new HashSet<String>();
    	expected.add(VERTEX1);
    	assertEquals("expected one vertex", expected, test.vertices());
    	assertEquals("expected set to have 1 element", expected.size(), test.vertices().size());
    }
    
    @Test
    //Test remove when size 1 and vertex not part of an edge. 
    public void testRemoveOne() {
    	Graph<String> test = emptyInstance();
    	assert test.add(VERTEX1);
    	assertTrue("expected vertex to not be removed", !test.remove(VERTEX2));
    	assertEquals("expected set to contain one element", Collections.singleton(VERTEX1), test.vertices());
    	assertTrue("expected vertex to be removed", test.remove(VERTEX1));
    	assertEquals("expected graph to contain no vertices", Collections.EMPTY_SET, test.vertices());
    }
    
    @Test
    //Test remove when size n and vertex removed was part of an edge. 
    public void testRemove(){
    	Graph<String> test = makeGraph();
    	test.remove(VERTEX2);
    	
    	assertEquals("expected VERTEX1 to have no targets ", Collections.EMPTY_MAP, test.targets(VERTEX1));
    	assertEquals("expected VERTEX3 to have no sources ", Collections.EMPTY_MAP, test.sources(VERTEX3));
    	
    	Set<String> expected = new HashSet<String>();
    	expected.add(VERTEX1);
    	expected.add(VERTEX3);
    	expected.add(VERTEX4);
    	assertEquals("expected graph to have 3 vertices", expected, test.vertices()); 
    	assertEquals("expected set to have 3 elements", expected.size(), test.vertices().size());
    	
    }
    
}
