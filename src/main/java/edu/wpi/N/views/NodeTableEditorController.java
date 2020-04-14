package edu.wpi.N.views;

import edu.wpi.N.Main;
import edu.wpi.N.database.DbNode;
import edu.wpi.N.database.dbController;
import edu.wpi.N.entities.TableNode;
import edu.wpi.N.models.CSVParser;
import java.io.InputStream;
import java.util.LinkedList;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

public class NodeTableEditorController {
  @FXML Button btn_saveChanges, btn_addNode, btn_delNode;
  @FXML TableView nodesTable;
  @FXML ChoiceBox menu_pickBuilding, menu_pickFloor, menu_pickNode;
  private LinkedList<String> buildings = new LinkedList<>();
  private LinkedList<TableNode> allNodes = new LinkedList<>();
  private ObservableList<TableNode> currentNodes = FXCollections.observableArrayList();
  private int currentFloor, totalFloorsInBuilding;
  private String currentBuilding;

  @FXML
  private void initialize() throws java.io.IOException {
    InputStream prototype = Main.class.getResourceAsStream("csv/TestNodes.csv");
    CSVParser.parseCSV(prototype);

    System.out.println(dbController.allNodes().size());

    // fillTable();
  }

  @FXML
  public void onPickBuilding(MouseEvent event) {
    // getFloorsInBuilding(currentBuilding);
    getNodesOnFloor(currentBuilding, 1);
    fillTable();
  }

  @FXML
  public void onPickFloor(MouseEvent event) {
    currentFloor = (int) menu_pickFloor.getValue();
    getNodesOnFloor(currentBuilding, currentFloor);
    fillTable();
  }

  @FXML
  public void onSaveClicked(MouseEvent event) {
    // nodesTable.
    saveEdits();
  }

  @FXML
  public void onTableRowClicked(MouseEvent event) {
    nodesTable.scrollTo(0);
  }

  @FXML
  public void onCellSelected(Event event) {}

  private void refreshAllNodes() {
    allNodes.clear();
    for (DbNode dn : dbController.allNodes()) {
      TableNode tn = new TableNode(dn);
      allNodes.add(tn);
    }
  }

  private void getBuildings() {
    buildings.clear();
    for (TableNode tn : allNodes) {
      if (!buildings.contains(tn.getBuilding())) {
        buildings.add(tn.getBuilding().get());
        menu_pickBuilding.getItems().add(tn.getBuilding());
      }
    }
    getFloorsInBuilding(buildings.getFirst());
  }

  private void getFloorsInBuilding(String building) {
    totalFloorsInBuilding = 0;
    for (TableNode tn : allNodes) {
      if (tn.getBuilding().get().equals(building)) {
        totalFloorsInBuilding = Math.max(totalFloorsInBuilding, tn.getFloor().get());
        menu_pickFloor.getItems().add(tn.getFloor());
      }
    }
    getNodesOnFloor(building, totalFloorsInBuilding);
  }

  private void getNodesOnFloor(String building, int floor) {
    trashEdits(); // Undo unsaved changes to current floor
    currentNodes.clear();
    for (DbNode dn : dbController.floorNodes(floor, building)) {
      TableNode tn = new TableNode(dn);
      currentNodes.add(tn);
      menu_pickNode.getItems().add(tn.getShortName());
    }
  }

  private void fillTable() {

    nodesTable.setItems(currentNodes);

    TableColumn<TableNode, StringProperty> nameCol = new TableColumn<>("Name");
    TableColumn<TableNode, StringProperty> longNameCol = new TableColumn<>("Long Name");
    TableColumn<TableNode, StringProperty> idCol = new TableColumn<>("ID");
    TableColumn<TableNode, StringProperty> typeCol = new TableColumn<>("Type");
    TableColumn<TableNode, IntegerProperty> xCol = new TableColumn<>("X");
    TableColumn<TableNode, IntegerProperty> yCol = new TableColumn<>("Y");
    TableColumn<TableNode, IntegerProperty> floorCol = new TableColumn<>("Floor");
    TableColumn<TableNode, StringProperty> buildingCol = new TableColumn<>("Building");
    TableColumn<TableNode, StringProperty> teamCol = new TableColumn<>("Team");

    nameCol.setCellValueFactory(new PropertyValueFactory("shortName"));
    longNameCol.setCellValueFactory(new PropertyValueFactory("longName"));
    idCol.setCellValueFactory(new PropertyValueFactory("nodeID"));
    typeCol.setCellValueFactory(new PropertyValueFactory("nodeType"));
    xCol.setCellValueFactory(new PropertyValueFactory("x"));
    yCol.setCellValueFactory(new PropertyValueFactory("y"));
    floorCol.setCellValueFactory(new PropertyValueFactory("floor"));
    buildingCol.setCellValueFactory(new PropertyValueFactory("building"));
    teamCol.setCellValueFactory(new PropertyValueFactory("teamAssigned"));

    nodesTable
        .getColumns()
        .setAll(nameCol, longNameCol, idCol, typeCol, xCol, yCol, floorCol, buildingCol, teamCol);
  }

  private void saveEdits() {}

  private void trashEdits() {}
}
