package eif.viko.lt.predictionappserver.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponseDto {
    private long expiration;
    private String token;
    private String email;
    private String role;
}