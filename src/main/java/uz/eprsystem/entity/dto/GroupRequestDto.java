package uz.eprsystem.entity.dto;

import lombok.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GroupRequestDto {

    private String course;
    private UserResponseDto mentor;
    private List<UserRequestDto> students;
    private GroupStageRequestDto groupStageResponseDto;

}
