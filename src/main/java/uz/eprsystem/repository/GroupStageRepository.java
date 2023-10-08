package uz.eprsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.eprsystem.entity.GroupStage;

import java.util.UUID;

@Repository
public interface GroupStageRepository extends JpaRepository<GroupStage, UUID> {

}
