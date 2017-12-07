/* Copyright (c) 2015-2017 MIT 6.005/6.031 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package graph;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Tests for ConcreteEdgesGraph<String>.
 * 
 * This class runs the GraphInstanceTest tests against ConcreteEdgesGraph<String>, as
 * well as tests for that particular implementation.
 * 
 * Tests against the Graph spec should be in GraphInstanceTest.
 */
public class ConcreteEdgesGraphTest extends GraphInstanceTest {
    
    /*
     * Provide a ConcreteEdgesGraph<String> for tests in GraphInstanceTest.
     */
    @Override public Graph<String> emptyInstance() {
        return new ConcreteEdgesGraph<String>();
    }
    
    /*
     * Testing ConcreteEdgesGraph<String>...
     */
    
    // Testing strategy for ConcreteEdgesGraph<String>.toString()
    //   Graph size 0, 1, n
    //   Source has 0, 1, n targets
    
    private static final String VERTEX1 = "Test1";
    private static final String VERTEX2 = "Test2";
    private static final String VERTEX3 = "Test3";
    private static final String VERTEX4 = "Test4";
    private final int WEIGHT1 = 1;
    private final int WEIGHT3 = 3;
    private final int WEIGHT5 = 5;

    @Test
    public void testToStringEmptyGraph() {
    	ConcreteEdgesGraph<String> test = new ConcreteEdgesGraph<String>();
    	final String expected = "{}";
    	assertEquals("expected string ", expected, test.toString());
    }
    
    @Test
    public void testToStringSingleVertex() {
    	ConcreteEdgesGraph<String> test = new ConcreteEdgesGraph<String>();
    	test.add(VERTEX1);
    	final String expected = "{Test1=[]}";
    	assertEquals("expected string ", expected, test.toString());
    }
    @Test
    public void testToStringNVertices() {
    	ConcreteEdgesGraph<String> test = new ConcreteEdgesGraph<String>();
    	test.add(VERTEX1);
    	test.add(VERTEX2);
    	test.add(VERTEX3);
    	test.add(VERTEX4);
    	test.set(VERTEX1, VERTEX2, WEIGHT1);  // VERTEX1 --1--> VERTEX2
    	test.set(VERTEX2, VERTEX3, WEIGHT5);  // VERTEX2 --5--> VERTEX3
    	test.set(VERTEX2, VERTEX4, WEIGHT3);  // VERTEX2 --3--> VERTEX4
    	
    	final String expected = "{Test1=[Test2: 1], Test2=[Test3: 5, Test4: 3], Test3=[], Test4=[]}";
    	assertEquals("expected string ", expected, test.toString());
    }
    /*
     * Testing Edge...
     */
    
    // Testing strategy for Edge
    //   Test for rep exposure of source, target, weight fields
    //   Test weight <> 0 throws AssertionError
    
    private final Edge<String> TEST = new Edge<String>(VERTEX1, VERTEX2, WEIGHT5);
    
    @Test
    public void testImmutableEdgeSource() {
    	String source = TEST.source();
    	source = source + "Mutable";
    	assertEquals("expected source to be  immutable ", VERTEX1, TEST.source());
    }
    
    @Test
    public void testImmutableEdgeTarget() {
    	String target = TEST.target();
    	target = target + "Mutable";
    	assertEquals("expected target to be  immutable ", VERTEX2, TEST.target());
    }
    @Test
    public void testImmutableEdgeWeight() {
    	int weight = TEST.weight();
    	weight = weight + 1;
    	assertEquals("expected weight to be  immutable ", WEIGHT5, TEST.weight());	
    	
    }
    
    @Test 
    public void testEdgeToString() {
    	String expected = "(" + TEST.source() + "-->" + TEST.target() + ": " + TEST.weight() + ")";
    	assertEquals("expedted string", expected, TEST.toString());
    }
}
