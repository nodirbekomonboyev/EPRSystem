package uz.eprsystem.entity.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GroupStageResponseDto {

    private UUID id;
    private GroupResponseDto group;
    private LessonResponseDto lesson;
    private String lessonStatus;
    private List<AttendanceResponseDto> studentAttendance;

}