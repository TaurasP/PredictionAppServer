package eif.viko.lt.predictionappserver.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatUserResponseDto {

    private Long id;
    private int rowId;
    private String email;
    private String fullName;

}
