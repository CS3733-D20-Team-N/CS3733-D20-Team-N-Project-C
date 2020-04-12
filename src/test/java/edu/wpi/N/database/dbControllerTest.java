package edu.wpi.N.database;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestTemplate;

import java.sql.SQLException;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

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

    //Noah
    @Test
    public void testAddNodeID(){}

    //Noah
    @Test
    public void testModifyNode(){}

    //Noah
    @Test
    public void testMoveNode(){}

    //Noah
    @Test
    public void testDeleteNode(){}

    //Noah
    @Test
    public void testGetNode(){}

    //Chris
    @Test
    public void testAddNodeNoID(){}

    //Chris
    @Test
    public void testSearchNode(){}

    //Chris
    @Test
    public void testGetGNode(){}

    //Chris
    @Test
    public void testGetGAdjacent(){}

    //Chris
    @Test
    public void testFloorNodes(){}

    //Nick
    @Test
    public void testVisNodes(){
        LinkedList<DbNode> vis = dbController.allNodes();
        assertNotNull(vis);
        assertEquals(3, vis.size());

        assertTrue(vis.contains(new DbNode(
                "NDEPT00104", 1350, 950, 4, "Faulkner", "DEPT", "Cardiology", "Dept 1", 'N')));
        assertTrue(vis.contains(new DbNode(
                "NDEPT00204", 1450, 950, 4, "Faulkner", "DEPT", "Neurology", "Dept 2", 'N')));
        assertTrue(vis.contains(new DbNode(
                "NDEPT01005", 1300, 1200, 5, "Faulkner", "DEPT", "Software Engineering", "Dept 10", 'N')));
    }

    //Nick
    @Test
    public void testAllNodes(){
        LinkedList<DbNode> all = dbController.allNodes();
        assertNotNull(all);
        assertEquals(5, all.size());

        assertTrue(all.contains(new DbNode(
                "NHALL00104", 1250, 850, 4, "Faulkner", "HALL", "Hall 1", "Hall 1", 'N')));
        assertTrue(all.contains(new DbNode(
                "NDEPT00104", 1350, 950, 4, "Faulkner", "DEPT", "Cardiology", "Dept 1", 'N')));
        assertTrue(all.contains(new DbNode(
                "NDEPT00204", 1450, 950, 4, "Faulkner", "DEPT", "Neurology", "Dept 2", 'N')));
        assertTrue(all.contains(new DbNode(
                "NHALL00204", 1350, 1250, 4, "Faulkner", "HALL", "Hall 2", "Hall 2", 'N')));
        assertTrue(all.contains(new DbNode(
                "NDEPT01005", 1300, 1200, 5, "Faulkner", "DEPT", "Software Engineering", "Dept 10", 'N')));
    }

    //Nick
    @Test
    public void testGetAdjacent(){
        dbController.addEdge("NHALL00104", "NHALL00204");
        dbController.addEdge("NHALL00104", "NDEPT00104");
        dbController.addEdge("NHALL00104", "NDEPT00204");

        LinkedList<DbNode> adj = dbController.getAdjacent("NHALL00104");
        assertNotNull(adj);

        assertTrue(adj.contains(new DbNode(
                "NHALL00204", 1350, 1250, 4, "Faulkner", "HALL", "Hall 2", "Hall 2", 'N')));
        assertTrue(adj.contains(new DbNode(
                "NDEPT00104", 1350, 950, 4, "Faulkner", "DEPT", "Cardiology", "Dept 1", 'N')));
        assertTrue(adj.contains(new DbNode(
                "NDEPT00204", 1450, 950, 4, "Faulkner", "DEPT", "Neurology", "Dept 2", 'N')));

        assertEquals(3, adj.size());

        dbController.removeEdge("NHALL00104", "NHALL00204");
        dbController.removeEdge("NHALL00104", "NDEPT00104");
        dbController.removeEdge("NHALL00104", "NDEPT00204");
    }

    //Nick
    @Test
    public void testAddEdge(){
        assertTrue(dbController.addEdge("NHALL00104", "NHALL00204"));

        LinkedList<DbNode> adj = dbController.getAdjacent("NHALL00204");
        assertNotNull(adj);
        assertTrue(adj.contains(new DbNode(
                "NHALL00104", 1250, 850, 4, "Faulkner", "HALL", "Hall 1", "Hall 1", 'N')));

        adj = dbController.getAdjacent("NHALL00104");
        assertNotNull(adj);
        assertTrue(adj.contains(new DbNode(
                "NHALL00204", 1350, 1250, 4, "Faulkner", "HALL", "Hall 2", "Hall 2", 'N')));

        dbController.removeEdge("NHALL00104", "NHALL00204");
    }

    //Nick
    @Test
    public void testRemoveEdge(){
        dbController.addEdge("NHALL00204", "NDEPT00104");

        assertTrue(dbController.removeEdge("NHALL00204", "NDEPT00104"));

        LinkedList<DbNode> adj = dbController.getAdjacent("NHALL00204");
        assertNotNull(adj);
        assertFalse(adj.contains(new DbNode(
                "NDEPT00104", 1350, 950, 4, "Faulkner", "DEPT", "Cardiology", "Dept 1", 'N')));

        adj = dbController.getAdjacent("NDEPT00104");
        assertNotNull(adj);
        assertFalse(adj.contains(new DbNode(
                "NHALL00204", 1350, 1250, 4, "Faulkner", "HALL", "Hall 2", "Hall 2", 'N')));
    }

}
