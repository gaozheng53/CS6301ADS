/*
 * Copyright (c) 2017.
 *
 * @author Hanlin He (hxh160630@utdallas.edu) ,
 * @author Binhan Wang (bxw161330@utdallas.edu),
 * @author Zheng Gao (zxg170430@utdallas.edu)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package cs6301.g16;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * A residual graph class extending {@code Graph} using parallel array.
 */
public class ResidualGraph extends Graph {
    public static class ResidualVertex extends Graph.Vertex {
        List<ResidualEdge> residualAdj;
        List<ResidualEdge> residualRevAdj;
        int height;
        int excess;

        public ResidualVertex(Vertex u) {
            super(u);
            residualAdj = new LinkedList<>();
            residualRevAdj = new LinkedList<>();
            height = 0;
            excess = 0;
        }

        @Override
        public Iterator<Edge> iterator() {
            return new ResidualVertexIterator(this);
        }

        class ResidualVertexIterator implements Iterator<Edge> {
            ResidualEdge cur;
            Iterator<ResidualEdge> it;
            boolean ready;

            ResidualVertexIterator(ResidualVertex u) {
                this.it = u.residualAdj.iterator();
                ready = false;
            }

            public boolean hasNext() {
                if (ready) {
                    return true;
                }
                if (!it.hasNext()) {
                    return false;
                }
                cur = it.next();
                while (cur.isZeroResidualCapacity() && it.hasNext()) {
                    cur = it.next();
                }
                ready = true;
                return !cur.isZeroResidualCapacity();
            }

            public Edge next() {
                if (!ready) {
                    if (!hasNext()) {
                        throw new java.util.NoSuchElementException();
                    }
                }
                ready = false;
                return cur;
            }

            public void remove() {
                throw new UnsupportedOperationException();
            }
        }

        public int getHeight() {
            return height;
        }

        public void setHeight(int height) {
            this.height = height;
        }

        public int getExcess() {
            return excess;
        }

        public void changeExcess(int difference) {
            this.excess += difference;
        }

        @Override
        public String toString() {
            return super.toString() + " " + this.excess + " " + this.height;
        }
    }

    public static class ResidualEdge extends Graph.Edge {
        private final int capacity;
        private int flow;

        public ResidualEdge(Vertex u, Vertex v, int w, int n, int capacity, int flow) {
            super(u, v, w, n);
            this.capacity = capacity;
            this.flow = flow;
        }

        public boolean isZeroResidualCapacity() {
            return capacity == flow;
        }

        private int getRemainCapacity() {
            return capacity - flow;
        }

        public int getFlow() {
            return flow;
        }

        public int getCapacity() {
            return capacity;
        }

        /**
         * Private interface to increase the flow by specific size, used only by {@code augment}
         * method in {@code ResidualGraph} class.
         *
         * @param f Flow size to increase.
         */
        private void increase(int f) {
            flow += f;
        }

        /**
         * Private interface to decrease the flow by specific size, used only by {@code augment}
         * method in {@code ResidualGraph} class.
         *
         * @param f Flow size to decrease.
         */
        private void decrease(int f) {
            flow -= f;
        }

        @Override
        public String toString() {
            return "(" + from + ", " + to + ": " + flow + "/" + capacity + ")";
        }
    }

    /**
     * Parallel array of vertices.
     */
    private ResidualVertex[] fv;

    /**
     * Parallel array of edges with index [0, m-1] as the {@code ResidualEdge} instance of the
     * original edges, and [m, 2m-1] as the {@code ResidualEdge} instance of the edges with opposite
     * direction.
     */
    private ResidualEdge[] fe;

    /**
     * Determine whether the residual graph is weighted. If not, all edges have weight of 1.
     */
    private boolean weighted;

    public ResidualGraph(Graph g, HashMap<Edge, Integer> capacity, boolean weighted) {
        super(g);
        this.fv = new ResidualVertex[g.size()];
        this.fe = new ResidualEdge[g.edgeSize() * 2];
        this.weighted = weighted;

        // Create parallel array for vertices.
        g.forEach(u -> this.fv[u.getName()] = new ResidualVertex(u));

        // Make copy of edges.
        // Each edge would generate two edges between two node, each with different direction.
        // Initial flow is 0 for original direction, and full capacity for reverse direction.
        g.forEach(u -> u.forEach(e -> addResidualEdge(u, e.otherEnd(u), e.weight, e.name, capacity.get(e))));
    }

    private void addResidualEdge(Vertex fromVertex, Vertex toVertex, int weight, int name, int capacity) {

        ResidualVertex from = getVertex(fromVertex);
        ResidualVertex to = getVertex(toVertex);

        // Let index of original edge be the name.
        final int oriEdgeIndex = name - 1;

        // Let index of reversed edge be the name + number of edges.
        final int revEdgeIndex = name + m - 1;

        if (weighted) {
            fe[oriEdgeIndex] = new ResidualEdge(from, to, weight, oriEdgeIndex + 1, capacity, 0);
            fe[revEdgeIndex] = new ResidualEdge(to, from, weight, revEdgeIndex + 1, capacity, capacity);
            from.residualAdj.add(fe[oriEdgeIndex]);
            from.residualRevAdj.add(fe[revEdgeIndex]);
            to.residualAdj.add(fe[revEdgeIndex]);
            to.residualRevAdj.add(fe[oriEdgeIndex]);
        } else {
            fe[oriEdgeIndex] = new ResidualEdge(from, to, 1, oriEdgeIndex + 1, capacity, 0);
            fe[revEdgeIndex] = new ResidualEdge(to, from, 1, revEdgeIndex + 1, capacity, capacity);
            from.residualAdj.add(fe[oriEdgeIndex]);
            from.residualRevAdj.add(fe[revEdgeIndex]);
            to.residualAdj.add(fe[revEdgeIndex]);
            to.residualRevAdj.add(fe[oriEdgeIndex]);
        }
    }

    @Override
    public Iterator<Vertex> iterator() {
        return new ResidualGraphIterator(this);
    }

    class ResidualGraphIterator implements Iterator<Vertex> {
        Iterator<ResidualVertex> it;
        ResidualVertex xcur;

        ResidualGraphIterator(ResidualGraph rg) {
            this.it = new ArrayIterator<>(rg.fv, 0, rg.size() - 1);  // Iterate over existing elements only
        }


        public boolean hasNext() {
            if (!it.hasNext()) {
                return false;
            }
            xcur = it.next();
            return true;
        }

        public Vertex next() {
            return xcur;
        }

        public void remove() {
        }

    }

    public void augment(List<Edge> path) {
        int minCapacity = Integer.MAX_VALUE;

        for (Edge e : path) {
            final int remainCapacity = getEdge(e).getRemainCapacity();
            if (remainCapacity < minCapacity)
                minCapacity = remainCapacity;
        }

        final int flow = minCapacity;

        if (flow > 0)
            path.forEach(e -> augment(e, flow));
    }

    public void push(Edge edge, int flow) {
        augment(edge, flow);
        getVertex(edge.fromVertex()).changeExcess(-flow);
        getVertex(edge.toVertex()).changeExcess(flow);
    }

    private void push(ResidualVertex u, ResidualVertex v, ResidualEdge e) {
        int delta = Integer.min(u.getExcess(), e.getCapacity());
        if (e.fromVertex().getName() == u.getName()) {
            augment(e, delta);
        } else {
            augment(getOtherEdge(e), delta);
        }
        u.changeExcess(-delta);
        v.changeExcess(delta);
    }

    public void discharge(ResidualVertex ru) {
        while (ru.getExcess() > 0) {

            LinkedList<ResidualEdge> incident = new LinkedList<>(ru.residualAdj);
            incident.addAll(ru.residualRevAdj);
            for (ResidualEdge re : incident) {
                Vertex v = re.otherEnd(ru);
                ResidualVertex rv = getVertex(v);
                if (inGf(ru, re) && ru.getHeight() == 1 + rv.getHeight()) {
                    push(ru, rv, re);
                    if (ru.getExcess() == 0)
                        return;
                }
            }
            relabel(ru);
        }
    }

    boolean inGf(ResidualVertex u, ResidualEdge e) {
        return e.fromVertex().getName() == u.getName() ? e.getFlow() < e.getCapacity() : e.getFlow() > 0;
    }

    void relabel(ResidualVertex u) {
        int min = Integer.MAX_VALUE;
        for (Edge e : u) {
            ResidualVertex v = getVertex(e.otherEnd(u));
            if (v.height < min)
                min = v.height;
        }
        u.setHeight(1 + min);
    }

    private void augment(Edge e, int flow) {
        getEdge(e).increase(flow);
        getOtherEdge(e).decrease(flow);
    }

    @Override
    public Vertex getVertex(int n) {
        return fv[n - 1];
    }

    ResidualVertex getVertex(Vertex u) {
        return Vertex.getVertex(fv, u);
    }

    /**
     * Get the {@code ResidualEdge} instance from the original {@code Edge} instance.
     *
     * @param e {@code Edge} instance.
     *
     * @return Corresponding {@code ResidualEdge} instance.
     */
    public ResidualEdge getEdge(Edge e) {
        return fe[e.getName() - 1];
    }

    /**
     * Get the {@code ResidualEdge} instance of edge which has reversed direction of the original
     * {@code Edge} instance.
     *
     * @param e {@code Edge} instance.
     *
     * @return Corresponding {@code ResidualEdge} instance.
     */
    public ResidualEdge getOtherEdge(Edge e) {
        final int name = e.getName();
        return fe[name <= m ? name + m - 1 : name - m - 1];
    }
}