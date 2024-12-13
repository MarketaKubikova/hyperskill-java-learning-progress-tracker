package tracker.command;

import tracker.model.Student;

import java.util.List;

public class ListCommand implements Command {
    private final List<Student> students;

    public ListCommand(List<Student> students) {
        this.students = students;
    }

    @Override
    public void execute() {
        System.out.println("Students:");
        if (students.isEmpty()) {
            System.out.println("No students found");
        } else students.forEach(student -> System.out.println(student.getId()));
    }
}
