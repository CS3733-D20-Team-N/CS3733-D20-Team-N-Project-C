package edu.wpi.N.models;

import com.opencsv.CSVReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class CSVParser {
  private Graph graphFromCSV;

  /**
   * Parse CSV file and create graph
   *
   * @param pathToFile: path to CSV file
   * @return Graph created from given CSV file
   */
  public Graph parseCSV(InputStream pathToFile) {
    // Initialize graph
    this.graphFromCSV = new Graph();

    try {
      // create csvReader object passing
      CSVReader csvReader = new CSVReader(new InputStreamReader(pathToFile, "UTF-8"));

      String[] nextLine;

      // Read header
      csvReader.readNext();

      // Read data line by line except header
      while ((nextLine = csvReader.readNext()) != null) {
        // Read respective columns of the row and add Nodes and Edges to graph
        parseRow(nextLine);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return this.graphFromCSV;
  }

  /**
   * Parse data from a given row: add Node and Edges to graph
   *
   * @param row: a row to parse data from
   */
  private void parseRow(String[] row) {
    try {
      String id = row[0];
      double xcoord = Double.parseDouble(row[1]);
      double ycoord = Double.parseDouble(row[3]);
      String[] edgesTo = row[10].split(",");

      // Create new node (location)
      Node node = new Node(xcoord, ycoord, id);
      // Add to Graph
      this.graphFromCSV.addNode(node);

      // Add edge(s)
      for (String adjNode : edgesTo) {
        adjNode.replaceAll("\\s", "");
        this.graphFromCSV.addEdge(id, adjNode);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
