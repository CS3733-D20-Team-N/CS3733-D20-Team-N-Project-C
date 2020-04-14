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

  /** Tests that findPath returns a Path object with the best route from H9 to EEE */
  @Test
  public void findPathNormalCase() {
    LinkedList<DbNode> actualPath = new LinkedList<DbNode>();
    actualPath.add(dbController.getNode("H100000001"));
    actualPath.add(dbController.getNode("H900000000"));
    actualPath.add(dbController.getNode("H120000000"));
    actualPath.add(dbController.getNode("H130000000"));
    actualPath.add(dbController.getNode("EEEEEEEEEE"));

    Path testingPath = Pathfinder.findPath("H100000001", "EEEEEEEEEE");

    for (int i = 0; i < actualPath.size(); i++) {
      Assertions.assertEquals(
          actualPath.get(i).getNodeID(), testingPath.getPath().get(i).getNodeID());
    }
  }

  /**
   * Tests that findPath method return a Path object with route consisting of 2 Nodes, since start
   * and end nodes are neighbors
   */
  @Test
  public void findPathStartIsNeighborWithEndNode() {

    LinkedList<DbNode> actualPath = new LinkedList<DbNode>();

    actualPath.add(dbController.getNode("H120000000"));
    actualPath.add(dbController.getNode("H130000000"));

    Path testingPath = Pathfinder.findPath("H120000000", "H130000000");

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
        NullPointerException.class, () -> Pathfinder.findPath("H120000000", "NonExistentNode"));
  }

  /**
   * Tests that findPath method throws NullPointerException if start Node doesn't have a connection
   * to any node (including end node)
   */
  @Test
  public void findPathStartNodeHasNoEdges() {
    Assertions.assertThrows(
        NullPointerException.class, () -> Pathfinder.findPath("NonExistentNode", "H120000000"));
  }

  /**
   * Tests that findPath method returns a Path object with only one node in its route since Start
   * Node = End Node
   */
  @Test
  public void findPathEndIsStartNode() {
    LinkedList<DbNode> actualPath = new LinkedList<DbNode>();

    actualPath.add(dbController.getNode("H120000000"));
    Path testingPath = Pathfinder.findPath("H120000000", "H120000000");

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

