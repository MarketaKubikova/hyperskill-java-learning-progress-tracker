package tracker.model;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Student {
        private final long id;
        private final String firstName;
        private final String lastName;
        private final String email;
        private final Map<CourseName, Integer> coursePoints = new HashMap<>();

    public Student(long id, String firstName, String lastName, String email) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        Arrays.stream(CourseName.values()).forEach(course -> this.coursePoints.put(course, 0));
    }

    public long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public Map<CourseName, Integer> getCoursePoints() {
        return coursePoints;
    }
}
