package uz.eprsystem.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import uz.eprsystem.entity.dto.GroupResponseDto;
import uz.eprsystem.service.GroupService;
import uz.eprsystem.service.GroupStageService;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class StudentController {
    private final GroupService groupService;
    private final GroupStageService groupStageService;

    @PreAuthorize("hasRole('STUDENT')")
    @GetMapping("/student/get-my-groups")
    public GroupResponseDto getMyGroups(@RequestParam UUID userId){
        return groupService.getMyGroupByUserId(userId);
    }

}
