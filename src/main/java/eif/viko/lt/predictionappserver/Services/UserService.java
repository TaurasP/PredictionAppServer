package eif.viko.lt.predictionappserver.Services;

import eif.viko.lt.predictionappserver.Dto.ChatUserResponseDto;
import eif.viko.lt.predictionappserver.Entities.ChatUser;
import eif.viko.lt.predictionappserver.Entities.Role;
import eif.viko.lt.predictionappserver.Repositories.ChatUserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static eif.viko.lt.predictionappserver.Utils.EmailToNameConverter.getNameFromEmail;

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
    public List<ChatUserResponseDto> findAllChatUsersByRole(String role) {
        List<ChatUserResponseDto> result = new ArrayList<>();
        if (role != null && !role.isEmpty()) {
            List<ChatUser> users =  userRepository.findAllByRole(Role.valueOf(role.toUpperCase()));
            List<ChatUserResponseDto> mappedUsers = users.stream().map(user -> new ChatUserResponseDto(
                    user.getId(),
                    0,
                    user.getEmail(),
                    getNameFromEmail(user.getEmail())
            )).toList();
            List<ChatUserResponseDto> sortedUsers = mappedUsers.stream()
                    .sorted((s1, s2) -> s1.getEmail().compareToIgnoreCase(s2.getEmail()))
                    .toList();
            sortedUsers.forEach(it -> it.setRowId(sortedUsers.indexOf(it) + 1));
            result.addAll(sortedUsers);
        }
        return result;
    }
}
