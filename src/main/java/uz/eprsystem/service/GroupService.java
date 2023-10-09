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
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GroupService {
    private final GroupRepository groupRepository;
    private final ModelMapper modelMapper;

    public GroupResponseDto create(GroupRequestDto groupRequestDto) {
        GroupEntity groupEntity = modelMapper.map(groupRequestDto, GroupEntity.class);
        groupRepository.save(groupEntity);
        return entityToResponse(groupEntity);
    }

    public GroupResponseDto update(UUID id, GroupRequestDto groupRequestDto) {
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

    public GroupResponseDto getById(UUID id) {
        GroupEntity groupEntity = groupRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Not found data"));
        if (groupEntity.getIsActive()){
            return entityToResponse(groupEntity);
        }

        throw new DataNotFoundException("Not existed data");
    }


    public void delete(UUID id) {
        GroupEntity groupEntity = groupRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Data Not Exist"));
        groupEntity.setIsActive(false);
        groupRepository.save(groupEntity);
    }

    public GroupEntity findById(UUID id){
        GroupEntity groupEntity = groupRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Not found data"));

        if (groupEntity.getIsActive())
            return groupEntity;
        else
            throw new DataNotFoundException("Not exist data");
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

    public List<GroupResponseDto> getMyGroups(UUID id) {
        List<GroupResponseDto> groups = new ArrayList<>();
        groupRepository.findAllByMentorId(id).forEach(response -> {
            groups.add(modelMapper.map(response, GroupResponseDto.class));
        });

        if(!groups.isEmpty()){
            return groups;
        }
        throw new DataNotFoundException("Data not found");
    }

    public String transferStudent(UUID studentId, UUID newGroupId) {
        return null;
    }

    public List<UserResponseDto> getStudentsByGroup(UUID groupId) {
        return null;
    }
}
