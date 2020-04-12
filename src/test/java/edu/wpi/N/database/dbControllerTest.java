package edu.wpi.N.database;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.SQLException;
import java.util.LinkedList;

import edu.wpi.N.models.Node;
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
  public void testAddNodeID() {}

  // Noah
  @Test
  public void testModifyNode() {}

  // Noah
  @Test
  public void testMoveNode() {}

  // Noah
  @Test
  public void testDeleteNode() {}

  // Noah
  @Test
  public void testGetNode() {}

  // Chris
  @Test
  public void testAddNodeNoID() {
    /*dbController.addNode(1300, 1200, 4, "Faulkner", "DEPT", "Database", "Dept 7");
    assertTrue(dbController.floorNodes(4, "Faulkner").contains());*/
    assertTrue(dbController.addNode(1300, 1200, 4, "Faulkner", "DEPT", "Database", "Dept 7"));
  }

  // Chris
  @Test
  public void testSearchNode() {
    assertEquals(1,dbController.searchNode("Neurology").size());
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
  public void testGetGAdjacent() {
    dbController.addEdge("NHALL00104", "NHALL00204");
    dbController.addEdge("NHALL00104", "NDEPT00104");
    dbController.addEdge("NHALL00104", "NDEPT00204");

    LinkedList<DbNode> adjList = dbController.getAdjacent("NHALL00104");
    assertNotNull(adjList);//error here

    assertTrue(adjList.get(0).getNodeID().equals("NHALL00204"));
    assertTrue(adjList.get(1).getNodeID().equals("NDEPT00104"));
    assertTrue(adjList.get(2).getNodeID().equals("NDEPT00204"));

    assertTrue(
            adjList.contains(
                    new DbNode("NHALL00204", 1350, 1250, 4, "Faulkner", "HALL", "Hall 2", "Hall 2", 'N')));
    assertTrue(
            adjList.contains(
                    new DbNode(
                            "NDEPT00104", 1350, 950, 4, "Faulkner", "DEPT", "Cardiology", "Dept 1", 'N')));
    assertTrue(
            adjList.contains(
                    new DbNode(
                            "NDEPT00204", 1450, 950, 4, "Faulkner", "DEPT", "Neurology", "Dept 2", 'N')));

    assertEquals(3, adjList.size());
  }

  // Chris
  @Test
  public void testFloorNodes() {
    LinkedList<DbNode> nodeList = dbController.floorNodes(4, "Faulkner");
    assertEquals(2, nodeList.size());
    assertTrue(nodeList.get(0).getNodeID().equals("NDEPT00104"));
  }

  // Nick
  @Test
  // TODO: fix
  public void testVisNodes() {
    LinkedList<DbNode> vis = dbController.allNodes();
    assertNotNull(vis);
    assertEquals(3, vis.size());//error here

    assertTrue(
        vis.contains(
            new DbNode(
                "NDEPT00104", 1350, 950, 4, "Faulkner", "DEPT", "Cardiology", "Dept 1", 'N')));
    assertTrue(
        vis.contains(
            new DbNode(
                "NDEPT00204", 1450, 950, 4, "Faulkner", "DEPT", "Neurology", "Dept 2", 'N')));
    assertTrue(
        vis.contains(
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
  // TODO: fix
  public void testGetAdjacent() {
    dbController.addEdge("NHALL00104", "NHALL00204");
    dbController.addEdge("NHALL00104", "NDEPT00104");
    dbController.addEdge("NHALL00104", "NDEPT00204");

    LinkedList<DbNode> adj = dbController.getAdjacent("NHALL00104");
    assertNotNull(adj);//error here

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
  // TODO: fix
  public void testAddEdge() {
    assertTrue(dbController.addEdge("NHALL00104", "NHALL00204"));

    LinkedList<DbNode> adj = dbController.getAdjacent("NHALL00204");
    assertNotNull(adj);//error here
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
  // TODO: fix
  public void testRemoveEdge() {
    dbController.addEdge("NHALL00204", "NDEPT00104");

    assertTrue(dbController.removeEdge("NHALL00204", "NDEPT00104"));

    LinkedList<DbNode> adj = dbController.getAdjacent("NHALL00204");
    assertNotNull(adj);//error here
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
}
