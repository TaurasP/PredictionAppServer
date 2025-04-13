package eif.viko.lt.predictionappserver.Services;

import eif.viko.lt.predictionappserver.Dto.StudentCourseResponseDto;
import eif.viko.lt.predictionappserver.Entities.StudentCourse;
import eif.viko.lt.predictionappserver.Repositories.StudentCourseRepository;
import org.springframework.stereotype.Service;

import java.util.List;

import static eif.viko.lt.predictionappserver.Utils.DateFormatter.formatDateTime;
import static eif.viko.lt.predictionappserver.Utils.EmailToNameConverter.getNameFromEmail;

@Service
public class StudentCourseService {

    private final StudentCourseRepository studentCourseRepository;

    public StudentCourseService(StudentCourseRepository studentCourseRepository) {
        this.studentCourseRepository = studentCourseRepository;
    }

    public StudentCourse saveStudentCourse(StudentCourse studentCourse) {
        return studentCourseRepository.save(studentCourse);
    }

    public List<StudentCourseResponseDto> findAllStudentCourses() {
        try {
            List<StudentCourse> studentCourses = studentCourseRepository.findAll();
            List<StudentCourseResponseDto> mappedStudents = studentCourses.stream()
                    .map(studentCourse -> new StudentCourseResponseDto(
                            0,
                            studentCourse.getAttendance(),
                            studentCourse.getAssignments(),
                            studentCourse.getMidterm(),
                            studentCourse.getFinalExam(),
                            studentCourse.getGrade() != null ? studentCourse.getGrade() : "",
                            studentCourse.getPredictedGrade() != null ? studentCourse.getPredictedGrade() : "",
                            studentCourse.getDate() != null ? formatDateTime(studentCourse.getDate()) : "",
                            studentCourse.getCourse() != null ? studentCourse.getCourse().getName() : "",
                            studentCourse.getTeacher() != null ? getNameFromEmail(studentCourse.getTeacher().getEmail()) : "",
                            studentCourse.getStudent() != null ? getNameFromEmail(studentCourse.getStudent().getEmail()) : ""))
                    .toList();
            List<StudentCourseResponseDto> sortedStudents = mappedStudents.stream()
                    .sorted((s1, s2) -> s1.getStudentName().compareToIgnoreCase(s2.getStudentName()))
                    .toList();
            sortedStudents.forEach(it -> it.setId(sortedStudents.indexOf(it) + 1));
            return sortedStudents;
        } catch (Exception e) {
            // Log the exception
//            log.error("Error fetching student courses", e);
            throw new RuntimeException("Could not fetch student courses");
        }
    }

    public List<StudentCourse> findStudentsByCourseIdAndTeacherId(Long courseId, Long teacherId) {
        return studentCourseRepository.findStudentsByCourseIdAndTeacherId(courseId, teacherId);
    }

    public List<StudentCourse> findAllByStudentId(Long studentId) {
        return studentCourseRepository.findAllByStudentId(studentId);
    }

    public StudentCourse updateStudentCourse(Long studentCourseId, String grade,
                                             double attendance, double assignments,
                                             double midterm, double finalExam) {
        // Fetch the existing StudentCourse
        StudentCourse studentCourse = studentCourseRepository.findById(studentCourseId)
                .orElseThrow(() -> new RuntimeException("StudentCourse not found"));

        // Update the fields
        studentCourse.setGrade(grade);
        studentCourse.setAttendance(attendance);
        studentCourse.setAssignments(assignments);
        studentCourse.setMidterm(midterm);
        studentCourse.setFinalExam(finalExam);

        // Save and return the updated entity
        return studentCourseRepository.save(studentCourse);
    }
}

