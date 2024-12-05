package lk.ijse.main.service;

import lk.ijse.main.dto.UserDTO;
import lk.ijse.main.jwtModels.JWTAuthResponse;
import lk.ijse.main.jwtModels.SignIn;

public interface AuthenticationService {
    JWTAuthResponse signIn(SignIn signIn);
    JWTAuthResponse signUp(UserDTO signUp);
    JWTAuthResponse refreshToken(String accessToken);

}
