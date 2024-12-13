package tracker.command;

import tracker.model.CourseName;
import tracker.model.Student;

import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

public class NotifyCommand implements Command {
    private static final Predicate<Map.Entry<CourseName, Integer>> COMPLETED_COURSE_PREDICATE = course -> course.getValue().equals(CourseName.valueOf(course.getKey().getName()).getPoints());

    private final List<Student> students;

    public NotifyCommand(List<Student> students) {
        this.students = students;
    }

    @Override
    public void execute() {
        List<Student> studentsToNotify = students.stream()
                .filter(student -> student.getCoursePoints().entrySet().stream()
                        .anyMatch(COMPLETED_COURSE_PREDICATE))
                .toList();

        if (!studentsToNotify.isEmpty()) {
            studentsToNotify.forEach(student -> student.getCoursePoints().entrySet().stream()
                    .filter(COMPLETED_COURSE_PREDICATE)
                    .forEach(course -> System.out.printf("""
                            To: %s%n
                            Re: Your Learning Progress%n
                            Hello, %s %s! You have accomplished our %s course!%n
                            """, student.getEmail(), student.getFirstName(), student.getLastName(), course.getKey().getName()))
            );
        }
        System.out.printf("Total %d students have been notified.%n", studentsToNotify.size());
        students.removeAll(studentsToNotify);
    }
}
