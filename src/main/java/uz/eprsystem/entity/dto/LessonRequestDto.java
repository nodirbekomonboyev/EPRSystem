package uz.eprsystem.entity.dto;

import lombok.*;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LessonRequestDto {

    private UUID groupId;
    private int module;
    private int lessonQueue;
    private String theme;
    private String course;
}
