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

public class NickController {

  @FXML Button btn_return;

  @FXML Button btn_ldryRequest;

  @FXML Button btn_Home;

  @FXML Button btn_translationRequest;

  @FXML
  private void handleOnClick(MouseEvent event) throws IOException {
    Stage stage;
    Parent root;
    stage = (Stage) btn_return.getScene().getWindow();
    root = FXMLLoader.load(getClass().getResource("homePage.fxml"));
    Scene scene = new Scene(root);
    stage.setScene(scene);
    stage.show();
  }

  @FXML
  private void laundryClick(MouseEvent e) throws IOException {
    if (e.getSource() == btn_ldryRequest) {
      Stage stage;
      Parent root;
      stage = new Stage();
      root = FXMLLoader.load(getClass().getResource("popupWindow.fxml"));
      stage.setScene(new Scene(root));
      stage.setTitle("Laundry Request");
      stage.initModality(Modality.APPLICATION_MODAL);
      stage.initOwner(btn_ldryRequest.getScene().getWindow());
      stage.showAndWait();
    } else {
      Stage stage;
      stage = (Stage) btn_Home.getScene().getWindow();
      stage.close();
    }
  }

  @FXML
  private void translatorClick(MouseEvent e) throws IOException {
    if (e.getSource() == btn_translationRequest) {
      Stage stage;
      Parent root;
      stage = new Stage();
      root = FXMLLoader.load(getClass().getResource("translatorWindow.fxml"));
      stage.setScene(new Scene(root));
      stage.setTitle("Translation Request");
      stage.initModality(Modality.APPLICATION_MODAL);
      stage.initOwner(btn_translationRequest.getScene().getWindow());
      stage.showAndWait();
    } else {
      Stage stage;
      stage = (Stage) btn_Home.getScene().getWindow();
      stage.close();
    }
  }
}
