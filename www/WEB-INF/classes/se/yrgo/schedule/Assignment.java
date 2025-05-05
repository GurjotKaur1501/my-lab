package se.yrgo.schedule;

public class Assignment {
  private Substitute teacher;
  private String date;
  private School school;

  public Assignment(Substitute teacher, String date, School school) {
    this.teacher = teacher;
    this.date = date;
    this.school = school;
  }

    public Assignment(String name, String day, String schoolName) {
    }

    public Substitute teacher() {
    return teacher;
  }

  public String date() {
    return date;
  }

  public School school() {
    return school;
  }

  @Override
  public String toString() {
    return "Assignment: " + teacher.name() + " at " + school.name() +
            " (" + school.address() + ") on " + date;
  }
}