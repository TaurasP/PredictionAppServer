package eif.viko.lt.predictionappserver.Controllers;

import eif.viko.lt.predictionappserver.Entities.Course;
import eif.viko.lt.predictionappserver.Services.CourseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/courses")
public class CourseController {

    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    // POST request to save a course
    @PostMapping
    public ResponseEntity<Course> saveCourse(@RequestBody Course course) {
        Course savedCourse = courseService.saveCourse(course);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedCourse);
    }
}
