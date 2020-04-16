package edu.wpi.N.views;

import edu.wpi.N.database.DbNode;
import edu.wpi.N.database.dbController;
import edu.wpi.N.entities.TableNode;
import java.io.IOException;
import java.util.LinkedList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class NodeTableViewerController {
  @FXML Button btn_saveChanges, btn_next, btn_back;
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
  private boolean editing;

  @FXML
  private void initialize() throws IOException {

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

    // Place columns (separated to avoid compiler warning re. "unsafe" commands)
    nodesTable.getColumns().add(nameCol);
    nodesTable.getColumns().add(longNameCol);
    nodesTable.getColumns().add(idCol);
    nodesTable.getColumns().add(typeCol);
    nodesTable.getColumns().add(xCol);
    nodesTable.getColumns().add(yCol);
    nodesTable.getColumns().add(floorCol);
    nodesTable.getColumns().add(buildingCol);
    nodesTable.getColumns().add(teamCol);

    // Get nodes from database and fill table
    refreshAllNodes();
    if (allNodes.size() > 0) {
      fillTable();
    }
  }

  /// FXML methods

  // Called when user clicks "Save" button
  @FXML
  public void onSaveClicked(MouseEvent event) {
    saveEdits();
  }

  // Called when user clicks "Next" navigation button
  @FXML
  private void onNextClicked(MouseEvent event) throws IOException {
    trashEdits();
    Stage stage = (Stage) btn_next.getScene().getWindow();
    Parent root = FXMLLoader.load(getClass().getResource("nodeTableEditor.fxml"));
    Scene scene = new Scene(root);
    stage.setScene(scene);
    stage.show();
  }

  // Called when user clicks "Back" navigation button
  @FXML
  private void onBackClicked(MouseEvent event) throws IOException {
    trashEdits();
    Stage stage = (Stage) btn_back.getScene().getWindow();
    Parent root = FXMLLoader.load(getClass().getResource("dataEditor.fxml"));
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

  private void fillTable() {
    currentNodes.setAll(allNodes);
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
      fillTable(); // Update menu items
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
