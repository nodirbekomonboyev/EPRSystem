package uz.eprsystem.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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

    private LessonStatus status;

    @JsonIgnore
    @OneToMany
    @JoinColumn(name = "attendances")
    private List<Attendance> attendances;
}
