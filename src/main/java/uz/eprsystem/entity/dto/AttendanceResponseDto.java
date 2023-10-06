package uz.eprsystem.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AttendanceResponseDto {

    private UUID id;
    private UserResponseDto userResponseDto;
    private boolean isAttendant;

}
