package edu.wpi.N.models;

import java.util.LinkedList;

public class Path {
  private LinkedList<Node> path;

  // constructor
  public Path(LinkedList<Node> path) {
    this.path = path;
  }

  public LinkedList<Node> getPath() {
    return this.path;
  }
}
