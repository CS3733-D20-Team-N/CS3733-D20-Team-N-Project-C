package edu.wpi.N.models;

import com.opencsv.CSVReader;
import edu.wpi.N.database.dbController;
import java.io.InputStream;
import java.io.InputStreamReader;

public class CSVParser {

  /**
   * Parse NodeCSV file and add entries to Database
   *
   * @param pathToFile: path to the CSV file as an InputStream
   */
  public static void parseCSV(InputStream pathToFile) {
    try {
      // Assume that it is NodeCSV
      Boolean isNodeCSV = true;

      // create csvReader object passing
      CSVReader csvReader = new CSVReader(new InputStreamReader(pathToFile, "UTF-8"));

      String[] nextLine;

      // Read header
      nextLine = csvReader.readNext();

      // Check if it is EdgeCSV
      if ((nextLine[0].toLowerCase() == "edgeid") || nextLine[1].toLowerCase() == "startnode") {
        isNodeCSV = false;
      }

      if (isNodeCSV) {
        // Parse NodeCSV data line by line except header
        while ((nextLine = csvReader.readNext()) != null) {
          parseNodeRow(nextLine);
        }
      } else {
        // Parse EdgesCSV data line by line except header
        while ((nextLine = csvReader.readNext()) != null) {
          parseEdgesRow(nextLine);
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Parse data from a given row: add Node to the database
   *
   * @param row: a row to parse data from
   */
  static void parseNodeRow(String[] row) {
    try {
      String nodeID = row[0];
      int xcoord = Integer.parseInt(row[1]);
      int ycoord = Integer.parseInt(row[2]);
      int floor = Integer.parseInt(row[3]);
      String building = row[4];
      String nodeType = row[5];
      String longName = row[6];
      String shortName = row[7];
      char teamAssigned = 'i';

      dbController.addNode(
          nodeID, xcoord, ycoord, floor, building, nodeType, longName, shortName, teamAssigned);

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Parse data from a given row: add Edge to the database
   *
   * @param row: a row to parse data from
   */
  static void parseEdgesRow(String[] row) {
    try {
      String edgeID = row[0];
      String startNodeId = row[1];
      String endNodeId = row[2];

      dbController.addEdge(startNodeId, endNodeId);

    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
