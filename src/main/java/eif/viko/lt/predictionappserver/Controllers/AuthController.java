package eif.viko.lt.predictionappserver.Controllers;

import eif.viko.lt.predictionappserver.Dto.LoginResponseDto;
import eif.viko.lt.predictionappserver.Dto.LoginRequestDto;
import eif.viko.lt.predictionappserver.Dto.RegisterRequestDto;
import eif.viko.lt.predictionappserver.Entities.ChatUser;
import eif.viko.lt.predictionappserver.Repositories.ChatUserRepository;
import eif.viko.lt.predictionappserver.Services.AuthService;
import eif.viko.lt.predictionappserver.Services.JwtService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/auth")
@RestController
public class AuthController {
    private final JwtService jwtService;

    private final AuthService authenticationService;

    private final ChatUserRepository userRepository;

    public AuthController(JwtService jwtService, AuthService authenticationService, ChatUserRepository userRepository) {
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
        this.userRepository = userRepository;
    }

    @PostMapping("/signup")
    public ResponseEntity<ChatUser> register(@RequestBody RegisterRequestDto registerUserDto) {
        ChatUser registeredUser = authenticationService.signup(registerUserDto);
        return ResponseEntity.ok(registeredUser);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> authenticate(@RequestBody LoginRequestDto loginUserDto) {
        ChatUser authenticatedUser = authenticationService.authenticate(loginUserDto);

        String jwtToken = jwtService.generateToken(authenticatedUser);

        ChatUser chatUser = userRepository.findByEmail(loginUserDto.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("User not found with email: " + loginUserDto.getEmail()));
        LoginResponseDto loginResponse = new LoginResponseDto(
                jwtService.getExpirationTime(),
                jwtToken,
                chatUser.getEmail(),
                chatUser.getRole().name()
        );
        System.out.println(loginResponse);

        return ResponseEntity.ok(loginResponse);
    }
}