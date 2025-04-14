package eif.viko.lt.predictionappserver.Services;

import eif.viko.lt.predictionappserver.Dto.PredictedGradeHistoryResponseDto;
import eif.viko.lt.predictionappserver.Entities.PredictedGradeHistory;
import eif.viko.lt.predictionappserver.Repositories.PredictedGradeHistoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static eif.viko.lt.predictionappserver.Utils.DateFormatter.formatDateTimeToString;
import static eif.viko.lt.predictionappserver.Utils.EmailToNameConverter.getNameFromEmail;

@Service
@Transactional
public class PredictedGradeHistoryService {

    @Autowired
    private PredictedGradeHistoryRepository repository;

    public PredictedGradeHistory savePredictedGradeHistory(PredictedGradeHistory history) {
        return repository.save(history);
    }

    public PredictedGradeHistory updatePredictedGradeHistory(Long id, PredictedGradeHistory updatedHistory) {
        PredictedGradeHistory existingHistory = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("PredictedGradeHistory not found"));
        existingHistory.setAttendance(updatedHistory.getAttendance());
        existingHistory.setAssignments(updatedHistory.getAssignments());
        existingHistory.setMidterm(updatedHistory.getMidterm());
        existingHistory.setFinalExam(updatedHistory.getFinalExam());
        existingHistory.setPredictedGrade(updatedHistory.getPredictedGrade());
        existingHistory.setDate(updatedHistory.getDate());
        existingHistory.setStudentCourse(updatedHistory.getStudentCourse());
        existingHistory.setCourse(updatedHistory.getCourse());
        existingHistory.setStudent(updatedHistory.getStudent());
        existingHistory.setTeacher(updatedHistory.getTeacher());
        return repository.save(existingHistory);
    }

    public List<PredictedGradeHistoryResponseDto> getAllPredictedGradeHistory() {
        List<PredictedGradeHistory> predictedGradesHistory = repository.findAll();
        return predictedGradesHistory.stream()
                .map(it -> new PredictedGradeHistoryResponseDto(
                        it.getId(),
                        it.getAttendance(),
                        it.getAssignments(),
                        it.getMidterm(),
                        it.getFinalExam(),
                        it.getPredictedGrade(),
                        formatDateTimeToString(it.getDate()),
                        it.getCourse().getName(),
                        getNameFromEmail(it.getStudent().getEmail()),
                        getNameFromEmail(it.getTeacher().getEmail())))
                .toList();
    }
}
