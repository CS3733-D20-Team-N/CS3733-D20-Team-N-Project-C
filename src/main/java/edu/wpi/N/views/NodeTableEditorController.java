package edu.wpi.N.views;

import edu.wpi.N.Main;
import edu.wpi.N.database.DbNode;
import edu.wpi.N.database.dbController;
import edu.wpi.N.entities.TableNode;
import edu.wpi.N.models.CSVParser;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.converter.CharacterStringConverter;
import javafx.util.converter.IntegerStringConverter;

public class NodeTableEditorController {
  @FXML Button btn_saveChanges, btn_next, btn_back, btn_addNode, btn_delNode;
  @FXML TableView<TableNode> nodesTable;
  @FXML ComboBox<String> menu_pickBuilding;
  @FXML ComboBox<Integer> menu_pickFloor;
  @FXML Label lbl_changesSaved;
  private LinkedList<String> buildings = new LinkedList<>();
  private LinkedList<TableNode> allNodes = new LinkedList<>();
  private LinkedList<Integer> floors = new LinkedList<>();
  private ObservableList<TableNode> currentNodes = FXCollections.observableArrayList();
  private LinkedList<TableNode> editedNodes =
      new LinkedList<>(); // Stores field changes until database is saved
  private String currentBuilding;

  @FXML
  private void initialize() throws java.io.IOException {

    // Set up table column parameters

    TableColumn<TableNode, String> nameCol = new TableColumn<>("Name");
    TableColumn<TableNode, String> longNameCol = new TableColumn<>("Long Name");
    TableColumn<TableNode, String> idCol = new TableColumn<>("Node ID");
    TableColumn<TableNode, String> typeCol = new TableColumn<>("Type");
    TableColumn<TableNode, Integer> xCol = new TableColumn<>("X");
    TableColumn<TableNode, Integer> yCol = new TableColumn<>("Y");
    TableColumn<TableNode, Integer> floorCol = new TableColumn<>("Floor");
    TableColumn<TableNode, String> buildingCol = new TableColumn<>("Building");
    TableColumn<TableNode, Character> teamCol = new TableColumn<>("Team");

    // Set columns

    nameCol.setCellValueFactory(new PropertyValueFactory<>("ShortName"));
    longNameCol.setCellValueFactory(new PropertyValueFactory<>("LongName"));
    idCol.setCellValueFactory(new PropertyValueFactory<>("NodeID"));
    typeCol.setCellValueFactory(new PropertyValueFactory<>("NodeType"));
    xCol.setCellValueFactory(new PropertyValueFactory<>("X"));
    yCol.setCellValueFactory(new PropertyValueFactory<>("Y"));
    floorCol.setCellValueFactory(new PropertyValueFactory<>("Floor"));
    buildingCol.setCellValueFactory(new PropertyValueFactory<>("Building"));
    teamCol.setCellValueFactory(new PropertyValueFactory<>("TeamAssigned"));

    // Set functions to be called when fields are edited

    nameCol.setCellFactory(TextFieldTableCell.forTableColumn());
    nameCol.setOnEditCommit(
        (TableColumn.CellEditEvent<TableNode, String> t) -> {
          t.getRowValue().SetShortName(t.getNewValue());
          cacheEdit(t.getRowValue());
        });

    longNameCol.setCellFactory(TextFieldTableCell.forTableColumn());
    longNameCol.setOnEditCommit(
        (TableColumn.CellEditEvent<TableNode, String> t) -> {
          t.getRowValue().SetLongName(t.getNewValue());
          cacheEdit(t.getRowValue());
        });

    idCol.setCellFactory(TextFieldTableCell.forTableColumn());
    idCol.setOnEditCommit(
        (TableColumn.CellEditEvent<TableNode, String> t) -> {
          t.getRowValue().SetNodeID(t.getNewValue());
          cacheEdit(t.getRowValue());
        });

    typeCol.setCellFactory(TextFieldTableCell.forTableColumn());
    typeCol.setOnEditCommit(
        (TableColumn.CellEditEvent<TableNode, String> t) -> {
          t.getRowValue().SetNodeType(t.getNewValue());
          cacheEdit(t.getRowValue());
        });

    xCol.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
    xCol.setOnEditCommit(
        (TableColumn.CellEditEvent<TableNode, Integer> t) -> {
          t.getRowValue().SetX(t.getNewValue());
          cacheEdit(t.getRowValue());
        });

    yCol.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
    yCol.setOnEditCommit(
        (TableColumn.CellEditEvent<TableNode, Integer> t) -> {
          t.getRowValue().SetY(t.getNewValue());
          cacheEdit(t.getRowValue());
        });

    floorCol.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
    floorCol.setOnEditCommit(
        (TableColumn.CellEditEvent<TableNode, Integer> t) -> {
          t.getRowValue().SetFloor(t.getNewValue());
          cacheEdit(t.getRowValue());
        });

    buildingCol.setCellFactory(TextFieldTableCell.forTableColumn());
    buildingCol.setOnEditCommit(
        (TableColumn.CellEditEvent<TableNode, String> t) -> {
          t.getRowValue().SetBuilding(t.getNewValue());
          cacheEdit(t.getRowValue());
        });

    teamCol.setCellFactory(TextFieldTableCell.forTableColumn(new CharacterStringConverter()));
    teamCol.setOnEditCommit(
        (TableColumn.CellEditEvent<TableNode, Character> t) -> {
          t.getRowValue().SetTeamAssigned(t.getNewValue());
          cacheEdit(t.getRowValue());
        });

    // Place columns
    nodesTable
        .getColumns()
        .setAll(nameCol, longNameCol, idCol, typeCol, xCol, yCol, floorCol, buildingCol, teamCol);

    // Load PrototypeNodes CSV file as per Part C instructions
    InputStream nodesToAdd = Main.class.getResourceAsStream("csv/PrototypeNodes.csv");

    // Add nodes from CSV to database
    CSVParser.parseCSV(nodesToAdd);

    // Get nodes from database and fill table
    refreshAllNodes();
    getBuildings();
    fillTable();
  }

  /// FXML methods

  // Called when user has changed which building they want to look at
  @FXML
  public void onPickBuilding(ActionEvent event) {
    currentBuilding = menu_pickBuilding.getValue();
    getFloorsInBuilding(currentBuilding);
  }

  // Called when user has changed which floor they want to look at
  @FXML
  public void onPickFloor(ActionEvent event) {
    int currentFloor = menu_pickFloor.getValue();
    getNodesOnFloor(currentBuilding, currentFloor);
  }

  // Called when user clicks "Save" button
  @FXML
  public void onSaveClicked(MouseEvent event) {
    saveEdits();
  }

  // Called when user clicks "+ Node" button
  @FXML
  public void onAddNodeClicked(MouseEvent event) {}

  // Called when user clicks "- Node" button
  @FXML
  public void onDelNodeClicked(MouseEvent event) {}

  // Called when user clicks "Next" navigation button
  @FXML
  private void onNextClicked(MouseEvent event) throws IOException {
    trashEdits();
    Stage stage = (Stage) btn_next.getScene().getWindow();
    Parent root = FXMLLoader.load(getClass().getResource("downloadMapCSVPage.fxml"));
    Scene scene = new Scene(root);
    stage.setScene(scene);
    stage.show();
  }

  // Called when user clicks "Back" navigation button
  @FXML
  private void onBackClicked(MouseEvent event) throws IOException {
    trashEdits();
    Stage stage = (Stage) btn_back.getScene().getWindow();
    Parent root = FXMLLoader.load(getClass().getResource("kioskHome.fxml"));
    Scene scene = new Scene(root);
    stage.setScene(scene);
    stage.show();
  }

  /// Helper methods

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
        buildings.add(tn.getBuilding());
        menu_pickBuilding.getItems().add(tn.getBuilding());
      }
    }
    menu_pickBuilding.setValue(buildings.getFirst());
    getFloorsInBuilding(buildings.getFirst());
  }

  private void getFloorsInBuilding(String building) {
    for (TableNode tn : allNodes) {
      if (!floors.contains(tn.getFloor())) {
        floors.add(tn.getFloor());
        menu_pickFloor.getItems().add(tn.getFloor());
      }
    }
    menu_pickFloor.setValue(floors.getFirst());
    getNodesOnFloor(building, floors.getFirst());
  }

  private void getNodesOnFloor(String building, int floor) {
    trashEdits(); // Undo unsaved changes to current floor
    currentNodes.clear();
    for (TableNode tn : allNodes) {
      if (tn.getFloor() == floor && tn.getBuilding().equals(building)) {
        currentNodes.add(tn);
      }
    }
    fillTable();
  }

  private void fillTable() {
    nodesTable.setItems(currentNodes);
  }

  private void cacheEdit(TableNode edited) {
    if (editedNodes.size() < 1) {
      btn_saveChanges.setDisable(false); // Enable Save button
    }

    editedNodes.removeIf(
        tn ->
            tn.getNodeID()
                .equals(edited.getNodeID())); // If node was edited previously, delete old value
    editedNodes.add(edited); // Cache edit
    lbl_changesSaved.setText(editedNodes.size() + " unsaved"); // Show number of edits to be saved
  }

  private void saveEdits() {
    if (editedNodes.size() > 0) {
      for (TableNode tn : editedNodes) { // Update DB using chached changes to node data
        tn.UpdateDbNode();
      }
      trashEdits(); // Clear list of edits
      refreshAllNodes(); // Re-load nodes from DB, just in case
      getBuildings(); // Update menu items
    }
  }

  private void trashEdits() {
    if (editedNodes.size() > 0) {
      editedNodes.clear();
      lbl_changesSaved.setText("All changes saved");
      btn_saveChanges.setDisable(true);
    }
  }
}
