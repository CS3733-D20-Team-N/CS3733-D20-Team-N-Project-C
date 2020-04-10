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
    Map<String, String> came_from = new HashMap<String, String>();
    Map<String, Double> cost_so_far = new HashMap<String, Double>();
    came_from.put(start.ID, "");
    cost_so_far.put(start.ID, 0.0);
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
        Node next_node = graph.getNode(nextNodeID);
        // calculate the cost of next node
        double new_cost = cost_so_far.get(current.ID) + cost(next_node, current);

        if (!cost_so_far.containsKey(nextNodeID) || new_cost < cost_so_far.get(nextNodeID)) {
          // update the cost of nextNode
          cost_so_far.put(nextNodeID, new_cost);
          // calculate and update the Score of nextNode
          double priority = new_cost + heuristic(next_node, end);
          next_node = graph.getNode(nextNodeID);
          next_node.score = priority;
          // add to the priority queue
          frontier.add(next_node);
          // keep track of where nodes come from
          // to generate the path to goal node
          came_from.put(nextNodeID, current.ID);
        }
      }
    }

    // Generate and return the path in proper order
    return this.generatePath(came_from);
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
  private static LinkedList<Node> reversePath(LinkedList<Node> initialPath) {
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
