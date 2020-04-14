package edu.wpi.N.views;

import edu.wpi.N.Main;
import edu.wpi.N.database.DbNode;
import edu.wpi.N.database.dbController;
import edu.wpi.N.entities.TableNode;
import edu.wpi.N.models.CSVParser;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class NodeTableEditorController {
  @FXML Button btn_saveChanges, btn_next, btn_back;
  @FXML TableView nodesTable;
  @FXML ChoiceBox menu_pickBuilding, menu_pickFloor; // , menu_pickNode;
  private LinkedList<String> buildings = new LinkedList<>();
  private LinkedList<TableNode> allNodes = new LinkedList<>();
  private LinkedList<Number> floors = new LinkedList<>();
  private ObservableList<TableNode> currentNodes = FXCollections.observableArrayList();
  private int currentFloor;
  private String currentBuilding;

  @FXML
  private void initialize() throws java.io.IOException {
    InputStream prototype = Main.class.getResourceAsStream("csv/PrototypeNodes.csv");
    CSVParser.parseCSV(prototype);

    System.out.println(dbController.allNodes().size());

    refreshAllNodes();
    getBuildings();
    fillTable();
  }

  @FXML
  public void onPickBuilding(MouseEvent event) {
    getFloorsInBuilding(currentBuilding);
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

  @FXML
  private void onNextClicked(MouseEvent event) throws IOException {
    Stage stage = (Stage) btn_next.getScene().getWindow();
    Parent root = FXMLLoader.load(getClass().getResource("downloadMapCSVPage.fxml"));
    Scene scene = new Scene(root);
    stage.setScene(scene);
    stage.show();
  }

  @FXML
  private void onBackClicked(MouseEvent event) throws IOException {
    Stage stage = (Stage) btn_back.getScene().getWindow();
    Parent root = FXMLLoader.load(getClass().getResource("dataEditor.fxml"));
    Scene scene = new Scene(root);
    stage.setScene(scene);
    stage.show();
  }

  private void getBuildings() {
    buildings.clear();
    for (TableNode tn : allNodes) {
      if (!buildings.contains(tn.buildingProperty().getValue())) {
        buildings.add(tn.buildingProperty().getValue());
        menu_pickBuilding.getItems().add(tn.buildingProperty().getValue());
      }
    }
    getFloorsInBuilding(buildings.getFirst());
  }

  private void getFloorsInBuilding(String building) {
    for (TableNode tn : allNodes) {
      if (!floors.contains(tn.floorProperty().getValue())) {
        floors.add(tn.floorProperty().getValue());
        menu_pickFloor.getItems().add(tn.floorProperty().getValue());
      }
    }
    getNodesOnFloor(building, floors.getFirst().intValue());
  }

  private void getNodesOnFloor(String building, int floor) {
    trashEdits(); // Undo unsaved changes to current floor
    currentNodes.clear();
    for (DbNode dn : dbController.floorNodes(floor, building)) {
      TableNode tn = new TableNode(dn);
      currentNodes.add(tn);
      // menu_pickNode.getItems().add(tn.getShortName());
    }
  }

  private void fillTable() {

    TableColumn<TableNode, StringProperty> nameCol = new TableColumn<>("Name");
    TableColumn<TableNode, StringProperty> longNameCol = new TableColumn<>("Long Name");
    TableColumn<TableNode, StringProperty> idCol = new TableColumn<>("Node ID");
    TableColumn<TableNode, StringProperty> typeCol = new TableColumn<>("Type");
    TableColumn<TableNode, IntegerProperty> xCol = new TableColumn<>("X");
    TableColumn<TableNode, IntegerProperty> yCol = new TableColumn<>("Y");
    TableColumn<TableNode, IntegerProperty> floorCol = new TableColumn<>("Floor");
    TableColumn<TableNode, StringProperty> buildingCol = new TableColumn<>("Building");
    TableColumn<TableNode, StringProperty> teamCol = new TableColumn<>("Team");

    nameCol.setCellValueFactory(new PropertyValueFactory<TableNode, StringProperty>("shortName"));
    longNameCol.setCellValueFactory(
        new PropertyValueFactory<TableNode, StringProperty>("longName"));
    idCol.setCellValueFactory(new PropertyValueFactory<TableNode, StringProperty>("nodeID"));
    typeCol.setCellValueFactory(new PropertyValueFactory<TableNode, StringProperty>("nodeType"));
    xCol.setCellValueFactory(new PropertyValueFactory<TableNode, IntegerProperty>("x"));
    yCol.setCellValueFactory(new PropertyValueFactory<TableNode, IntegerProperty>("y"));
    floorCol.setCellValueFactory(new PropertyValueFactory<TableNode, IntegerProperty>("floor"));
    buildingCol.setCellValueFactory(
        new PropertyValueFactory<TableNode, StringProperty>("building"));
    teamCol.setCellValueFactory(
        new PropertyValueFactory<TableNode, StringProperty>("teamAssigned"));

    nodesTable
        .getColumns()
        .setAll(nameCol, longNameCol, idCol, typeCol, xCol, yCol, floorCol, buildingCol, teamCol);

    nodesTable.setItems(currentNodes);
  }

  private void saveEdits() {}

  private void trashEdits() {}
}
