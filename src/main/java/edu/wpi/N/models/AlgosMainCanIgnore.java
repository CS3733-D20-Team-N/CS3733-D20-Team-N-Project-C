package edu.wpi.N.models;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class AlgosMainCanIgnore {

  public static void main(String[] args) {

    // relative path to .csv file
    String pathToFile =
        "/Users/Ivan/IdeaProjects/CS3733-D20-Team-N-Project-C/src/main/resources/edu/wpi/N/csv/MapEnodes.csv";
    try {
      parseCSVfromPath(pathToFile);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }

    //    Graph nodeGraph = parser.parseCSV(input);
    //    Node startNode = nodeGraph.getNode("MOHSClinic");
    //    Node endNode = nodeGraph.getNode("HVMANeurology");
    //    Pathfinder newPath = new Pathfinder(nodeGraph, startNode, endNode);
    //    Path myPath = newPath.findPath();

    System.out.println("Set a break point here to see the path in debugger");
  }

  /**
   * @param pathToFile
   * @return
   * @throws FileNotFoundException
   */
  public static void parseCSVfromPath(String pathToFile) throws FileNotFoundException {

    File initialFile = new File(pathToFile);
    InputStream input = new FileInputStream(initialFile);

    CSVParser csvParser = new CSVParser();
    csvParser.parseCSV(input);
  }
}
