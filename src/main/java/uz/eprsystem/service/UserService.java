package uz.eprsystem.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.eprsystem.entity.UserEntity;
import uz.eprsystem.entity.UserRole;
import uz.eprsystem.entity.dto.*;
import uz.eprsystem.exception.DataAlreadyExistsException;
import uz.eprsystem.exception.DataNotFoundException;
import uz.eprsystem.repository.UserRepository;
import uz.eprsystem.service.jwt.JwtService;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final GroupService groupService;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;


    public String save(UserRequestDto dto) {
        Optional<UserEntity> userEntity = userRepository.findUserEntityByPhoneNumber(dto.getPhoneNumber());
        if(userEntity.isPresent()) {
            throw  new DataAlreadyExistsException("User already exists");
        }
        UserEntity map = modelMapper.map(dto, UserEntity.class);
        map.setRole(UserRole.STUDENT);
        map.setPassword(passwordEncoder.encode(map.getPassword()));
        userRepository.save(map);
        return "Successful saved!";
    }

    public JwtResponse signIn(AuthDto authDto) {
        UserEntity user = userRepository.findUserEntityByPhoneNumber(authDto.getPhoneNumber())
                .orElseThrow(() -> new DataNotFoundException("user not found!"));
        if (passwordEncoder.matches(authDto.getPassword(), user.getPassword())) {
            return new JwtResponse(jwtService.generateToken(user));
        }
        throw new AuthenticationCredentialsNotFoundException("password did not match");
    }

    public UserEntity getById(UUID id){
        return userRepository.getById(id);
    }
    public void deleteById(UUID uid) {
        userRepository.deleteById(uid);
    }

    public UserRole checkRole(UUID id){
        UserEntity byId = getById(id);
        return byId.getRole();
    }


    public List<UserResponseDto> getAllUserByRole(UserRole role) {
        List<UserResponseDto> users = new ArrayList<>();
        userRepository.findAllByRole(role).forEach(response -> {
            users.add(modelMapper.map(response, UserResponseDto.class));
        });

        if(!users.isEmpty()){
            return users;
        }

        throw new DataNotFoundException("Data not found");
    }


    public List<UserResponseDto> getStudentsByGroup(UUID id) {
        GroupResponseDto byId = groupService.getById(id);
        return byId.getStudents();
    }

    public String createAdmin(UserRequestDto admin) {
        return null;
    }

    public String deleteAdmin(String phoneNumber) {
        return null;
    }
}
