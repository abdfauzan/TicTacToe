package com.tictactoe.game;

import com.tictactoe.game.dto.UserRequestDTO;
import com.tictactoe.game.dto.UserResponseDTO;
import com.tictactoe.game.service.UserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/auth")//, headers = "x-api-version=v02")
public class AuthenticationController {
    @Autowired
    AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;

    public AuthenticationController(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @PostMapping("/login")
    public ResponseEntity<UserResponseDTO> authenticateUser(@RequestBody UserRequestDTO userRequestDTO) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        userRequestDTO.getUsername(),
                        userRequestDTO.getPassword()
                )
        );
        return ResponseEntity.ok().body(userDetailsService.login(userRequestDTO, authentication));
    }

    @PostMapping("/signup")
    public ResponseEntity<String> registerUser(@RequestBody UserRequestDTO userRequestDTO) {
        return ResponseEntity.ok().body(userDetailsService.signup(userRequestDTO));
    }
}
