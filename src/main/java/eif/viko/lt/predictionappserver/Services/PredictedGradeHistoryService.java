package eif.viko.lt.predictionappserver.Services;

import eif.viko.lt.predictionappserver.Entities.PredictedGradeHistory;
import eif.viko.lt.predictionappserver.Repositories.PredictedGradeHistoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

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
}
