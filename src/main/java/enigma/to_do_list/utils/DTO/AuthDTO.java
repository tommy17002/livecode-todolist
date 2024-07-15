package enigma.to_do_list.utils.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@RequiredArgsConstructor

public class AuthDTO {
    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class AuthRequest {
        @NotBlank(message = "Write your username!")
        private String username;

        @NotBlank(message = "Write your password!")
        private String password;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class AuthenticationResponse {
        private String token;
        private String message;
    }
}