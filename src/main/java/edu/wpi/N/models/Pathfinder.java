package edu.wpi.N.models;

import java.util.*;

public class Pathfinder {
  private Graph graph;
  private Node start, end;

  public Pathfinder(Graph graph, Node start, Node end) {
    this.graph = graph;
    this.start = start;
    this.end = end;
  }

  /**
   * Function calculates Euclidean distance between the next Node and current Node (cost of given
   * node)
   *
   * @param currNode: current Node
   * @param nextNode: next Node
   * @return: Euclidean distance from the start
   */
  static double cost(Node currNode, Node nextNode) {
    return Math.sqrt(
        Math.pow(nextNode.getX() - currNode.getX(), 2)
            + Math.pow(nextNode.getY() - currNode.getY(), 2));
  }

  /**
   * Function calculates Manhatten distance between goal and current Node
   *
   * @param currNode: current Node
   * @return: Manhattan distance to the goal Node
   */
  static double heuristic(Node currNode, Node end) {
    return Math.abs(end.getX() - currNode.getX()) + Math.abs(end.getY() - currNode.getY());
  }

  /**
   * Finds the shortest path from Start to Goal node
   *
   * @return Path object indicating the shortest path to the goal Node from Start Node
   */
  public Path findPath() {
    // Initialize variables
    PriorityQueue<Node> frontier = new PriorityQueue<Node>();
    frontier.add(start);
    Map<String, String> cameFrom = new HashMap<String, String>();
    Map<String, Double> costSoFar = new HashMap<String, Double>();
    cameFrom.put(start.ID, "");
    costSoFar.put(start.ID, 0.0);
    start.score = 0;

    // While priority queue is not empty, get the node with highest Score (priority)
    while (!frontier.isEmpty()) {
      Node current = frontier.poll();

      // if the goal node was found, break out of the loop
      if (current == end) {
        break;
      }

      // for every node (next node), current node has edge to:
      for (String nextNodeID : graph.getEdges(current.ID)) {
        Node nextNode = graph.getNode(nextNodeID);
        // calculate the cost of next node
        double newCost = costSoFar.get(current.ID) + cost(nextNode, current);

        if (!costSoFar.containsKey(nextNodeID) || newCost < costSoFar.get(nextNodeID)) {
          // update the cost of nextNode
          costSoFar.put(nextNodeID, newCost);
          // calculate and update the Score of nextNode
          double priority = newCost + heuristic(nextNode, end);
          nextNode = graph.getNode(nextNodeID);
          nextNode.score = priority;
          // add to the priority queue
          frontier.add(nextNode);
          // keep track of where nodes come from
          // to generate the path to goal node
          cameFrom.put(nextNodeID, current.ID);
        }
      }
    }

    // Generate and return the path in proper order
    return this.generatePath(cameFrom);
  }

  /**
   * Helper function which generates Path given a Map
   *
   * @param came_from: Map, where key: NodeID, value: came-from-NodeID
   * @return Path object containing generated path
   */
  private Path generatePath(Map<String, String> came_from) {

    String currentID = end.ID;
    LinkedList<Node> path = new LinkedList<Node>();
    path.add(this.graph.getNode(currentID));

    try {
      while (!currentID.equals(start.ID)) {
        currentID = came_from.get(currentID);
        path.add(this.graph.getNode(currentID));
      }
    } catch (NullPointerException e) {
      System.out.println("Location was not found.");
      throw e;
    }
    // reverse the path, so it stores nodes in proper order
    LinkedList<Node> reversedPath = reversePath(path);
    Path finalPath = new Path(reversedPath);

    return finalPath;
  }

  /**
   * Helper function that reverses a given list
   *
   * @param initialPath: list which needs to be reversed
   * @return: reversed list
   */
  private LinkedList<Node> reversePath(LinkedList<Node> initialPath) {
    LinkedList<Node> reversedPath = new LinkedList<Node>();

    // iterate through initial path in descending order
    Iterator i = initialPath.descendingIterator();
    while (i.hasNext()) {
      // add nodes to reversed path
      reversedPath.add((Node) i.next());
    }
    return reversedPath;
  }
}
