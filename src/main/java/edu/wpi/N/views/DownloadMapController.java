package edu.wpi.N.views;

import edu.wpi.N.database.DbNode;
import edu.wpi.N.database.dbController;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
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
      root = FXMLLoader.load(getClass().getResource("nodeTableEditor.fxml"));
    } else {
      stage = (Stage) btn_next.getScene().getWindow();
      root = FXMLLoader.load(getClass().getResource("dataEditor.fxml"));
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
        FileWriter fileWriter = new FileWriter(file.getAbsoluteFile(), true);
        BufferedWriter csvWriter = new BufferedWriter(fileWriter);
        // nodeID,xcoord,ycoord,floor,building,nodeType,longName,shortName,teamAssigned
        csvWriter.append("nodeID");
        csvWriter.append(",");
        csvWriter.append("xcoord");
        csvWriter.append(",");
        csvWriter.append("ycoord");
        csvWriter.append(",");
        csvWriter.append("floor");
        csvWriter.append(",");
        csvWriter.append("building");
        csvWriter.append(",");
        csvWriter.append("nodeType");
        csvWriter.append(",");
        csvWriter.append("longName");
        csvWriter.append(",");
        csvWriter.append("shortName");
        csvWriter.append(",");
        csvWriter.append("teamAssigned");
        csvWriter.append("\n");

        LinkedList<DbNode> csvNodeList = dbController.allNodes();

        for (int index = 0; index < csvNodeList.size(); index++) {
          DbNode indexNode = csvNodeList.get(index);
          csvWriter.append(indexNode.getNodeID());
          csvWriter.append(",");
          csvWriter.append(Integer.toString(indexNode.getX()));
          csvWriter.append(",");
          csvWriter.append(Integer.toString(indexNode.getY()));
          csvWriter.append(",");
          csvWriter.append(Integer.toString(indexNode.getFloor()));
          csvWriter.append(",");
          csvWriter.append(indexNode.getBuilding());
          csvWriter.append(",");
          csvWriter.append(indexNode.getNodeType());
          csvWriter.append(",");
          csvWriter.append(indexNode.getLongName());
          csvWriter.append(",");
          csvWriter.append(indexNode.getShortName());
          csvWriter.append(",");
          csvWriter.append(indexNode.getTeamAssigned());
          csvWriter.append("\n");
        }
        csvWriter.flush();
        csvWriter.close();
      }

      stage.show();
    }
  }
}
