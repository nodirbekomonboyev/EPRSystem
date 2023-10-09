package uz.eprsystem.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity(name = "groups")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class GroupEntity extends BaseEntity {

    @Column(unique = true)
    private String name;

    private Course course;

    @ManyToOne
    private UserEntity mentor;


    @JsonIgnore
    @OneToMany
    private List<UserEntity> students;

    @JsonIgnore
    @ManyToOne
    private GroupStage stage;
}
