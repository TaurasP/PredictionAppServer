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
public class PredictedGradeHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private double attendance;
    private double assignments;
    private double midterm;
    private double finalExam;
    private String predictedGrade;
    private LocalDateTime date;

    @ManyToOne
    @JoinColumn(name = "student_course_id")
    private StudentCourse studentCourse;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private ChatUser student;

    @ManyToOne
    @JoinColumn(name = "teacher_id")
    private ChatUser teacher;

    public PredictedGradeHistory(double attendance, double assignments, double midterm, double finalExam, String predictedGrade, LocalDateTime date, StudentCourse studentCourse, Course course, ChatUser student, ChatUser teacher) {
        this.attendance = attendance;
        this.assignments = assignments;
        this.midterm = midterm;
        this.finalExam = finalExam;
        this.predictedGrade = predictedGrade;
        this.date = date;
        this.studentCourse = studentCourse;
        this.course = course;
        this.student = student;
        this.teacher = teacher;
    }
}
