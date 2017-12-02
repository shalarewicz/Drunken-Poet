/* Copyright (c) 2015-2017 MIT 6.005/6.031 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.HashSet;
import java.util.HashMap;

/**
 * An implementation of Graph.
 * 
 * <p>PS2 instructions: you MUST use the provided rep.
 */
public class ConcreteVerticesGraph<L> implements Graph<L> {
    
    private final List<Vertex<L>> vertices = new ArrayList<>();
    
    // Abstraction function:
    //   Each vertex is a node in the Graph.
    // Representation invariant:
    //   No edge has weight <= 0
    //   Every node has a name which is a string of postive length
    // Safety from rep exposure:
    //   TODO
    
    
    /**
     * Constructs an Empty ConcreteVerticesGraph<L>
     */
    public ConcreteVerticesGraph() {}
    
    /**
     * Constructs a new ConcreteVerticesGraph<L> from the given list. Each Item in the list will be 
     * the name of a vertex in the graph
     */
    /**
     * Constructs a new ConcreteVerticesGraph<L> from the given list. Each Item in the list will be 
     * the name of a vertex in the graph
     * 
     * @param strings - The list of strings to be made into vertices
     */
    public ConcreteVerticesGraph(List<L> strings) {
    	for (L s : strings) {
    		vertices.add(new Vertex<L>(s));
    	}
    }
    
    // TODO checkRep
    private void checkRep() {
    	for (Vertex<L> vertex : vertices) {
    		assert vertex.getName() != null;
    		for (Vertex<L> target : vertex.getTargets()) {
    			assert vertex.getWeight(target) > 0;
    		}
    	}
    }
    
    @Override public boolean add(L vertex) {
    	Vertex<L> toAdd = new Vertex<L>(vertex);
    	if (!this.vertices.contains(toAdd)) {
    		this.vertices.add(new Vertex<L>(vertex));
    		checkRep();
    		return true;
    	}
    	else {return false;}
    }
    /**
     * Finds vertex in graph with name s. Requires s to be a name of vertex in the graph otherwise throws exception
     * @param s - Name of vertex to find
     * @return - returns the Vertex with name s
     */
    private Vertex<L> findVertex(L s) {
    	for (Vertex<L> vertex : vertices) {
    		if (vertex.getName().equals(s)) {
    			return vertex;
    		}
    	}
    	throw new RuntimeException("Should never get here. Vertex not in graph");
    }
    @Override public int set(L source, L target, int weight) {
    	Vertex<L> sourceV = findVertex(source);
    	Vertex<L> targetV = findVertex(target);
    	int oldWeight = sourceV.getWeight(targetV);
    	sourceV.setTarget(targetV, weight);
    	targetV.setSource(sourceV, weight);
    	return oldWeight;
    }
    
    @Override public boolean remove(L vertex) {
    	for (int i = 0; i < vertices.size(); i++) {
    		Vertex<L> v = vertices.get(i);
    		if (v.getName().equals(vertex)) {
    			for (Vertex<L> t : v.getTargets()) {
    				v.setTarget(t, 0);
    				t.setSource(v, 0);
    			}
    			for (Vertex<L> s: v.getSources()) {
    				v.setSource(s, 0);
    				s.setTarget(v, 0);
    			}
    			this.vertices.remove(v);
    			checkRep();
    			return true;
    		}
    	}
    	return false;
    }
    
    @Override public Set<L> vertices() {
    	Set<L> result = new HashSet<L>();
    	for (Vertex<L> v: vertices) {
    		result.add(v.getName());
    	}
    	return result;
    }
    
    @Override public Map<L, Integer> sources(L target) {
    	Vertex<L> targetV = findVertex(target);
    	Map<L, Integer> result = new HashMap<L, Integer>();
    	for (Vertex<L> source : targetV.getSources()) {
    		result.put(source.getName(), targetV.getWeightFromSource(source));
    	}
    	return result;

    }
    
    @Override public Map<L, Integer> targets(L source) {
    	Vertex<L> sourceV = findVertex(source);
    	Map<L, Integer> result = new HashMap<L, Integer>();
    	for (Vertex<L> target : sourceV.getTargets()) {
    		result.put(target.getName(), sourceV.getWeight(target));
    	}
    	return result;
    }
    
    // TODO toString()
    @Override
    public String toString() {
    	// {source=[target: weight, target: weight.....], source=[target: weight, target: weight.....],...}
    	
    	StringBuilder ans = new StringBuilder();
    	ans.append("{");
    	
    	int i = 0;
    	for (Vertex<L> vertex : vertices) {
    		ans.append(vertex);
    		if (i < vertices.size() - 1) {
    			ans.append(", ");
    		}
    	}
        	
    	System.out.println(ans);
    	return ans.toString() + "}";
    }
    		
}

/**
 * Mutable.
 * This class is internal to the rep of ConcreteVerticesGraph<L>.
 * 
 * <p> Vertex is a mutable structure. Each vertex has a name as well as 0 or more targets which are
 * connected to this vertex by and edge with a weight greater than zero. 
 */
class Vertex<L> {
    
	private Map<Vertex<L>, Integer> sources = new HashMap<Vertex<L>, Integer>();
    private Map<Vertex<L>, Integer> targets = new HashMap<Vertex<L>, Integer>();
    private L name;
    
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
    public Vertex(L name) {
    	this.name = name;
    }

    
    private void checkRep() {
    	assert name != null;
    	for (Vertex<L> target : targets.keySet()) {
    		assert targets.get(target) != 0;
    	}
    	for (Vertex<L> source : sources.keySet()) {
    		assert sources.get(source) != 0;
    	}
    }
    
    //Setters
    /**
     * Changes the name of the current vertex to name
     * @param name - The new name of vertex
     */
    public void setName(L name) {
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
    public int setTarget(Vertex<L> target, int weight) {
    	int oldWeight = getWeight(target);
    	if (weight > 0) {
    		targets.put(target, weight);
    	} else {
    		targets.remove(target);
    	}
    	checkRep();
    	return oldWeight;  		
    }
    
    /**
     * If weight > 0, adds an edge in the graph between this vertex and the source with weight weight. If the vertex already has this source
     * the weight is updated. If this edge did not exist the edge is added. If weight = 0, the edge is removed.
     * @param name - Name of the source
     * @param weight - Weight of edge from this vertex to source. Requires weight >= 0;
     * @return - Weight of the previous edge between the two vertices or 0 if no such edge existed.
     */
    public int setSource(Vertex<L> source, int weight) {
    	int oldWeight = getWeightFromSource(source);
    	if (weight > 0) {
    		
    		sources.put(source, weight);
    	} else {
    		sources.remove(source);
    	}
    	checkRep();
    	return oldWeight;  		
    }
    
    //Getters
    
    /**
     * 
     * @return Returns the name of the vertex
     */
    public L getName() {
    	return this.name;
    }
    /**
     * Return the weight of the edge between the this vertex and one of it's target vertices. If no edge exists between 
     * these vertices 0 is returned
     * @param target - Target vertex of the edge
     * @return - Weight of the edge or 0 if edge does not exist
     */
    public int getWeight(Vertex<L> target) {
    	return this.targets.getOrDefault(target, 0);
    }
    
    /**
     * Return the weight of the edge between the this vertex and one of it's source vertices. If no edge exists between 
     * these vertices 0 is returned
     * @param source - Source vertex of the edge
     * @return - Weight of the edge or 0 if edge does not exist
     */
    public int getWeightFromSource(Vertex<L> source) {
    	return this.sources.getOrDefault(source, 0);
    }
    
    /**
     * 
     * @return Returns a set of targets of this vertex. 
     */
    public Set<Vertex<L>> getTargets(){
    	return this.targets.keySet();
    }
    
    /**
     * 
     * @return Returns a set of sources of this vertex. 
     */
    public Set<Vertex<L>> getSources(){
    	return this.sources.keySet();
    }
    
    @Override
    public boolean equals(Object that) {
    	return that instanceof Vertex<?> && this.namesEqual((Vertex<L>) that);
    }
    
    /**
     * Compares to vertices for equality by name
     * @param that Vertex we're comparting to
     * @return True if names are equal
     */
    private boolean namesEqual(Vertex<L> that) {
		return this.getName().equals(that.getName());

    }
    
    private Vertex<L> findVertex(L s) {
    	for (Vertex<L> v : targets.keySet()) {
    		if (v.getName().equals(s)) {
    			return v;
    		}
    	}
    	return null; //This is less than ideal
    }
    @Override
    public String toString() {
    	// Source=[target: weight, target, weight]
    	StringBuilder result = new StringBuilder();
    	result.append(this.name + "=[");
    	
    	int j = 0;
    	for (Vertex<L> target : targets.keySet()) {
    		result.append(target.getName() + ": " + targets.get(target));
    		if (j < targets.size() - 1) {
    			result.append(", ");
    		}
    		j++;
		}
    	System.out.println(result);
    	return result.toString() + "]";
    }
    
    
    // TODO toString()
 
    
}

	
