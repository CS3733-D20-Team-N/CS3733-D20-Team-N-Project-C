package edu.wpi.N.models;

import edu.wpi.N.database.DbNode;
import java.util.LinkedList;

public class Path {
  private LinkedList<DbNode> path;

  // constructor
  public Path(LinkedList<DbNode> path) {
    this.path = path;
  }

  public LinkedList<DbNode> getPath() {
    return this.path;
  }
}
