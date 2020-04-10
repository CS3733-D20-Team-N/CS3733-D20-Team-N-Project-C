package edu.wpi.N.models;

import java.io.InputStream;

public class AlgosMainCanIgnore {

  public static void main(String[] args) {

    // relative path to .csv file
    InputStream input = edu.wpi.N.Main.class.getResourceAsStream("csv/MapCoordinates.csv");

    CSVParser parser = new CSVParser();
    Graph nodeGraph = parser.parseCSV(input);
    Node startNode = nodeGraph.getNode("MOHSClinic");
    Node endNode = nodeGraph.getNode("HVMANeurology");
    Pathfinder newPath = new Pathfinder(nodeGraph, startNode, endNode);
    Path myPath = newPath.findPath();

    System.out.println("Set a break point here to see the path in debugger");
  }
}
