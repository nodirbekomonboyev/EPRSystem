package uz.eprsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.eprsystem.entity.UserEntity;

import java.util.UUID;

public interface UserRepository extends JpaRepository<UserEntity, UUID> {
}