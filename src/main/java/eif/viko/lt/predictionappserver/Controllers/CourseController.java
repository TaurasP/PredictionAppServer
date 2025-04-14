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

@RestController
@RequestMapping("/api/courses")
public class CourseController {

    private final CourseService courseService;
    private final ChatUserRepository chatUserRepository;

    public CourseController(CourseService courseService, ChatUserRepository chatUserRepository) {
        this.courseService = courseService;
        this.chatUserRepository = chatUserRepository;
    }

    // POST request to save a course
    @PostMapping("/teacher")
    public ResponseEntity<String> saveCourseFromTeacherName(@RequestBody CourseRequestDto course) {
        ChatUser teacher = chatUserRepository.findByEmail(course.getTeacher()).orElseThrow(() -> new IllegalArgumentException("Teacher not found with email: " + course.getTeacher()));
        Course savedCourse = courseService.saveCourse(new Course(null, course.getName(), teacher));
        return ResponseEntity.ok("Saved course: " + savedCourse.getName());
    }

    @PostMapping
    public ResponseEntity<String> saveCourse(@RequestBody Course course) {
        Course savedCourse = courseService.saveCourse(course);
        return ResponseEntity.ok("Saved course: " + savedCourse.getName());
    }
}
