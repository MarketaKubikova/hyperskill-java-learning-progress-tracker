package tracker.command;

import tracker.model.Student;

import java.util.List;
import java.util.Scanner;

import static tracker.model.CourseName.*;

public class FindCommand implements Command {
    private final List<Student> students;

    public FindCommand(List<Student> students) {
        this.students = students;
    }

    @Override
    public void execute() {
        System.out.println("Enter an id or 'back' to return");

        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine().trim();

        try {
            long studentId = Long.parseLong(input);
            students.stream()
                    .filter(student -> student.getId() == studentId)
                    .findFirst()
                    .ifPresentOrElse(student -> System.out.printf("id points: Java=%d; DSA=%d; Databases=%d; Spring=%d%n",
                                    student.getCoursePoints().get(JAVA),
                                    student.getCoursePoints().get(DSA),
                                    student.getCoursePoints().get(DATABASES),
                                    student.getCoursePoints().get(SPRING)
                            ),
                            () -> System.out.printf("No student is found for id=%d%n", studentId));
        } catch (NumberFormatException e) {
            System.out.println("Invalid format of ID");
        }
    }
}
