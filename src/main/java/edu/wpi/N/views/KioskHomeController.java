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
  @FXML Button btn_goToTable; // ** Remove
  @FXML Button btn_laundry;
  @FXML Button btn_translator;
  @FXML Button btn_moreServices;
  @FXML Button btn_login;
  @FXML TextField txt_User;
  @FXML PasswordField pf_Pass;
  @FXML Button btn_Home;
  @FXML Button btn_Submit;
  @FXML Button btn_import;
  @FXML Button btn_map;
  @FXML Button btn_Prototype;

  @FXML
  private void storeData() throws IOException {
    username = txt_User.getText();
    password = pf_Pass.getText();
    // System.out.println("Username: " + username + " Password: " + password);
    loginCheck(username, password);
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
      } else if (event.getSource() == btn_moreServices) {
        stage = (Stage) btn_moreServices.getScene().getWindow();
        root = FXMLLoader.load(getClass().getResource("downloadMapCSVPage.fxml"));
      } else if (event.getSource() == btn_Prototype) {
        stage = (Stage) btn_Prototype.getScene().getWindow();
        root = FXMLLoader.load(getClass().getResource("dataEditor.fxml"));
      } else {
        stage = (Stage) btn_goToTable.getScene().getWindow();
        root = FXMLLoader.load(getClass().getResource("nodeTableEditor.fxml"));
      }
      Scene scene = new Scene(root);
      stage.setScene(scene);
      stage.show();
    }
  }

  @FXML
  private void keyPressSubmission(KeyEvent key) throws IOException {
    if (key.getCode() == KeyCode.ENTER) {
      storeData();
    }
  }

  @FXML
  private void loginCheck(String user, String pass) throws IOException {
    if (username.equals("admin") && password.equals("1234")) {
      Stage stage;
      Parent root;
      stage = new Stage();
      stage.getScene();
      root = FXMLLoader.load(getClass().getResource("adminRequestPage.fxml"));
      Scene scene = new Scene(root);
      stage.setScene(scene);
      stage.setTitle("Admin Request");
      stage.show();
      System.out.println("Valid Username and Password");
    } else {
      System.out.println("Invalid Username or Password");
    }
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

  @FXML
  private void onDisplayMapClicked(MouseEvent event) throws Exception {
    Stage stage;
    Parent root;
    stage = (Stage) btn_import.getScene().getWindow();
    root = FXMLLoader.load(getClass().getResource("mapDisplay.fxml"));
    Scene scene = new Scene(root);
    stage.setScene(scene);
    stage.show();
  }
}
