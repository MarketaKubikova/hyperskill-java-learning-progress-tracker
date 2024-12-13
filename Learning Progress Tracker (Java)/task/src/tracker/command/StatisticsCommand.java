package tracker.command;

import tracker.model.Course;
import tracker.model.CourseName;
import tracker.model.Student;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.function.Function;

public class StatisticsCommand implements Command {
    private final List<Student> students;
    private final List<Course> courses;

    public StatisticsCommand(List<Student> students, List<Course> courses) {
        this.students = students;
        this.courses = courses;
    }

    @Override
    public void execute() {
        boolean exit = false;
        System.out.println("Type the name of a course to see details or 'back' to quit:");
        String mostPopular = getComparedMax(course -> course.getEnrolledStudents().size());
        String leastPopular = getComparedMin(course -> course.getEnrolledStudents().size());
        String highestActivity = getComparedMax(Course::getActivity);
        String lowestActivity = getComparedMin(Course::getActivity);
        String easiestCourse = getComparedMax(course -> (double) course.getTotalPoints() / course.getActivity());
        String hardestCourse = getComparedMin(course -> (double) course.getTotalPoints() / course.getActivity());
        System.out.printf("""
                Most popular: %s%n
                Least popular: %s%n
                Highest activity: %s%n
                Lowest activity: %s%n
                Easiest course: %s%n
                Hardest course: %%n
                """, mostPopular, leastPopular, highestActivity, lowestActivity, easiestCourse, hardestCourse);

        do {
            Scanner scanner = new Scanner(System.in);
            String input = scanner.nextLine().trim().toLowerCase();

            if (input.equals("back")) {
                exit = true;
            }

            CourseName.getByName(input)
                    .ifPresentOrElse(this::printCourseStatistics, () -> System.out.println("Unknown course"));
        } while (!exit);
    }

    private String getComparedMax(Function<Course, ? extends Number> function) {
        return courses.stream()
                .max(Comparator.comparingDouble(course -> function.apply(course).doubleValue()))
                .map(course -> course.getCourseName().getName())
                .orElse("n/a");
    }

    private String getComparedMin(Function<Course, ? extends Number> function) {
        return courses.stream()
                .min(Comparator.comparingDouble(course -> function.apply(course).doubleValue()))
                .map(course -> course.getCourseName().getName())
                .orElse("n/a");
    }

    private void printCourseStatistics(CourseName course) {
        System.out.println(course.getName());
        System.out.println("id  points  completed");
        students.stream()
                .filter(c -> c.getCoursePoints().get(course) > 0)
                .sorted(Comparator.comparing(s -> s.getCoursePoints().get(course)))
                .forEach(student -> {
                    Integer coursePoints = student.getCoursePoints().get(course);
                    BigDecimal completion = BigDecimal.valueOf((double) (coursePoints /course.getPoints() * 100));
                    System.out.printf("%d   %d  %b%%n", student.getId(), coursePoints, completion.setScale(1, RoundingMode.HALF_UP));
                });
    }
}
