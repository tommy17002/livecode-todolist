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
    public static class RegisterRequest {
//        @NotBlank(message = "Write your name!")
//        private String name;

        @NotBlank(message = "Write your email!")
        private String email;

        @NotBlank(message = "Write your username!")
        private String username;

        @NotBlank(message = "Write your password!")
        private String password;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class LoginRequest {
        @NotBlank(message = "Write your username!")
        private String username;

        @NotBlank(message = "Write your password!")
        private String password;
    }

//    @Data
//    @Builder
//    @AllArgsConstructor
//    @NoArgsConstructor
//    public  static class RefreshTokenRequest {
//        private String refreshToken;
//    }
//
//    public static class AccessTokenResponse {
//        private String accessToken;
//    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class AuthenticationResponse {
        private String token;
        private String message;
    }
}