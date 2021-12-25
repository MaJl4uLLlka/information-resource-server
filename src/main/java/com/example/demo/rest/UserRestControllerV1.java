package com.example.demo.rest;

    import com.example.demo.dto.user.UserDTO;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/users")
public class UserRestControllerV1 {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers(@RequestParam(required=false) Long eventId) {
        if (eventId != null) {
            return new ResponseEntity<>(userService.getAllOfEvents(eventId), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(userService.getAll(), HttpStatus.OK);
        }
    }

    @GetMapping("{userId}")
    public ResponseEntity<UserDTO> getOneUser(@PathVariable Long userId) throws ResourceNotFoundException {
        return new ResponseEntity<>(userService.getOne(userId), HttpStatus.OK);
    }

    @DeleteMapping("{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {
        userService.delete(userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
