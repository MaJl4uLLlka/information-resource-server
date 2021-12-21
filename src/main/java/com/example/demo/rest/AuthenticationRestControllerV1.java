package com.example.demo.rest;

import com.example.demo.dto.AuthenticationRequestDTO;
import com.example.demo.dto.UserDTO;
import com.example.demo.entity.user.User;
import com.example.demo.exception.UserAlreadyExistException;
import com.example.demo.repository.UserRepo;
import com.example.demo.security.jwt.JwtTokenProvider;
import com.example.demo.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationRestControllerV1 {

    private AuthenticationManager authenticationManager;
    private UserRepo userRepo;
    private UserService userService;
    private JwtTokenProvider jwtTokenProvider;

    public AuthenticationRestControllerV1(AuthenticationManager authenticationManager, UserRepo userRepo, UserService userService, JwtTokenProvider jwtTokenProvider) {
        this.authenticationManager = authenticationManager;
        this.userRepo = userRepo;
        this.userService = userService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @PostMapping("/reg")
    public ResponseEntity<UserDTO> registration(@Valid @RequestBody User newUser) throws UserAlreadyExistException {
        return new ResponseEntity<>(userService.add(newUser), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticate(@RequestBody AuthenticationRequestDTO request) {
        String login = request.getLogin();
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getLogin(), request.getPassword()));
        User user = userRepo.findByLogin(request.getLogin()).orElseThrow(() -> new UsernameNotFoundException("User doesn't exists"));
        String token = jwtTokenProvider.createToken(request.getLogin(), user.getRole().name());
        Map<Object, Object> response = new HashMap<>();
        response.put("id", user.getId());
        response.put("login", user.getLogin());
        response.put("role", user.getRole().name());
        response.put("token", token);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/logout")
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        SecurityContextLogoutHandler securityContextLogoutHandler = new SecurityContextLogoutHandler();
        securityContextLogoutHandler.logout(request, response, null);
    }
}
