package uz.eprsystem.entity.dto;

import lombok.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GroupRequestDto {
    private String name;
    private String course;
    private UserResponseDto mentor;
    private List<UserResponseDto> students;
    private GroupStageResponseDto groupStageResponseDto;

}
