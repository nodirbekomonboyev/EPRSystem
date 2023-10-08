package uz.eprsystem.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class AttendanceResponseDto {
    private UserResponseDto userResponseDto;
    private Boolean isAttendant;

}
