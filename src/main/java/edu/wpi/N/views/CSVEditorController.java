package edu.wpi.N.views;

import edu.wpi.N.models.CSVParser;
import java.io.File;
import java.io.FileNotFoundException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;

public class CSVEditorController {
    @FXML Button btn_loadFile, btn_saveFile;
    @FXML Label lbl_fileName;
    @FXML ScrollPane pane_tableScroller;
    @FXML GridPane pane_colTitles, pane_attributesList;

    private String currentFile = "";
    private String tempFileName = "draft.csv";

    @FXML
    public void onLoadFileClicked(MouseEvent event) {
        pickFile();
    }

    @FXML
    public void onSaveFileClicked(MouseEvent event) {
        saveEdits();
    }


    private void pickFile () {
        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("CSV Files", "*.csv"));
        File selectedFile = fc.showOpenDialog(null);
        if (selectedFile != null) {
            currentFile = selectedFile.getName();
        }

        if (currentFile != "") {
            lbl_fileName.setText(currentFile);
            generateTable(selectedFile.getName());
        } else {
            lbl_fileName.setText("Invalid file!");
            System.out.println("The file is invalid");
        }
    }

    private void generateTable (String fileName) {

        // Count headers, resize width of pane_colTitles and pane_attributesList
        // While EOF is not reached
            // Add row to pane_attributesList
            // For each cell in row
                // Add text box
                // Set default value to value from CSV

    }

    private void copyOriginalFile () {
        // Make copy of [currentFile] with name [tempFileName] to store changes in
    }

    private void saveEdits () {
        // Delete file with name [currentFile]
        // Rename [tempFileName] to [currentFile]
    }

    private void trashEdits () {
        // Delete [tempFileName]
    }

}
