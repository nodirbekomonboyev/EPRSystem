package uz.eprsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.eprsystem.entity.GroupEntity;
import uz.eprsystem.entity.UserEntity;

import java.util.Optional;
import java.util.UUID;

public interface GroupRepository extends JpaRepository<GroupEntity, UUID> {

}
