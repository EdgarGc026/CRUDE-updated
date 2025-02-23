package services;

import models.Student;
import utils.KeyboardInput;

import java.sql.SQLException;
import java.util.List;

public class CrudeService {
  CrudeDAOService crudeDAOService;
  KeyboardInput keyboardInput;

  public CrudeService() {
    try {
      crudeDAOService = new CrudeDAOService();
      keyboardInput = new KeyboardInput();
    } catch (SQLException | ClassNotFoundException e) {
      System.out.println("Error: " + e.getMessage());
    }
  }

  public List<Student> list() throws SQLException {
    return crudeDAOService.list();
  }

  public int create( Student student ) throws SQLException {
    if (student.getIdentification() == null || student.getIdentification().trim().isBlank()) {
      throw new IllegalArgumentException("Student cannot be null");
    }
    Student studentFounded = crudeDAOService.findStudentByIdentification(student.getIdentification());
    if (studentFounded == null){
      return this.save(student, true);
    }
    return 0;
  }

  public Student show(String identification){
    Student studentFounded = crudeDAOService.findStudentByIdentification(identification);
    if (studentFounded == null) {
      throw new IllegalArgumentException("Student not found");
    }
    return studentFounded;
  }

  public int update(Student student) throws SQLException {
    if (student.getIdentification() == null || student.getIdentification().trim().isBlank()) {
      throw new IllegalArgumentException("Student cannot be null or blank");
    }
    Student studentFounded = crudeDAOService.findStudentByIdentification(student.getIdentification());
    if (studentFounded == null) {
      throw new IllegalArgumentException("Student not found");
    }
    return this.save(student, false);
  }

  private int save(Student student, boolean isNewStudent) throws SQLException {
    return crudeDAOService.buildSave(student, isNewStudent);
  }

  public int delete(String identification) throws SQLException {
    if (identification == null || identification.trim().isBlank()) {
      throw new IllegalArgumentException("Identification cannot be null or blank");
    }
    return crudeDAOService.executeDelete(identification);
  }
}
