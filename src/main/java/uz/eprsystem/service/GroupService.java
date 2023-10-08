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
        GroupEntity groupEntity = checkingLessonIsFinished(modelMapper.map(groupRequestDto, GroupEntity.class));
        groupRepository.save(groupEntity);
        return entityToResponse(groupEntity);
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


    private GroupResponseDto update(UUID id, GroupRequestDto groupRequestDto) {
        GroupEntity groupEntity = groupRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Not found data"));
        GroupEntity enteredGroup = modelMapper.map(groupRequestDto, GroupEntity.class);
        if (groupEntity.getIsActive()){
            modelMapper.map(enteredGroup, groupEntity);
            groupRepository.save(groupEntity);
            return entityToResponse(groupEntity);
        }else
            throw new DataNotFoundException("Not existed data");
    }

    private GroupResponseDto getById(UUID id) {
        GroupEntity groupEntity = groupRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Not found data"));
        if (groupEntity.getIsActive()){
            return entityToResponse(groupEntity);
        }

        throw new DataNotFoundException("Not existed data");
    }


    private void delete(UUID id) {
        GroupEntity groupEntity = groupRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("DataNotExist"));
        groupEntity.setIsActive(false);
        groupRepository.save(groupEntity);
    }


//    private LessonEntity changerTheme(LessonEntity lesson){
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
