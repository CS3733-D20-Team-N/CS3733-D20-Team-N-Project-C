package edu.wpi.N.database;

import edu.wpi.N.models.Node;

import java.sql.*;

import java.util.LinkedList;

public class dbController {


  private static Statement statement;

    /**
     * Adds a node to the database including the nodeID for importing from the CSV
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
    public static boolean addNode(String nodeID, int x, int y,  int floor, String building, String nodeType, String longName, String shortName, char teamAssigned) throws SQLException {
        String query = "INSERT INTO nodes(" +
                "+nodeID, x, y, floor, building, nodeType, longName, shortName, teamAssigned) VALUES" +
                "'"+nodeID+"', "+x+", "+y+", "+floor+", '"+building+"', '"+nodeType+"', '"+longName+"', '"+shortName+"', ''+teamAssigned+''";
        statement.execute(query);
        System.out.println("Values Inserted");
        return true;
    }

    /**
     * Modifies a Node in the database. May change NodeID
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
    public static boolean modifyNode(String nodeID, int x, int y,  int floor, String building, String nodeType, String longName, String shortName, char teamAssigned) throws SQLException {
        String query= "UPDATE nodes SET x = "+x+", y = "+y+", floor = "+floor+", building = '"+ building +"', nodeType = '"+nodeType+"', " +
                "longName = '"+longName+"', shortName = '"+shortName+"', teamAssigned = ''+TeamAssigned+'' "+
                "WHERE nodeID = '"+nodeID+"'";
        statement.execute(query);
        return true;
    }

    /**
     * Moves a node to a new location
     * @param nodeID the node ID of the node you wish to move
     * @param x The new x value that you want to move the node to
     * @param y The new y value that you want to move the node to
     * @return True if valid and successful, false otherwise.
     */
    public static boolean moveNode(String nodeID, int x, int y){
        try{
            String query = "UPDATE nodes SET x = '"+x+"', y = '"+y+"' WHERE nodeID = '"+nodeID+"'";
            statement.executeQuery(query);
            return true;
        }catch(SQLException e){
            return false;
        }
    }

    /**
     * Deletes a node from the database
     * @param nodeID the nodeID of the node to be deleted
     * @return true if delete successful, false otherwise.
     */
    public static boolean deleteNode(String nodeID){
        try{
            String query = "DELETE FROM nodes WHERE (nodeID = '"+nodeID+"')";
            statement.executeQuery(query);
            return statement.getUpdateCount() > 0;
        }catch(SQLException e){
            return false;
        }

    }

    /**
     * Gets the node with the specified nodeID
     * @param nodeID nodeID of the node
     * @return the specified node
     */
    public static DbNode getNode(String nodeID) {
      try {
        String query = "SELECT * FROM nodes WHERE (nodeID = '" + nodeID + "')";
        ResultSet rs = statement.executeQuery(query);
        DbNode sample = null;
        if(rs.next()) sample = new DbNode(rs.getString("nodeID"), rs.getInt("x"), rs.getInt("y"), rs.getInt("floor"), rs.getString("building"), rs.getString("nodeType"), rs.getString("longName"), rs.getString("shortName"), rs.getString("teamAssigned").charAt(0));
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

    //not necessary when running the database in memory
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
              + "nodeID VARCHAR(10) NOT NULL PRIMARY KEY, "
              + "xcoord INT NOT NULL, "
              + "ycoord INT NOT NULL, "
              + "floor INT NOT NULL, "
              + "building VARCHAR(255) NOT NULL, "
              + "nodeType VARCHAR(4) NOT NULL, "
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
     * Gets the next available number for a particular node type for the purposes of making the nodeID
     * @param nodeType the node type to search for
     * @return A formatted string with three digits and leading zeros for the next available number
     * @throws SQLException if something goes wrong with the sql
     */
  private static String nextAvailNum(String nodeType) throws SQLException{
      return null;
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
      String nodeID = "I" + nodeType.toUpperCase() + nextAvailNum(nodeType) + "0" + floor;
      String query = "INSERT INTO nodes VALUES ('" + nodeID + "', " + x + "," + y + "," + floor + ",'" + building +
              "','" + nodeType + "','" + longName + "','" + shortName + "','I')";
      statement.execute(query);
    } catch(SQLException e){
      return false;
    }
    return true;
  }


    /**
     * Gets a list of all the nodes matching the specified searchQuery
     * @param searchQuery The string with which to search the nodes
     * @return A list of all nodes with a long name containing the searchQuery
     */
    public static LinkedList<DbNode> searchNode(String searchQuery){
        LinkedList<DbNode> searchList= new LinkedList<DbNode>();
        try{
            String result = "SELECT nodeID FROM * WHERE EXISTS" +
                    "(SELECT nodeID FROM '"+ searchQuery +"' WHERE *.nodeID = '"+ searchQuery +"'.nodeID";
            ResultSet rs = statement.executeQuery(result);
            while(rs.next()){
                DbNode chosenOne = new DbNode(rs.getString("nodeID"), rs.getInt("x"), rs.getInt("y"), rs.getInt("floor"), rs.getString("building"), rs.getString("nodeType"), rs.getString("longName"), rs.getString("shortName"), rs.getString("teamAssigned").charAt(0));
                searchList.add(chosenOne);
            }
        }catch(SQLException e){
            return null;
        }
        return searchList;
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
          rs = statement.executeQuery("SELECT xcoord, ycoord, nodeID FROM nodes WHERE nodeID = '" + nodeID + "'");
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
     * Gets a list of all the nodes on a floor except for invisible (HALL) nodes
     * @param floor
     * @param building
     * @return
     */
  public static LinkedList<DbNode> visNodes(int floor, String building){
      return null;
  }

    /**
     * Gets a list of all the nodes in the database.
     * @return A linked list of all the nodes in the database
     */
  public static LinkedList<DbNode> allNodes(){
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
  public static boolean addEdge(String nodeID1, String nodeID2){
    String edgeID = nodeID1 + "_" + nodeID2;
    try {
      //Look in to a more efficient way to do this, but it's probably OK for now
      String query = "SELECT * FROM edges WHERE (node1 = '" + nodeID1 + "' AND node2 = '" + nodeID2 + "') OR" +
              "(node2 = '" + nodeID1 + "' AND node1 = '" + nodeID2 + "')";
      ResultSet result = statement.executeQuery(query);

      if(result.next()){
        return false;
      }
      query = "INSERT INTO edges " +
              "VALUES ('" + edgeID + "', '" + nodeID1 + "', '" + nodeID2 + "')";
      statement.execute(query);
    } catch(SQLException e){
      return false;
    }

    return true;
  }

  /**
   * Removes an edge from the graph
   *
   * @param nodeID1 the nodeID of the first node
   * @param nodeID2 the nodeID of the second node
   * @return True if valid and successful, false otherwise
   */
  public static boolean removeEdge(String nodeID1, String nodeID2){
//    String query = "SELECT * FROM edges WHERE edgeID = '" + edgeID + "'";
//    ResultSet result = statement.executeQuery(query);
//
//    if(!result.next()){
//      return false;
//    }
//top method probably works, but is inefficient
    try {
      String query = "DELETE FROM edges WHERE (node1 = '" + nodeID1 + "' AND node2 = '" + nodeID2 + "') OR" +
              "(node2 = '" + nodeID1 + "' AND node1 = '" + nodeID2 + "')";
      statement.execute(query);
      return statement.getUpdateCount() > 0;
    }
    catch(SQLException e) {
      return false;
    }
  }
}
