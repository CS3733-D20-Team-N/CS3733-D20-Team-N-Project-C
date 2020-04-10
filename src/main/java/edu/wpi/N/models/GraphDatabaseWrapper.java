package edu.wpi.N.models;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;

public class GraphDatabaseWrapper {

  /**
   * Search for the node
   *
   * @param id: id of the node
   * @return: node with the given id
   */
  public static Node getNode(String id) {
  //TODO gets a node with id from database
    return null;
  }

  /**
   * Add new node to the graph
   *
   * @param node
   */
  public static void addNode(Node node) {
    // TODO:
    // adds a node to the database

  }

  /**
   * Get all the edges a node is connected with
   *
   * @param id: id of the Node
   * @return: List of IDs of nodes, the given node is connected to
   */
  public static LinkedList<String> getEdges(String id) {
    //TODO gets edges from database
    return null;
  }

  /**
   * Add an Edge between 2 nodes
   *
   * @param id1: Id of the first node
   * @param id2: Id of the second node
   */
  public static void addEdge(String id1, String id2) {
  //TODO adds edges to database, no duplicates, bidirectional
  }
}
