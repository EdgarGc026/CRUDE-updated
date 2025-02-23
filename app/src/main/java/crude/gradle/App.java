package crude.gradle;

import models.Student;
import services.CrudeService;
import utils.KeyboardInput;

import java.sql.SQLException;
import java.util.List;

public class App {

  public static void main(String[] args) {
    CrudeService crudeService = new CrudeService();
    KeyboardInput keyboardInput = new KeyboardInput();
    init(crudeService, keyboardInput);
  }

  private static void init(CrudeService crudeService, KeyboardInput keyboardInput) {
    int option = 0;
    do {
      try {
        option = Integer.parseInt(keyboardInput.readString("Select an option: \n1. Create student\n2. Show student\n3. Update student\n4. Delete student\n5. Show all students\n6. Exit"));
        menuOperations(option, crudeService, keyboardInput);
      } catch (SQLException | ClassNotFoundException e) {
        System.out.println("Error: " + e.getMessage());
      }
    } while (option != 6);
  }

  public static void menuOperations(int option, CrudeService crudeService, KeyboardInput keyboardInput) throws SQLException, ClassNotFoundException {
    switch (option) {
      case 1:
        System.out.println("Create student \n");

        int wasCreated = crudeService.create(new Student(keyboardInput.readString("FullName student: "), keyboardInput.readString("Group student: "), keyboardInput.readString("Identification student: ")));
        if (wasCreated == 0) {
          System.out.println("Student not created");
        } else {
          System.out.println("Student created successfully");
        }
        break;
      case 2:
        System.out.println("Show students \n");
        Student st = crudeService.show(keyboardInput.readString("Enter student identification: "));
        if (st == null) {
          System.out.println("Student not found");
        } else {
          System.out.println("Student found: " + st.getFullname() + " - " + st.getGroup() + " - " + st.getIdentification());
        }
        break;
      case 3:
        System.out.println("Update student \n");

        String identification = keyboardInput.readString("Enter student identification: ");
        int wasUpdate = crudeService.update(new Student(keyboardInput.readString("Enter new full name: "), keyboardInput.readString("Enter new group: "), identification));
        if (wasUpdate == 0) {
          System.out.println("Student not updated");
        } else {
          System.out.println("Student updated successfully");
        }
        break;
      case 4:
        System.out.println("Delete student \n");

        int wasDeleted = crudeService.delete(keyboardInput.readString("Enter student identification: "));
        if (wasDeleted == 0) {
          System.out.println("Student not deleted");
        } else {
          System.out.println("Student deleted successfully");
        }
        break;
      case 5:
        System.out.println("Show all students \n");

        List<Student> list = crudeService.list();
        if (list.isEmpty()) {
          System.out.println("No students found");
        } else {
          for (Student student : list) {
            System.out.println("Student found: " + student.getFullname() + " - " + student.getGroup() + " - " + student.getIdentification());
          }
        }
        break;
      case 6:
        System.out.println("Exit");
        break;
    }
  }
}
