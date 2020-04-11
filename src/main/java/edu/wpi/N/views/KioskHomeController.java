package edu.wpi.N.views;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class KioskHomeController {
  @FXML Button btn_goToMap;
  @FXML Button btn_laundry;
  @FXML Button btn_translator;
  @FXML Button btn_moreServices;

  @FXML
  private void handleOnClick(MouseEvent event) throws IOException {

    Stage stage;
    Parent root;

    if (event.getSource() == btn_translator) {
      stage = new Stage();
      root = FXMLLoader.load(getClass().getResource("translatorWindow.fxml"));
      stage.setScene(new Scene(root));
      stage.setTitle("Translation Request");
      stage.initModality(Modality.APPLICATION_MODAL);
      stage.initOwner(btn_translator.getScene().getWindow());
      stage.showAndWait();

    } else if (event.getSource() == btn_laundry) {

      stage = new Stage();
      root = FXMLLoader.load(getClass().getResource("popupWindow.fxml"));
      stage.setScene(new Scene(root));
      stage.setTitle("Laundry Request");
      stage.initModality(Modality.APPLICATION_MODAL);
      stage.initOwner(btn_laundry.getScene().getWindow());
      stage.showAndWait();

    } else {

      if (event.getSource() == btn_goToMap) {
        stage = (Stage) btn_goToMap.getScene().getWindow();
        root = FXMLLoader.load(getClass().getResource("mapExample.fxml"));
      } else if (event.getSource() == btn_laundry) {
        stage = (Stage) btn_laundry.getScene().getWindow();
        root = FXMLLoader.load(getClass().getResource("popupWindow.fxml"));
      } else {
        stage = (Stage) btn_moreServices.getScene().getWindow();
        root = FXMLLoader.load(getClass().getResource("downloadMapCSVPage.fxml"));
      }
      Scene scene = new Scene(root);
      stage.setScene(scene);
      stage.show();
    }
  }
}
