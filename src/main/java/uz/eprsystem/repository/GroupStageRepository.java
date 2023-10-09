package uz.eprsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uz.eprsystem.entity.GroupStage;
import uz.eprsystem.entity.LessonEntity;
import uz.eprsystem.entity.LessonStatus;
import uz.eprsystem.entity.dto.GroupStageResponseDto;
import java.util.Optional;
import java.util.UUID;

public interface GroupStageRepository extends JpaRepository<GroupStage, UUID> {
    Optional<GroupStage> findGroupStageByLesson(LessonEntity lesson);
    void deleteGroupStageByLesson(LessonEntity lesson);

    @Query(value = "select g from group_stage g where g.status =:status")
    Optional<GroupStageResponseDto> findByLessonStatus(
            @Param("status") LessonStatus status);



}
