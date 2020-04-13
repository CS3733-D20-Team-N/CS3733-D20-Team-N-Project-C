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

  /** Tests that getGAdjacent(nodeID) returns the correct list of edges for a given node */
  @Test
  public void getGAdjacentTester() {
    LinkedList<Node> hall1Edges = new LinkedList<Node>();
    hall1Edges.add(dbController.getGNode("H200000000"));
    Assertions.assertEquals(dbController.getGAdjacent("H100000000"), hall1Edges);

    LinkedList<Node> hall3Edges = new LinkedList<Node>();
    hall3Edges.add(dbController.getGNode("H200000000"));
    hall3Edges.add(dbController.getGNode("H400000000"));
    Assertions.assertEquals(dbController.getGAdjacent("H300000000"), hall3Edges);
  }

  /**
   * Tests that getGAdjacent(nodeID) will return null if the node is not in the graph and if the
   * node does not have any edges
   */
  @Test
  public void getGAdjacentNullTester() throws SQLException {
    // null for a node that is not in the database
    Node testNode = new Node(2.345, 5.5657, "TESTNODE02");
    Assertions.assertNull(dbController.getGAdjacent("TESTNODE02"));
    // null for a node that is in the database but has no edges
    dbController.addNode("TESTNODE03", 23, 345, 4, "Foisie", "sdfkjd", "fskjd", "sdfk", 'N');
    Assertions.assertNull(dbController.getGAdjacent("TESTNODE3"));
  }

  /**
   * Tests that addEdges(nodeID1,nodeID2) will add the given edge to the list of nodes for both
   * given nodes
   */
  @Test
  public void addEdgesTester() {
    dbController.addNode("TESTNODE04", 23, 345, 4, "Foisie", "sdfkjd", "fskjd", "sdfk", 'N');
    LinkedList<Node> hall1Edges = new LinkedList<Node>();
    hall1Edges.add(dbController.getGNode("H200000000"));
    hall1Edges.add(dbController.getGNode("TESTNODE04"));
    dbController.addEdge("H100000000", "TESTNODE04");
    // checks that TESTNODE04 was added to H100000000 adjacent nodes
    Assertions.assertEquals(dbController.getGAdjacent("H100000000"), hall1Edges);

    // checks that H100000000 was added to TESTNODE04 adjacent nodes
    LinkedList<Node> testNodeEdges = new LinkedList<Node>();
    testNodeEdges.add(dbController.getGNode("H100000000"));
    Assertions.assertEquals(dbController.getGAdjacent("TESTNODE04"), testNodeEdges);
  }

  /**
   * Tests that addEdges(nodeID1,nodeID2) will create a list of edges for a node that currently has
   * no edges
   */
  @Test
  public void addEdgesEmptyNodeTester() {
    dbController.addNode("TESTNODE05", 23, 345, 4, "Foisie", "sdfkjd", "fskjd", "sdfk", 'N');
    dbController.addEdge("TESTNODE05", "H800000000");
    LinkedList<Node> testNodeEdges = new LinkedList<Node>();
    testNodeEdges.add(dbController.getGNode("H800000000"));
    Assertions.assertEquals(dbController.getGAdjacent("TESTNODE05"), testNodeEdges);
  }

  /**
   * Tests that addEdges(nodeID1,nodeID2) will not add a node that does not exist in the database
   */
  @Test
  public void addInvalidEdgesTester() {
    dbController.addEdge("CCCCCCCCCC", "NOTANODE01");
    LinkedList<Node> CCCEdges = new LinkedList<Node>();
    CCCEdges.add(dbController.getGNode("H900000000"));
    Assertions.assertEquals(dbController.getGAdjacent("CCCCCCCCCC"), CCCEdges);
  }

  /**
   * Tests that a duplicate edge will not be added if you run addEdge(nodeID1,nodeID2) on an edge
   * that already exists
   */
  @Test
  public void addEdgeAlreadyThereTester() {
    LinkedList<Node> hall5Edges = new LinkedList<Node>();
    hall5Edges.add(dbController.getGNode("H200000000"));
    hall5Edges.add(dbController.getGNode("H600000000"));
    LinkedList<Node> hall6Edges = new LinkedList<Node>();
    hall6Edges.add(dbController.getGNode("H500000000"));
    hall6Edges.add(dbController.getGNode("AAAAAAAAAA"));
    hall6Edges.add(dbController.getGNode("H700000000"));
    dbController.addEdge("H500000000", "H600000000");
    Assertions.assertEquals(dbController.getGAdjacent("H500000000"), hall5Edges);
    Assertions.assertEquals(dbController.getGAdjacent("H600000000"), hall6Edges);
  }

  /** Tests that heuristic(currNode, endNode) returns the correct calculated value */
  @Test
  public void heuristicTester() {
    Assertions.assertEquals(
        Pathfinder.heuristic(
            dbController.getGNode("AAAAAAAAAA"), dbController.getGNode("BBBBBBBBBB")),
        455,
        0.01);
  }

  /**
   * Tests that getGNode(nodeID) returns the correct node when given a nodeID that is in the
   * database
   */
  @Test
  public void getNodeTester() throws SQLException {
    Node testNode3 = new Node(447, 672, "BBBBBBBBBB");
    Assertions.assertEquals(dbController.getGNode("BBBBBBBBBB"), testNode3);
  }

  /** Second test for getGNode(nodeID) */
  @Test
  public void getNodeTester2() throws SQLException {
    Node testNode4 = new Node(517, 904, "H700000000");
    Assertions.assertEquals(dbController.getGNode("H700000000"), testNode4);
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
    Node testNode5 = new Node(25, 30, "testNodeT5");
    dbController.addNode("testNodeT5", 25, 30, 4, "Buil", "OFFI", "TESTNODE5", "T5", 'Z');
    Assertions.assertEquals(dbController.getGNode("testNodeT5"), testNode5);
    dbController.deleteNode("testNodeT5");
  }

  /**
   * Second test for addNode(nodeID, x, y, floor, building, nodeType, longName, shortName,
   * teamAssigned)
   */
  @Test
  public void addNodeTester2() throws SQLException {
    Node testNode6 = new Node(108, 55, "testNodeT6");
    dbController.addNode("testNodeT6", 108, 55, 4, "Buil", "OFFI", "TESTNODE6", "T6", 'Z');
    Assertions.assertEquals(dbController.getGNode("testNodeT6"), testNode6);
    dbController.deleteNode("testNodeT6");
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
        Pathfinder.cost(dbController.getGNode("AAAAAAAAAA"), dbController.getGNode("EEEEEEEEEE")),
        1196.75,
        0.05);
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
