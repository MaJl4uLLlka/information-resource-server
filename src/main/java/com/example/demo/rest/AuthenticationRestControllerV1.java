package com.example.demo.rest;

import com.example.demo.dto.AuthenticationRequestDTO;
import com.example.demo.dto.user.CreateUserDTO;
import com.example.demo.dto.user.UserDTO;
import com.example.demo.entity.user.User;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.exception.UserAlreadyExistException;
import com.example.demo.repository.UserRepo;
import com.example.demo.security.jwt.JwtTokenProvider;
import com.example.demo.service.MailService;
import com.example.demo.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationRestControllerV1 {

    private final AuthenticationManager authenticationManager;
    private final UserRepo userRepo;
    private final UserService userService;
    private final MailService mailService;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthenticationRestControllerV1(AuthenticationManager authenticationManager, UserRepo userRepo, UserService userService, MailService mailService, JwtTokenProvider jwtTokenProvider) {
        this.authenticationManager = authenticationManager;
        this.userRepo = userRepo;
        this.userService = userService;
        this.jwtTokenProvider = jwtTokenProvider;
        this.mailService = mailService;
    }


    @PostMapping("/reg")
    public ResponseEntity<UserDTO> registration(@Valid @RequestBody CreateUserDTO newUser) throws UserAlreadyExistException {
        UserDTO userDTO = userService.add(newUser);

        String message = "<a href=\"http://localhost:3000/activate/" + userDTO.getId() + "\">Activate</a>";

        new Thread(() -> {
            try {
                mailService.send(userDTO.getEmail(), "User activation", message);
            } catch (MessagingException e) {
                e.printStackTrace();
            }
        }).start();

        return new ResponseEntity<>(userDTO, HttpStatus.CREATED);
    }

    @PatchMapping("/activate/{userId}")
    public ResponseEntity<Void> activate(@PathVariable Long userId) throws ResourceNotFoundException {
        userService.activate(userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticate(@RequestBody AuthenticationRequestDTO request) {
        String username = request.getUsername();
        String password = request.getPassword();

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        User user = userRepo.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User doesn't exists"));
        String token = jwtTokenProvider.createToken(username, user.getRole().name());
        Map<Object, Object> response = new HashMap<>();
        response.put("id", user.getId());
        response.put("username", user.getUsername());
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
