package uz.eprsystem.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import uz.eprsystem.entity.Attendance;
import uz.eprsystem.entity.GroupStage;
import uz.eprsystem.entity.dto.AttendanceResponseDto;
import uz.eprsystem.entity.dto.GroupResponseDto;
import uz.eprsystem.entity.dto.UserResponseDto;
import uz.eprsystem.service.GroupService;
import uz.eprsystem.service.GroupStageService;
import uz.eprsystem.service.UserService;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class MentorController {
    private final UserService userService;
    private final GroupService groupService;
    private final GroupStageService groupStageService;

    @PreAuthorize("hasRole('MENTOR')")
    @GetMapping("/get-my-groups")
    public List<GroupResponseDto> getMyGroups(@RequestParam UUID userId){
        return groupService.getMyGroups();
    }
    @PreAuthorize("hasRole('MENTOR')")
    @GetMapping("/get-students-by-group")
    public List<UserResponseDto> getStudentsByGroup(@RequestParam UUID groupId){
        return userService.getStudentsByGroup();
    }

    @PreAuthorize("hasRole('MENTOR')")
    @GetMapping("/starting-group")
    public String startingGroup(@RequestParam UUID groupId){
        return groupStageService.startingGroup(groupId);
    }

    @PreAuthorize("hasRole('MENTOR')")
    @PostMapping("/attendance-of-students")
    public List<AttendanceResponseDto> attendanceOfStudents(
            @RequestParam UUID groupId,
            @RequestParam List<Boolean> attendance
    ){
        return groupStageService.groupAttendance(groupId, attendance);
    }
}