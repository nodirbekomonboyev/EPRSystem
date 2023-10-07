package uz.eprsystem.entity.dto;

import com.sun.security.auth.UnixNumericUserPrincipal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GroupResponseDto {

    private UUID id;
    private int module;
    private List<UserResponseDto> students;
    private GroupStageResponseDto groupStage;

}