package uz.eprsystem.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity(name = "group")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class GroupEntity extends BaseEntity {

    private Course course;

    private Integer module;

    @JsonIgnore
    @OneToMany
    @JoinColumn(name = "students")
    private List<UserEntity> students;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "group_stage")
    private GroupStage stage;
}
