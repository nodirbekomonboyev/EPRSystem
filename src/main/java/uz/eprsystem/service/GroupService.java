package uz.eprsystem.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import uz.eprsystem.entity.*;
import uz.eprsystem.entity.dto.GroupRequestDto;
import uz.eprsystem.entity.dto.GroupResponseDto;
import uz.eprsystem.entity.dto.GroupStageResponseDto;
import uz.eprsystem.entity.dto.UserResponseDto;
import uz.eprsystem.repository.GroupRepository;

import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GroupService {


    private final GroupRepository groupRepository;
    private final ModelMapper modelMapper;


    public GroupResponseDto create(GroupRequestDto groupRequestDto) {
        GroupEntity groupEntity = requestToEntity(groupRequestDto);
        LessonEntity lesson = groupEntity.getStage().getLesson();

    }


    private GroupEntity checkingLessonIsFinished(GroupEntity groupEntity) {
        LessonStatus lessonStatus = groupEntity.getStage().getStatus();
        LessonEntity lesson = groupEntity.getStage().getLesson();
        if (LessonStatus.FINISHED.toString().equals(lessonStatus.toString().toUpperCase())) {
            groupEntity.getStage().setStatus(LessonStatus.STARTED);
            if (lesson.getLessonQueue() != 12)
                  lesson.setLessonQueue(groupEntity.getStage().getLesson().getLessonQueue() + 1);
//                lesson.setTheme();
            else {
                int numberOfLessonToZero = 0;
                lesson.setLessonQueue(numberOfLessonToZero);
//                lesson.setTheme();
            }
        }
    }

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
