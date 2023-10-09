package uz.eprsystem.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import uz.eprsystem.entity.*;
import uz.eprsystem.entity.dto.LessonRequestDto;
import uz.eprsystem.entity.dto.LessonResponseDto;
import uz.eprsystem.exception.DataNotFoundException;
import uz.eprsystem.repository.LessonRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

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
    public LessonResponseDto update(LessonRequestDto lessonRequestDto, UUID id){
        LessonEntity enteredRequestLesson = requestToEntity(lessonRequestDto);
        LessonEntity lessonEntity = lessonRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Data not found"));
        if (lessonEntity.getIsActive()){
            lessonRepository.save(lessonEntity);
            modelMapper.map(enteredRequestLesson, lessonEntity);
            return entityToResponse(lessonEntity);
        }
        throw new DataNotFoundException("Not existed data");
    }

    public LessonResponseDto getById(UUID id){
        LessonEntity lessonEntity = lessonRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Data not found"));
        if (lessonEntity.getIsActive())
            return entityToResponse(lessonEntity);

        throw new DataNotFoundException("Not existed data");
    }

    public void delete(UUID id){
        LessonEntity lessonEntity = lessonRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Data not found "));
        lessonEntity.setIsActive(false);
        lessonRepository.save(lessonEntity);
    }

    private GroupEntity checkingLessonIsFinished(GroupEntity groupEntity) {
        LessonStatus lessonStatus = groupEntity.getStage().getLessonStatus();
        LessonEntity lesson = groupEntity.getStage().getLesson();
        if (LessonStatus.FINISHED.toString().equals(lessonStatus.toString().toUpperCase())) {
            groupEntity.getStage().setLessonStatus(LessonStatus.STARTED);
            int nextLesson = groupEntity.getStage().getLesson().getLessonQueue() + 1;
            if (lesson.getLessonQueue() != 12) {
                lesson.setLessonQueue(nextLesson);
                changerTheme(lesson);
            } else {
                int lessonToNewModule = 1;
                int nextModule = lesson.getModule() + 1;
                lesson.setLessonQueue(lessonToNewModule);
                lesson.setModule(nextModule);
                changerTheme(lesson);
            }
        }

        groupEntity.getStage().setLesson(lesson);
        return groupEntity;
    }


    private LessonEntity changerTheme(LessonEntity lesson){
        Integer lessonQueue = lesson.getLessonQueue();
        Integer lessonModule = lesson.getModule();

        String theme = lessonQueue + " theme in module " + lessonModule;
        lesson.setTheme(theme);
        return lesson;
    }


    private LessonEntity requestToEntity(LessonRequestDto requestDto) {
        GroupEntity groupEntity = groupService.findById(requestDto.getGroupId());
        LessonEntity lesson = modelMapper.map(requestDto, LessonEntity.class);
        Course course = Course.valueOf(requestDto.getCourse().toUpperCase());
        lesson.setCourse(course);
        lesson.setGroupEntity(groupEntity);
        return lesson;
    }


    private LessonResponseDto entityToResponse(LessonEntity lesson) {
        LessonResponseDto lessonResponseDto = modelMapper.map(lesson, LessonResponseDto.class);
        lessonResponseDto.setGroupId(lesson.getId());
        lessonResponseDto.setCourse(lesson.getCourse().name());

        return lessonResponseDto;
    }

    public String save(LessonRequestDto dto){
        LessonEntity map = modelMapper.map(dto, LessonEntity.class);
        lessonRepository.save(map);
        return "lesson successfully saved";
    }

    public List<LessonEntity> getByCourse(Course course){
        return lessonRepository.findAllByCourse(course);
    }


    public Optional<LessonEntity> newLesson(Integer lessonQueue, Integer module) {
        if (module<=12 && lessonQueue == 12) {
            return lessonRepository.findByLessonQueueAndModule(1, module + 1);
        }
        return lessonRepository.findByLessonQueueAndModule(lessonQueue + 1,module);
    }
}
