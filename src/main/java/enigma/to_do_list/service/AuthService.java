package enigma.to_do_list.service;

import enigma.to_do_list.utils.DTO.AuthDTO.AuthRequest;
import enigma.to_do_list.utils.DTO.AuthDTO.AuthenticationResponse;
import org.springframework.http.ResponseEntity;

public interface AuthService {
    ResponseEntity<?> register(AuthRequest request);
    AuthenticationResponse login(AuthRequest request);
}
