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
    private List<UserEntity> students;

    @JsonIgnore
    @ManyToOne
    private GroupStage stage;
}
