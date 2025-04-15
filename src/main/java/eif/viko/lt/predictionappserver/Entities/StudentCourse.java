package eif.viko.lt.predictionappserver.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class StudentCourse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private double attendance;
    private double assignments;
    private double midterm;
    private double finalExam;
    private String grade;
    private String predictedGrade;
    private LocalDateTime date;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private ChatUser student;

    @ManyToOne
    @JoinColumn(name = "teacher_id")
    private ChatUser teacher;

    public StudentCourse(LocalDateTime date, Course course, ChatUser student, ChatUser teacher) {
        this.date = date;
        this.course = course;
        this.student = student;
        this.teacher = teacher;
    }
}
