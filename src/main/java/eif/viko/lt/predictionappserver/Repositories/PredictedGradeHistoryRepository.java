package eif.viko.lt.predictionappserver.Repositories;

import eif.viko.lt.predictionappserver.Entities.PredictedGradeHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PredictedGradeHistoryRepository extends JpaRepository<PredictedGradeHistory, Long> {
}
