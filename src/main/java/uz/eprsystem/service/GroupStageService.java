package uz.eprsystem.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.eprsystem.entity.dto.AttendanceResponseDto;
import uz.eprsystem.repository.GroupStageRepository;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GroupStageService {

    private final GroupStageRepository groupStageRepository;

    //method already finished but I don't know why invisible logic part this is test
    public String startingGroup(UUID groupId) {
        return null;
    }

    public List<AttendanceResponseDto> groupAttendance(UUID groupId, List<Boolean> attendance) {
        return null;
    }

}
