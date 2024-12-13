package tracker.model;

import java.util.Arrays;
import java.util.Optional;

public enum CourseName {
    JAVA("Java", 600),
    DSA("DSA", 400),
    DATABASES("Databases", 480),
    SPRING("Spring", 550);

    private final String name;
    private final int points;

    CourseName(String name, int points) {
        this.name = name;
        this.points = points;
    }

    public String getName() {
        return name;
    }

    public int getPoints() {
        return points;
    }

    public static Optional<CourseName> getByName(String name) {
        return Arrays.stream(CourseName.values())
                .filter(courseName -> courseName.getName().equalsIgnoreCase(name))
                .findFirst();
    }
}
