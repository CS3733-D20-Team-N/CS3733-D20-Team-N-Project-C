package edu.wpi.N.models;

import edu.wpi.N.Main;
import edu.wpi.N.database.dbController;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.LinkedList;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class DbControllerMethodsTest {

  @BeforeAll
  public static void initializeTest() throws SQLException, ClassNotFoundException {
    dbController.initDB();
    InputStream inputNodes = Main.class.getResourceAsStream("csv/TestNodes.csv");
    InputStream inputEdges = Main.class.getResourceAsStream("csv/TestEdges.csv");
    CSVParser.parseCSV(inputNodes);
    CSVParser.parseCSV(inputEdges);
  }

    /** Tests that getEdges(nodeID) returns the correct list of edges for a given node */
    @Test
    public void getEdgesTester() {
      LinkedList<Node> hall1Edges = new LinkedList<Node>();
      hall1Edges.add(dbController.getGNode("MOHSClinic"));
      hall1Edges.add(dbController.getGNode("HALL2"));
      Assertions.assertEquals(dbController.getGAdjacent("HALL1"), hall1Edges);

      LinkedList<Node> hall3Edges = new LinkedList<Node>();
      hall3Edges.add(dbController.getGNode("HALL2"));
      hall3Edges.add(dbController.getGNode("HALL4"));
      Assertions.assertEquals(dbController.getGAdjacent("HALL3"), hall3Edges);

      LinkedList<Node> neurologyEdges = new LinkedList<Node>();
      neurologyEdges.add(dbController.getGNode("HALL6"));
      Assertions.assertEquals(dbController.getGAdjacent("Neurology"), neurologyEdges);
    }

    /**
     * Tests that getEdges(nodeID) will return null if the node is not in the graph and if the
   node
     * does not have any edges
     */
    @Test
    public void getEdgesNullTester() throws SQLException {
      Node testNode = new Node(2.345, 5.5657, "TESTNODE2");
      Assertions.assertNull(dbController.getGAdjacent("TESTNODE2"));

      dbController.addNode("TESTNODE2", 23, 345, 4, "Foisie", "sdfkjd", "fskjd", "sdfk", 'N');
      Assertions.assertNull(dbController.getGAdjacent("TESTNODE2"));
    }

    /**
     * Tests that addEdges(nodeID1,nodeID2) will add the given edge to the list of nodes for both
     * given nodes
     */
    @Test
    public void addEdgesTester() {
      LinkedList<Node> hall1Edges = new LinkedList<Node>();
      hall1Edges.add(dbController.getGNode("MOHSClinic"));
      hall1Edges.add(dbController.getGNode("HALL2"));
      hall1Edges.add(dbController.getGNode("HALL3"));
      dbController.addEdge("HALL1", "HALL3");
      Assertions.assertEquals(dbController.getGAdjacent("HALL1"), hall1Edges);

      LinkedList<Node> hall3Edges = new LinkedList<Node>();
      hall3Edges.add(dbController.getGNode("HALL2"));
      hall3Edges.add(dbController.getGNode("HALL4"));
      hall3Edges.add(dbController.getGNode("HALL1"));
      Assertions.assertEquals(dbController.getGAdjacent("HALL3"), hall3Edges);
    }

    /**
     * Tests that addEdges(nodeID1,nodeID2) will create a list of edges for a node that currently
   has
     * no edges
     */
    @Test
    public void addEdgesEmptyNodeTester() {
      dbController.addEdge("TESTNODE2", "Elevator");
      LinkedList<Node> testNodeEdges = new LinkedList<Node>();
      testNodeEdges.add(dbController.getGNode("Elevator"));
      Assertions.assertEquals(dbController.getGAdjacent("TESTNODE2"), testNodeEdges);
    }

    /**
     * Tests that a duplicate edge will not be added if you run addEdge(nodeID1,nodeID2) on an
   edge
     * that already exists
     */
    @Test
    public void addEdgeAlreadyThereTester() {
      LinkedList<Node> neurologyEdges = new LinkedList<Node>();
      neurologyEdges.add(dbController.getGNode("HALL6"));
      LinkedList<Node> hall6Edges = new LinkedList<Node>();
      hall6Edges.add(dbController.getGNode("HALL5"));
      hall6Edges.add(dbController.getGNode("HALL7"));
      hall6Edges.add(dbController.getGNode("Neurology"));
      dbController.addEdge("HALL6", "Neurology");
      Assertions.assertEquals(dbController.getGAdjacent("Neurology"), neurologyEdges);
      Assertions.assertEquals(dbController.getGAdjacent("HALL6"), hall6Edges);
    }

    /** Tests that heuristic(currNode, endNode) returns the correct calculated value */
    @Test
    public void heuristicTester() {
      Assertions.assertEquals(
          Pathfinder.heuristic(dbController.getGNode("MOHSClinic"),
   dbController.getGNode("HALL1")),
          0.77,
          0.0001);
    }

    /**
     * Tests that heuristic(currNode, endNode) returns the correct calculated value for a node
   that is
     * not in the graph
     */
    @Test
    public void heuristicNotInGraphTester() {
      Node testNode1 = new Node(1, 0, "TESTNODE3");
      Node testNode2 = new Node(0, 1, "TESTNODE4");
      Assertions.assertEquals(Pathfinder.heuristic(testNode1, testNode2), 2, 0.0001);
    }

  /**
   * Tests that getGNode(nodeID) returns the correct node when given a nodeID that is in the
   * database
   */
  @Test
  public void getNodeTester() throws SQLException {
    Node testNode3 = new Node(447, 672, "BBB");
    Assertions.assertEquals(dbController.getGNode("BBB"), testNode3);
  }

  /** Second test for getGNode(nodeID) */
  @Test
  public void getNodeTester2() throws SQLException {
    Node testNode4 = new Node(517, 904, "H7");
    Assertions.assertEquals(dbController.getGNode("H7"), testNode4);
  }

  /** Tests that getGNode(nodeID) returns null when given an nodeID that isn't in the database */
  @Test
  public void getNodeNullTester() {
    // Change in future to reflect getting an exception/error
    Assertions.assertNull(dbController.getGNode("test1"));
  }

  /**
   * Tests that addNode(nodeID, x, y, floor, building, nodeType, longName, shortName, teamAssigned)
   * takes the given information and makes it into a node in the database
   */
  @Test
  public void addNodeTester() throws SQLException {
    Node testNode5 = new Node(25, 30, "testNode5");
    dbController.addNode("testNode5", 25, 30, 4, "Foisie", "OFF", "TESTNODE5", "T5", 'N');
    Assertions.assertEquals(dbController.getGNode("testNode5"), testNode5);
    dbController.deleteNode("testNode5");
  }

  /**
   * Second test for addNode(nodeID, x, y, floor, building, nodeType, longName, shortName,
   * teamAssigned)
   */
  @Test
  public void addNodeTester2() throws SQLException {
    Node testNode6 = new Node(108, 55, "testNode6");
    dbController.addNode("testNode6", 108, 55, 4, "AK", "OFF", "TESTNODE6", "T6", 'N');
    Assertions.assertEquals(dbController.getGNode("testNode6"), testNode6);
    dbController.deleteNode("testNode6");
  }

  //
  //  /**
  //   * Tests that addNode(node) doesn't add the given node to the database if it has the same ID
  //   * as another node in the database
  //   * (future test to implement once that functionality is added)
  //   */
  //

  /**
   * Tests that cost(currNode, nextNode) returns the correct cost value for nodes in the database
   */
  @Test
  public void costTester() {
    Assertions.assertEquals(
        Pathfinder.cost(dbController.getGNode("AAA"), dbController.getGNode("EEE")), 1196.75, 0.05);
  }

  /**
   * Tests that cost(currNode, nextNode) returns the correct value for nodes even if they aren't in
   * the database
   */
  @Test
  public void costNotInGraphTester() {
    Node testNode7 = new Node(0, 0, "node7");
    Node testNode8 = new Node(3, 4, "node8");
    Assertions.assertEquals(Pathfinder.cost(testNode7, testNode8), 5, 0.0001);
  }
}
