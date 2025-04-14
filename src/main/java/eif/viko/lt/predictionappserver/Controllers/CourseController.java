package eif.viko.lt.predictionappserver.Controllers;

import eif.viko.lt.predictionappserver.Dto.CourseRequestDto;
import eif.viko.lt.predictionappserver.Entities.ChatUser;
import eif.viko.lt.predictionappserver.Entities.Course;
import eif.viko.lt.predictionappserver.Repositories.ChatUserRepository;
import eif.viko.lt.predictionappserver.Services.CourseService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static eif.viko.lt.predictionappserver.Utils.EmailToNameConverter.generateEmail;

@RestController
@RequestMapping("/api/courses")
public class CourseController {

    private final CourseService courseService;
    private final ChatUserRepository chatUserRepository;

    public CourseController(CourseService courseService, ChatUserRepository chatUserRepository) {
        this.courseService = courseService;
        this.chatUserRepository = chatUserRepository;
    }

    @PostMapping("/teacher")
    public ResponseEntity<String> saveCourseFromTeacherName(@RequestBody CourseRequestDto course) {
        String teacherEmail = generateEmail(course.getTeacherName(), course.getTeacherSurname());
        ChatUser teacher = chatUserRepository.findByEmail(teacherEmail).orElseThrow(() -> new IllegalArgumentException("Teacher not found with email: " + teacherEmail));
        courseService.saveCourse(new Course(null, course.getCourseName(), teacher));
        return ResponseEntity.ok("Course saved successfully");
    }

    @PostMapping
    public ResponseEntity<String> saveCourse(@RequestBody Course course) {
        Course savedCourse = courseService.saveCourse(course);
        return ResponseEntity.ok("Saved course: " + savedCourse.getName());
    }
}
