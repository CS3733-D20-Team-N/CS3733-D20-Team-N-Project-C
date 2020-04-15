package edu.wpi.N.entities;

import edu.wpi.N.database.DbNode;
import edu.wpi.N.database.dbController;

// Re-maps DbNode data to a format that automatically updates a JavaFX TableViewer node
public class TableNode {

  private Integer x, y, floor;
  private String nodeID, building, nodeType, longName, shortName;
  private Character teamAssigned;

  public TableNode(DbNode node) {
    x = node.getX();
    y = node.getY();
    floor = node.getFloor();
    nodeID = node.getNodeID();
    building = node.getBuilding();
    nodeType = node.getNodeType();
    longName = node.getLongName();
    shortName = node.getShortName();
    teamAssigned = node.getTeamAssigned();
  }

  /**
   * Gets the x value of the node
   *
   * @return the x value of the node
   */
  public int getX() {
    return x;
  }

  /**
   * Gets the floor of the node
   *
   * @return The node's floor
   */
  public int getFloor() {
    return floor;
  }

  /**
   * Gets the node's ID
   *
   * @return The ID of the node
   */
  public String getNodeID() {
    return nodeID;
  }

  /**
   * Gets the building of the node
   *
   * @return the Node's building
   */
  public String getBuilding() {
    return building;
  }

  /**
   * Gets the type of the node
   *
   * @return the node's type
   */
  public String getNodeType() {
    return nodeType;
  }

  /**
   * Gets the long name of the Node
   *
   * @return The node's long name
   */
  public String getLongName() {
    return longName;
  }

  /**
   * Gets the short name of the Node
   *
   * @return The node's short name
   */
  public String getShortName() {
    return shortName;
  }

  /**
   * Gets the team assigned to the node
   *
   * @return the team assigned to the node
   */
  public Character getTeamAssigned() {
    return teamAssigned;
  }

  /**
   * Gets the Y value of the node
   *
   * @return the node's Y value
   */
  public int getY() {
    return y;
  }

  public void SetX(Integer newX) {
    x = newX;
  }

  public void SetY(Integer newY) {
    y = newY;
  }

  public void SetFloor(Integer newFloor) {
    floor = newFloor;
  }

  public void SetNodeID(String newID) {
    nodeID = newID;
  }

  public void SetBuilding(String newBuilding) {
    building = newBuilding;
  }

  public void SetNodeType(String newNodeType) {
    nodeType = newNodeType;
  }

  public void SetLongName(String newLongName) {
    longName = newLongName;
  }

  public void SetShortName(String newShortName) {
    shortName = newShortName;
  }

  public void SetTeamAssigned(String newTeam) {
    teamAssigned = newTeam.charAt(0);
  }

  public void SetTeamAssigned(char newTeam) {
    teamAssigned = newTeam;
  }

  public void UpdateDbNode() {
    // Run modifyNode using this node's info
    dbController.modifyNode(
        nodeID, x, y, floor, building, nodeType, longName, shortName, teamAssigned);
  }
}
