package edu.wpi.N.views;

import java.io.File;
import java.util.LinkedList;

import edu.wpi.N.database.DbNode;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;

public class NodeTableEditorController {
    @FXML Button btn_saveChanges, btn_addNode, btn_delNode;
    @FXML Label lbl_fileName;
    @FXML TableView nodesTable;
    @FXML ChoiceBox menu_pickBuilding, menu_pickFloor, menu_pickNode;

    private LinkedList<String> buildings, propertyNames;
    private LinkedList<DbNode> allNodes, currentNodes;
    private int currentFloor, totalFloorsInBuilding;
    private String currentBuilding;

    @FXML
    public void onPickBuilding (MouseEvent event) {
        getFloorsInBuilding(currentBuilding);
        getNodesOnFloor(currentBuilding, 1);
        fillTable();
    }

    @FXML
    public void onPickFloor (MouseEvent event) {
        currentFloor = (int)menu_pickFloor.getValue();
        getNodesOnFloor(currentBuilding, currentFloor);
        fillTable();
    }

    @FXML
    public void onSaveClicked (MouseEvent event) {
        //nodesTable.
        saveEdits();
    }

    @FXML
    public void onTableRowClicked (MouseEvent event) {
        nodesTable.scrollTo(0);
    }

    @FXML
    public void onCellSelected (Event event) {

    }


    private void refreshAllNodes() {
        allNodes.clear();
        allNodes = new LinkedList<>();  // Load nodes from DB
    }

    private void getBuildings() {
        buildings.clear();
        for (DbNode n : allNodes) {
            if (!buildings.contains(n.getBuilding())) {
                buildings.add(n.getBuilding());
            }
        }
    }

    private void getFloorsInBuilding(String building) {
        totalFloorsInBuilding = 1;
        for (DbNode n : allNodes) {
            totalFloorsInBuilding = Math.max(totalFloorsInBuilding, n.getFloor());
        }
    }

    private void getNodesOnFloor (String building, int floor) {
        trashEdits();  // Undo unsaved changes to current floor
        currentNodes.clear();  // Wipe current values
        for (DbNode n : allNodes) {
            if (n.getBuilding().equals(building) && n.getFloor() == floor) {
                currentNodes.add(n);
            }
        }
    }

    private void fillTable () {

        for (DbNode n : currentNodes) {
            menu_pickNode.getItems().add("NodeName");
        }

    }

    private void saveEdits () {

    }

    private void trashEdits () {
    }

}
