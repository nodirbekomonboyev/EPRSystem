package uz.eprsystem.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LessonResponseDto {

    private UUID groupId;
    private int module;
    private int lessonQueue;
    private String theme;
    private String course;

}
