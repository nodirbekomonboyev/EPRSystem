package uz.eprsystem.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
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

}
