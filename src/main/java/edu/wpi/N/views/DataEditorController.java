package edu.wpi.N.views;

import edu.wpi.N.database.dbController;
import edu.wpi.N.models.CSVParser;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class DataEditorController {
  @FXML Button btn_select;
  @FXML Button btn_done;
  @FXML Label lbl_filePath;
  @FXML Button btn_select_edges;
  @FXML Label lbl_filePath_edges;

  public void onSelectClicked(MouseEvent event) {
    FileChooser fc = new FileChooser();
    fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("CSV Files", "*.csv"));
    File selectedFile = fc.showOpenDialog(null);
    if (selectedFile != null) {
      lbl_filePath.setText(selectedFile.getAbsolutePath());
      lbl_filePath.setDisable(false);
    } else {
      System.out.println("The file is invalid");
    }
  }

  public void onSelectEdgesClicked(MouseEvent event) {
    FileChooser fc = new FileChooser();
    fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("CSV Files", "*.csv"));
    File selectedFile = fc.showOpenDialog(null);
    if (selectedFile != null) {
      lbl_filePath_edges.setText(selectedFile.getAbsolutePath());
      lbl_filePath_edges.setDisable(false);
    } else {
      System.out.println("The file is invalid");
    }
  }

  @FXML
  public void onDoneClicked(MouseEvent event)
      throws SQLException, ClassNotFoundException, IOException {
    dbController.initDB();
    String path = lbl_filePath.getText();
    CSVParser.parseCSVfromPath(path);
    String path_edges = lbl_filePath_edges.getText();
    CSVParser.parseCSVfromPath(path_edges);
    Stage stage;
    Parent root;
    stage = (Stage) btn_done.getScene().getWindow();
    root = FXMLLoader.load(getClass().getResource("KioskHome.fxml"));
    Scene scene = new Scene(root);
    stage.setScene(scene);
    stage.show();
  }
}
