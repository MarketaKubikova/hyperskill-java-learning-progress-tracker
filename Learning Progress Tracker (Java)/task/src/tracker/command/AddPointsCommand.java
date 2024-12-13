package tracker.command;

import tracker.model.Course;
import tracker.model.CourseName;
import tracker.model.Student;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import static tracker.model.CourseName.*;

public class AddPointsCommand implements Command {
    private final List<Student> students;
    private final List<Course> courses;

    public AddPointsCommand(List<Student> students, List<Course> courses) {
        this.students = students;
        this.courses = courses;
    }

    @Override
    public void execute() {
        boolean exit = false;
        System.out.println("Enter an id and points or 'back' to return");

        do {
            Scanner scanner = new Scanner(System.in);
            String input = scanner.nextLine().trim();

            if (input.equalsIgnoreCase("back")) {
                exit = true;
            }

            String[] parts = input.split("\\s+", 5);

            if (parts.length != 5) {
                System.out.println("Incorrect points format");
                break;
            }

            try {
                long studentId = Long.parseLong(parts[0]);
                Student student = students.stream()
                        .filter(s -> s.getId() == studentId)
                        .findFirst()
                        .orElse(null);
                if (student != null) {
                    writePointsAndEnrollStudent(student, parts, courses);
                } else System.out.printf("No student is found for id=%d%n", studentId);
            } catch (NumberFormatException e) {
                System.out.println("Incorrect id format");
                exit = true;
            }
        } while (!exit);
    }

    private void writePointsAndEnrollStudent(Student student, String[] input, List<Course> courses) {
        Map<CourseName, Integer> coursePoints = student.getCoursePoints();
        int javaPoints = getPoints(input[1]);
        int dsaPoints = getPoints(input[2]);
        int dbPoints = getPoints(input[3]);
        int springPoints = getPoints(input[4]);

        coursePoints.replace(JAVA, coursePoints.get(JAVA) + javaPoints);
        coursePoints.replace(DSA, coursePoints.get(DSA) + dsaPoints);
        coursePoints.replace(DATABASES, coursePoints.get(DATABASES) + dbPoints);
        coursePoints.replace(SPRING, coursePoints.get(SPRING) + springPoints);

        byte i = 0;
        for (int points : Arrays.asList(javaPoints, dsaPoints, dbPoints, springPoints)) {
            enrollStudent(courses.get(i), points, student.getId());
            i++;
        }

        System.out.println("Points updated");
    }

    private int getPoints(String stringPoints) {
        try {
            int points = Integer.parseInt(stringPoints);
            return Math.max(points, 0);
        } catch (NumberFormatException e) {
            System.out.println("Incorrect points format");
            return 0;
        }
    }

    private void enrollStudent(Course course, int points, long studentId) {
        if (points != 0) {
            course.setActivity(course.getActivity() + 1);
            course.getEnrolledStudents().add(studentId);
            course.setTotalPoints(course.getTotalPoints() + points);
        }
    }
}
