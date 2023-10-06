package uz.eprsystem.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GroupRequestDto {

    private String course;
    private Integer module;
    private List<UserRequestDto> students;
    private GroupStageRequestDto groupStageResponseDto;

}
