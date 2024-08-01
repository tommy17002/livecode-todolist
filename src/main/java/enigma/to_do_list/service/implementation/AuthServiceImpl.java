package enigma.to_do_list.service.implementation;

import enigma.to_do_list.model.Role;
import enigma.to_do_list.model.UserEntity;
import enigma.to_do_list.repository.UserRepository;
import enigma.to_do_list.security.JWTService;
import enigma.to_do_list.service.AuthService;
import enigma.to_do_list.utils.DTO.AuthDTO;
import enigma.to_do_list.utils.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JWTService jwtService;
    private final AuthenticationManager authenticationManager;

    @Override
    public ResponseEntity<?> register(AuthDTO.RegisterRequest request) {
        if (repository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("User with this username already exists");
        }
        if (repository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("User with this email already exists");
        }

        var user = UserEntity.builder()
//                .name(request.getName())
                .email(request.getEmail())
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .roles(List.of(Role.ROLE_USER))
                .build();

        UserEntity savedUser = repository.save(user);

        return Response.renderJson(
                savedUser,
                "User registered successfully",
                HttpStatus.CREATED
        );
    }

    @Override
    public AuthDTO.AuthenticationResponse login(AuthDTO.LoginRequest request) {
        UserEntity user = repository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found or username incorrect"));

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        var jwtToken = jwtService.generateToken((UserDetails) user);
        return AuthDTO.AuthenticationResponse.builder()
                .token(jwtToken)
                .message("Login success for User_ID = " + user.getId())
                .build();
    }

//    @Service
//    public class TokenService {
//
//        @Autowired
//        private JwtTokenUtil jwtTokenUtil;
//        @Autowired
//        private RefreshTokenRepository refreshTokenRepository;
//
//        public String refreshToken(String refreshToken) {
//            RefreshToken dbRefreshToken = refreshTokenRepository.findByToken(refreshToken)
//                    .orElseThrow(() -> new TokenRefreshException("Refresh token is not valid"));
//
//            if (dbRefreshToken.isExpired()) {
//                throw new TokenRefreshException("Refresh token is expired");
//            }
//
//            // Generate new access token
//            String accessToken = jwtTokenUtil.generateAccessToken(dbRefreshToken.getUser().getId());
//
//            // Revoke old refresh token
//            dbRefreshToken.setExpired(true);
//            refreshTokenRepository.save(dbRefreshToken);
//
//            // Generate new refresh token
//            String newRefreshToken = jwtTokenUtil.generateRefreshToken();
//            dbRefreshToken.setToken(newRefreshToken);
//            refreshTokenRepository.save(dbRefreshToken);
//
//            return accessToken;
//        }
//    }
}
