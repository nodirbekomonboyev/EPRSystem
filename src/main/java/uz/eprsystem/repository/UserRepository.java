package uz.eprsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import uz.eprsystem.entity.UserEntity;
import uz.eprsystem.entity.UserRole;
import uz.eprsystem.entity.dto.UserResponseDto;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, UUID> {
    Optional<UserEntity> findUserEntityByPhoneNumber(String number);

    @Query(value = "select u from users u where u.role =:role")
    List<UserResponseDto> findAllByRole(
            @Param("role") UserRole role);


}
