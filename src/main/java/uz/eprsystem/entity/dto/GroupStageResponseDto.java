package uz.eprsystem.entity.dto;


import lombok.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GroupStageResponseDto {
    private GroupResponseDto group;
    private LessonResponseDto lesson;
    private String lessonStatus;
    private String courseStatus;
    private List<AttendanceResponseDto> studentAttendance;

}
