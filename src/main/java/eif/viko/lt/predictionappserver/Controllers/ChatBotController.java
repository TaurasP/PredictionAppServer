package eif.viko.lt.predictionappserver.Controllers;

import eif.viko.lt.predictionappserver.Entities.ChatHistory;
import eif.viko.lt.predictionappserver.Entities.ChatUser;
import eif.viko.lt.predictionappserver.Repositories.ChatHistoryRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import opennlp.tools.doccat.DoccatModel;
import opennlp.tools.doccat.DocumentCategorizerME;
import opennlp.tools.tokenize.SimpleTokenizer;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.time.LocalDateTime;

import static eif.viko.lt.predictionappserver.Utils.DateFormatter.formatDateTime;

@RestController
@RequestMapping("/chatbot")
public class ChatBotController {

    private final DoccatModel model;
    private final ChatHistoryRepository chatHistoryRepository;

    public ChatBotController(ChatHistoryRepository chatHistoryRepository) throws IOException {
        this.chatHistoryRepository = chatHistoryRepository;
        InputStream customModel = new FileInputStream(Paths.get("src/main/resources/static/trained_models/chatbot-model.bin").toFile());
        model = new DoccatModel(customModel);
    }

    // DTO
    @Getter
    @AllArgsConstructor
    public static class CategorizationResponse {
        private String bestCategory;
        private String[] allCategories;
    }

    @GetMapping("/ask")
    public ResponseEntity<CategorizationResponse> ask(@RequestParam String question) {
        DocumentCategorizerME categorizer = new DocumentCategorizerME(model);
        SimpleTokenizer tokenizer = SimpleTokenizer.INSTANCE;
        String[] tokens = tokenizer.tokenize(question);
        double[] outcomes = categorizer.categorize(tokens);

        // Get all categories
        String[] categories = new String[outcomes.length];
        for (int i = 0; i < outcomes.length; i++) {
            categories[i] = categorizer.getCategory(i);
        }

        // Wrap in a DTO
        CategorizationResponse response = new CategorizationResponse(categorizer.getBestCategory(outcomes), categories);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        ChatUser currentUser = (ChatUser) authentication.getPrincipal();
        chatHistoryRepository.save(new ChatHistory(question, formatDateTime(LocalDateTime.now()), currentUser));
        return ResponseEntity.ok(response);
    }

}



