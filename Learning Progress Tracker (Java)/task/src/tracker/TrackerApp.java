package tracker;

import tracker.command.Command;
import tracker.model.Course;
import tracker.model.CourseName;
import tracker.model.Student;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class TrackerApp {
    public void run() {
        Scanner scanner = new Scanner(System.in);
        List<Student> students = new ArrayList<>();
        List<Course> courses = Arrays.stream(CourseName.values())
                .map(Course::new)
                .toList();

        CommandFactory commandFactory = new CommandFactory(students, courses);

        System.out.println("Learning Progress Tracker");

        while (true) {
            String input = scanner.nextLine().trim();
            if (input.isEmpty()) {
                System.out.println("No input.");
            } else {
                Command command = commandFactory.getCommand(input);
                command.execute();
            }
        }
    }
}
