package uz.eprsystem.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.eprsystem.entity.UserEntity;
import uz.eprsystem.entity.dto.JwtResponse;
import uz.eprsystem.entity.dto.UserRequestDto;
import uz.eprsystem.exception.DataAlreadyExistsException;
import uz.eprsystem.exception.DataNotFoundException;
import uz.eprsystem.repository.UserRepository;
import uz.eprsystem.service.jwt.JwtService;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;


    public String save(UserRequestDto dto) {
        Optional<UserEntity> userEntity = userRepository.findUserEntityByPhoneNumber(dto.getPhoneNumber());
        if(userEntity.isPresent()) {
            throw  new DataAlreadyExistsException("User already exists");
        }
        UserEntity map = modelMapper.map(dto, UserEntity.class);
        map.setPassword(passwordEncoder.encode(map.getPassword()));
        userRepository.save(map);
        return "Successful saved!";
    }

    public JwtResponse signIn(UserRequestDto dto) {
        UserEntity user = userRepository.findUserEntityByPhoneNumber(dto.getPhoneNumber())
                .orElseThrow(() -> new DataNotFoundException("user not found!"));
        if (passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            return new JwtResponse(jwtService.generateToken(user));
        }
        throw new AuthenticationCredentialsNotFoundException("password did not match");
    }

}
