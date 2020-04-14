package edu.wpi.N.entities;

import edu.wpi.N.database.DbNode;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;

// Re-maps DbNode data to a format that automatically updates a JavaFX TableViewer node
public class TableNode {

  private IntegerProperty x, y, floor;
  private StringProperty nodeID, building, nodeType, longName, shortName, teamAssigned;

  public TableNode(DbNode node) {
    x.set(node.getX());
    y.set(node.getY());
    floor.set(node.getFloor());
    nodeID.set(node.getNodeID());
    building.set(node.getBuilding());
    nodeType.set(node.getNodeType());
    longName.set(node.getLongName());
    shortName.set(node.getShortName());
    teamAssigned.set("" + node.getTeamAssigned());
  }

  public IntegerProperty getX() {
    return x;
  }

  public IntegerProperty getY() {
    return y;
  }

  public IntegerProperty getFloor() {
    return floor;
  }

  public StringProperty getID() {
    return nodeID;
  }

  public StringProperty getBuilding() {
    return building;
  }

  public StringProperty getNodeType() {
    return nodeType;
  }

  public StringProperty getLongName() {
    return longName;
  }

  public StringProperty getShortName() {
    return shortName;
  }

  public StringProperty getTeamAssigned() {
    return teamAssigned;
  }
}
