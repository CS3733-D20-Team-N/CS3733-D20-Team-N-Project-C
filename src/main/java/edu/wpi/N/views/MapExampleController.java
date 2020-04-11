package edu.wpi.N.views;

import java.io.IOException;
import java.util.Vector;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Point2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class MapExampleController {

  @FXML StackPane pane_map;
  @FXML AnchorPane pane_mapClickTarg;
  @FXML AnchorPane pane_kioskNodes, pane_pathNodes;
  @FXML Button btn_zoomIn, btn_zoomOut;
  @FXML Button btn_cardiology, btn_mohs, btn_neurology, btn_urology, btn_admin, btn_backToKiosk;
  @FXML Button btn_import;
  private double clickStartX, clickStartY;
  private final double MIN_MAP_SCALE = 1; // pane_map scale when zoomed out fully
  private final double MAX_MAP_SCALE = 3.5; // pane_map scale when zoomed in fully
  private double mapScaleAlpha = 0; // Zoom value between 0 (out) and 1 (in)
  private Vector<Circle> nodeDots;

  @FXML
  private void sideMenuBtnHandler(MouseEvent event) throws IOException {
    Stage stage = null;
    Parent root = null;
    Object source = event.getSource();

    if (source == btn_backToKiosk) {
      stage = (Stage) btn_backToKiosk.getScene().getWindow();
      root = FXMLLoader.load(getClass().getResource("kioskHome.fxml"));
    }

    if (stage != null && root != null) {
      Scene scene = new Scene(root);
      stage.setScene(scene);
      stage.show();
    }
  }

  @FXML
  private void zoomToolHandler(MouseEvent event) throws IOException {

    if (event.getSource() == btn_zoomIn) {
      zoom(0.1);
    } else if (event.getSource() == btn_zoomOut) {
      zoom(-0.1);
    }
  }

  @FXML
  private void mapPressHandler(MouseEvent event) throws IOException {
    if (event.getSource() == pane_mapClickTarg) {
      clickStartX = event.getSceneX();
      clickStartY = event.getSceneY();
    }
  }

  @FXML
  private void mapClickHandler(MouseEvent event) throws IOException {
    if (event.getSource() == pane_mapClickTarg) {
      Point2D clickLocation = pane_map.screenToLocal(event.getScreenX(), event.getScreenY());
      addNodeToMap(clickLocation, pane_pathNodes);
    }
  }

  @FXML
  private void mapDragHandler(MouseEvent event) throws IOException {
    if (event.getSource() == pane_mapClickTarg) {
      double dragDeltaX = event.getSceneX() - clickStartX;
      double dragDeltaY = event.getSceneY() - clickStartY;

      double newTranslateX = pane_map.getTranslateX() + dragDeltaX;
      double newTranslateY = pane_map.getTranslateY() + dragDeltaY;

      clickStartX = event.getSceneX();
      clickStartY = event.getSceneY();

      pane_map.setTranslateX(newTranslateX);
      pane_map.setTranslateY(newTranslateY);
    }
  }

  private void addNodeToMap(Point2D point, AnchorPane layer) {
    Circle newNode = new Circle();
    layer.getChildren().add(newNode);
    newNode.setRadius(5);
    newNode.setLayoutX(point.getX());
    newNode.setLayoutY(point.getY());
    newNode.setFill(Color.DODGERBLUE);
  }
  private void removeNodeFromMap(Circle nodeClicked) {
    if (nodeDots.contains(nodeClicked)) {
      nodeDots.remove(nodeClicked);
    }
  }

  @FXML
  private void mapScrollHandler(ScrollEvent event) throws IOException {
    if (event.getSource() == pane_mapClickTarg) {
      double deltaY = event.getDeltaY();
      zoom(deltaY * 0.005);
    }
  }

  private void zoom(double percent) {

    mapScaleAlpha = Math.max(0, Math.min(1, mapScaleAlpha + percent));

    // Maps 0-1 value (alpha) to min-max value
    double lerpedScale = MIN_MAP_SCALE + mapScaleAlpha * (MAX_MAP_SCALE - MIN_MAP_SCALE);
    pane_map.setScaleX(lerpedScale);
    pane_map.setScaleY(lerpedScale);
  }

  @FXML
  private void onImportClicked(MouseEvent event) throws Exception {
    Stage stage;
    Parent root;
    stage = (Stage) btn_import.getScene().getWindow();
    root = FXMLLoader.load(getClass().getResource("dataEditor.fxml"));
    Scene scene = new Scene(root);
    stage.setScene(scene);
    stage.show();
  }
}
