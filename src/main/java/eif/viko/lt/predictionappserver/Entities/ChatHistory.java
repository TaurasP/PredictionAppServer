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
public class ChatHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String message;
    private LocalDateTime date;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private ChatUser user;

    public ChatHistory(String message, LocalDateTime date, ChatUser user) {
        this.message = message;
        this.date = date;
        this.user = user;
    }
}
