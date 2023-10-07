package uz.eprsystem.service;

import org.springframework.stereotype.Service;
import uz.eprsystem.entity.dto.AttendanceResponseDto;

import java.util.List;
import java.util.UUID;

@Service
public class GroupStageService {
    public String startingGroup(UUID groupId) {
        return null;
    }

    public List<AttendanceResponseDto> groupAttendance(UUID groupId, List<Boolean> attendance) {
        return null;
    }
}
