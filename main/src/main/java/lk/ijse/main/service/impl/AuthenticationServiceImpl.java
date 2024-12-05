package lk.ijse.main.service.impl;

import lk.ijse.main.dto.UserDTO;
import lk.ijse.main.entity.Staff;
import lk.ijse.main.entity.User;
import lk.ijse.main.exception.StaffNotFoundException;
import lk.ijse.main.jwtModels.JWTAuthResponse;
import lk.ijse.main.jwtModels.SignIn;
import lk.ijse.main.repository.StaffRepository;
import lk.ijse.main.repository.UserRepository;
import lk.ijse.main.service.AuthenticationService;
import lk.ijse.main.service.JWTService;
import lk.ijse.main.util.Mapping;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository userRepository;
    private final StaffRepository staffRepository;
    private final JWTService jwtService;
    private final Mapping mapping;
    private final AuthenticationManager authenticationManager;

    @Override
    public JWTAuthResponse signIn(SignIn signIn) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(signIn.getEmail(),signIn.getPassword()));
        }catch (BadCredentialsException e){
            throw new BadCredentialsException("Invalid credentials");
        }
        var userByEmail = userRepository.findByEmail(signIn.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        var generatedToken = jwtService.generateToken(userByEmail);
        return JWTAuthResponse.builder().token(generatedToken).build() ;
    }

    @Override
    public JWTAuthResponse signUp(UserDTO signUp) {
        Staff staff = staffRepository.findByEmail(signUp.getEmail())
                .orElseThrow(() -> new StaffNotFoundException("Staff not found"));
        userRepository.findByEmail(signUp.getEmail()).ifPresent(user -> {
            throw new IllegalArgumentException("User already exists");
        });
        User userEntity = mapping.convertToUserEntity(signUp);
        userEntity.setRole(staff.getRole());
        var savedUser = userRepository.save(userEntity);
        var genToken = jwtService.generateToken(savedUser);
        return JWTAuthResponse.builder().token(genToken).build();
    }

    @Override
    public JWTAuthResponse refreshToken(String accessToken) {
        var userName = jwtService.extractUsername(accessToken);
        var userEntity =
                userRepository.findByEmail(userName).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        var refreshToken = jwtService.refreshToken(userEntity);
        return JWTAuthResponse.builder().token(refreshToken).build();
    }
}
