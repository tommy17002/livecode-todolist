package enigma.to_do_list.controller;

import enigma.to_do_list.service.AuthService;
import enigma.to_do_list.utils.DTO.AuthDTO;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/todo/auth")
@RequiredArgsConstructor
@Transactional
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<?> register(
            @RequestBody AuthDTO.RegisterRequest request
    ) {
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthDTO.AuthenticationResponse> login(
            @RequestBody AuthDTO.LoginRequest request
    ) {
        return ResponseEntity.ok(authService.login(request));
    }

//    @PostMapping("/refresh")
//    public ResponseEntity<AuthDTO.AuthenticationResponse> refreshToken
//            (@RequestBody AuthDTO.RefreshTokenRequest request) {
//        String refreshToken = request.getRefreshToken();
//        String accessToken = authService.refreshToken(refreshToken);
//        AuthDTO.AccessTokenResponse response = new AuthDTO.AccessTokenResponse(accessToken);
//        return ResponseEntity.ok(response);
//    }
}

