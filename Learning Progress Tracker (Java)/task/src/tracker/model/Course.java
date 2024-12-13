package tracker.model;

import java.util.HashSet;
import java.util.Set;

public class Course {
    private final CourseName courseName;
    private final Set<Long> enrolledStudents = new HashSet<>();
    private int activity;
    private int totalPoints;


    public Course(CourseName courseName) {
        this.courseName = courseName;
    }

    public CourseName getCourseName() {
        return courseName;
    }

    public Set<Long> getEnrolledStudents() {
        return enrolledStudents;
    }

    public int getActivity() {
        return activity;
    }

    public void setActivity(int activity) {
        this.activity = activity;
    }

    public int getTotalPoints() {
        return totalPoints;
    }

    public void setTotalPoints(int totalPoints) {
        this.totalPoints = totalPoints;
    }
}
