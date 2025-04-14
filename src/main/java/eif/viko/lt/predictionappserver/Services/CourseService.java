package eif.viko.lt.predictionappserver.Services;

import eif.viko.lt.predictionappserver.Dto.CourseResponseDto;
import eif.viko.lt.predictionappserver.Entities.Course;
import eif.viko.lt.predictionappserver.Repositories.CourseRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static eif.viko.lt.predictionappserver.Utils.EmailToNameConverter.getNameFromEmail;

@Service
public class CourseService {

    private final CourseRepository courseRepository;

    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public Course saveCourse(Course course) {
        return courseRepository.save(course);
    }

    public List<CourseResponseDto> getCourses() {
        List<Course> courses = courseRepository.findAll();
        List<CourseResponseDto> courseResponse = courses.stream()
                .map(course -> new CourseResponseDto(
                        0,
                        course.getName(),
                        getNameFromEmail(course.getTeacher().getEmail())))
                .toList();
        List<CourseResponseDto> sortedCourseResponse = courseResponse.stream().sorted((s1, s2) -> s1.getCourseName().compareToIgnoreCase(s2.getCourseName()))
                .toList();
        sortedCourseResponse.forEach(it -> it.setRowId(sortedCourseResponse.indexOf(it) + 1));
        return sortedCourseResponse;
    }
}
