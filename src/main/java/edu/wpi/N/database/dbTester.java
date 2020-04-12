package edu.wpi.N.database;

import static edu.wpi.N.database.dbController.*;

import edu.wpi.N.models.Node;
import java.util.LinkedList;

public class dbTester {
  public static void main(String[] args) throws Exception {
    initDB();
    //    Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
    //    Connection connection;
    //    String URL;
    //
    //    URL = "jdbc:derby:memory:MapDB;create=true";
    //    connection = DriverManager.getConnection(URL);
    //    Statement statement = connection.createStatement();
    //    String query =
    //        "INSERT INTO nodes VALUES ('NHALL00104', 1250, 850, 4, 'Faulkner', 'HALL', 'Hall 1',
    // 'Hall 1', 'N'), "
    //            + "('NDEPT00104', 1350, 950, 4, 'Faulkner', 'DEPT', 'Cardiology', 'Dept 1', 'N'),
    // "
    //            + "('NDEPT00204', 1450, 950, 4, 'Faulkner', 'DEPT', 'Neurology', 'Dept 2', 'N'), "
    //            + "('NHALL00204', 1350, 1250, 4, 'Faulkner', 'HALL', 'Hall 2', 'Hall 2', 'N'), "
    //            + "('NDEPT01005', 1300, 1200, 5, 'Faulkner', 'DEPT', 'Software Engineering', 'Dept
    // 10', 'N')";
    //    statement.execute(query);

    dbController.addNode("NHALL00104", 1250, 850, 4, "Faulkner", "HALL", "Hall 1", "Hall 1", 'N');
    dbController.addNode(
        "NDEPT00104", 1350, 950, 4, "Faulkner", "DEPT", "Cardiology", "Dept 1", 'N');
    dbController.addNode(
        "NDEPT00204", 1450, 950, 4, "Faulkner", "DEPT", "Neurology", "Dept 2", 'N');
    dbController.addNode("NHALL00204", 1350, 1250, 4, "Faulkner", "HALL", "Hall 2", "Hall 2", 'N');
    dbController.addNode(
        "NDEPT01005", 1300, 1200, 5, "Faulkner", "DEPT", "Software Engineering", "Dept 10", 'N');
    boolean testaddNode =
        addNode("NDEPT00304", 1550, 1200, 4, "Faulkner", "DEPT", "epistemology", "Dept 3", 'N');
    if (testaddNode) {
      System.out.println("addNode Worked");
    }
    boolean testaddNode2 = addNode(1250, 950, 4, "Faulkner", "HALL", "Hall 3", "Hall 3");
    if (testaddNode2) {
      System.out.println("addNode without nodeID worked");
    }
    boolean testmodifyNode =
        modifyNode("NDEPT00104", 1300, 900, 4, "WPI", "DEPT", "Computer Room", "Dept 1", 'N');
    if (testmodifyNode) {
      System.out.println("modifyNode worked");
    }
    boolean testmoveNode = moveNode("NDEPT00304", 1500, 1100);
    if (testmoveNode) {
      System.out.println("moveNode worked");
    }
    boolean testdeleteNode = deleteNode("NDEPT00304");
    if (testdeleteNode) {
      System.out.println("deleteNode worked");
    }
    DbNode testgetNode = getNode("NDEPT00204");
    if (testgetNode.getX() == 1450 && testgetNode.getY() == 950) {
      System.out.println("getNode Worked");
    }
    Node testGNode = getGNode("NDEPT01005");
    if (testGNode.getX() == 1500 && testGNode.getY() == 1100) {
      System.out.println("getGNode worked");
    }
    LinkedList<DbNode> testfloorNodes = floorNodes(5, "Faulkner");
    if (testfloorNodes.get(0).getNodeID() == "NDEPT01005") {
      System.out.println("floorNode worked");
    }
    LinkedList<DbNode> testvisNodes = visNodes(4, "Faulkner");
    if (testvisNodes.get(0).getNodeID() == "NDEPT00104") {
      System.out.println("visNodes worked");
    }
    addNode("NDEPT01104", 1950, 1200, 4, "Faulkner", "DEPT", "Neucleology", "Dept 11", 'N');
    LinkedList<DbNode> testallNodes = allNodes();
    if (testallNodes.get(testallNodes.size() - 1).getNodeID() == "NDEPT01104") {
      System.out.println("allNodes worked");
    }
  }
}
