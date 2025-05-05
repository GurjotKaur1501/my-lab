package se.yrgo.schedule;

import java.sql.*;
import java.util.*;

public class DatabaseAssignments implements Assignments {
  private DBHelper db;
  private static final String SELECT_ALL = "SELECT name, day, school_name, address FROM assignments";
  private static final String SELECT_FOR_TEACHER = "SELECT name, day, school_name, address FROM assignments WHERE substitute_id = ?";
  private static final String SELECT_AT = "SELECT name, day, school_name, address FROM assignments WHERE day = ?";
  private static final String SELECT_FOR_TEACHER_AT = "SELECT name, day, school_name, address FROM assignments WHERE substitute_id = ? AND day = ?";

  public DatabaseAssignments() {
    this.db = db;
  }

  @Override
  public List<Assignment> all() throws AccessException {
    List<Assignment> result = new ArrayList<>();
    try {
      ResultSet rs = db.fetch(SELECT_ALL);
      while (rs.next()) {
        result.add(new Assignment(
                new Substitute(rs.getString("name")),
                rs.getString("day"),
                new School(rs.getString("school_name"), rs.getString("address"))
        ));
      }
      return result;
    } catch (SQLException sqle) {
      throw new AccessException("Problem fetching all assignments", sqle);
    }
  }

  @Override
  public List<Assignment> forTeacher(String substituteId) throws AccessException {
    List<Assignment> result = new ArrayList<>();
    try {
      ResultSet rs = db.fetch(SELECT_FOR_TEACHER, substituteId);
      while (rs.next()) {
        result.add(new Assignment(
                new Substitute(rs.getString("name")),
                rs.getString("day"),
                new School(rs.getString("school_name"), rs.getString("address"))
        ));
      }
      return result;
    } catch (SQLException sqle) {
      throw new AccessException("Problem fetching assignments for teacher", sqle);
    }
  }

  @Override
  public List<Assignment> at(String date) throws AccessException {
    List<Assignment> result = new ArrayList<>();
    try {
      ResultSet rs = db.fetch(SELECT_AT, date);
      while (rs.next()) {
        result.add(new Assignment(
                new Substitute(rs.getString("name")),
                rs.getString("day"),
                new School(rs.getString("school_name"), rs.getString("address"))
        ));
      }
      return result;
    } catch (SQLException sqle) {
      throw new AccessException("Problem fetching assignments at date", sqle);
    }
  }

  @Override
  public List<Assignment> forTeacherAt(String substituteId, String date) throws AccessException {
    List<Assignment> result = new ArrayList<>();
    try {
      ResultSet rs = db.fetch(SELECT_FOR_TEACHER_AT, substituteId, date);
      while (rs.next()) {
        result.add(new Assignment(
                new Substitute(rs.getString("name")),
                rs.getString("day"),
                new School(rs.getString("school_name"), rs.getString("address"))
        ));
      }
      return result;
    } catch (SQLException sqle) {
      throw new AccessException("Problem fetching assignments for teacher at date", sqle);
    }
  }
}