package edu.wpi.N.database;

import static org.junit.jupiter.api.Assertions.*;

import edu.wpi.N.models.Node;
import java.sql.SQLException;
import java.util.LinkedList;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class dbControllerTest {
  @BeforeAll
  public static void setup() throws SQLException, ClassNotFoundException {
    dbController.initDB();
    dbController.addNode("NHALL00104", 1250, 850, 4, "Faulkner", "HALL", "Hall 1", "Hall 1", 'N');
    dbController.addNode(
        "NDEPT00104", 1350, 950, 4, "Faulkner", "DEPT", "Cardiology", "Dept 1", 'N');
    dbController.addNode(
        "NDEPT00204", 1450, 950, 4, "Faulkner", "DEPT", "Neurology", "Dept 2", 'N');
    dbController.addNode("NHALL00204", 1350, 1250, 4, "Faulkner", "HALL", "Hall 2", "Hall 2", 'N');
    dbController.addNode(
        "NDEPT01005", 1300, 1200, 5, "Faulkner", "DEPT", "Software Engineering", "Dept 10", 'N');
  }

  // Noah
  @Test
  public void testAddNodeID() {
    assertTrue(
        dbController.addNode(
            "NHALL01404", 771, 123, 4, "Faulkner", "HALL", "HALL 14", "Hall 14", 'N'));
    dbController.deleteNode("NHALL01404");
  }

  // Noah
  @Test
  public void testModifyNode() {
    dbController.addEdge("NHALL00204", "NHALL00104");
    dbController.modifyNode("NHALL00204", 123, 771, 3, "Faulkner", "SEXY", "DEPT 3", "Dept 3", 'N');
    dbTester.printDB();
    DbNode n = dbController.getNode("NSEXY00103");
    assertEquals(123, n.getX());
    assertEquals("SEXY", n.getNodeType());
    dbController.deleteNode("NSEXY00103");
    dbController.addNode("NHALL00204", 1350, 1250, 4, "Faulkner", "HALL", "Hall 2", "Hall 2", 'N');
  }

  // Noah
  @Test
  public void testMoveNode() {
    dbController.moveNode("NHALL00204", 135, 445);
    DbNode n = dbController.getNode("NHALL00204");
    assertTrue(n.getX() == 135 && n.getY() == 445);
    dbController.moveNode("NHALL00204", 1350, 1250);
  }

  // Noah
  @Test
  public void testDeleteNode() {
    dbController.addNode("NHALL00704", 1250, 850, 4, "Faulkner", "HALL", "Hall 7", "Hall 7", 'N');
    dbController.deleteNode("NHALL00704");
    assertNull(dbController.getNode("NHALL00704"));
  }

  // Noah
  @Test
  public void testGetNode() {
    DbNode n = dbController.getNode("NDEPT00204");
    assertTrue(n.getX() == 1450 && n.getY() == 950);
  }

  // Chris
  @Test
  // TODO: just see if it contains
  public void testAddNodeNoID() {
    /*dbController.addNode(1300, 1200, 4, "Faulkner", "DEPT", "Database", "Dept 7");
    assertTrue(dbController.floorNodes(4, "Faulkner").contains());*/
    assertNotNull(dbController.addNode(1300, 1200, 4, "Faulkner", "DEPT", "Database", "Dept 7"));
    DbNode node = dbController.addNode(1300, 1200, 4, "Faulkner", "DEPT", "Database", "Dept 7");
    assertTrue(node.getNodeID() != null);
    assertTrue(
        dbController
            .allNodes()
            .get(dbController.allNodes().size() - 1)
            .getLongName()
            .equals("Database"));
  }

  // Chris
  @Test
  public void testSearchNode() {
    assertEquals(1, dbController.searchNode("Neurology").size());
    assertTrue(dbController.searchNode("Cardiology").get(0).getNodeID().equals("NDEPT00104"));
  }

  // Chris
  @Test
  public void testGetGNode() {
    Node sample = dbController.getGNode("NDEPT01005");
    assertNotNull(sample);
    assertTrue(sample.getX() == 1300 && sample.getY() == 1200);
  }

  // Chris
  @Test
  public void testGetGAdjacent() throws SQLException, ClassNotFoundException {

    dbController.addEdge("NHALL00104", "NHALL00204");
    dbController.addEdge("NHALL00104", "NDEPT00104");
    dbController.addEdge("NHALL00104", "NDEPT00204");

    LinkedList<Node> adjList = dbController.getGAdjacent("NHALL00104");
    assertNotNull(adjList); // error here

    assertTrue(adjList.contains(new Node(1350, 1250, "NHALL00204")));
    assertTrue(adjList.contains(new Node(1350, 950, "NDEPT00104")));
    assertTrue(adjList.contains(new Node(1450, 950, "NDEPT00204")));

    assertEquals(3, adjList.size());
    dbController.removeEdge("NHALL00104", "NHALL00204");
    dbController.removeEdge("NHALL00104", "NDEPT00104");
    dbController.removeEdge("NHALL00104", "NDEPT00204");
  }

  // Chris
  @Test
  public void testFloorNodes() {
    LinkedList<DbNode> nodeList = dbController.floorNodes(4, "Faulkner");
    assertEquals(6, nodeList.size());
    // assertTrue(nodeList.get(0).getNodeID().equals("NDEPT01005"));
  }

  // Nick
  @Test
  public void testVisNodes() {
    LinkedList<DbNode> vis = dbController.visNodes(4, "Faulkner");
    assertNotNull(vis);
    assertEquals(2, vis.size());

    assertTrue(
        vis.contains(
            new DbNode(
                "NDEPT00104", 1350, 950, 4, "Faulkner", "DEPT", "Cardiology", "Dept 1", 'N')));
    assertTrue(
        vis.contains(
            new DbNode(
                "NDEPT00204", 1450, 950, 4, "Faulkner", "DEPT", "Neurology", "Dept 2", 'N')));
  }

  // Nick
  @Test
  public void testAllNodes() {
    LinkedList<DbNode> all = dbController.allNodes();
    assertNotNull(all);
    assertEquals(5, all.size());

    assertTrue(
        all.contains(
            new DbNode("NHALL00104", 1250, 850, 4, "Faulkner", "HALL", "Hall 1", "Hall 1", 'N')));
    assertTrue(
        all.contains(
            new DbNode(
                "NDEPT00104", 1350, 950, 4, "Faulkner", "DEPT", "Cardiology", "Dept 1", 'N')));
    assertTrue(
        all.contains(
            new DbNode(
                "NDEPT00204", 1450, 950, 4, "Faulkner", "DEPT", "Neurology", "Dept 2", 'N')));
    assertTrue(
        all.contains(
            new DbNode("NHALL00204", 1350, 1250, 4, "Faulkner", "HALL", "Hall 2", "Hall 2", 'N')));
    assertTrue(
        all.contains(
            new DbNode(
                "NDEPT01005",
                1300,
                1200,
                5,
                "Faulkner",
                "DEPT",
                "Software Engineering",
                "Dept 10",
                'N')));
  }

  // Nick
  @Test
  public void testGetAdjacent() {
    dbController.addEdge("NHALL00104", "NHALL00204");
    dbController.addEdge("NHALL00104", "NDEPT00104");
    dbController.addEdge("NHALL00104", "NDEPT00204");

    LinkedList<DbNode> adj = dbController.getAdjacent("NHALL00104");
    assertNotNull(adj);

    assertTrue(
        adj.contains(
            new DbNode("NHALL00204", 1350, 1250, 4, "Faulkner", "HALL", "Hall 2", "Hall 2", 'N')));
    assertTrue(
        adj.contains(
            new DbNode(
                "NDEPT00104", 1350, 950, 4, "Faulkner", "DEPT", "Cardiology", "Dept 1", 'N')));
    assertTrue(
        adj.contains(
            new DbNode(
                "NDEPT00204", 1450, 950, 4, "Faulkner", "DEPT", "Neurology", "Dept 2", 'N')));

    assertEquals(3, adj.size());

    dbController.removeEdge("NHALL00104", "NHALL00204");
    dbController.removeEdge("NHALL00104", "NDEPT00104");
    dbController.removeEdge("NHALL00104", "NDEPT00204");
  }

  // Nick
  @Test
  public void testAddEdge() {
    assertTrue(dbController.addEdge("NHALL00104", "NHALL00204"));

    LinkedList<DbNode> adj = dbController.getAdjacent("NHALL00204");
    assertNotNull(adj);
    assertTrue(
        adj.contains(
            new DbNode("NHALL00104", 1250, 850, 4, "Faulkner", "HALL", "Hall 1", "Hall 1", 'N')));

    adj = dbController.getAdjacent("NHALL00104");
    assertNotNull(adj);
    assertTrue(
        adj.contains(
            new DbNode("NHALL00204", 1350, 1250, 4, "Faulkner", "HALL", "Hall 2", "Hall 2", 'N')));

    dbController.removeEdge("NHALL00104", "NHALL00204");
  }

  // Nick
  @Test
  public void testRemoveEdge() {
    dbController.addEdge("NHALL00204", "NDEPT00104");

    assertTrue(dbController.removeEdge("NHALL00204", "NDEPT00104"));

    LinkedList<DbNode> adj = dbController.getAdjacent("NHALL00204");
    assertNotNull(adj);
    assertFalse(
        adj.contains(
            new DbNode(
                "NDEPT00104", 1350, 950, 4, "Faulkner", "DEPT", "Cardiology", "Dept 1", 'N')));

    adj = dbController.getAdjacent("NDEPT00104");
    assertNotNull(adj);
    assertFalse(
        adj.contains(
            new DbNode("NHALL00204", 1350, 1250, 4, "Faulkner", "HALL", "Hall 2", "Hall 2", 'N')));
  }

  @AfterAll
  public static void clearDB() {
    dbController.clearNodes();
  }
}
