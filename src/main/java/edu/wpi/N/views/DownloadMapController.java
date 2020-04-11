package edu.wpi.N.views;

import java.io.File;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class DownloadMapController {
  @FXML Button btn_downloadCSV;

  @FXML Button btn_previous;

  @FXML Button btn_next;

  @FXML
  private void handleOnClick(MouseEvent event) throws IOException {
    Stage stage = null;
    Parent root = null;
    if (event.getSource() == btn_previous) {
      stage = (Stage) btn_previous.getScene().getWindow();
      root = FXMLLoader.load(getClass().getResource("kioskHome.fxml"));
    } else {
      stage = (Stage) btn_next.getScene().getWindow();
      root = FXMLLoader.load(getClass().getResource("kioskHome.fxml"));
    }

    if (stage != null && root != null) {
      Scene scene = new Scene(root);
      stage.setScene(scene);
      stage.show();
    }
  }

  @FXML
  private void downloadClick(MouseEvent e) throws IOException {
    if (e.getSource() == btn_downloadCSV) {
      Stage stage;
      stage = (Stage) btn_downloadCSV.getScene().getWindow();
      FileChooser fileChooser = new FileChooser();

      // Set extension filter for text files
      FileChooser.ExtensionFilter extFilter =
          new FileChooser.ExtensionFilter("CSV files (*.csv)", "*.csv");
      fileChooser.getExtensionFilters().add(extFilter);

      // Show save file dialog
      File file = fileChooser.showSaveDialog(stage);

      if (file != null) {
        // Save to file somehow
      }

      stage.show();
    }
  }
}
