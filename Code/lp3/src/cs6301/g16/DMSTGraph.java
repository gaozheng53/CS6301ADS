/**
 * <h1>Fall 2017 Long Project 3</h1>
 * <p>
 * Helper class extends Graph to provide graph operation needed in the DMST algorithm.
 * This class is a modification of XGraph class given.
 *
 * @author Binhan Wang (bxw161330) / Hanlin He (hxh160630) / Zheng Gao (zxg170430)
 * @version 1.0
 * @since 2017-10-11
 */

package cs6301.g16;

import cs6301.g00.ArrayIterator;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.io.File;
import java.io.FileNotFoundException;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;

public class DMSTGraph extends Graph {
    public static class DMSTVertex extends Vertex {
    	WeakReference<DMSTGraph> g;
        boolean disabled = false;
        List<DMSTEdge> xadj = new LinkedList<>();
        List<DMSTEdge> xrevAdj = new LinkedList<>();
        Set<DMSTVertex> shrunkenVSet = null;
        Edge selectedEdge = null; // used to store the incoming edge in the MST

        DMSTVertex(int n, Set<DMSTVertex> vs, DMSTGraph g) {
            super(n);
            this.shrunkenVSet = vs;
            this.g = new WeakReference<DMSTGraph>(g);
        }

        // isDisabled() method capable with mask
        boolean isDisabled() {
        	DMSTGraph graph = g.get();
        	if(graph!=null && graph.useMask())
        		return disabled||!graph.getMaskVertices().contains(this);
        	else
        		return disabled;
        }

        boolean isShrunkenVertex(){
            return shrunkenVSet!=null;
        }

        // Getters & Setters
        void disable() {
            disabled = true;
        }

        void enable() {
            disabled = false;
        }

        public Edge getSelectedEdge() {
            return selectedEdge;
        }

        public void setSelectedEdge(Edge selectedEdge) {
            this.selectedEdge = selectedEdge;
        }

        @Override
        public Iterator<Edge> iterator() {
            return new DMSTVertexIterator(this,false);
        }
        
        @Override
        public Iterator<Edge> reverseIterator() {
            return new DMSTVertexIterator(this,true);
        }

        class DMSTVertexIterator implements Iterator<Edge> {
            DMSTEdge cur;
            Iterator<DMSTEdge> it;
            boolean ready;

            DMSTVertexIterator(DMSTVertex u, boolean reversed) {
                if(reversed)
                	this.it = u.xrevAdj.iterator();
                else
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

    static class DMSTEdge extends Edge {
        DMSTEdge superEdge = null;
        Edge origEdge = null;

        DMSTEdge(DMSTVertex from, DMSTVertex to, DMSTEdge e) {
            super(from, to, e.weight);
            superEdge = e;
        }

        DMSTEdge(DMSTVertex from, DMSTVertex to, Edge e) {
            super(from, to, e.weight);
            origEdge = e;
        }

        // only zero edge is enabled
        boolean isDisabled() {
            DMSTVertex xfrom = (DMSTVertex) from;
            DMSTVertex xto = (DMSTVertex) to;
            return weight > 0 || xfrom.isDisabled() || xto.isDisabled();
        }
    }

    private DMSTVertex[] xv; // vertices of graph
    private Set<DMSTVertex> maskVertices;
    private int startName;

	public DMSTGraph(Graph g, Vertex start) {
	    super(g);

        setStartVertex(start);

        xv = new DMSTVertex[2 * g.size()]; // Extra space is allocated in array for
        // nodes to be added later
        for (Vertex u : g) {
            xv[u.getName()] = new DMSTVertex(u.name, null,this);
        }

        // Make copy of edges
        for (Vertex u : g) {
            for (Edge e : u) {
                Vertex v = e.otherEnd(u);
                DMSTVertex x1 = getVertex(u);
                DMSTVertex x2 = getVertex(v);
                DMSTEdge xe = new DMSTEdge(x1, x2, e);
                x1.xadj.add(xe);
                x2.xrevAdj.add(xe);
            }
        }
    }

    public Vertex getStartVertex(){
	    Vertex s = null;
	    if(0<=startName&&startName<xv.length)
	        s = xv[startName];
	    return s;
    }

    public void setStartVertex(Vertex start) {
        this.startName = start.getName();
    }

    /**
	 * Use mask on the graph
	 */
	public Set<DMSTVertex> getMaskVertices() {
		return maskVertices;
	}

	public void setMaskVertices(Set<DMSTVertex> maskVertices) {
		this.maskVertices = maskVertices;
	}

	public boolean useMask(){
		return maskVertices!=null;
	}

    @Override
    public Iterator<Vertex> iterator() {
        return new XGraphIterator(this);
    }

    class XGraphIterator implements Iterator<Vertex> {
        Iterator<DMSTVertex> it;
        DMSTVertex xcur;
        Vertex startVertex;
        boolean first;

        XGraphIterator(DMSTGraph dmstG) {
            first = true;
            startVertex = dmstG.getStartVertex();
        	if(dmstG.useMask())
        		this.it = dmstG.getMaskVertices().iterator();
        	else
        		this.it = new ArrayIterator<DMSTVertex>(dmstG.xv, 0, dmstG.size() - 1);
        }

        public boolean hasNext() {
            if(first&&startVertex!=null)
                return true;
            if (!it.hasNext()) {
                return false;
            }
            xcur = it.next();
            while ((xcur.isDisabled()||xcur.name==startVertex.name) && it.hasNext()) {
                xcur = it.next();
            }
            return !(xcur.isDisabled()||xcur.name==startVertex.name);
        }

        public Vertex next() {
            if(first) {
                first = false;
                if(startVertex!=null)
                    return startVertex;
            }
            return xcur;
        }
    }

    @Override
    public Vertex getVertex(int n) {
        return xv[n - 1];
    }

    DMSTVertex getVertex(Vertex u) {
        return Vertex.getVertex(xv, u);
    }

    void disable(int i) {
        DMSTVertex u = (DMSTVertex) getVertex(i);
        u.disable();
    }

    /**
     * Decrease weight for all edges by the minimum weight amount incoming edges
     * to the same node
     */
    void decreaseWeight() {
        for (Vertex v : this) {
            DMSTVertex xv = (DMSTVertex) v;
            int minWeight = Integer.MAX_VALUE;
            for (DMSTEdge e : xv.xrevAdj) {
                if (!((DMSTVertex) e.otherEnd(v)).disabled) {
                	// skip the disabled edge, don't use isDisabled() function since that is used 
                    if (e.weight < minWeight)
                        minWeight = e.weight;
                    if (minWeight == 0)
                        break;
                }
            }

            // continue to next vertex if we found 0 weight incoming edge on
            // this vertex
            if (minWeight == 0)
                continue;

            for (DMSTEdge e : xv.xrevAdj) {
                if (!((DMSTVertex) e.otherEnd(v)).disabled) {
                    e.weight -= minWeight;
                }
            }
        }
    }

    /**
     * Shrink a set of vertices into a new vertex
     *
     * @param vs
     *            - list of vertices to shrink
     */
    void shrinkVertexes(Set<DMSTVertex> vSet) {
        if (n + 1 > xv.length) {
            System.out.println("Notice - graph vertexes array reach maximum capacity, allocate new array - theoretically won't happend");
            DMSTVertex[] nxv = new DMSTVertex[xv.length * 2];
            for (int i = 0; i < xv.length; i++)
                nxv[i] = xv[i];
            xv = nxv;
        }

        DMSTVertex c = new DMSTVertex(n, vSet,this);
        xv[n++] = c;
        Map<DMSTVertex, DMSTEdge> inMap = new HashMap<>();
        Map<DMSTVertex, DMSTEdge> outMap = new HashMap<>();
        for (DMSTVertex v : vSet) {
            // disable shrunken vertices
            v.disable();

            // find outgoing edge to keep after shrink
            for (DMSTEdge outE : v.xadj) {
                DMSTVertex other = (DMSTVertex) outE.otherEnd(v);
                if (!vSet.contains(other)) {
                    if ((!outMap.containsKey(other)) || outMap.get(other).weight > outE.weight) {
                        outMap.put(other, outE);
                    }
                }
            }

            // find incoming edge to keep after shrink
            for (DMSTEdge inE : v.xrevAdj) {
                DMSTVertex other = (DMSTVertex) inE.otherEnd(v);
                if (!vSet.contains(other)) {
                    if ((!inMap.containsKey(other)) || inMap.get(other).weight > inE.weight) {
                        inMap.put(other, inE);
                    }
                }
            }
        }

        for (DMSTVertex v : inMap.keySet()) {
            DMSTEdge superE = inMap.get(v);
            DMSTEdge e = new DMSTEdge(v, c, superE);
            v.xadj.add(e);
            c.xrevAdj.add(e);
        }

        for (DMSTVertex v : outMap.keySet()) {
            DMSTEdge superE = outMap.get(v);
            DMSTEdge e = new DMSTEdge(c, v, superE);
            c.xadj.add(e);
            v.xrevAdj.add(e);
        }
    }

    /**
     * Expand a shrunken vertex
     *
     * @param c
     */
    void expand(DMSTVertex c) {
        if (c.shrunkenVSet != null) {
            for (DMSTVertex v : c.shrunkenVSet) {
                // enable each shrunk vertex
                v.enable();
            }
            // disable the component vertex
            c.disable();
        }
    }
    
    public static void printGraph(Graph g) {
    	for (Graph.Vertex u : g) {
            System.out.print(u + ": ");
            for (Graph.Edge e : u)
                System.out.print(e +"-"+ e.getWeight() +" ");
            System.out.println();
        }
    }

    /**
     * Main function for testing
     * @param args
     * @throws FileNotFoundException
     */
    public static void main(String[] args) throws FileNotFoundException {
    	Scanner in;
        if (args.length > 0) {
            File inputFile = new File(args[0]);
            in = new Scanner(inputFile);
        } else {
            in = new Scanner("5 7	1 5 8	1 4 7	1 3 6	4 3 3	3 5 6	5 3 2	5 2 1");
        }
        Graph g = Graph.readGraph(in, true);

        System.out.println("\n==============\nInput Graph:");
        printGraph(g);
        
        Graph dmstG = new DMSTGraph(g,g.getVertex(3));
        System.out.println("\n==============\nDMST Graph before decrease weight:");
        printGraph(dmstG);

        ((DMSTGraph)dmstG).decreaseWeight();
        
        System.out.println("\n==============\nDMST Graph after decrease weight:");
        printGraph(dmstG);
        
        Set<DMSTVertex> scc = new HashSet<>();
        scc.add((DMSTVertex) dmstG.getVertex(3));
        scc.add((DMSTVertex) dmstG.getVertex(5));
        
        ((DMSTGraph)dmstG).shrinkVertexes(scc);
        
        System.out.println("\n==============\nDMST Graph after shrink vertices 3, 5:");
        printGraph(dmstG);
        
        ((DMSTGraph)dmstG).decreaseWeight();
        
        System.out.println("\n==============\nDMST Graph after decrease weight:");
        printGraph(dmstG);
        
        ((DMSTGraph)dmstG).expand((DMSTVertex) dmstG.getVertex(6));
        
        System.out.println("\n==============\nDMST Graph after expand vertex 6:");
        printGraph(dmstG);
        
        ((DMSTGraph)dmstG).setMaskVertices(scc);
        
        System.out.println("\n==============\nDMST Graph with mask vertex 3, 5:");
        printGraph(dmstG);
        
        ((DMSTGraph)dmstG).setMaskVertices(null);
        
        System.out.println("\n==============\nDMST Graph without mask");
        printGraph(dmstG);
        
    }

}
