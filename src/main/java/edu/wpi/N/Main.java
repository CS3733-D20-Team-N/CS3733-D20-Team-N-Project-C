package edu.wpi.N;

import edu.wpi.N.database.dbController;
import java.sql.SQLException;

public class Main {

  public static void main(String[] args) throws SQLException, ClassNotFoundException {
    dbController.initDB();
    App.launch(App.class, args);
  }
}
