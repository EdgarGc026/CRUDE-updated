package services;

import connection.DatabaseConnection;
import models.Student;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CrudeDAOService {
  private static Connection conn;

  CrudeDAOService() throws SQLException, ClassNotFoundException {
     conn = DatabaseConnection.getConnection();
  }

  List<Student> list() throws SQLException {
    List<Student> studentList =  new ArrayList<>();

    String sql = "SELECT * FROM students";
    try (PreparedStatement preparedStatement = conn.prepareStatement(sql); ResultSet resultSet = preparedStatement.executeQuery()) {
      while (resultSet.next()) {
        studentList.add(new Student(resultSet.getString("fullname"), resultSet.getString("group_student"), resultSet.getString("identification")));
      }
    }catch (SQLException e) {
      throw new SQLException("Error fetching students", e.getMessage());
    }
    return studentList;
  }

  Student findStudentByIdentification( String identification ) {
    if (identification == null || identification.trim().isBlank()) {
      throw new IllegalArgumentException("Identification cannot be null or blank");
    }

    String sql = "SELECT * FROM students WHERE identification = ?";
    try (PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
      preparedStatement.setString(1, identification);

      try (ResultSet resultSet = preparedStatement.executeQuery() ) {
        if (resultSet.next()) {
          return new Student(resultSet.getString("fullname"), resultSet.getString("group_student"), resultSet.getString("identification"));
        }
      }
    } catch (SQLException e) {
      e.printStackTrace();
      throw new RuntimeException("Database error while finding students: ", e);
    }
    return null;
  }

  int executeDelete(String identification) throws SQLException {
    String sql = "DELETE FROM students WHERE identification = ?";

    try (PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
      preparedStatement.setString(1, identification);
      conn.setAutoCommit(false);

      int rowsAffected = preparedStatement.executeUpdate();
      if (rowsAffected > 0) {
        System.out.println("Student deleted successfully");
      }
      return rowsAffected;
    } catch (SQLException e) {
      conn.rollback();
      e.printStackTrace();
      throw new SQLException("Error deleting student", e.getMessage());
    } finally {
      conn.setAutoCommit(true);
    }
  }

  public int buildSave(Student student, boolean isNewStudent) throws SQLException {
    String sql = this.createQuery(student, isNewStudent);
    return this.executeSave(sql, student);
  }

  private String createQuery(Student student, boolean isNewStudent) {
    if (isNewStudent) {
      return "INSERT INTO students (fullname, group_student, identification) VALUES (?, ?, ?)";
    }
    return "UPDATE students SET fullname = ?, group_student = ? WHERE identification = ?";
  }

  private int executeSave(String sql, Student student) throws SQLException {
    this.validateInputs(student);
    try(PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
      preparedStatement.setString(1, student.getFullname());
      preparedStatement.setString(2, student.getGroup());
      preparedStatement.setString(3, student.getIdentification());

      conn.setAutoCommit(false);
      int rowsAffected = preparedStatement.executeUpdate();
      if (rowsAffected > 0) {
        conn.commit();
        System.out.println("Executed successfully");
      }
      return rowsAffected;
    }catch (SQLException e) {
      conn.rollback();
      throw new SQLException("Error saving student", e.getMessage());
    } finally {
      conn.setAutoCommit(true);
    }
  }

  private void validateInputs( Student student ) {
    if (student.getIdentification() == null || student.getIdentification().trim().isBlank()) {
      throw new IllegalArgumentException("Identification cannot be null");
    }
    if (student.getFullname() == null || student.getFullname().trim().isBlank()) {
      throw new IllegalArgumentException("Fullname cannot be null");
    }
    if (student.getGroup() == null || student.getGroup().trim().isBlank()) {
      throw new IllegalArgumentException("Group student cannot be null");
    }
  }
}
