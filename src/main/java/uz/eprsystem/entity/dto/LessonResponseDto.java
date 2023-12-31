package uz.eprsystem.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LessonResponseDto {
    private int module;
    private int lessonQueue;
    private String theme;
    private String course;

}
