package edu.wpi.N.views;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class KioskHomeController {

  // Local Variable Initiation
  String username;
  String password;
  String location;
  String language;
  String reasonForRequest;

  // FXML Variable Initialization
  @FXML Button btn_goToMap;
  @FXML Button btn_laundry;
  @FXML Button btn_translator;
  @FXML Button btn_moreServices;
  @FXML Button btn_login;
  @FXML TextField txt_User;
  @FXML PasswordField pf_Pass;
  @FXML Button btn_Home;
  // @FXML Button btn_Submit;

  @FXML
  private void storeData() {
    username = txt_User.getText();
    password = pf_Pass.getText();
    System.out.println("Username: " + username + " Password: " + password);
  }

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

    } else if (event.getSource() == btn_login) {
      stage = new Stage();
      root = FXMLLoader.load(getClass().getResource("loginWindow.fxml"));
      Scene scene = new Scene(root);
      stage.setScene(scene);
      stage.setTitle("Admin Login");
      stage.initModality(Modality.APPLICATION_MODAL);
      stage.initOwner(btn_login.getScene().getWindow());
      stage.showAndWait();

    } else if (event.getSource() == btn_Home) {
      stage = (Stage) btn_Home.getScene().getWindow();
      stage.close();

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

  @FXML
  private void keyPressSubmission(KeyEvent key) {
    if (key.getCode() == KeyCode.ENTER) {
      storeData();
    }
  }
}
