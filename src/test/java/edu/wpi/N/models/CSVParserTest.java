package edu.wpi.N.models;

import edu.wpi.N.Main;
import edu.wpi.N.database.DbNode;
import edu.wpi.N.database.dbController;
import java.io.File;
import java.io.InputStream;
import java.sql.SQLException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito.*;
import org.mockito.internal.matchers.apachecommons.ReflectionEquals;

public class CSVParserTest {

  @BeforeAll
  public static void initializeTest() throws SQLException, ClassNotFoundException {
    dbController.initDB();
  }

  /** Tests that ParseCSV imports Nodes and Edges from csv files into Database successfully */
  @Test
  public void testParseCSV() {
    InputStream inputNodes = Main.class.getResourceAsStream("csv/TestNodes.csv");
    InputStream inputEdges = Main.class.getResourceAsStream("csv/TestEdges.csv");
    CSVParser.parseCSV(inputNodes);
    CSVParser.parseCSV(inputEdges);

    // Will check if first, middle and last were added to DB
    DbNode firstActual = new DbNode("AAA", 171, 851, 1, "MainBuilding", "OFF", "Arnold", "A", 'E');
    DbNode middleActual = new DbNode("H5", 316, 1132, 1, "MainBuilding", "HALL", "HALOL", "L", 'N');
    DbNode lastActual = new DbNode("H13", 1341, 1114, 1, "MainBuilding", "HALL", "HALOL", "T", 'V');

    // Compare with first
    Assertions.assertTrue(new ReflectionEquals(dbController.getNode("AAA")).matches(firstActual));
    // Compare center
    Assertions.assertTrue(new ReflectionEquals(dbController.getNode("H5")).matches(middleActual));
    // Compare last
    Assertions.assertTrue(new ReflectionEquals(dbController.getNode("H13")).matches(lastActual));
  }

  /** */
  @Test
  public void testParseCSVfromPath() {
    File f = new File("src/main/resources/edu/wpi/N/csv/TestNodes.csv");
    String path = f.getAbsolutePath();

    CSVParser.parseCSVfromPath(path);

    // Will check if first, middle and last were added to DB
    DbNode firstActual = new DbNode("AAA", 171, 851, 1, "MainBuilding", "OFF", "Arnold", "A", 'E');
    DbNode middleActual = new DbNode("H5", 316, 1132, 1, "MainBuilding", "HALL", "HALOL", "L", 'N');
    DbNode lastActual = new DbNode("H13", 1341, 1114, 1, "MainBuilding", "HALL", "HALOL", "T", 'V');

    // Compare with first
    Assertions.assertTrue(new ReflectionEquals(dbController.getNode("AAA")).matches(firstActual));
    // Compare center
    Assertions.assertTrue(new ReflectionEquals(dbController.getNode("H5")).matches(middleActual));
    // Compare last
    Assertions.assertTrue(new ReflectionEquals(dbController.getNode("H13")).matches(lastActual));
  }
}
