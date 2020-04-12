package edu.wpi.N.models;

import edu.wpi.N.Main;
import edu.wpi.N.database.dbController;
import java.io.InputStream;
import java.sql.SQLException;
import org.junit.jupiter.api.BeforeAll;

public class CSVParserTest {

  @BeforeAll
  public static void initializeTest() throws SQLException, ClassNotFoundException {
    dbController.initDB();
    InputStream inputNodes = Main.class.getResourceAsStream("csv/TestNodes.csv");
    InputStream inputEdges = Main.class.getResourceAsStream("csv/TestEdges.csv");
    CSVParser.parseCSV(inputNodes);
    CSVParser.parseCSV(inputEdges);
  }
}
