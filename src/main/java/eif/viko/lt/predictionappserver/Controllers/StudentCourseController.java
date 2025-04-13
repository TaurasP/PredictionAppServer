package eif.viko.lt.predictionappserver.Controllers;

import eif.viko.lt.predictionappserver.Dto.StudentCourseResponseDto;
import eif.viko.lt.predictionappserver.Entities.StudentCourse;
import eif.viko.lt.predictionappserver.Services.StudentCourseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/student-courses")
public class StudentCourseController {

    private final StudentCourseService studentCourseService;

    public StudentCourseController(StudentCourseService studentCourseService) {
        this.studentCourseService = studentCourseService;
    }

    // POST request to save a StudentCourse
    @PostMapping
    public ResponseEntity<StudentCourse> saveStudentCourse(@RequestBody StudentCourse studentCourse) {
        StudentCourse savedStudentCourse = studentCourseService.saveStudentCourse(studentCourse);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedStudentCourse);
    }

    // GET request to find all StudentCourse records
    @GetMapping
    public ResponseEntity<List<StudentCourseResponseDto>> findAllStudentCourses() {
        List<StudentCourseResponseDto> studentCourses = studentCourseService.findAllStudentCourses();
        return ResponseEntity.ok(studentCourses);
    }

    // GET request to find students by courseId and teacherId
    @GetMapping("/by-course-and-teacher")
    public ResponseEntity<List<StudentCourse>> findStudentsByCourseIdAndTeacherId(
            @RequestParam Long courseId,
            @RequestParam Long teacherId) {
        List<StudentCourse> studentCourses = studentCourseService.findStudentsByCourseIdAndTeacherId(courseId, teacherId);
        return ResponseEntity.ok(studentCourses);
    }

    // GET request to find all StudentCourses by studentId
    @GetMapping("/by-student")
    public ResponseEntity<List<StudentCourse>> findAllByStudentId(@RequestParam Long studentId) {
        List<StudentCourse> studentCourses = studentCourseService.findAllByStudentId(studentId);
        return ResponseEntity.ok(studentCourses);
    }

    @PutMapping("/{id}")
    public ResponseEntity<StudentCourse> updateStudentCourse(
            @PathVariable Long id,
            @RequestParam String grade,
            @RequestParam double attendance,
            @RequestParam double assignments,
            @RequestParam double midterm,
            @RequestParam double finalExam) {
        StudentCourse updatedStudentCourse = studentCourseService.updateStudentCourse(id, grade, attendance, assignments, midterm, finalExam);
        return ResponseEntity.ok(updatedStudentCourse);
    }


}

