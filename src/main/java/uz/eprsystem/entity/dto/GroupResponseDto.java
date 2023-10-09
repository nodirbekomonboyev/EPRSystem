package uz.eprsystem.entity.dto;

import lombok.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GroupResponseDto {
    private UserResponseDto mentor;
    private List<UserResponseDto> students;
    private GroupStageResponseDto groupStage;

}
