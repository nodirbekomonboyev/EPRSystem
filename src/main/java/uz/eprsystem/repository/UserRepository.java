package uz.eprsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Repository;
import uz.eprsystem.entity.UserEntity;
import uz.eprsystem.entity.UserRole;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static uz.eprsystem.entity.UserRole.MENTOR;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, UUID> {
    Optional<UserEntity> findUserEntityByPhoneNumber(String number);

    List<UserEntity> findAllByRole(UserRole role);
}
