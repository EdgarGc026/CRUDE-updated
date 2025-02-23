package models;

public class Student {
  private String fullname;
  private String group;
  private String identification;

  public Student(String fullname, String group, String identification) {
    this.fullname = fullname;
    this.group = group;
    this.identification = identification;
  }

  public String getFullname() {
    return fullname;
  }

  public void setFullname( String fullname ) {
    this.fullname = fullname;
  }

  public String getGroup() {
    return group;
  }

  public void setGroup( String group ) {
    this.group = group;
  }

  public String getIdentification() {
    return identification;
  }

  public void setIdentification( String identification ) {
    this.identification = identification;
  }

  @Override
  public String toString() {
    return "Student{" + "fullName='" + fullname + '\'' +  ", group='" + group + '\'' +  ", identification='" + identification + '\'' + '}';
  }
}
