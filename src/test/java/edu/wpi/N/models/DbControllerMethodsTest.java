package edu.wpi.N.models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import edu.wpi.N.Main;
import edu.wpi.N.database.DbNode;
import edu.wpi.N.database.dbController;
import java.io.InputStream;
import java.sql.SQLException;

import java.util.LinkedList;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class DbControllerMethodsTest {

  //  TODO: initialize database, then call Parser with the test CSV file you need
  @BeforeAll
  public static void initializeTest() throws SQLException, ClassNotFoundException {
    dbController.initDB();
    InputStream input = Main.class.getResourceAsStream("csv/MapEnodes.csv");
    CSVParser.parseCSV(input);
    InputStream input2 = Main.class.getResourceAsStream("csv/MapEedges.csv");
    CSVParser.parseCSV(input2);
  }

  // Then, conduct the tests
  //  InputStream input = Main.class.getResourceAsStream("csv/MapEnodes.csv");
  //  CSVParser parser = new CSVParser();
  //  Graph testGraph = parser.parseCSV(input);
  //  Node startNode = testGraph.getNode("MOHSClinic");
  //  Node endNode = testGraph.getNode("HVMANeurology");
  //  Pathfinder newPath = new Pathfinder(testGraph, startNode, endNode);
  //  Path myPath = newPath.findPath();
  //

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
  * Tests that getEdges(nodeID) will return null if the node is not in the graph and if the node
  * does not have any edges
  */
  @Test
  public void getEdgesNullTester() throws SQLException {
  Node testNode = new Node(2.345, 5.5657, "TESTNODE2");
  assertNull(dbController.getGAdjacent("TESTNODE2"));

  dbController.addNode("TESTNODE2", 23, 345, 4, "Foisie", "sdfkjd", "fskjd", "sdfk", 'N');
  assertNull(dbController.getGAdjacent("TESTNODE2"));
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
   * Tests that addEdges(nodeID1,nodeID2) will create a list of edges for a node that currently has
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
  * Tests that a duplicate edge will not be added if you run addEdge(nodeID1,nodeID2) on an edge
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
          Pathfinder.heuristic(dbController.getGNode("MOHSClinic"), dbController.getGNode("HALL1")),
         0.77,
         0.0001);
  }

  /**
  * Tests that heuristic(currNode, endNode) returns the correct calculated value for a node that is
  * not in the graph
  */
  @Test
  public void heuristicNotInGraphTester() {
  Node testNode1 = new Node(1, 0, "TESTNODE3");
  Node testNode2 = new Node(0, 1, "TESTNODE4");
  Assertions.assertEquals(Pathfinder.heuristic(testNode1, testNode2), 2, 0.0001);
   }

    /**
     * Tests that getNode(nodeID) returns the correct node when given a nodeID that is in
     * the database
     */
    @Test
    public void getNodeTester() throws SQLException {
      DbNode testNode = new DbNode("testNode", 23, 345, 4, "Foisie", "sdfkjd", "fskjd", "sdfk", 'N');
      dbController.addNode("testNode", 23, 345, 4, "Foisie", "sdfkjd", "fskjd", "sdfk", 'N');
      Assertions.assertEquals(dbController.getNode("MOHSClinic"), testNode);

      DbNode testNode2 = new DbNode("testNode2", 25, 347, 4, "AK", "sdfkjd", "fskjd", "sdfk", 'N');
      dbController.addNode("testNode2", 25, 347, 4, "AK", "sdfkjd", "fskjd", "sdfk", 'N');
      Assertions.assertEquals(dbController.getNode("testNode2"), testNode2);
    }

    /**
     * Tests that getNode(nodeID) returns null when given an nodeID that isn't in the database
     * or doesn't exist at all
     */
    @Test
    public void getNodeNullTester() {
      // Call getNode on node that doesn't exist at all
      Assertions.assertNull(dbController.getNode("test1"));

      // Call getNode on node that exists but not isn't in the graph
      DbNode testNode = new DbNode("test2", 10, 200, 4, "Fuller", "sdfkjd", "fskjd", "sdfk", 'N');
      Assertions.assertNull(dbController.getNode("test2"));
    }

    /** Tests that addNode(node) adds the given node to the database */
    @Test
    public void addNodeTester() throws SQLException {
      DbNode testNode1 = new DbNode("testNode1", 20, 340, 4, "Salisbury", "sdfkjd", "fskjd", "sdfk", 'N');
      dbController.addNode("testNode1", 20, 340, 4, "Salisbury", "sdfkjd", "fskjd", "sdfk", 'N');
      Assertions.assertEquals(dbController.getNode("testNode1"), testNode1);

      DbNode testNode2 = new DbNode("testNode2", 15, 330, 4, "Goddard", "sdfkjd", "fskjd", "sdfk", 'N');
      dbController.addNode("testNode2", 15, 330, 4, "Goddard", "sdfkjd", "fskjd", "sdfk", 'N');
      Assertions.assertEquals(dbController.getNode("testNode2"), testNode2);
    }

  //
  //  /**
  //   * Tests that addNode(node) doesn't add the given node to the database if it has the same ID as
  //   * another node in the database (future test to implement once functionality is added)
  //   */
  //
    /**
    *Tests that cost(currNode, nextNode) returns the correct cost value for nodes in the database
    */
    @Test
    public void costTester() {
      Assertions.assertEquals(
          Pathfinder.cost(dbController.getGNode("MOHSClinic"), dbController.getGNode("Neurology")),
          2.641,
          0.005);
    }

    /**
     * Tests that cost(currNode, nextNode) returns the correct value for nodes even if they aren't
     * in a database
     */
    @Test
    public void costNotInGraphTester() {
      Node testNode = new Node(0, 0, "node1");
      Node testNode2 = new Node(3, 4, "node2");
      Assertions.assertEquals(Pathfinder.cost(testNode, testNode2), 5, 0.0001);
    }
}
