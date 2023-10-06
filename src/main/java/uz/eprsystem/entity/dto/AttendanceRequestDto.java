package uz.eprsystem.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AttendanceRequestDto {

    private UserRequestDto user;
    private boolean isAttendant;

}
