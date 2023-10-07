package uz.eprsystem.entity;

import com.fasterxml.jackson.databind.ser.Serializers;
import jakarta.persistence.Entity;
import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "lesson")
public class LessonEntity extends BaseEntity {

    private Integer module;

    private Integer lessonQueue;

    private String theme;

    private Course course;

}
