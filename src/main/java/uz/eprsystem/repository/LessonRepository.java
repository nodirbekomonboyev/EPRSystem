package uz.eprsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.eprsystem.entity.Course;
import uz.eprsystem.entity.LessonEntity;

import java.util.List;
import java.util.UUID;

@Repository
public interface LessonRepository extends JpaRepository<LessonEntity, UUID> {
    List<LessonEntity> findAllByCourse(Course course);
    LessonEntity findByLessonQueueAndModule(Integer lessonQueue, Integer Module);
}
