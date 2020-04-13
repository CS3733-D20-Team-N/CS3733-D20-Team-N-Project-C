// package edu.wpi.N.models;
//
// import java.util.LinkedList;
// import org.junit.jupiter.api.Assertions;
// import org.junit.jupiter.api.Test;
//
// public class PathfinderMethodsTest {
//
//  /** Tests that findPath returns a Path object with the best route from H4 to H8 */
//  @Test
//  public void findPathNormalCase() {
//    Graph testGraph = new Graph();
//    // create nodes
//    Node n1 = new Node(6, 10, "H1");
//    Node n2 = new Node(4, 10, "H2");
//    Node n3 = new Node(5, 9, "H3");
//    Node n4 = new Node(6, 8, "H4");
//    Node n5 = new Node(4, 7, "H5");
//    Node n6 = new Node(3, 8, "H6");
//    Node n7 = new Node(2, 9, "H7");
//    Node n8 = new Node(1, 10, "H8"); // end node
//    Node n9 = new Node(2, 11, "H9");
//    Node n10 = new Node(4, 11, "H10");
//    Node n11 = new Node(6, 11, "H11");
//
//    // add nodes
//    testGraph.addNode(n1);
//    testGraph.addNode(n2);
//    testGraph.addNode(n3);
//    testGraph.addNode(n4);
//    testGraph.addNode(n5);
//    testGraph.addNode(n6);
//    testGraph.addNode(n7);
//    testGraph.addNode(n8);
//    testGraph.addNode(n9);
//    testGraph.addNode(n10);
//    testGraph.addNode(n11);
//
//    // add edges
//    testGraph.addEdge("H1", "H2");
//    testGraph.addEdge("H2", "H3");
//    testGraph.addEdge("H3", "H4");
//    testGraph.addEdge("H4", "H5");
//    testGraph.addEdge("H5", "H6");
//    testGraph.addEdge("H6", "H7");
//    testGraph.addEdge("H7", "H8");
//    testGraph.addEdge("H8", "H9");
//    testGraph.addEdge("H9", "H10");
//    testGraph.addEdge("H10", "H11");
//    testGraph.addEdge("H11", "H1");
//
//    Pathfinder pfinder = new Pathfinder(testGraph, n4, n8);
//
//    LinkedList<Node> actualPath = new LinkedList<Node>();
//    actualPath.add(n4);
//    actualPath.add(n5);
//    actualPath.add(n6);
//    actualPath.add(n7);
//    actualPath.add(n8);
//
//    for (int i = 0; i < actualPath.size(); i++) {
//      Assertions.assertEquals(pfinder.findPath().getPath().get(i).ID, actualPath.get(i).ID);
//    }
//  }
//
//  /**
//   * Tests that findPath method return a Path object with route consisting of 2 Nodes, since start
//   * and end nodes are neighbors
//   */
//  @Test
//  public void findPathStartIsNeighborWithEndNode() {
//    Graph testGraph = new Graph();
//    // create nodes
//    Node n1 = new Node(6, 10, "H1");
//    Node n2 = new Node(4, 10, "H2");
//    Node n3 = new Node(5, 9, "H3");
//    Node n4 = new Node(6, 8, "H4"); // start node
//    Node n5 = new Node(4, 7, "H5"); // end node
//    Node n6 = new Node(3, 8, "H6");
//    Node n7 = new Node(2, 9, "H7");
//    Node n8 = new Node(1, 10, "H8");
//    Node n9 = new Node(2, 11, "H9");
//    Node n10 = new Node(4, 11, "H10");
//    Node n11 = new Node(6, 11, "H11");
//
//    // add nodes
//    testGraph.addNode(n1);
//    testGraph.addNode(n2);
//    testGraph.addNode(n3);
//    testGraph.addNode(n4);
//    testGraph.addNode(n5);
//    testGraph.addNode(n6);
//    testGraph.addNode(n7);
//    testGraph.addNode(n8);
//    testGraph.addNode(n9);
//    testGraph.addNode(n10);
//    testGraph.addNode(n11);
//
//    // add edges
//    testGraph.addEdge("H1", "H2");
//    testGraph.addEdge("H2", "H3");
//    testGraph.addEdge("H3", "H4");
//    testGraph.addEdge("H4", "H5");
//    testGraph.addEdge("H5", "H6");
//    testGraph.addEdge("H6", "H7");
//    testGraph.addEdge("H7", "H8");
//    testGraph.addEdge("H8", "H9");
//    testGraph.addEdge("H9", "H10");
//    testGraph.addEdge("H10", "H11");
//    testGraph.addEdge("H11", "H1");
//
//    Pathfinder pfinder = new Pathfinder(testGraph, n4, n8);
//
//    LinkedList<Node> actualPath = new LinkedList<Node>();
//    actualPath.add(n4);
//    actualPath.add(n5);
//
//    for (int i = 0; i < actualPath.size(); i++) {
//      Assertions.assertEquals(pfinder.findPath().getPath().get(i).ID, actualPath.get(i).ID);
//    }
//  }
//
//  /**
//   * Tests that findPath throws NullPointerException if the destination given is not connected to
//   * any node
//   */
//  @Test
//  public void findPathDestinationNotFound() {
//    Graph testGraph = new Graph();
//    // create nodes
//    Node n1 = new Node(6, 10, "H1");
//    Node n2 = new Node(4, 10, "H2");
//    Node n3 = new Node(5, 9, "H3");
//    Node n4 = new Node(6, 8, "H4"); // start node
//    Node n5 = new Node(4, 7, "H5");
//    Node n6 = new Node(3, 8, "H6");
//    Node n7 = new Node(2, 9, "H7");
//    Node n8 = new Node(1, 10, "H8");
//    Node n9 = new Node(2, 11, "H9");
//    Node n10 = new Node(4, 11, "H10");
//    Node n11 = new Node(6, 11, "H11");
//    Node n12 = new Node(10, 15, "H12"); // end node
//
//    // add nodes
//    testGraph.addNode(n1);
//    testGraph.addNode(n2);
//    testGraph.addNode(n3);
//    testGraph.addNode(n4);
//    testGraph.addNode(n5);
//    testGraph.addNode(n6);
//    testGraph.addNode(n7);
//    testGraph.addNode(n8);
//    testGraph.addNode(n9);
//    testGraph.addNode(n10);
//    testGraph.addNode(n11);
//
//    // add edges
//    testGraph.addEdge("H1", "H2");
//    testGraph.addEdge("H2", "H3");
//    testGraph.addEdge("H3", "H4");
//    testGraph.addEdge("H4", "H5");
//    testGraph.addEdge("H5", "H6");
//    testGraph.addEdge("H6", "H7");
//    testGraph.addEdge("H7", "H8");
//    testGraph.addEdge("H8", "H9");
//    testGraph.addEdge("H9", "H10");
//    testGraph.addEdge("H10", "H11");
//    testGraph.addEdge("H11", "H1");
//
//    Pathfinder pfinder = new Pathfinder(testGraph, n4, n12);
//    Assertions.assertThrows(NullPointerException.class, () -> pfinder.findPath());
//  }
//
//  /**
//   * Tests that findPath method throws NullPointerException if start Node doesn't have a
// connection
//   * to any node (including end node)
//   */
//  @Test
//  public void findPathStartNodeHasNoEdges() {
//    Graph testGraph = new Graph();
//    // create nodes
//    Node n1 = new Node(6, 10, "H1");
//    Node n2 = new Node(4, 10, "H2");
//    Node n3 = new Node(5, 9, "H3");
//    Node n4 = new Node(6, 8, "H4"); // end node
//    Node n5 = new Node(4, 7, "H5");
//    Node n6 = new Node(3, 8, "H6");
//    Node n7 = new Node(2, 9, "H7");
//    Node n8 = new Node(1, 10, "H8");
//    Node n9 = new Node(2, 11, "H9");
//    Node n10 = new Node(4, 11, "H10");
//    Node n11 = new Node(6, 11, "H11");
//    Node n12 = new Node(10, 15, "H12"); // start node
//
//    // add nodes
//    testGraph.addNode(n1);
//    testGraph.addNode(n2);
//    testGraph.addNode(n3);
//    testGraph.addNode(n4);
//    testGraph.addNode(n5);
//    testGraph.addNode(n6);
//    testGraph.addNode(n7);
//    testGraph.addNode(n8);
//    testGraph.addNode(n9);
//    testGraph.addNode(n10);
//    testGraph.addNode(n11);
//
//    // add edges
//    testGraph.addEdge("H1", "H2");
//    testGraph.addEdge("H2", "H3");
//    testGraph.addEdge("H3", "H4");
//    testGraph.addEdge("H4", "H5");
//    testGraph.addEdge("H5", "H6");
//    testGraph.addEdge("H6", "H7");
//    testGraph.addEdge("H7", "H8");
//    testGraph.addEdge("H8", "H9");
//    testGraph.addEdge("H9", "H10");
//    testGraph.addEdge("H10", "H11");
//    testGraph.addEdge("H11", "H1");
//
//    Pathfinder pfinder = new Pathfinder(testGraph, n12, n4);
//
//    Assertions.assertThrows(NullPointerException.class, () -> pfinder.findPath());
//  }
//
//  /**
//   * Tests that findPath method returns a Path object with only one node in its route since Start
//   * Node = End Node
//   */
//  @Test
//  public void findPathEndIsStartNode() {
//    Graph testGraph = new Graph();
//    // create nodes
//    Node n1 = new Node(6, 10, "H1");
//    Node n2 = new Node(4, 10, "H2");
//    Node n3 = new Node(5, 9, "H3");
//    Node n4 = new Node(6, 8, "H4"); // end node // start node
//    Node n5 = new Node(4, 7, "H5");
//    Node n6 = new Node(3, 8, "H6");
//    Node n7 = new Node(2, 9, "H7");
//    Node n8 = new Node(1, 10, "H8");
//    Node n9 = new Node(2, 11, "H9");
//    Node n10 = new Node(4, 11, "H10");
//    Node n11 = new Node(6, 11, "H11");
//    Node n12 = new Node(10, 15, "H12");
//
//    // add nodes
//    testGraph.addNode(n1);
//    testGraph.addNode(n2);
//    testGraph.addNode(n3);
//    testGraph.addNode(n4);
//    testGraph.addNode(n5);
//    testGraph.addNode(n6);
//    testGraph.addNode(n7);
//    testGraph.addNode(n8);
//    testGraph.addNode(n9);
//    testGraph.addNode(n10);
//    testGraph.addNode(n11);
//
//    // add edges
//    testGraph.addEdge("H1", "H2");
//    testGraph.addEdge("H2", "H3");
//    testGraph.addEdge("H3", "H4");
//    testGraph.addEdge("H4", "H5");
//    testGraph.addEdge("H5", "H6");
//    testGraph.addEdge("H6", "H7");
//    testGraph.addEdge("H7", "H8");
//    testGraph.addEdge("H8", "H9");
//    testGraph.addEdge("H9", "H10");
//    testGraph.addEdge("H10", "H11");
//    testGraph.addEdge("H11", "H1");
//
//    Pathfinder pfinder = new Pathfinder(testGraph, n4, n4);
//
//    LinkedList<Node> actualPath = new LinkedList<Node>();
//    actualPath.add(n4);
//
//    for (int i = 0; i < actualPath.size(); i++) {
//      Assertions.assertEquals(pfinder.findPath().getPath().get(i).ID, n4.ID);
//    }
//  }
//
//  //  to test generatePath: uncomment the necessary test methods make the method itself public
//  //  just for the time of testing, then switch back to private after test
//  //  /**
//  //  * Tests that generatePath function generates a correct route from Start to End nodes
//  //  * given a Map cameFrom ("NodeID", "NodeID-came-from")
//  //  */
//  //  @Test
//  //  public void generateCorrectPathGivenUsualCaseMap() {
//  //    Graph testGraph = new Graph();
//  //
//  //    // create nodes
//  //    Node n1 = new Node(5, 5, "First");
//  //    Node n2 = new Node(4, 3, "Second");
//  //    Node n3 = new Node(1, 2, "Third");
//  //
//  //    // add nodes to the graph
//  //    testGraph.addNode(n1);
//  //    testGraph.addNode(n2);
//  //    testGraph.addNode(n3);
//  //
//  //    // create Pathfinder object
//  //    Pathfinder pfinder = new Pathfinder(testGraph, n1, n3);
//  //
//  //    // initialize map (nodeID, came-from-nodeID)
//  //    Map<String, String> cameFrom = new HashMap<String, String>();
//  //    cameFrom.put("Third", "Second");
//  //    cameFrom.put("Second", "First");
//  //    cameFrom.put("First", null);
//  //
//  //    // create an actual path which must be generated
//  //    LinkedList<Node> actualPathList = new LinkedList<Node>();
//  //    actualPathList.add(n1);
//  //    actualPathList.add(n2);
//  //    actualPathList.add(n3);
//  //
//  //    Assertions.assertEquals(pfinder.generatePath(cameFrom).getPath(), actualPathList);
//  //  }
//  //
//  //  /**
//  //  * Tests that generatePath method throws NullPointerException, given empty map
//  //  */
//  //  @Test
//  //  public void generatePathGivenEmptyMap() {
//  //    Graph testGraph = new Graph();
//  //
//  //    // create nodes
//  //    Node n1 = new Node(5, 5, "First");
//  //    Node n2 = new Node(4, 3, "Second");
//  //    Node n3 = new Node(1, 2, "Third");
//  //
//  //    // add nodes to the graph
//  //    testGraph.addNode(n1);
//  //    testGraph.addNode(n2);
//  //    testGraph.addNode(n3);
//  //
//  //    // create Pathfinder object
//  //    Pathfinder pfinder = new Pathfinder(testGraph, n1, n3);
//  //
//  //    // initialize map (nodeID, came-from-nodeID)
//  //    Map<String, String> cameFrom = new HashMap<String, String>();
//  //
//  //    // create an actual path which must be generated
//  //    LinkedList<Node> actualPathList = new LinkedList<Node>();
//  //
//  //    Assertions.assertThrows(NullPointerException.class, () -> pfinder.generatePath(cameFrom));
//  //  }
//
// }

package edu.wpi.N.models;

import edu.wpi.N.Main;
import edu.wpi.N.database.DbNode;
import edu.wpi.N.database.dbController;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.LinkedList;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class PathfinderMethodsTest {

  @BeforeAll
  public static void initializeTest() throws SQLException, ClassNotFoundException {
    dbController.initDB();
    InputStream inputNodes = Main.class.getResourceAsStream("csv/TestNodes.csv");
    InputStream inputEdges = Main.class.getResourceAsStream("csv/TestEdges.csv");
    CSVParser.parseCSV(inputNodes);
    CSVParser.parseCSV(inputEdges);
  }

  /** Tests that findPath returns a Path object with the best route from H10 to EEE */
  @Test
  public void findPathNormalCase() {
    LinkedList<DbNode> actualPath = new LinkedList<DbNode>();
    actualPath.add(dbController.getNode("H10"));
    actualPath.add(dbController.getNode("H9"));
    actualPath.add(dbController.getNode("H12"));
    actualPath.add(dbController.getNode("H13"));
    actualPath.add(dbController.getNode("EEE"));

    Path testingPath = Pathfinder.findPath("H10", "EEE");

    for (int i = 0; i < actualPath.size(); i++) {
      Assertions.assertEquals(
          testingPath.getPath().get(i).getNodeID(), actualPath.get(i).getNodeID());
    }
  }

  /**
   * Tests that findPath method return a Path object with route consisting of 2 Nodes, since start
   * and end nodes are neighbors
   */
  @Test
  public void findPathStartIsNeighborWithEndNode() {

    LinkedList<DbNode> actualPath = new LinkedList<DbNode>();

    actualPath.add(dbController.getNode("H12"));
    actualPath.add(dbController.getNode("H13"));

    Path testingPath = Pathfinder.findPath("H12", "H13");

    for (int i = 0; i < actualPath.size(); i++) {
      Assertions.assertEquals(
          testingPath.getPath().get(i).getNodeID(), actualPath.get(i).getNodeID());
    }
  }

  /**
   * Tests that findPath throws NullPointerException if the destination given is not connected to
   * any node
   */
  @Test
  public void findPathDestinationNotFound() {
    Assertions.assertThrows(
        NullPointerException.class, () -> Pathfinder.findPath("H12", "NonExistentNode"));
  }

  /**
   * Tests that findPath method throws NullPointerException if start Node doesn't have a connection
   * to any node (including end node)
   */
  @Test
  public void findPathStartNodeHasNoEdges() {
    Assertions.assertThrows(
        NullPointerException.class, () -> Pathfinder.findPath("NonExistentNode", "H12"));
  }

  /**
   * Tests that findPath method returns a Path object with only one node in its route since Start
   * Node = End Node
   */
  @Test
  public void findPathEndIsStartNode() {
    LinkedList<DbNode> actualPath = new LinkedList<DbNode>();

    actualPath.add(dbController.getNode("H12"));
    Path testingPath = Pathfinder.findPath("H12", "H12");

    for (int i = 0; i < actualPath.size(); i++) {
      Assertions.assertEquals(
          testingPath.getPath().get(i).getNodeID(), actualPath.get(i).getNodeID());
    }
  }

  // to test generatePath: uncomment the necessary test methods make the method itself public
  // just for the time of testing, then switch back to private after test

  //  /**
  //   * Tests that generatePath function generates a correct route from Start to End nodes given a
  // Map
  //   * cameFrom ("NodeID", "NodeID-came-from")
  //   */
  //  @Test
  //  public void generateCorrectPathGivenUsualCaseMap() {
  //
  //    // initialize map (nodeID, came-from-nodeID)
  //    Map<String, String> cameFrom = new HashMap<String, String>();
  //    cameFrom.put("EEE", "H4");
  //    cameFrom.put("H4", "H3");
  //    cameFrom.put("H3", null);
  //
  //    // create an actual path which must be generated
  //    LinkedList<DbNode> actualPathList = new LinkedList<DbNode>();
  //    actualPathList.add(new DbNode("H3", 0, 0, 0, "", "", "", "", 'c'));
  //    actualPathList.add(new DbNode("H4", 0, 0, 0, "", "", "", "", 'c'));
  //    actualPathList.add(new DbNode("EEE", 0, 0, 0, "", "", "", "", 'c'));
  //
  //    Node start = new Node(0, 0, "H3");
  //    Node end = new Node(0, 0, "EEE");
  //
  //    for (int i = 0; i < actualPathList.size(); i++) {
  //      Assertions.assertEquals(
  //          Pathfinder.generatePath(start, end, cameFrom).getPath().get(i).getNodeID(),
  //          actualPathList.get(i).getNodeID());
  //    }
  //  }

  //  /** Tests that generatePath method throws NullPointerException, given empty map */
  //  @Test
  //  public void generatePathGivenEmptyMap() {
  //    // initialize map (nodeID, came-from-nodeID)
  //    Map<String, String> cameFrom = new HashMap<String, String>();
  //
  //    Node start = new Node(0, 0, "H3");
  //    Node end = new Node(0, 0, "EEE");
  //
  //    Assertions.assertThrows(
  //        NullPointerException.class, () -> Pathfinder.generatePath(start, end, cameFrom));
  //  }
}
