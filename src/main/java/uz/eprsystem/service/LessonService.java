package uz.eprsystem.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import uz.eprsystem.entity.Course;
import uz.eprsystem.entity.GroupEntity;
import uz.eprsystem.entity.LessonEntity;
import uz.eprsystem.entity.LessonStatus;
import uz.eprsystem.entity.dto.GroupResponseDto;
import uz.eprsystem.entity.dto.LessonRequestDto;
import uz.eprsystem.entity.dto.LessonResponseDto;
import uz.eprsystem.repository.LessonRepository;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class LessonService {

    private final LessonRepository lessonRepository;
    private final GroupService groupService;
    private final ModelMapper modelMapper;

    public LessonResponseDto create(LessonRequestDto lessonRequestDto) {
        GroupEntity groupEntity = groupService.findById(lessonRequestDto.getGroupId());
        GroupEntity checkedLesson = checkingLessonIsFinished(groupEntity);
        LessonEntity lesson = checkedLesson.getStage().getLesson();
        lessonRepository.save(lesson);
        return entityToResponse(lesson);
    }


    private GroupEntity checkingLessonIsFinished(GroupEntity groupEntity) {
        LessonStatus lessonStatus = groupEntity.getStage().getLessonStatus();
        LessonEntity lesson = groupEntity.getStage().getLesson();
        if (LessonStatus.FINISHED.toString().equals(lessonStatus.toString().toUpperCase())) {
            groupEntity.getStage().setLessonStatus(LessonStatus.STARTED);
            int nextLesson = groupEntity.getStage().getLesson().getLessonQueue() + 1;
            if (lesson.getLessonQueue() != 12)
                lesson.setLessonQueue(nextLesson);
//                lesson.setTheme();
            else {
                int lessonToNewModule = 0;
                int nextModule = lesson.getModule() + 1;
                lesson.setLessonQueue(lessonToNewModule);
                lesson.setModule(nextModule);
//                lesson.setTheme();
            }
        }

        groupEntity.getStage().setLesson(lesson);
        return groupEntity;
    }


//    private LessonEntity changerTheme(LessonEntity lesson){
//
//    }


    private LessonEntity requestToEntity(LessonRequestDto requestDto) {
        GroupEntity groupEntity = groupService.findById(requestDto.getGroupId());
        LessonEntity lesson = modelMapper.map(requestDto, LessonEntity.class);
        Course course = Course.valueOf(requestDto.getCourse().toUpperCase());
        lesson.setCourse(course);
        lesson.setGroupEntity(groupEntity);
        return lesson;
    }


    private LessonResponseDto entityToResponse(LessonEntity lesson) {
        return null;
    }

}
