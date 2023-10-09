package uz.eprsystem.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import uz.eprsystem.entity.UserRole;
import uz.eprsystem.entity.dto.UserRequestDto;
import uz.eprsystem.entity.dto.UserResponseDto;
import uz.eprsystem.service.GroupService;
import uz.eprsystem.service.UserService;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class AdminController {
    private final UserService userService;
    private final GroupService groupService;
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/gat-all-students")
    public List<UserResponseDto> getAllStudents(){
       return userService.getAllUserByRole(UserRole.STUDENT);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/gat-all-mentor")
    public List<UserResponseDto> getMentors(){
        return userService.getAllUserByRole(UserRole.MENTOR);
    }


    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/transfer-student")
    public String transferStudent(
            @RequestParam UUID studentId,
            @RequestParam UUID newGroupId)
    {
        return groupService.transferStudent(studentId, newGroupId);
    }

    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @PostMapping("/create-admin")
    public String createAdmin(@RequestBody UserRequestDto admin) {
        return userService.createAdmin(admin);
    }

    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @GetMapping("/delete-admin")
    public String deleteAdmin(@RequestParam String phoneNumber) {
        return userService.deleteAdmin(phoneNumber);
    }

    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @GetMapping("/get-all-admins")
    public List<UserResponseDto> getAllAdmins() {
        return userService.getAllUserByRole(UserRole.ADMIN);
    }
}
