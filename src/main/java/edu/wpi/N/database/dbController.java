package edu.wpi.N.database;

import edu.wpi.N.models.Node;
import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedList;

public class dbController {

  private static Statement statement;

  /**
   * Adds a node to the database including the nodeID for importing from the CSV
   *
   * @param nodeID The node ID
   * @param x The x coordinate of the node
   * @param y The y coordinate of the node
   * @param floor The floor of the node
   * @param building The building the node is in
   * @param nodeType The node's type
   * @param longName The node's longName
   * @param shortName The node's shortName
   * @param teamAssigned The team assigned to the Node
   * @return True if valid and inserted properly, false otherwise.
   */
  public static boolean addNode(
      String nodeID,
      int x,
      int y,
      int floor,
      String building,
      String nodeType,
      String longName,
      String shortName,
      char teamAssigned) {
    try {
      String query =
          "INSERT INTO nodes VALUES ('"
              + nodeID
              + "', "
              + x
              + ","
              + y
              + ","
              + floor
              + ",'"
              + building
              + "','"
              + nodeType
              + "','"
              + longName.replace("\'", "\\'")
              + "','"
              + shortName.replace("\'", "\\'")
              + "','"
              + teamAssigned
              + "')";
      statement.execute(query);
      // System.out.println("Values Inserted");
      return true;
    } catch (SQLException e) {
      return false;
    }
  }

  /**
   * Modifies a Node in the database. May change NodeID
   *
   * @param nodeID the ID of the node you wish to change
   * @param x The new x coordinate of the node
   * @param y The new y coordinate of the node
   * @param floor The new floor of the node
   * @param building The new building the node is in
   * @param longName The new node's longName
   * @param shortName The new node's shortName
   * @param teamAssigned The new team assigned to the Node
   * @return
   */
  public static boolean modifyNode(
      String nodeID,
      int x,
      int y,
      int floor,
      String building,
      String nodeType,
      String longName,
      String shortName,
      char teamAssigned) {
    String newID;
    try {
      if ((nodeID.substring(0, 5) + nodeID.substring(8))
          .equals(teamAssigned + nodeType.toUpperCase() + String.format("%02d", floor))) {
        if (nodeID.substring(1, 5).equals(nodeType)) {
          newID = teamAssigned + nodeID.substring(1, 8) + String.format("%02d", floor);
        } else {
          newID = teamAssigned + nodeType.toUpperCase() + nextAvailNum(nodeType) + "0" + floor;
        }
      } else newID = nodeID;
      String query =
          "UPDATE nodes SET nodeID = '"
              + newID
              + "', xcoord = "
              + x
              + ", ycoord = "
              + y
              + ", floor = "
              + floor
              + ", building = '"
              + building
              + "', nodeType = '"
              + nodeType
              + "', "
              + "longName = '"
              + longName.replace("\'", "\\'")
              + "', shortName = '"
              + shortName.replace("\'", "\\'")
              + "', teamAssigned = '"
              + teamAssigned
              + "'"
              + "WHERE nodeID = '"
              + nodeID
              + "'";
      statement.execute(query);
    } catch (SQLException e) {
      return false;
    }
    return true;
  }

  /**
   * Moves a node to a new location
   *
   * @param nodeID the node ID of the node you wish to move
   * @param x The new x value that you want to move the node to
   * @param y The new y value that you want to move the node to
   * @return True if valid and successful, false otherwise.
   */
  public static boolean moveNode(String nodeID, int x, int y) {
    try {
      String query =
          "UPDATE nodes SET xcoord = " + x + ", ycoord = " + y + " WHERE nodeID = '" + nodeID + "'";
      statement.execute(query);
      return true;
    } catch (SQLException e) {
      return false;
    }
  }

  /**
   * >>>>>>> 244323def286ea6969588df22b7d1d49bbce118f Deletes a node from the database
   *
   * @param nodeID the nodeID of the node to be deleted
   * @return true if delete successful, false otherwise.
   */
  public static boolean deleteNode(String nodeID) {
    try {
      String query = "DELETE FROM nodes WHERE (nodeID = '" + nodeID + "')";
      statement.execute(query);
      return statement.getUpdateCount() > 0;
    } catch (SQLException e) {
      return false;
    }
  }

  /**
   * Gets the node with the specified nodeID
   *
   * @param nodeID nodeID of the node
   * @return the specified node
   */
  public static DbNode getNode(String nodeID) {
    try {
      String query = "SELECT * FROM nodes WHERE (nodeID = '" + nodeID + "')";
      ResultSet rs = statement.executeQuery(query);
      DbNode sample = null;
      if (rs.next())
        sample =
            new DbNode(
                rs.getString("nodeID"),
                rs.getInt("xcoord"),
                rs.getInt("ycoord"),
                rs.getInt("floor"),
                rs.getString("building"),
                rs.getString("nodeType"),
                rs.getString("longName"),
                rs.getString("shortName"),
                rs.getString("teamAssigned").charAt(0));
      return sample;
    } catch (SQLException e) {
      return null;
    }
  }

  /** Initializes the database, should be run before interfacing with it. */
  public static void initDB() throws ClassNotFoundException, SQLException {
    Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
    Connection connection;
    String URL;

    URL = "jdbc:derby:memory:MapDB;create=true";
    connection = DriverManager.getConnection(URL);
    statement = connection.createStatement();
    String query;

    // not necessary when running the database in memory
    //    try {
    //      query = "DROP TABLE nodes";
    //      statement.execute(query);
    //    } catch (SQLException e) {
    //      if (!e.getSQLState().equals("42Y55")) throw e;
    //    }
    //
    //    try {
    //      query = "DROP TABLE edges";
    //      statement.execute(query);
    //    } catch (SQLException e) {
    //      if (!e.getSQLState().equals("42Y55")) throw e;
    //    }

    try {
      query =
          "CREATE TABLE nodes ("
              + "nodeID CHAR(10) NOT NULL PRIMARY KEY, "
              + "xcoord INT NOT NULL, "
              + "ycoord INT NOT NULL, "
              + "floor INT NOT NULL, "
              + "building VARCHAR(255) NOT NULL, "
              + "nodeType CHAR(4) NOT NULL, "
              + "longName VARCHAR(255) NOT NULL, "
              + "shortName VARCHAR(255) NOT NULL, "
              + "teamAssigned CHAR(1) NOT NULL"
              + ")";
      statement.execute(query);
    } catch (SQLException e) {
      if (!e.getSQLState().equals("X0Y32")) throw e;
    }

    try {
      query =
          "CREATE TABLE edges ("
              + "edgeID CHAR(21) NOT NULL PRIMARY KEY, "
              + "node1 CHAR(10) NOT NULL, "
              + "node2 CHAR(10) NOT NULL, "
              + "FOREIGN KEY (node1) REFERENCES nodes(nodeID), "
              + "FOREIGN KEY (node2) REFERENCES nodes(nodeID)"
              + ")";
      statement.execute(query);
    } catch (SQLException e) {
      if (!e.getSQLState().equals("X0Y32")) throw e;
    }
  }

  /**
   * Gets the next available number for a particular node type for the purposes of making the nodeID
   *
   * @param nodeType the node type to search for
   * @return A formatted string with three digits and leading zeros for the next available number
   * @throws SQLException if something goes wrong with the sql
   */
  private static String nextAvailNum(String nodeType) throws SQLException {
    String query = "SELECT nodeID FROM nodes WHERE nodeType = '" + nodeType + "'";
    ResultSet rs = statement.executeQuery(query);
    ArrayList<Integer> nums = new ArrayList<Integer>();
    while (rs.next()) {
      try {
        nums.add(Integer.parseInt(rs.getString("nodeID").substring(5, 8)));
      } catch (NumberFormatException e) {
        continue; // skip all nodes with an invalid nodeID
      }
    }
    int size = nums.size();
    int val;
    int nextVal;
    int lowest;
    for (int i = 0; i < size; i++) {
      if (nums.get(i) <= 0 || nums.get(i) < size) continue;
      val = nums.get(i);
      while (nums.get(val - 1) != val) {
        nextVal = nums.get(val - 1);
        nums.set(val - 1, val);
        val = nextVal;
        if (val <= 0 || val > size) break;
      }
    }
    lowest = size + 1;
    for (int i = 0; i < size; i++) {
      if (nums.get(i) != i + 1) {
        lowest = i + 1;
        break;
      }
    }
    return String.format("%03d", lowest);
  }
  /**
   * Adds a node to the database, the NodeID is generated automatically and the teamAssigned is I
   * indicating a node added through the interface.
   *
   * @param x The x coordinate of the node
   * @param y The y coordinate of the node
   * @param floor The floor of the node
   * @param building The building the node is in
   * @param nodeType The node's type
   * @param longName The node's longName
   * @param shortName The node's shortName
   * @return True if valid and inserted properly, false otherwise.
   */
  public static boolean addNode(
      int x,
      int y,
      int floor,
      String building,
      String nodeType,
      String longName,
      String shortName) {
    try {
      String nodeID =
          "I" + nodeType.toUpperCase() + nextAvailNum(nodeType) + String.format("%02d", floor);
      String query =
          "INSERT INTO nodes VALUES ('"
              + nodeID
              + "', "
              + x
              + ","
              + y
              + ","
              + floor
              + ",'"
              + building
              + "','"
              + nodeType
              + "','"
              + longName.replace("\'", "\\'")
              + "','"
              + shortName.replace("\'", "\\'")
              + "','I')";
      statement.execute(query);
      return true;
    } catch (SQLException e) {
      return false;
    }
  }

  /**
   * Gets a list of all the nodes matching the specified searchQuery
   *
   * @param searchQuery The string with which to search the nodes
   * @return A list of all nodes with a long name containing the searchQuery
   */
  public static LinkedList<DbNode> searchNode(String searchQuery) {
    String query = "SELECT * FROM nodes WHERE longName LIKE '%" + searchQuery + "%'";
    return getAllNodesSQL(query);
  }

  /**
   * Gets the graph-style node with the specified nodeID with a score of zero
   *
   * @param nodeID the nodeID of the node to fetch
   * @return the specified graph-style Node
   */
  public static Node getGNode(String nodeID) {
    ResultSet rs = null;
    int x = 0;
    int y = 0;
    String id = "";

    try {
      rs =
          statement.executeQuery(
              "SELECT xcoord, ycoord, nodeID FROM nodes WHERE nodeID = '" + nodeID + "'");
      rs.next();
      x = rs.getInt("xcoord");
      y = rs.getInt("ycoord");
      id = rs.getString("nodeID");
    } catch (SQLException e) {
      return null;
    }

    return new Node(x, y, id);
  }

  /**
   * Gets the graph-style nodes of all nodes adjacent to the specified Node
   *
   * @param nodeID
   * @return
   */
  public static LinkedList<Node> getGAdjacent(String nodeID) {
    LinkedList<Node> ret = new LinkedList<Node>();

    ResultSet rs = null;
    String query =
        "WITH connected_edges(node) AS(SELECT node1 AS nodeID FROM edges WHERE node2 = '"
            + nodeID
            + "' UNION "
            + "SELECT node2 AS nodeID FROM edges WHERE node1 = '"
            + nodeID
            + "') "
            + "SELECT nodes.xcoord, nodes.ycoord, nodes.nodeID FROM nodes "
            + "JOIN connected_edges ON connected_edges.nodeID = nodes.nodeID";

    try {
      rs = statement.executeQuery(query);
      while (rs.next()) {
        ret.add(
            new Node(
                rs.getInt("nodes.xcoord"),
                rs.getInt("nodes.ycoord"),
                rs.getString("nodes.nodeID")));
      }
    } catch (SQLException e) {
      return null;
    }

    return ret;
  }

  /**
   * Gets a list of all the nodes on the specified floor
   *
   * @param floor the floor from which you want to get all the nodes
   * @param building the building which has the floor from which you want to get all the nodes
   * @return a list of all the nodes with the specified floor
   */
  public static LinkedList<DbNode> floorNodes(int floor, String building) {
    String query =
        "SELECT * FROM nodes WHERE floor = " + floor + "AND building = '" + building + "'";
    return getAllNodesSQL(query);
  }

  /**
   * Gets a list of all the nodes on a floor except for invisible (HALL) nodes
   *
   * @param floor
   * @param building
   * @return
   */
  public static LinkedList<DbNode> visNodes(int floor, String building) {
    String query =
        "SELECT * FROM nodes WHERE floor = "
            + floor
            + "AND building = '"
            + building
            + "' AND NOT nodeType = 'HALL'";
    return getAllNodesSQL(query);
  }

  /**
   * Gets a list of all the nodes in the database.
   *
   * @return A linked list of all the nodes in the database
   */
  public static LinkedList<DbNode> allNodes() {
    LinkedList<DbNode> nodes = new LinkedList<DbNode>();
    String query = "SELECT * FROM nodes";
    return getAllNodesSQL(query);
  }

  /**
   * Gets all nodes that match a particular sql query returns them as a linked list
   *
   * @param sqlquery the sql query to select nodes with
   * @return a linked list of all the DbNodes which match the sql query
   */
  private static LinkedList<DbNode> getAllNodesSQL(String sqlquery) {
    try {
      LinkedList<DbNode> nodes = new LinkedList<DbNode>();
      ResultSet rs = statement.executeQuery(sqlquery);
      while (rs.next()) {
        nodes.add(
            new DbNode(
                rs.getString("nodeID"),
                rs.getInt("xcoord"),
                rs.getInt("ycoord"),
                rs.getInt("floor"),
                rs.getString("building"),
                rs.getString("nodeType"),
                rs.getString("longName"),
                rs.getString("shortName"),
                rs.getString("teamAssigned").charAt(0)));
      }
      return nodes;
    } catch (SQLException e) {
      return null;
    }
  }

  /**
   * Gets a list of all the nodes with an edge to the specified node
   *
   * @param nodeID The nodeID of the node for which you want the edges
   * @return All the nodes directly connected to the passed-in one
   */
  public static LinkedList<DbNode> getAdjacent(String nodeID) {
    LinkedList<DbNode> ret = new LinkedList<DbNode>();

    ResultSet rs = null;
    String query =
        "WITH connected_edges(node) AS(SELECT node1 AS nodeID FROM edges WHERE node2 = '"
            + nodeID
            + "' UNION "
            + "SELECT node2 AS nodeID FROM edges WHERE node1 = '"
            + nodeID
            + "') "
            + "SELECT nodes.* FROM nodes "
            + "JOIN connected_edges ON connected_edges.nodeID = nodes.nodeID";

    try {
      rs = statement.executeQuery(query);
      while (rs.next()) {
        ret.add(
            new DbNode(
                rs.getString("nodes.nodeID"),
                rs.getInt("nodes.xcoord"),
                rs.getInt("nodes.ycoord"),
                rs.getInt("nodes.floor"),
                rs.getString("nodes.building"),
                rs.getString("nodes.nodeType"),
                rs.getString("nodes.longName"),
                rs.getString("nodes.shortName"),
                rs.getString("nodes.teamAssigned").charAt(0)));
      }
    } catch (SQLException e) {
      e.printStackTrace();
      return null;
    }

    return ret;
  }

  /**
   * Adds an edge to the graph
   *
   * @param nodeID1 the nodeID of the first edge
   * @param nodeID2 the nodeID of the second edge
   * @return True if valid and successful, false otherwise
   */
  public static boolean addEdge(String nodeID1, String nodeID2) {
    String edgeID = nodeID1 + "_" + nodeID2;
    try {
      // Look in to a more efficient way to do this, but it's probably OK for now
      String query =
          "SELECT * FROM edges WHERE (node1 = '"
              + nodeID1
              + "' AND node2 = '"
              + nodeID2
              + "') OR"
              + "(node2 = '"
              + nodeID1
              + "' AND node1 = '"
              + nodeID2
              + "')";
      ResultSet result = statement.executeQuery(query);

      if (result.next()) {
        return false;
      }
      query =
          "INSERT INTO edges " + "VALUES ('" + edgeID + "', '" + nodeID1 + "', '" + nodeID2 + "')";
      statement.execute(query);
      return true;
    } catch (SQLException e) {
      return false;
    }
  }

  /**
   * Removes an edge from the graph
   *
   * @param nodeID1 the nodeID of the first node
   * @param nodeID2 the nodeID of the second node
   * @return True if valid and successful, false otherwise
   */
  public static boolean removeEdge(String nodeID1, String nodeID2) {
    //    String query = "SELECT * FROM edges WHERE edgeID = '" + edgeID + "'";
    //    ResultSet result = statement.executeQuery(query);
    //
    //    if(!result.next()){
    //      return false;
    //    }
    // top method probably works, but is inefficient
    try {
      String query =
          "DELETE FROM edges WHERE (node1 = '"
              + nodeID1
              + "' AND node2 = '"
              + nodeID2
              + "') OR"
              + "(node2 = '"
              + nodeID1
              + "' AND node1 = '"
              + nodeID2
              + "')";
      statement.execute(query);
      return statement.getUpdateCount() > 0;
    } catch (SQLException e) {
      return false;
    }
  }
}
