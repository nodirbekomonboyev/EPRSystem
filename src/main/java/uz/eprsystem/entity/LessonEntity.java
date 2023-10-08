package uz.eprsystem.entity;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.*;

@Entity(name = "lesson")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class LessonEntity extends BaseEntity {

    @ManyToOne
    private GroupEntity groupEntity;

    private Integer module;

    private Integer lessonQueue;

    private String theme;

    private Course course;

}
