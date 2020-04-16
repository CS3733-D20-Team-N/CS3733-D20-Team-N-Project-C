package edu.wpi.N.views;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class NickController {

  String floor;
  String roomNum;
  String language;
  String location;
  String reason;

  @FXML Button btn_return;
  @FXML Button btn_ldryRequest;
  @FXML Button btn_Home;
  @FXML Button btn_translationRequest;
  @FXML MenuButton mnu_floorNum;
  @FXML MenuButton mnu_rmNum;
  @FXML MenuItem mi_firstFloor;
  @FXML MenuItem mi_secondFloor;
  @FXML MenuItem mi_rm101;
  @FXML MenuItem mi_rm102;
  @FXML Button btn_Submit;
  @FXML TextField txt_location;
  @FXML TextField txt_langRequest;
  @FXML TextArea txt_reasonReq;
  @FXML Button btn_Confirm;

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

  @FXML
  private void laundryData() throws IOException {

    // Currently does not work on first click but it does store the data
    mi_firstFloor.setOnAction(
        e -> {
          mnu_floorNum.setText(mi_firstFloor.getText());
          floor = mi_firstFloor.getText();
          System.out.println(floor);
        });
    mi_secondFloor.setOnAction(
        e -> {
          mnu_floorNum.setText(mi_secondFloor.getText());
          floor = mi_secondFloor.getText();
          System.out.println(floor);
        });
    mi_rm101.setOnAction(
        e -> {
          mnu_rmNum.setText(mi_rm101.getText());
          roomNum = mi_rm101.getText();
          System.out.println(roomNum);
        });
    mi_rm102.setOnAction(
        e -> {
          mnu_rmNum.setText(mi_rm102.getText());
          roomNum = mi_rm102.getText();
          System.out.println(roomNum);
        });
  }

  @FXML
  private void displayData(MouseEvent e) {
    if (e.getSource() == btn_Submit) {
      System.out.println("Floor Number: " + floor + " Room Number: " + roomNum);
    }
  }

  @FXML
  private void storeTranslatorData(MouseEvent event) {
    if (event.getSource() == btn_Confirm) {
      language = txt_langRequest.getText();
      location = txt_location.getText();
      reason = txt_reasonReq.getText();
    }
    System.out.println(
        "Language: " + language + " Location: " + location + " Reasoning: " + reason);
  }
}
