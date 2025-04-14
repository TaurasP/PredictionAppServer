package eif.viko.lt.predictionappserver.Controllers;

import eif.viko.lt.predictionappserver.Entities.PredictedGradeHistory;
import eif.viko.lt.predictionappserver.Services.PredictedGradeHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/predicted-grade-history")
public class PredictedGradeHistoryController {

    @Autowired
    private PredictedGradeHistoryService service;

    @PostMapping
    public PredictedGradeHistory savePredictedGradeHistory(@RequestBody PredictedGradeHistory history) {
        return service.savePredictedGradeHistory(history);
    }

    @PutMapping("/{id}")
    public PredictedGradeHistory updatePredictedGradeHistory(
            @PathVariable Long id, @RequestBody PredictedGradeHistory updatedHistory) {
        return service.updatePredictedGradeHistory(id, updatedHistory);
    }
}
