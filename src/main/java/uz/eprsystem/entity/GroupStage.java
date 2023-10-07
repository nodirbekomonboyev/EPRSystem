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
    private GroupEntity group;

    @ManyToOne
    private LessonEntity lesson;

    private LessonStatus status;

    @JsonIgnore
    @OneToMany
    private List<Attendance> attendances;
}
