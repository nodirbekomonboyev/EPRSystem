package uz.eprsystem.entity;
import jakarta.persistence.Entity;
import lombok.*;

@Entity(name = "lesson")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class LessonEntity extends BaseEntity {

    private Integer module;

    private Integer lessonQueue;

    private String theme;

    private Course course;

}
