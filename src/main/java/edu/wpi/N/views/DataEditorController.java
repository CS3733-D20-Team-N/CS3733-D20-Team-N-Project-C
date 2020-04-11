package edu.wpi.N.views;

import edu.wpi.N.models.CSVParser;
import java.io.File;
import java.io.FileNotFoundException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;

public class DataEditorController {
  @FXML Button btn_select;
  @FXML Button btn_done;
  @FXML Label lbl_filePath;

  @FXML
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

  @FXML
  public void onDoneClicked(MouseEvent event) throws FileNotFoundException {
    String path = lbl_filePath.getText();
    CSVParser.parseCSVfromPath(path);
  }
}
