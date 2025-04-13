package eif.viko.lt.predictionappserver.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentCourseResponseDto {

    private int id;
    private double attendance;
    private double assignments;
    private double midterm;
    private double finalExam;
    private String grade;
    private String predictedGrade;
    private String date;
    private String courseName;
    private String teacherName;
    private String studentName;

}
