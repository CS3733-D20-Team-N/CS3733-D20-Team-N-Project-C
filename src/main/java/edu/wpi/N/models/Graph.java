package edu.wpi.N.models;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;

public class Graph {

  private LinkedList<Node> nodes;
  private HashMap<String, LinkedList<String>> edges;

  public Graph() {
    this.nodes = new LinkedList<Node>();
    this.edges = new HashMap<String, LinkedList<String>>();
  }

  /**
   * Search for the node
   *
   * @param id: id of the node
   * @return: node with the given id
   */
  public Node getNode(String id) {
    Iterator<Node> nodeIt = this.nodes.iterator();
    Node n;
    while (nodeIt.hasNext()) {
      n = nodeIt.next();
      if (n.ID.equals(id)) return n;
    }
    return null;
  }

  /**
   * Add new node to the graph
   *
   * @param node
   */
  public void addNode(Node node) {
    // TODO:
    // Need to change functionality so that it doesn't add a node that has the same ID as one
    // already in the list
    this.nodes.add(node);
  }

  /**
   * Get all the edges a node is connected with
   *
   * @param id: id of the Node
   * @return: List of IDs of nodes, the given node is connected to
   */
  public LinkedList<String> getEdges(String id) {
    return this.edges.get(id);
  }

  /**
   * Add an Edge between 2 nodes
   *
   * @param id1: Id of the first node
   * @param id2: Id of the second node
   */
  public void addEdge(String id1, String id2) {

    // if no record of such nodes in graph's edges, initialize them
    edges.computeIfAbsent(id1, k -> new LinkedList<String>());
    edges.computeIfAbsent(id2, k -> new LinkedList<String>());

    // Add id1 to the list of "connections" of id2
    if (!edges.get(id1).contains(id2)) {
      LinkedList<String> nodes = edges.get(id1);
      nodes.add(id2);
    }
    // Add id2 to the list of "connections" of id1
    if (!edges.get(id2).contains(id1)) {
      LinkedList<String> nodes = edges.get(id2);
      nodes.add(id1);
    }
  }
}
