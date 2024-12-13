package tracker.command;

import tracker.model.Student;

import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

public class AddStudentsCommand implements Command {
    private final List<Student> students;

    public AddStudentsCommand(List<Student> students) {
        this.students = students;
    }

    @Override
    public void execute() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter student credentials or 'back' to return:");
        int count = 0;

        while (true) {
            String line = scanner.nextLine().trim();
            if (line.equalsIgnoreCase("back")) {
                System.out.printf("Total %d students have been added.%n", count);
                break;
            }

            Student student = parseStudent(line);
            if (student != null) {
                students.add(student);
                count++;
                System.out.println("The student has been added.");
            } else {
                System.out.println("Incorrect credentials.");
            }
        }
    }

    private Student parseStudent(String input) {
        String[] parts = input.split("\\s+", 3);
        if (parts.length < 3) return null;

        String firstName = parts[0];
        String lastName = parts[1];
        String email = parts[2];

        if (isValidName(firstName) && isValidName(lastName) && isEmailAvailable(email) && isValidEmail(email)) {
            long studentId = students.size();
            return new Student(studentId, firstName, lastName, email);
        }

        if (!isValidName(firstName)) {
            System.out.println("Incorrect first name.");
        }
        if (!isValidName(lastName)) {
            System.out.println("Incorrect last name.");
        }
        if (!isEmailAvailable(email)) {
            System.out.println("This email is already taken.");
        }
        if (!isValidEmail(email)) {
            System.out.println("Incorrect email.");
        }

        return null;
    }

    private boolean isValidName(String name) {
        return name.length() >= 2 && name.matches("[A-Za-z]([A-Za-z'-]*[A-Za-z])?");
    }

    private boolean isValidEmail(String email) {
        return Pattern.compile("^[\\w.-]+@[A-Za-z]+\\.[A-Za-z]{2,}$").matcher(email).matches();
    }

    private boolean isEmailAvailable(String email) {
        return students.stream()
                .map(Student::getEmail)
                .noneMatch(studentsEmail -> studentsEmail.equals(email));
    }
}
