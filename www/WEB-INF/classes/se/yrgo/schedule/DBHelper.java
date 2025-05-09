package se.yrgo.schedule;

import javax.servlet.ServletContext;
import java.sql.*;

/**
 * A class with a helper method to get a ResultSet from the database.
 * Also "handles" the Connection to the DB.
 */
public class DBHelper {

  private static Connection con;
  static {
    try {
      con = DriverManager.getConnection("jdbc:sqlite:www/WEB-INF/resources/vikarie.db");
    } catch (SQLException e) {
      System.err.println("Error getting connection: " + e.getMessage());
    }
  }

  public DBHelper(ServletContext servletContext) {

  }

    public ResultSet fetch(String SQL) {
    try {
      //System.out.println("SQL:\n" + SQL);
      Statement stm = con.createStatement();
      return stm.executeQuery(SQL);
    } catch (SQLException e) {
      System.err.println("Error reading from DB: " + e.getMessage());
      return null;
    }
  }

  public ResultSet fetch(String selectForTeacherAt, String substituteId, String date) {
      return null;
  }

  public ResultSet fetch(String selectForTeacher, String substituteId) {
      return null;
  }
}
