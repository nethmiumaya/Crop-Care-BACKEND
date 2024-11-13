package lk.ijse.main.controller;
import lk.ijse.main.dto.UserDTO;
import lk.ijse.main.exception.DataPersistFailedException;
import lk.ijse.main.jwtModels.JWTAuthResponse;
import lk.ijse.main.jwtModels.SignIn;
import lk.ijse.main.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {
    private final AuthenticationService authenticationService;
    private final PasswordEncoder passwordEncoder;

    @PostMapping(value = "/signUp", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<JWTAuthResponse> signUp(@RequestBody UserDTO dto) {
        try {
            log.info("Sign up controller");
            dto.setPassword(passwordEncoder.encode(dto.getPassword()));
            return ResponseEntity.status(HttpStatus.CREATED).body(authenticationService.signUp(dto));
        } catch (DataPersistFailedException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "/signIn")
    public ResponseEntity<JWTAuthResponse> signIn(@RequestBody SignIn signIn) {
        System.out.println("Sign in controller");
        return ResponseEntity.status(HttpStatus.CREATED).body(authenticationService.signIn(signIn));
    }
    @PostMapping("/refresh")
    public ResponseEntity<JWTAuthResponse> refreshToken (@RequestParam ("refreshToken") String refreshToken) {
        return ResponseEntity.status(HttpStatus.CREATED).body(authenticationService.refreshToken(refreshToken));
    }
}