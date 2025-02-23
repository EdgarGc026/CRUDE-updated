package utils;

import java.util.Scanner;

public class KeyboardInput {
  private final Scanner scanner = new Scanner(System.in);

  public String readString(String message) {
    try {
      System.out.println(message);
      return scanner.nextLine();
    } catch (Exception e) {
      System.out.println("Error: " + e.getMessage());
    }
    return "";
  }

  public int readInt() {
    try {
      int value = scanner.nextInt();
      scanner.nextLine();
      return value;
    }catch (Exception e) {
      System.out.println("Error: " + e.getMessage());
    }
    return 0;
  }
}
