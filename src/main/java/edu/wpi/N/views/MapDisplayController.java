package edu.wpi.N.views;

import edu.wpi.N.Main;
import edu.wpi.N.database.DbNode;
import edu.wpi.N.database.dbController;
import edu.wpi.N.models.CSVParser;
import edu.wpi.N.models.Path;
import edu.wpi.N.models.Pathfinder;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

public class MapDisplayController {

  @FXML AnchorPane pane_nodes;
  @FXML Button btn_path;
  @FXML Button btn_reset;
  HashMap<Circle, DbNode> masterNodes;
  LinkedList<DbNode> nodesList;
  LinkedList<DbNode> selectedNodes;
  final double SCALE = 0.55;

  public void initialize() {
    InputStream inputNodes = Main.class.getResourceAsStream("csv/MapEnodes.csv");
    InputStream inputEdges = Main.class.getResourceAsStream("csv/MapEedges.csv");
    CSVParser.parseCSV(inputNodes);
    CSVParser.parseCSV(inputEdges);
    selectedNodes = new LinkedList<DbNode>();
    populateMap();
  }

  public void populateMap() {
    nodesList = dbController.floorNodes(4, "Faulkner");
    masterNodes = new HashMap<Circle, DbNode>();
    Iterator iter = nodesList.iterator();
    while (iter.hasNext()) {
      DbNode newNode = (DbNode) iter.next();
      Circle newMapNode = makeNode(newNode);
      pane_nodes.getChildren().add(newMapNode);
      masterNodes.put(newMapNode, newNode);
    }
  }

  public Circle makeNode(DbNode newNode) {
    Circle newMapNode = new Circle();
    newMapNode.setRadius(5);
    newMapNode.setLayoutX(newNode.getX() * SCALE);
    newMapNode.setLayoutY(newNode.getY() * SCALE);
    newMapNode.setFill(Color.PURPLE);
    newMapNode.setOnMouseClicked(
        mouseEvent -> {
          if (newMapNode.getFill() == Color.PURPLE) {
            newMapNode.setFill(Color.RED);
            selectedNodes.add(masterNodes.get(newMapNode));
          } else {
            newMapNode.setFill(Color.PURPLE);
            selectedNodes.remove(masterNodes.get(newMapNode));
          }
        });
    return newMapNode;
  }

  @FXML
  private void onFindPathClicked(MouseEvent event) throws Exception {
    if (selectedNodes.size() != 2) {
      System.out.println("Incorrect number of nodes");
      return;
    }
    DbNode firstNode = selectedNodes.get(0);
    DbNode secondNode = selectedNodes.get(1);
    Path mapPath = Pathfinder.findPath(firstNode.getNodeID(), secondNode.getNodeID());
    LinkedList<DbNode> pathNodes = mapPath.getPath();
    drawPath(pathNodes);
  }

  private void drawPath(LinkedList<DbNode> pathNodes) {
    int size = pathNodes.size();
    DbNode firstNode;
    DbNode secondNode;
    for (int i = 0; i < size - 1; i++) {
      firstNode = pathNodes.get(i);
      secondNode = pathNodes.get(i + 1);
      Line line =
          new Line(
              firstNode.getX() * SCALE,
              firstNode.getY() * SCALE,
              secondNode.getX() * SCALE,
              secondNode.getY() * SCALE);

      pane_nodes.getChildren().add(line);
    }
  }

  @FXML
  private void onResetClicked(MouseEvent event) throws Exception {
    for (Map.Entry<Circle, DbNode> entry : masterNodes.entrySet()) {
      Circle mapNode = entry.getKey();
      mapNode.setFill(Color.PURPLE);
    }
    selectedNodes.clear();
  }
}
