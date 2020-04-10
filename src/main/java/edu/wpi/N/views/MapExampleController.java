package edu.wpi.N.views;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class MapExampleController {

  @FXML StackPane pane_map;
  @FXML AnchorPane pane_mapClickTarg;
  @FXML Button btn_zoomIn, btn_zoomOut;
  @FXML Button btn_cardiology, btn_mohs, btn_neurology, btn_urology, btn_admin, btn_backToKiosk;
  private double clickStartX, clickStartY;
  private final double MIN_MAP_SCALE = 1; // pane_map scale when zoomed out fully
  private final double MAX_MAP_SCALE = 3.5; // pane_map scale when zoomed in fully
  private double mapScaleAlpha = 0; // Zoom value between 0 (out) and 1 (in)

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
  private void mapClickHandler(MouseEvent event) throws IOException {
    if (event.getSource() == pane_mapClickTarg) {
      clickStartX = event.getSceneX();
      clickStartY = event.getSceneY();
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

  @FXML
  private void mapScrollHandler(ScrollEvent event) throws IOException {
    if (event.getSource() == pane_mapClickTarg) {
      double deltaY = event.getDeltaY();
      zoom(deltaY * 0.01);
    }
  }

  private void zoom(double percent) {

    mapScaleAlpha = Math.max(0, Math.min(1, mapScaleAlpha + percent));

    // Maps 0-1 value (alpha) to min-max value
    double lerpedScale = MIN_MAP_SCALE + mapScaleAlpha * (MAX_MAP_SCALE - MIN_MAP_SCALE);
    pane_map.setScaleX(lerpedScale);
    pane_map.setScaleY(lerpedScale);
  }
}
