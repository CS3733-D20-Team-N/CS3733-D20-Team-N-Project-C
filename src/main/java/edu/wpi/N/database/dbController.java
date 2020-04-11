package edu.wpi.N.database;

import edu.wpi.N.models.Node;

import java.sql.*;

import java.util.LinkedList;

public class dbController {


  private static Statement statement;
  /** Initializes the database, should be run before interfacing with it. */
  public static void initDB() throws ClassNotFoundException, SQLException {
    Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
    Connection connection;
    String URL;

    URL = "jdbc:derby:MapDB;create=true";
    connection = DriverManager.getConnection(URL);
    statement = connection.createStatement();
    String query;

    try {
      query = "DROP TABLE nodes";
      statement.execute(query);
    } catch (SQLException e) {
      if (!e.getSQLState().equals("42Y55")) throw e;
    }

    try {
      query = "DROP TABLE edges";
      statement.execute(query);
    } catch (SQLException e) {
      if (!e.getSQLState().equals("42Y55")) throw e;
    }

    try {
      query =
          "CREATE TABLE nodes ("
              + "nodeID VARCHAR(10) NOT NULL PRIMARY KEY, "
              + "xcoord INT NOT NULL, "
              + "ycoord INT NOT NULL, "
              + "floor INT NOT NULL, "
              + "building VARCHAR(255) NOT NULL, "
              + "nodeType VARCHAR(4) NOT NULL, "
              + "longName VARCHAR(255) NOT NULL, "
              + "shortName VARCHAR(255) NOT NULL, "
              + "teamAssigned CHAR NOT NULL"
              + ")";
      statement.execute(query);
    } catch (SQLException e) {
      if (!e.getSQLState().equals("X0Y32")) throw e;
    }

    try {
      query =
          "CREATE TABLE edges ("
              + "edgeID VARCHAR(21) NOT NULL PRIMARY KEY, "
              + "node1 VARCHAR(10) NOT NULL, "
              + "node2 VARCHAR(10) NOT NULL, "
              + "FOREIGN KEY (node1) REFERENCES nodes(nodeID), "
              + "FOREIGN KEY (node2) REFERENCES nodes(nodeID)"
              + ")";
      statement.execute(query);
    } catch (SQLException e) {
      if (!e.getSQLState().equals("X0Y32")) throw e;
    }
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
    return false;
  }

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
    return false;
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
    return false;
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
    return false;
  }

  /**
   * Deletes a node from the database
   *
   * @param nodeID the nodeID of the node to be deleted
   * @return
   */
  public static boolean deleteNode(String nodeID) {
    return false;
  }

  /**
   * Gets the node with the specified nodeID
   *
   * @param nodeID nodeID of the node
   * @return the specified node
   */
  public static DbNode getNode(String nodeID) {
    return null;
  }

  /**
   * Gets the graph-style node with the specified nodeID with a score of zero
   *
   * @param nodeID the nodeID of the node to fetch
   * @return the specified graph-style Node
   */
  public static Node getGNode(String nodeID) {
    return null;
  }

  /**
   * Gets the graph-style nodes of all nodes adjacent to the specified Node
   *
   * @param nodeID
   * @return
   */
  public static LinkedList<Node> getGAdjacent(String nodeID) {
    return null;
  }

  /**
   * Gets a list of all the nodes matching the specified query
   *
   * @param query The query with which to search the nodes
   * @return A list of all nodes with a long name containing the query
   */
  public static LinkedList<DbNode> searchNode(String query) {
    return null;
  }

  /**
   * Gets a list of all the nodes on the specified floor
   *
   * @param floor the floor from which you want to get all the nodes
   * @param building the building which has the floor from which you want to get all the nodes
   * @return a list of all the nodes with the specified floor
   */
  public static LinkedList<DbNode> floorNodes(int floor, String building) {
    return null;
  }

  /**
   * Gets a list of all the nodes with an edge to the specified node
   *
   * @param nodeID The nodeID of the node for which you want the edges
   * @return All the nodes directly connected to the passed-in one
   */
  public static LinkedList<DbNode> getAdjacent(String nodeID) {
    return null;
  }

  /**
   * Adds an edge to the graph
   *
   * @param nodeID1 the nodeID of the first edge
   * @param nodeID2 the nodeID of the second edge
   * @return True if valid and successful, false otherwise
   */
  public static boolean addEdge(String nodeID1, String nodeID2) throws SQLException {
    String edgeID = nodeID1 + "_" + nodeID2;

    String query = "SELECT * FROM edges WHERE edgeID = '" + edgeID + "'";
    ResultSet result = statement.executeQuery(query);

    if(result.next()){
      return false;
    }

    query = "INSERT INTO edges " +
            "VALUES ('" + edgeID + "', '" + nodeID1 + "', '" + nodeID2 + "')";
    statement.execute(query);

    return true;
  }

  /**
   * Removes an edge from the graph
   *
   * @param edgeID the edgeID of the node
   * @return True if valid and successful, false otherwise
   */
  public static boolean removeEdge(String edgeID) throws SQLException {
    String query = "SELECT * FROM edges WHERE edgeID = '" + edgeID + "'";
    ResultSet result = statement.executeQuery(query);

    if(!result.next()){
      return false;
    }

    query = "DELETE FROM edges WHERE edgeID = '" + edgeID + "'";
    statement.execute(query);

    return true;
  }
}
