package edu.wpi.N.entities;

import edu.wpi.N.database.DbNode;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;

// Re-maps DbNode data to a format that automatically updates a JavaFX TableViewer node
public class TableNode {

  private IntegerProperty x = new SimpleIntegerProperty();
  private IntegerProperty y = new SimpleIntegerProperty();
  private IntegerProperty floor = new SimpleIntegerProperty();
  private StringProperty nodeID = new SimpleStringProperty();
  private StringProperty building = new SimpleStringProperty();
  private StringProperty nodeType = new SimpleStringProperty();
  private StringProperty longName = new SimpleStringProperty();
  private StringProperty shortName = new SimpleStringProperty();
  private StringProperty teamAssigned = new SimpleStringProperty();

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

  public ObservableValue<Number> xProperty() {
    return x;
  }

  public ObservableValue<Number> yProperty() {
    return y;
  }

  public ObservableValue<Number> floorProperty() {
    return floor;
  }

  public ObservableValue<String> nodeIDProperty() {
    return nodeID;
  }

  public ObservableValue<String> buildingProperty() {
    return building;
  }

  public ObservableValue<String> nodeTypeProperty() {
    return nodeType;
  }

  public ObservableValue<String> longNameProperty() {
    return longName;
  }

  public ObservableValue<String> shortNameProperty() {
    return shortName;
  }

  public ObservableValue<String> teamAssignedProperty() {
    return teamAssigned;
  }
}
