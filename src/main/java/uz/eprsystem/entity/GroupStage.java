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
    @JoinColumn(name = "group")
    private GroupEntity group;

    @ManyToOne
    @JoinColumn(name = "lesson")
    private LessonEntity lesson;

    @Enumerated(EnumType.STRING)
    private LessonStatus lessonStatus;

    @Enumerated(EnumType.STRING)
    private CourseStatus courseStatus;

    @JsonIgnore
    @OneToMany
    @JoinColumn(name = "attendances")
    private List<Attendance> attendances;
}
