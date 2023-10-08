package uz.eprsystem.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import uz.eprsystem.entity.*;
import uz.eprsystem.entity.dto.AttendanceResponseDto;
import uz.eprsystem.entity.dto.GroupStageResponseDto;
import uz.eprsystem.entity.dto.LessonResponseDto;
import uz.eprsystem.entity.dto.UserResponseDto;
import uz.eprsystem.exception.DataAlreadyExistsException;
import uz.eprsystem.repository.GroupStageRepository;

import java.util.*;

@Service
@RequiredArgsConstructor
public class GroupStageService {
    private final GroupStageRepository groupStageRepository;
    private final GroupService groupService;
    private final LessonService lessonService;
    private final ModelMapper modelMapper;

    public String save(GroupStage groupStage){
        Optional<GroupStage> groupStage1 = groupStageRepository.findGroupStageByLesson(groupStage.getLesson());
        if(groupStage1.isPresent()) {
            throw  new DataAlreadyExistsException("Lesson already finished");
        }
        GroupStage map = modelMapper.map(groupStage, GroupStage.class);
        groupStageRepository.save(map);
        return "Successful saved!";
    }

    public String startingGroup(UUID groupId) {
        GroupEntity groupEntity = groupService.getById(groupId);
        List<GroupStage> byGroup = getByGroup(groupEntity);
        LessonEntity lesson = lessonService.newLesson(byGroup.get(byGroup.size() - 1));
        GroupStage groupStage = GroupStage.builder()
                .group(groupEntity)
                .lesson(lesson)
                .status(LessonStatus.STARTED)
                .build();
        save(groupStage);
        return "started";
    }


    public List<GroupStage> getByGroup(GroupEntity groupEntity){
        List<GroupStage> all = getAll();
        List<GroupStage> list = new ArrayList<>();
        for (GroupStage gs: all) {
            if (Objects.equals(gs.getGroup(), groupEntity)){
                list.add(gs);
            }
        }
        return list;
    }


    public void removeGroupStageByLesson(LessonEntity lesson){
        groupStageRepository.deleteGroupStageByLesson(lesson);
    }


    public List<GroupStage> getAll(){
        return groupStageRepository.findAll();
    }

    public String groupAttendance(UUID groupId, List<Boolean> attendance) {
        List<UserResponseDto> students =  groupService.getStudentsByGroup(groupId);
        GroupEntity group = groupService.getById(groupId);
        Optional<GroupStageResponseDto> byLessonStatus = groupStageRepository.findByLessonStatus(LessonStatus.STARTED);
        LessonResponseDto lesson = byLessonStatus.get().getLesson();
        LessonEntity theLesson = modelMapper.map(lesson, LessonEntity.class);

        List<Attendance> theAttendance = new ArrayList<>();
        for (int i = 0; i< students.size(); i++) {
            Attendance attendances = Attendance.builder()
                    .student(modelMapper.map(students.get(i), UserEntity.class))
                    .isCame(modelMapper.map(attendance.get(i),Attendance.class).getIsCame())
                    .build();
            theAttendance.add(attendances);
        }

        removeGroupStageByLesson(theLesson);
        GroupStage groupStage = GroupStage.builder()
                .group(group)
                .lesson(theLesson)
                .status(LessonStatus.FINISHED)
                .attendances(theAttendance)
                .build();
        save(groupStage);

        return "successfully done!";
    }
}
