/* Copyright (c) 2015-2017 MIT 6.005/6.031 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package graph;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.HashMap;

/**
 * An implementation of Graph.
 * 
 * <p>PS2 instructions: you MUST use the provided rep.
 */
public class ConcreteVerticesGraph implements Graph<String> {
    
    private final List<Vertex> vertices = new ArrayList<>();
    
    // Abstraction function:
    //   Each vertex is a node in the Graph.
    // Representation invariant:
    //   TODO
    // Safety from rep exposure:
    //   TODO
    
    // TODO constructor
    
    // TODO checkRep
    
    @Override public boolean add(String vertex) {
        throw new RuntimeException("not implemented");
    }
    
    @Override public int set(String source, String target, int weight) {
        throw new RuntimeException("not implemented");
    }
    
    @Override public boolean remove(String vertex) {
        throw new RuntimeException("not implemented");
    }
    
    @Override public Set<String> vertices() {
        throw new RuntimeException("not implemented");
    }
    
    @Override public Map<String, Integer> sources(String target) {
        throw new RuntimeException("not implemented");
    }
    
    @Override public Map<String, Integer> targets(String source) {
        throw new RuntimeException("not implemented");
    }
    
    // TODO toString()
    
}

/**
 * Mutable.
 * This class is internal to the rep of ConcreteVerticesGraph.
 * 
 * <p> Vertex is a mutable structure. Each vertex has a name as well as 0 or more targets which are
 * connected to this vertex by and edge with a weight greater than zero. 
 */
class Vertex {
    
    private Map<Vertex, Integer> targets = new HashMap<Vertex, Integer>();
    private String name;
    
    // Abstraction function:
    //   Vertex consists of a name and a map mapping each target where vertex is the source to the weight of the edge between 
    // 	 source and vertex. 
    // Representation invariant:
    //   Weight not equal to zero
    // 	 Name not null
    // Safety from rep exposure:
    //   TODO
    
    
    /**
     * Constructor for nested class Vertex
     * @param name - Creates vertex with name - name and now targets
     */
    public Vertex(String name) {
    	this.name = name;
    }

    
    private void checkRep() {
    	assert name != null;
    	for (Vertex target : targets.keySet()) {
    		assert targets.get(target) != 0;
    	}
    }
    
    //Setters
    /**
     * Changes the name of the current vertex to name
     * @param name - The new name of vertex
     */
    public void setName(String name) {
    	this.name = name;
    	checkRep();
    }
    
    /**
     * If weight > 0, adds an edge in the graph between this vertex and the target with weight weight. If the vertex already has this target
     * the weight is updated. If this edge did not exist the edge is added. If weight = 0, the edge is removed.
     * @param name - Name of the target
     * @param weight - Weight of edge from this vertex to target. Requires weight >= 0;
     * @return - Weight of the previous edge between the two vertices or 0 if no such edge existed.
     */
    public int setTarget(Vertex target, int weight) {
    	int oldWeight = getWeight(target);
    	if (weight > 0) {
    		targets.put(target, weight);
    	} else {
    		targets.remove(target);
    	}
    	checkRep();
    	return oldWeight;  		
    }
    
    //Getters
    
    /**
     * 
     * @return Returns the name of the vertex
     */
    public String getName() {
    	return this.name;
    }
    /**
     * Return the weight of the edge between the this vertex and one of it's target vertices. If no edge exists between 
     * these vertices 0 is returned
     * @param target - Target vertex of the edge
     * @return - Weight of the edge or 0 if edge does not exist
     */
    public int getWeight(Vertex target) {
    	return this.targets.getOrDefault(target, 0);
    }
    
    /**
     * 
     * @return Returns a set of targets of this vertex. 
     */
    public Set<Vertex> getTargets(){
    	return this.targets.keySet();
    }
    
    @Override
    public String toString() {
    	// Source=[target: weight, target, weight]
    	StringBuilder result = new StringBuilder();
    	
    	return result.toString();
    }
    
    
    // TODO toString()
    
}

	
