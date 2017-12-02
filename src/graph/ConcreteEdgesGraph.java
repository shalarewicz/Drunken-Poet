/* Copyright (c) 2015-2017 MIT 6.005/6.031 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package graph;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.TreeSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.HashMap;
import java.util.Arrays;

/**
 * An implementation of Graph.
 * 
 * <p>PS2 instructions: you MUST use the provided rep.
 */
public class ConcreteEdgesGraph<L extends Comparable<L>> implements Graph<L> {
    
    private final Set<L> vertices = new HashSet<>();
    private final List<Edge<L>> edges = new ArrayList<>();
    
    // Abstraction function:
    // 		The Graph consists of all the vertices in vertices connected by the edges listed in edges
    // Representation invariant:
    // 		Vertices contains no duplicates
    // 	 	All Edges have source and target. Can be checked in the edge class
    // 	 	All Edges have weight > 0. This is checked in the edge class. 
    // Safety from rep exposure:
    //   add, set, remove do not return references to the current rep. 
    //   set returns the previous weight if an edge is changed and therefore does not expose the rep
    //	 Vertices - vertices field is final
    // 	 Sources - returns a copy of each vertex as the key, weight
    // 	 Targets - returns a copy of each target as the key, weight
    
    
    public ConcreteEdgesGraph() {	
    }
    
    public ConcreteEdgesGraph(Set<L> v, List<Edge<L>> e) {
    	this.vertices.addAll(v);
    	this.edges.addAll(e);
    }
    public ConcreteEdgesGraph(Set<L> v) {
    	this.vertices.addAll(v);
    }
    public ConcreteEdgesGraph(List<Edge<L>> e) {
    	this.edges.addAll(e);
    }
    
    private void checkRep() {
    	for (Edge<L> edge : edges) {
    		assert vertices.contains(edge.source());
    		assert vertices.contains(edge.target());
    	}
    		
    	
    }
    
    @Override public boolean add(L vertex) {
        return vertices.add(vertex);
    }
    
    @Override public int set(L source, L target, int weight) {
    	if (!vertices.contains(source) || !vertices.contains(target)) {throw new RuntimeException("Source or Target not contained in graph");}
    	int result = 0;
    	List<Edge<L>> toRemove = new ArrayList<Edge<L>>();
    	for (Edge<L> edge : edges) {
    		if (edge.source() == source && edge.target() == target) {
    			result = edge.weight();
    			toRemove.add(edge);
    			break;
    		}
    	}
    	edges.removeAll(toRemove);
    	if (weight > 0) {
    		Edge<L> toSet = new Edge<L>(source, target, weight);
    		edges.add(toSet);   		
    	}
    	checkRep();
    	return result;
    }
    
    @Override public boolean remove(L vertex) {
    	List<Edge<L>> toRemove = new ArrayList<Edge<L>>();
    	for (Edge<L> edge : edges) {
    		if (edge.source() == vertex || edge.target() == vertex) {
    			toRemove.add(edge);
    		}
    	}
    	edges.removeAll(toRemove);
    	checkRep();
    	return vertices.remove(vertex);
    }
    
    @Override public Set<L> vertices() {
    	// Copy to prevent rep exposure but is it necessary?
    	// Vertices are supposes to be immutable
    	Set<L> answer = new HashSet<L>();
    	answer.addAll(vertices);
    	return answer;
    }
    
    @Override public Map<L, Integer> sources(L target) {
    	Map<L, Integer> result = new HashMap<L, Integer>();
    	for (Edge<L> edge : edges) {
    		if (edge.target() == target) {
    			result.put(edge.source(), edge.weight()); 
    		}
    	}
    	
    	return result;
    }
    
    @Override public Map<L, Integer> targets(L source) {
    	Map<L, Integer> result = new HashMap<L, Integer>();
    	for (Edge<L> edge : edges) {
    		if (edge.source() == source) {
    			result.put(edge.target(), edge.weight());
    		}
    	}
    	return result;
    	}
    
    /**
     * Returns the weight of the edge between source and target. 
     * @param source - Source of the edge to be found
     * @param target - Target of the edge to be found
     * @return - weight of the edge from source to target. Returns 0 if not found. 
     */
    private int getWeight(L source, L target) {
    	for (Edge<L> edge : edges) {
    		if (source == edge.source() && target == edge.target()) {
    			return edge.weight();
    		}
    	}
    	return 0;
    }
    
    /**
     * Returns a string representation of this graph. The string representation consists of a list of binding where each vertex
     * is bound to a list of target vertices and the weight of the edge connecting the source and target vertex. Each target, weight
     * pair is represented as (target: weight) and each pair is separated by ", " (comma space). Each source is bound to it's list of
     * target by "=" and the list of targets is enclosed by []. Each source, target list pair is separated by ", " (comma space). The 
     * entire graph is enclosed by braces "{}" Targets and sources are sorted lexicographically. 
     * @return - A string representation of this graph
     */
    @Override public String toString() {
    	// {source=[target: weight, target: weight.....], source=[target: weight, target: weight.....],...}
    	
    	StringBuilder ans = new StringBuilder();
    	ans.append("{");
    	
    	
    	SortedSet<L> sortedVertices = new TreeSet<L>(vertices);

    	int i = 0;
    	for (L vertex : sortedVertices) {
    		ans.append(vertex + "=[");
    		Map<L, Integer> targets = targets(vertex);
    		
    		int j = 0;
    		for (L target : new TreeSet<L>(targets.keySet())){
    			ans.append(target + ": " + targets.get(target));
    			j++;
    			if (j < targets.size()) {
    				ans.append(", ");
    			}
    		}
    		ans.append("]");
    		
    		if (i < sortedVertices.size() - 1) {
    			ans.append(", ");
			}
    		i++;
    	}
    	System.out.println(ans);
    	return ans.toString() + "}";
    	
    }
    
}

/**
 * Edge represent an immutable type used in implementing a graph. 
 * Each edge goes from a source vertex to a target vertex in the graph and has an integer weight unequal to 0
 * 
 * Immutable.
 * This class is internal to the rep of ConcreteEdgesGraph.
 * 
 * <p>PS2 instructions: the specification and implementation of this class is
 * up to you.
 */
class Edge<L extends Comparable<L>> implements Comparable<Edge<L>>{
    
	private L source, target;
	private int weight;
	
    // Abstraction function:
    //   Edge(s, t, w) = Edge from s to t with weight w
    // Representation invariant:
    //   weight <> 0
    // Safety from rep exposure:
    //   source and target are immutable String objects
	//   weight return a reference to anew Integer object
    
   /**
    * Constructs a new edge with given values
    * @param s - the source of the edge. 
    * @param t - the target of the edge. 
    * @param w - the weight of the edge. Must not equal 0. Edges that equal zero will throw an IllegalArgumentException
    */
	public Edge(L s, L t, int w) {
		if (weight != 0) { throw new IllegalArgumentException();}
		source = s;
		target = t;
		weight = w;
		assert checkRep();
	}
	
	private boolean checkRep() {
		return weight != 0;
	}
    
    /** Returns the source vertex of the edge
     * 
     * @return the source of the edge. 
     */
    public L source() {
    	return this.source;
    }
    
    /**
     * Return the target vertex of the edge
     * @return target of the edge
     */
    public L target() {
    	return this.target;
    }
    
    /**
     * Return the weight of the edge
     * @return weight of the edge
     */
    public int weight() {
    	return new Integer(this.weight); // prevents rep exposure
    }
    
    
    @Override 
    public String toString() {
    	return "(" + source + "-->" + target + ": " + weight + ")";
    }
    
    @Override
    public int compareTo(Edge<L> that) {
    	if (source.compareTo(that.source()) == 0) {
    		if (target.compareTo(that.target()) == 0) {
    			return this.weight - that.weight;
    		}
    		else {return (this.target().compareTo(that.target()));}
    	} else {return this.source.compareTo(that.source());}
    }
    
    
    
}
