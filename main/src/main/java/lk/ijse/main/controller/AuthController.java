package lk.ijse.main.controller;
import jakarta.validation.Valid;
import lk.ijse.main.dto.UserDTO;
import lk.ijse.main.exception.DataPersistFailedException;
import lk.ijse.main.jwtModels.JWTAuthResponse;
import lk.ijse.main.jwtModels.SignIn;
import lk.ijse.main.service.AuthenticationService;
import lk.ijse.main.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for managing authentication operations.
 */
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "http://127.0.0.1:5501")
public class AuthController {
    private final AuthenticationService authenticationService;
    private final PasswordEncoder passwordEncoder;
private final UserService userService;
    /**
     * POST /api/v1/auth/signUp : Register a new user.
     *
     * @param dto the user data transfer object
     * @return the ResponseEntity with status 201 (Created) and the JWT authentication response, or 400 (Bad Request) if the data is invalid
     */
    @PostMapping(value = "/signUp", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<JWTAuthResponse> signUp(@Valid @RequestBody UserDTO dto) {
        try {
            log.info("Sign up controller");
            dto.setPassword(passwordEncoder.encode(dto.getPassword()));
            return ResponseEntity.status(HttpStatus.CREATED).body(authenticationService.signUp(dto));
        } catch (DataPersistFailedException e) {
            log.error("Data persist failed during sign up");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            log.error("Internal server error during sign up");
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * POST /api/v1/auth/signIn : Authenticate a user.
     *
     * @param signIn the sign-in request containing user credentials
     * @return the ResponseEntity with status 201 (Created) and the JWT authentication response
     */
    @PostMapping(value = "/signIn")
    public ResponseEntity<JWTAuthResponse> signIn(@Valid @RequestBody SignIn signIn) {
        return ResponseEntity.status(HttpStatus.CREATED).body(authenticationService.signIn(signIn));
    }

    /**
     * POST /api/v1/auth/refresh : Refresh the JWT token.
     *
     * @param refreshToken the refresh token
     * @return the ResponseEntity with status 201 (Created) and the new JWT authentication response
     */
    @PostMapping("/refresh")
    public ResponseEntity<JWTAuthResponse> refreshToken (@RequestParam ("refreshToken") String refreshToken) {
        log.info("Refresh token controller");
        return ResponseEntity.status(HttpStatus.CREATED).body(authenticationService.refreshToken(refreshToken));
    }

}