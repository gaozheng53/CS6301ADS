
/** @author rbk
 *  Ver 1.0: 2017/09/29
 *  Example to extend Graph/Vertex/Edge classes to implement algorithms in which nodes and edges
 *  need to be disabled during execution.  Design goal: be able to call other graph algorithms
 *  without changing their codes to account for disabled elements.
 *
 *  Ver 1.1: 2017/10/09
 *  Updated iterator with boolean field ready. Previously, if hasNext() is called multiple
 *  times, then cursor keeps moving forward, even though the elements were not accessed
 *  by next().  Also, if program calls next() multiple times, without calling hasNext()
 *  in between, same element is returned.  Added UnsupportedOperationException to remove.
 **/

package cs6301.g16;

import java.util.Iterator;
import java.util.List;
import java.util.LinkedList;


public class XGraph extends Graph {
    public static class XVertex extends Vertex {
	boolean disabled;
	List<XEdge> xadj;

	XVertex(Vertex u) {
	    super(u);
	    disabled = false;
	    xadj = new LinkedList<>();
	}

	boolean isDisabled() { return disabled; }

	void disable() { disabled = true; }

	@Override
	public Iterator<Edge> iterator() { return new XVertexIterator(this); }

	class XVertexIterator implements Iterator<Edge> {
	    XEdge cur;
	    Iterator<XEdge> it;
	    boolean ready;

	    XVertexIterator(XVertex u) {
		this.it = u.xadj.iterator();
		ready = false;
	    }

	    public boolean hasNext() {
		if(ready) { return true; }
		if(!it.hasNext()) { return false; }
		cur = it.next();
		while(cur.isDisabled() && it.hasNext()) {
		    cur = it.next();
		}
		ready = true;
		return !cur.isDisabled();
	    }

	    public Edge next() {
		if(!ready) {
		    if(!hasNext()) {
			throw new java.util.NoSuchElementException();
		    }
		}
		ready = false;
		return cur;
	    }

	    public void remove() {
		throw new java.lang.UnsupportedOperationException();
	    }
	}
    }

    static class XEdge extends Edge {
	boolean disabled;

	XEdge(XVertex from, XVertex to, int weight) {
	    super(from, to, weight);
	    disabled = false;
	}

	boolean isDisabled() {
		XVertex xfrom = (XVertex) from;
		XVertex xto = (XVertex) to;
		return disabled || xfrom.isDisabled() || xto.isDisabled();
	    }
    }

    XVertex[] xv; // vertices of graph

    public XGraph(Graph g) {
	super(g);
	xv = new XVertex[2*g.size()];  // Extra space is allocated in array for nodes to be added later
        for(Vertex u: g) {
            xv[u.getName()] = new XVertex(u);
        }

	// Make copy of edges
	for(Vertex u: g) {
	    for(Edge e: u) {
		Vertex v = e.otherEnd(u);
		XVertex x1 = getVertex(u);
		XVertex x2 = getVertex(v);
		x1.xadj.add(new XEdge(x1, x2, e.weight));
	    }
	}
    }

    @Override
    public Iterator<Vertex> iterator() { return new XGraphIterator(this); }

    class XGraphIterator implements Iterator<Vertex> {
	Iterator<XVertex> it;
	XVertex xcur;
	
	XGraphIterator(XGraph xg) {
	    this.it = new ArrayIterator<XVertex>(xg.xv, 0, xg.size()-1);  // Iterate over existing elements only
	}
	

	public boolean hasNext() {
	    if(!it.hasNext()) { return false; }
	    xcur = it.next();
	    while(xcur.isDisabled() && it.hasNext()) {
		xcur = it.next();
	    }
	    return !xcur.isDisabled();
	}

	public Vertex next() {
	    return xcur;
	}

	public void remove() {
	}
	    
    }


    @Override
    public Vertex getVertex(int n) {
        return xv[n-1];
    }

    XVertex getVertex(Vertex u) {
	return Vertex.getVertex(xv, u);
    }

    void disable(int i) {
	XVertex u = (XVertex) getVertex(i);
	u.disable();
    }
    
    public static void main(String[] args) {
//        Graph g = Graph.readGraph(new Scanner(System.in));
//	XGraph xg = new XGraph(g);
//	Vertex src = xg.getVertex(1);
//
//	System.out.println("Node : Dist : Edges");
//	BFS b = new BFS(xg, src);
//        b.bfs();
//        Vertex farthest = DiameterTree.findFarthest(b);
//	xg.printGraph(b);
//	System.out.println("Source: " + src + " Farthest: " + farthest + " Distance: " + b.distance(farthest));
//
//	System.out.println("\nDisabling vertices 8 and 9");
//	xg.disable(8);
//	xg.disable(9);
//	b.reinitialize(src);
//	b.bfs();
//	farthest = DiameterTree.findFarthest(b);
//	xg.printGraph(b);
//	System.out.println("Source: " + src + " Farthest: " + farthest + " Distance: " + b.distance(farthest));
//    }
//
//    void printGraph(BFS b) {
//	for(Vertex u: this) {
//	    System.out.print("  " + u + "  :   " + b.distance(u) + "  : ");
//	    for(Edge e: u) {
//		System.out.print(e);
//	    }
//	    System.out.println();
//	}

    }

}

/*
Sample output:

Node : Dist : Edges
  1  :   0  : (1,2)(1,3)
  2  :   1  : (2,1)(2,4)(2,5)
  3  :   1  : (3,1)(3,6)(3,7)
  4  :   2  : (4,2)(4,8)
  5  :   2  : (5,2)
  6  :   2  : (6,3)
  7  :   2  : (7,3)(7,9)
  8  :   3  : (8,4)
  9  :   3  : (9,7)
Source: 1 Farthest: 8 Distance: 3

Disabling vertices 8 and 9
  1  :   0  : (1,2)(1,3)
  2  :   1  : (2,1)(2,4)(2,5)
  3  :   1  : (3,1)(3,6)(3,7)
  4  :   2  : (4,2)
  5  :   2  : (5,2)
  6  :   2  : (6,3)
  7  :   2  : (7,3)
Source: 1 Farthest: 4 Distance: 2

*/
