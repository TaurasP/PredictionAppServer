package eif.viko.lt.predictionappserver.Services;

import eif.viko.lt.predictionappserver.Entities.ChatUser;
import eif.viko.lt.predictionappserver.Entities.Role;
import eif.viko.lt.predictionappserver.Repositories.ChatUserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    private final ChatUserRepository userRepository;

    public UserService(ChatUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<ChatUser> allUsers() {

        return new ArrayList<>(userRepository.findAll());
    }

    // Fetch all ChatUsers by role
    public List<ChatUser> findAllChatUsersByRole(String role) {
        List<ChatUser> users = new ArrayList<>();
        if (role != null && !role.isEmpty()) {
            users =  userRepository.findAllByRole(Role.valueOf(role.toUpperCase()));
        }
        return users;
    }
}
