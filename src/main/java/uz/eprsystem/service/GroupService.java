package uz.eprsystem.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import uz.eprsystem.entity.*;
import uz.eprsystem.entity.dto.GroupRequestDto;
import uz.eprsystem.entity.dto.GroupResponseDto;
import uz.eprsystem.entity.dto.GroupStageResponseDto;
import uz.eprsystem.entity.dto.UserResponseDto;
import uz.eprsystem.exception.DataNotFoundException;
import uz.eprsystem.repository.GroupRepository;

import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GroupService {


    private final GroupRepository groupRepository;
    private final ModelMapper modelMapper;


    public GroupResponseDto create(GroupRequestDto groupRequestDto) {
        GroupEntity groupEntered = requestToEntity(groupRequestDto);
        groupEntered.getStage().setCourseStatus(CourseStatus.OPENED);
        GroupEntity groupEntity = checkingLessonIsFinished(groupEntered);
        if (groupEntity.getIsActive())
            groupRepository.save(groupEntity);
        else throw new DataNotFoundException("Data not exist");

        return entityToResponse(groupEntity);
    }

    public GroupResponseDto update(GroupRequestDto groupRequestDto, UUID id) {
        GroupEntity groupEntity = groupRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Not exist this group"));
        if (groupEntity.getIsActive()) {
            GroupEntity enteredGroup = requestToEntity(groupRequestDto);
            modelMapper.map(enteredGroup, groupEntity);
            groupRepository.save(groupEntity);
            return entityToResponse(groupEntity);
        } else
            throw new DataNotFoundException("Data not found");
    }

    public void delete(UUID id) {
        GroupEntity groupEntity = groupRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Not exist this group"));
        groupEntity.setIsActive(false);
        groupRepository.save(groupEntity);
    }

    public GroupResponseDto getById(UUID id){
        GroupEntity groupEntity = groupRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Not exist this group"));
        if (groupEntity.getIsActive())
            return entityToResponse(groupEntity);
        else
            throw new DataNotFoundException("Not exist this group");
    }


    private GroupEntity checkingLessonIsFinished(GroupEntity groupEntity) {
        LessonStatus lessonStatus = groupEntity.getStage().getLessonStatus();
        LessonEntity lesson = groupEntity.getStage().getLesson();

        if (LessonStatus.FINISHED.toString().equals(lessonStatus.toString().toUpperCase())) {
            groupEntity.getStage().setCourseStatus(CourseStatus.IN_PROGRESS);
            groupEntity.getStage().setLessonStatus(LessonStatus.STARTED);
            if (lesson.getLessonQueue() != 12 && lesson.getCourse().getDurationOfCourse() != lesson.getModule())
                lesson.setLessonQueue(groupEntity.getStage().getLesson().getLessonQueue() + 1);
//                lesson.setTheme();
            else {
                int numberOfLessonToZero = 0;
                lesson.setLessonQueue(numberOfLessonToZero);
                Course course = Course.valueOf(lesson.getCourse().name());
                if (Course.valueOf(course.name()).getDurationOfCourse() != course.getDurationOfCourse()) {
                    lesson.setModule(lesson.getModule() + 1);
//                  lesson.setTheme();
                } else groupEntity.getStage().setCourseStatus(CourseStatus.FINISHED);

            }
        }


        return groupEntity;
    }


//    private LessonEntity changerTheme(LessonEntity lesson) {
//
//    }

    private GroupResponseDto entityToResponse(GroupEntity groupEntity) {
        GroupResponseDto groupResponse = modelMapper.map(groupEntity, GroupResponseDto.class);
        if (!Objects.isNull(groupEntity.getStudents())) {
            groupResponse.setStudents(
                    groupEntity.getStudents()
                            .stream()
                            .map(entity -> modelMapper.map(entity, UserResponseDto.class))
                            .collect(Collectors.toList())
            );
        }

        groupResponse.setGroupStage(modelMapper.map(groupEntity.getStage(), GroupStageResponseDto.class));

        return groupResponse;
    }

    private GroupEntity requestToEntity(GroupRequestDto groupRequestDto) {

        GroupEntity groupEntity = modelMapper.map(groupRequestDto, GroupEntity.class);
        if (!groupRequestDto.getStudents().isEmpty()) {
            groupEntity.setStudents(
                    groupRequestDto.getStudents()
                            .stream()
                            .map(entity -> modelMapper.map(entity, UserEntity.class))
                            .collect(Collectors.toList())
            );
        }
        Course course = Course.valueOf(groupRequestDto.getCourse().toUpperCase());
        groupEntity.setCourse(course);

        groupEntity.setStage(modelMapper.map(groupRequestDto.getGroupStageResponseDto(), GroupStage.class));

        return groupEntity;
    }


}
