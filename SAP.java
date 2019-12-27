/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author deliamcgrath
 */
import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdIn;

import java.util.ArrayList;
import java.util.Arrays;

public class SAP {
    private Digraph digraph;
    private int commonAncestor;
    private int pathDistance;
    private int shortestPath;

    // constructor takes a digraph (not necessarily a DAG)
    public SAP(Digraph digraph) {
        this.digraph = new Digraph(digraph);
        commonAncestor = -1;
        pathDistance = 0;
        shortestPath = Integer.MAX_VALUE;
    }

    // length of shortest ancestral path between v and w; -1 if no such path
    public int length(int v, int w) {
        validateVertex(v, w);
        calculatePath(v, w);
        return shortestPath;
    }

    // a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
    public int ancestor(int v, int w) {
        validateVertex(v, w);
        calculatePath(v, w);
        return commonAncestor;
    }

    // length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
    public int length(Iterable<Integer> v, Iterable<Integer> w) {
        walkthroughManyPaths(v, w);
        return shortestPath;
    }

    // a common ancestor that participates in shortest ancestral path; -1 if no such path
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
        walkthroughManyPaths(v, w);
        return commonAncestor;
    }

    private void calculatePath(int v, int w) {
        validateVertex(v, w);
        resetSAP();
        BreadthFirstDirectedPaths bfdpv = new BreadthFirstDirectedPaths(digraph, v);
        BreadthFirstDirectedPaths bfdpw = new BreadthFirstDirectedPaths(digraph, w);

        for (int i = 0; i < digraph.V(); i++) {

            if (bfdpv.hasPathTo(i) && bfdpw.hasPathTo(i)) {

                path_distance = bfdpv.distTo(i) + bfdpw.distTo(i);

                if (pathDistance < shortestPath) {
                    shortestPath = pathDistance;
                    commonAncestor = i;
                }
            }
        }

        if (shortestPath == Integer.MAX_VALUE) {
            shortestPath = -1;
            commonAncestor = -1;
        }

    }


    private void walkthroughManyPaths(Iterable<Integer> v, Iterable<Integer> w) {
        validateIterable(v, w);
        resetSAP();

        BreadthFirstDirectedPaths bfs = new BreadthFirstDirectedPaths(digraph, v);
        BreadthFirstDirectedPaths bfs1 = new BreadthFirstDirectedPaths(digraph, w);


        for (int i = 0; i < digraph.V(); i++) {
            if (bfs.hasPathTo(i) && bfs1.hasPathTo(i)) {
                pathDistance = bfs.distTo(i) + bfs1.distTo(i);

                if (pathDistance < shortestPath) {
                    shortestPath = pathDistance;
                    commonAncestor = i;
                }
            }
        }

        if (shortestPath == Integer.MAX_VALUE) {
            shortestPath = -1;
            commonAncestor = -1;
        }
    }

    private void resetSAP() {
        commonAncestor = -1;
        pathDistance = 0;
        shortestPath = Integer.MAX_VALUE;
    }

    private void validateVertex(int v, int w) {
        if (v < 0 || w < 0 || v > digraph.V() - 1 || w > digraph.V() - 1)
            throw new java.lang.IllegalArgumentException();
    }

    private void validateIterable(Iterable<Integer> v, Iterable<Integer> w) {
        if (v == null || w == null)
            throw new IllegalArgumentException();
        for (Integer i : v) {
            if (i == null)
                throw new IllegalArgumentException();
        }
        for (Integer i : w) {
            if (i == null)
                throw new IllegalArgumentException();
        }
    }


    // do unit testing of this class
    public static void main(String[] args) {
        In in = new In(args[0]);
        Digraph G = new Digraph(in);
        SAP sap = new SAP(G);
//        //ArrayList<Integer> A = new ArrayList<Integer>(Arrays.asList(13, 23, 24));
//       // ArrayList<Integer> B = new ArrayList<Integer>(Arrays.asList(6, 16, 17));
//
//        ArrayList<Integer> A = new ArrayList<Integer>(Arrays.asList(0, 6, 8, 11, 16));
//        ArrayList<Integer> B = new ArrayList<Integer>(Arrays.asList( 5, 9, 11, 14, 18));
//
//        int length = sap.length(A, B);
//        int ancestor = sap.ancestor(A, B);
//        StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
////        while (!StdIn.isEmpty()) {
            int v = StdIn.readInt();
            int w = StdIn.readInt();
            int length = sap.length(v, w);
            int ancestor = sap.ancestor(v, w);
            StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
        }

}
