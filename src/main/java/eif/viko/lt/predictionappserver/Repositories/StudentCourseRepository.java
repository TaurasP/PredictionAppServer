package eif.viko.lt.predictionappserver.Repositories;

import eif.viko.lt.predictionappserver.Entities.StudentCourse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentCourseRepository extends JpaRepository<StudentCourse, Long> {

    List<StudentCourse> findStudentsByCourseIdAndTeacherId(Long courseId, Long teacherId);

    List<StudentCourse> findAllByStudentId(Long studentId);
}
