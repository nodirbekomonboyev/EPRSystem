package uz.eprsystem.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.*;

@Entity(name = "attendance")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Attendance extends BaseEntity{

    @JsonIgnore
    @ManyToOne
    //@JoinColumn(name = "student_id")
    private UserEntity student;

    private Boolean isCame;
}
