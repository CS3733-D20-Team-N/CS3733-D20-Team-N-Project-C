package edu.wpi.N.views;

import edu.wpi.N.Main;
import edu.wpi.N.database.dbController;
import edu.wpi.N.models.CSVParser;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
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
  @FXML Button btn_default;
  @FXML Button btn_back;
  @FXML Button btn_prototypeSwitcher;
  final String DEFAULT_NODES = "csv/MapEnodes.csv";
  final String DEFAULT_PATHS = "csv/MapEedges.csv";
  final InputStream INPUT_NODES_DEFAULT = Main.class.getResourceAsStream(DEFAULT_NODES);;
  final InputStream INPUT_EDGES_DEFAULT = Main.class.getResourceAsStream(DEFAULT_PATHS);

  public void initialize() throws SQLException, ClassNotFoundException {
    lbl_filePath.setText(DEFAULT_NODES);
    lbl_filePath_edges.setText(DEFAULT_PATHS);
  }

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

  public void onBackClicked(MouseEvent event) throws IOException {
    Stage stage;
    Parent root;
    stage = (Stage) btn_done.getScene().getWindow();
    root = FXMLLoader.load(getClass().getResource("kioskHome.fxml"));
    Scene scene = new Scene(root);
    stage.setScene(scene);
    stage.show();
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
    dbController.clearNodes();

    // For nodes
    String path = lbl_filePath.getText();
    if (path.equals(DEFAULT_NODES)) {
      CSVParser.parseCSV(INPUT_NODES_DEFAULT);
    } else {
      CSVParser.parseCSVfromPath(path);
    }
    // For paths
    String path_edges = lbl_filePath_edges.getText();
    if (path_edges.equals(DEFAULT_PATHS)) {
      CSVParser.parseCSV(INPUT_EDGES_DEFAULT);
    } else {
      CSVParser.parseCSVfromPath(path_edges);
    }

    Stage stage;
    Parent root;
    stage = (Stage) btn_done.getScene().getWindow();
    root = FXMLLoader.load(getClass().getResource("nodeTableViewer.fxml"));
    Scene scene = new Scene(root);
    stage.setScene(scene);
    stage.show();
  }

  @FXML
  public void onDefaultClicked(MouseEvent event) {
    lbl_filePath.setText(DEFAULT_NODES);
    lbl_filePath_edges.setText(DEFAULT_PATHS);
  }
}
