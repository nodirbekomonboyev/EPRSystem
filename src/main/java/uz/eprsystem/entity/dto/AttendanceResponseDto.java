package uz.eprsystem.entity.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AttendanceResponseDto {
    private UserResponseDto userResponseDto;
    private Boolean isAttendant;

}
