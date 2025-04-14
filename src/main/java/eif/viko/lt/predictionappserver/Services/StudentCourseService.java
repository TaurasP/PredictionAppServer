package eif.viko.lt.predictionappserver.Services;

import eif.viko.lt.predictionappserver.Dto.StudentCourseRequestDto;
import eif.viko.lt.predictionappserver.Dto.StudentCourseResponseDto;
import eif.viko.lt.predictionappserver.Entities.PredictedGradeHistory;
import eif.viko.lt.predictionappserver.Entities.StudentCourse;
import eif.viko.lt.predictionappserver.Repositories.PredictedGradeHistoryRepository;
import eif.viko.lt.predictionappserver.Repositories.StudentCourseRepository;
import eif.viko.lt.predictionappserver.Utils.DateFormatter;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import static eif.viko.lt.predictionappserver.Utils.DateFormatter.formatDateTime;
import static eif.viko.lt.predictionappserver.Utils.DateFormatter.formatDateTimeToString;
import static eif.viko.lt.predictionappserver.Utils.EmailToNameConverter.getNameFromEmail;

@Service
public class StudentCourseService {

    private final StudentCourseRepository studentCourseRepository;

    private final PredictedGradeHistoryRepository predictedGradeHistoryRepository;

    public StudentCourseService(StudentCourseRepository studentCourseRepository, PredictedGradeHistoryRepository predictedGradeHistoryRepository) {
        this.studentCourseRepository = studentCourseRepository;
        this.predictedGradeHistoryRepository = predictedGradeHistoryRepository;
    }

    public StudentCourse saveStudentCourse(StudentCourse studentCourse) {
        return studentCourseRepository.save(studentCourse);
    }

    public String updateStudentCourseById(Long id, StudentCourseRequestDto studentCourseRequestDto) {
        // Find the existing StudentCourse by ID
        StudentCourse studentCourse = studentCourseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("StudentCourse not found with id: " + id));

        // Update the fields of the StudentCourse entity
        studentCourse.setAttendance(studentCourseRequestDto.getAttendance());
        studentCourse.setAssignments(studentCourseRequestDto.getAssignments());
        studentCourse.setMidterm(studentCourseRequestDto.getMidterm());
        studentCourse.setFinalExam(studentCourseRequestDto.getFinalExam());
        studentCourse.setGrade(studentCourseRequestDto.getGrade());
        studentCourse.setPredictedGrade(studentCourseRequestDto.getPredictedGrade());
        studentCourse.setDate(formatDateTime(LocalDateTime.now()));

        // Save the updated StudentCourse back to the repository
        studentCourseRepository.save(studentCourse);

        predictedGradeHistoryRepository.save(new PredictedGradeHistory(
                studentCourse.getAttendance(),
                studentCourse.getAssignments(),
                studentCourse.getMidterm(),
                studentCourse.getFinalExam(),
                studentCourse.getPredictedGrade(),
                formatDateTime(LocalDateTime.now()),
                studentCourse,
                studentCourse.getCourse(),
                studentCourse.getStudent(),
                studentCourse.getTeacher()));
        return "Updated student with id " + id;
    }

    public List<StudentCourseResponseDto> findAllStudentCourses() {
        try {
            List<StudentCourse> studentCourses = studentCourseRepository.findAll();
            List<StudentCourseResponseDto> mappedStudents = studentCourses.stream()
                    .map(studentCourse -> new StudentCourseResponseDto(
                            studentCourse.getId(),
                            0,
                            studentCourse.getAttendance(),
                            studentCourse.getAssignments(),
                            studentCourse.getMidterm(),
                            studentCourse.getFinalExam(),
                            studentCourse.getGrade() != null ? studentCourse.getGrade() : "",
                            studentCourse.getPredictedGrade() != null ? studentCourse.getPredictedGrade() : "",
                            studentCourse.getDate() != null ? DateFormatter.formatDateTimeToString(studentCourse.getDate()) : "",
                            studentCourse.getCourse() != null ? studentCourse.getCourse().getName() : "",
                            studentCourse.getTeacher() != null ? getNameFromEmail(studentCourse.getTeacher().getEmail()) : "",
                            studentCourse.getStudent() != null ? getNameFromEmail(studentCourse.getStudent().getEmail()) : ""))
                    .toList();
            List<StudentCourseResponseDto> sortedStudents = mappedStudents.stream()
                    .sorted((s1, s2) -> s1.getStudentName().compareToIgnoreCase(s2.getStudentName()))
                    .toList();
            sortedStudents.forEach(it -> it.setRowId(sortedStudents.indexOf(it) + 1));
            return sortedStudents;
        } catch (Exception e) {
            throw new RuntimeException("Could not fetch student courses");
        }
    }

    public List<StudentCourse> findStudentsByCourseIdAndTeacherId(Long courseId, Long teacherId) {
        return studentCourseRepository.findStudentsByCourseIdAndTeacherId(courseId, teacherId);
    }

    public List<StudentCourse> findAllByStudentId(Long studentId) {
        return studentCourseRepository.findAllByStudentId(studentId);
    }
}

