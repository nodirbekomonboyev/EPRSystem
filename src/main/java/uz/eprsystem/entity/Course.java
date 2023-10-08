package uz.eprsystem.entity;

import lombok.Getter;
import lombok.Setter;

@Getter

public enum Course {
    JAVA(10),
    PYTHON(10),
    FLUTTER(10),
    JAVA_SCRIPT(10);


    private final int durationOfCourse;

    Course(int durationOfCourse) {
        this.durationOfCourse = durationOfCourse;
    }
}
