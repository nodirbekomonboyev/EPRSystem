package uz.eprsystem.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import uz.eprsystem.entity.GroupEntity;
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














    private GroupResponseDto entityToResponse(GroupEntity groupEntity){
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

    private GroupEntity requestToEntity(GroupRequestDto groupRequestDto){
        return null;
    }


}
