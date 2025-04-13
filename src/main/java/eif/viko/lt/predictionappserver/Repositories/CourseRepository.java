package eif.viko.lt.predictionappserver.Repositories;

import eif.viko.lt.predictionappserver.Entities.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
}