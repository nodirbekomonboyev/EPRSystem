package uz.eprsystem.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import java.util.List;


@Entity(name = "group_stage")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class GroupStage extends BaseEntity {

    @JsonIgnore
    @ManyToOne
    private GroupEntity group;

    @ManyToOne
    private LessonEntity lesson;

    @Enumerated(EnumType.STRING)
    private LessonStatus status;

    @Enumerated(EnumType.STRING)
    private CourseStatus courseStatus;

    @JsonIgnore
    @OneToMany
    private List<Attendance> attendances;
}
