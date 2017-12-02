/* Copyright (c) 2015-2017 MIT 6.005/6.031 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package graph;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

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
    //   TODO
    
    // TODO tests for ConcreteVerticesGraph<String>.toString()
    
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
    private final int WEIGHT4 = 4;
    private final int WEIGHT0 = 0;
    
    // TODO tests for operations of Vertex<String>
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
    // tests toString when no targets
    public void testToStringNoTargers() {
    	String expected = "Test1=[]";
    	assertEquals("expected string ", expected, V1.toString());
    }
    
    @Test
    // tests toString when 1 target
    public void testToStringOneTarget() {
    	V1.setTarget(V4, WEIGHT5);
    	String expected = "Test1=[Test4: 5]";
    	assertEquals("expected string ", expected, V1.toString());
    }
    
    @Test
    // tests toString when n targets
    // TODO: Correct Test to check for contents and not match  against a specific String
    public void testToStringManyTargets() {
    	V1.setTarget(V4, WEIGHT5);
    	V1.setTarget(V3, WEIGHT4);
    	String expected = "Test1=[Test4: 5, Test3: 4]";
    	assertEquals("expected string ", expected, V1.toString());
    }
    
}
