package edu.wpi.N.models;

import edu.wpi.N.database.dbController;
import java.sql.SQLException;

public class AlgosMainCanIgnore {

  public static void main(String[] args) throws SQLException, ClassNotFoundException, Exception {
    dbController.initDB();
    // relative path to .csv file
    String pathToFile =
        "/Users/Ivan/IdeaProjects/CS3733-D20-Team-N-Project-C/src/main/resources/edu/wpi/N/csv/MapEnodes.csv";
    CSVParser.parseCSVfromPath(pathToFile);

    //    Graph nodeGraph = parser.parseCSV(input);
    //    Node startNode = nodeGraph.getNode("MOHSClinic");
    //    Node endNode = nodeGraph.getNode("HVMANeurology");
    //    Pathfinder newPath = new Pathfinder(nodeGraph, startNode, endNode);
    //    Path myPath = newPath.findPath();

    System.out.println("Set a break point here to see the path in debugger");
  }
}
