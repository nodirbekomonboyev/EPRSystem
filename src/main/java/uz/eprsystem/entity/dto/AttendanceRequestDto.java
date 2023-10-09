package uz.eprsystem.entity.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AttendanceRequestDto {

    private UserRequestDto user;
    private boolean isAttendant;

}
