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
 * Tests for ConcreteVerticesGraph.
 * 
 * This class runs the GraphInstanceTest tests against ConcreteVerticesGraph, as
 * well as tests for that particular implementation.
 * 
 * Tests against the Graph spec should be in GraphInstanceTest.
 */
public class ConcreteVerticesGraphTest extends GraphInstanceTest {
    
    /*
     * Provide a ConcreteVerticesGraph for tests in GraphInstanceTest.
     */
    @Override public Graph<String> emptyInstance() {
        return new ConcreteVerticesGraph();
    }
    
    /*
     * Testing ConcreteVerticesGraph...
     */
    
    // Testing strategy for ConcreteVerticesGraph.toString()
    //   TODO
    
    // TODO tests for ConcreteVerticesGraph.toString()
    
    /*
     * Testing Vertex...
     */
    
    // Testing strategy for Vertex
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
    
    private final Vertex V1 = new Vertex("Test1");
    private final Vertex V2 = new Vertex("Test2");
    private final Vertex V3 = new Vertex("Test3");
    private final Vertex V4 = new Vertex("Test4");
    private final int WEIGHT5 = 5;
    private final int WEIGHT4 = 4;
    private final int WEIGHT0 = 0;
    
    // TODO tests for operations of Vertex
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
    	Set<Vertex> expected = new HashSet<Vertex>(Arrays.asList(V2, V3));
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
    	String expected = "Test2=[]";
    	assertEquals("expected string ", expected, V1.toString());
    }
    
    @Test
    // tests toString when 1 target
    public void testToStringOneTarget() {
    	V1.setTarget(V4, WEIGHT5);
    	String expected = "Test2=[Test4: 5]";
    	assertEquals("expected string ", expected, V1.toString());
    }
    
    @Test
    // tests toString when n targets
    public void testToStringManyTargets() {
    	V1.setTarget(V4, WEIGHT5);
    	V1.setTarget(V3, WEIGHT4);
    	String expected = "Test2=[Test3: 4, Test4: 5]";
    	assertEquals("expected string ", expected, V1.toString());
    }
    
}
