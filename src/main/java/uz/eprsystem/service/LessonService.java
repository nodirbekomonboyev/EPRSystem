package uz.eprsystem.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import uz.eprsystem.entity.Course;
import uz.eprsystem.entity.LessonEntity;
import uz.eprsystem.entity.dto.LessonRequestDto;
import uz.eprsystem.repository.LessonRepository;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LessonService {
    private final LessonRepository lessonRepository;
    private final ModelMapper modelMapper;



    public String save(LessonRequestDto dto){
        LessonEntity map = modelMapper.map(dto, LessonEntity.class);
        lessonRepository.save(map);
        return "lesson successfully saved";
    }

    public List<LessonEntity> getByCourse(Course course){
        return lessonRepository.getAllByCourse(course);
    }


    public LessonEntity getById(UUID id){
        return lessonRepository.getById(id);
    }


}
