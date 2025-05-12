package com.tictactoe.game.service;

import com.tictactoe.game.constant.ErrorCode;
import com.tictactoe.game.dto.UserRequestDTO;
import com.tictactoe.game.dto.UserResponseDTO;
import com.tictactoe.game.exception.ResponseStatusException;
import com.tictactoe.game.model.User;
import com.tictactoe.game.repository.UserRepository;
import com.tictactoe.game.security.JwtUtil;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder encoder;
    private final JwtUtil jwtUtils;

    public UserDetailsService(UserRepository userRepository, PasswordEncoder encoder, JwtUtil jwtUtils) {
        this.userRepository = userRepository;
        this.encoder = encoder;
        this.jwtUtils = jwtUtils;
    }

    public UserResponseDTO login(UserRequestDTO userRequestDTO, Authentication authentication) {
        try {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();

            return UserResponseDTO.builder()
                    .username(userRequestDTO.getUsername())
                    .token(jwtUtils.generateToken(userDetails.getUsername()))
                    .build();

        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE,
                    ErrorCode.SERVER_ERROR.getCode(),
                    ErrorCode.SERVER_ERROR.getMessage());
        }
    }

    public String signup(UserRequestDTO userRequestDTO) {
        try {
            if (userRepository.existsByUsername(userRequestDTO.getUsername())) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        ErrorCode.USERNAME_IS_EXIST_ERROR.getCode(),
                        ErrorCode.USERNAME_IS_EXIST_ERROR.getMessage());
            }
            // Create new user's account
            User newUser = new User();
            newUser.setUsername(userRequestDTO.getUsername());
            newUser.setPassword(encoder.encode(userRequestDTO.getPassword()));
            newUser.setCreatedBy("application");
            userRepository.save(newUser);

            return "User registered successfully";

        } catch (Exception exception) {
            if(exception instanceof ResponseStatusException){
                throw exception;
            } else {
                throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE,
                        ErrorCode.SERVER_ERROR.getCode(),
                        ErrorCode.SERVER_ERROR.getMessage());
            }
        }
    }
}
