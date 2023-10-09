package uz.eprsystem.entity.dto;

import lombok.*;
import uz.eprsystem.entity.LessonStatus;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GroupStageRequestDto {

    private GroupRequestDto groupRequestDto;
    private LessonRequestDto lessonRequestDto;
    private LessonStatus lessonStatus;
    private List<AttendanceRequestDto> studentAttendances;
    private String courseStatus;

}
