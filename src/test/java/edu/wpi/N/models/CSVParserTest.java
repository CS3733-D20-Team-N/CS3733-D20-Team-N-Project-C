package edu.wpi.N.models;

import edu.wpi.N.Main;
import edu.wpi.N.database.DbNode;
import edu.wpi.N.database.dbController;
import java.io.File;
import java.io.FileNotFoundException;
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
    DbNode firstActual =
        new DbNode("AAAAAAAAAA", 171, 851, 1, "MainBuil", "OFFI", "Arnold", "AA", 'E');
    DbNode middleActual =
        new DbNode("H500000000", 316, 1132, 1, "MainBuil", "HALL", "HALOL", "LL", 'N');
    DbNode lastActual =
        new DbNode("H130000000", 1341, 1114, 1, "MainBuil", "HALL", "HALOL", "TT", 'V');

    // Compare with first
    Assertions.assertTrue(
        new ReflectionEquals(dbController.getNode("AAAAAAAAAA")).matches(firstActual));
    // Compare center
    Assertions.assertTrue(
        new ReflectionEquals(dbController.getNode("H500000000")).matches(middleActual));
    // Compare last
    Assertions.assertTrue(
        new ReflectionEquals(dbController.getNode("H130000000")).matches(lastActual));
  }

  /**
   * Tests that parceCSVfromPath parces successfully data from CSV to Database given full path to
   * the file
   */
  @Test
  public void testParseCSVfromPath() throws FileNotFoundException {
    File f = new File("src/main/resources/edu/wpi/N/csv/TestNodes.csv");
    String path = f.getAbsolutePath();

    CSVParser.parseCSVfromPath(path);

    // Will check if first, middle and last were added to DB
    DbNode firstActual =
        new DbNode("AAAAAAAAAA", 171, 851, 1, "MainBuil", "OFFI", "Arnold", "AA", 'E');
    DbNode middleActual =
        new DbNode("H500000000", 316, 1132, 1, "MainBuil", "HALL", "HALOL", "LL", 'N');
    DbNode lastActual =
        new DbNode("H130000000", 1341, 1114, 1, "MainBuil", "HALL", "HALOL", "TT", 'V');

    // Compare center
    Assertions.assertTrue(
        new ReflectionEquals(dbController.getNode("H500000000")).matches(middleActual));
    // Compare last
    Assertions.assertTrue(
        new ReflectionEquals(dbController.getNode("H130000000")).matches(lastActual));
  }

  /**
   * Tests that parceCSVfromPath throws file not found exception if incorrect path to file gets
   * inputted
   */
  @Test
  public void testParseCSVFileNotFound() {
    File f = new File("src/main/resources/edu/wpi/N/csv/MapCoor.csv");
    String path = f.getAbsolutePath();

    Assertions.assertThrows(Exception.class, () -> CSVParser.parseCSVfromPath(path));
  }

  /** Tests that parceCSVfromPath successfully parses Prototype Node file */
  @Test
  public void testParseCSVPrototypeNode() throws FileNotFoundException {
    File f = new File("src/main/resources/edu/wpi/N/csv/PrototypeNodes.csv");
    String path = f.getAbsolutePath();

    CSVParser.parseCSVfromPath(path);

    DbNode firstExpected =
        new DbNode(
            "BCONF00102",
            2150,
            1025,
            2,
            "45 Francis",
            "CONF",
            "Duncan Reid Conference Room",
            "Conf B0102",
            'Z');

    // Compare with first
    Assertions.assertTrue(
        new ReflectionEquals(dbController.getNode("BCONF00102")).matches(firstExpected));
  }
}
