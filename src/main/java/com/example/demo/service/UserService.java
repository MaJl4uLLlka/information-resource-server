package com.example.demo.service;

import com.example.demo.dto.comment.CommentDTO;
import com.example.demo.dto.event.EventDTO;
import com.example.demo.dto.user.CreateUserDTO;
import com.example.demo.entity.Event;
import com.example.demo.entity.user.User;
import com.example.demo.exception.UserAlreadyExistException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.dto.user.UserDTO;
import com.example.demo.repository.EventRepo;
import com.example.demo.repository.UserRepo;
import com.example.demo.util.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private EventRepo eventRepo;

    public List<UserDTO> getAll() {
        List<User> users = userRepo.findAll();
        return Mapper.mapAll(users, UserDTO.class);
    }

    public List<UserDTO> getAllOfEvents(Long eventId) {
        Set<User> usersOfEvent = eventRepo.findById(eventId).get().getUsers();
        return Mapper.mapAll(usersOfEvent, UserDTO.class);
    }

    public UserDTO getOne(Long id) throws ResourceNotFoundException {
        Optional<User> user = userRepo.findById(id);

        if (user.isEmpty()) {
            throw new ResourceNotFoundException("Пользователь не был найден");
        } else {
            return Mapper.map(user.get(), UserDTO.class);
        }
    }

    public UserDTO add(CreateUserDTO newParamUser) throws UserAlreadyExistException {
        if (userRepo.findByUsername(newParamUser.getUsername()).isPresent()) {
            throw new UserAlreadyExistException("Пользователь с таким логином уже существует");
        }

        User newUser = new User();
        newUser.setUsername(newParamUser.getUsername());
        newUser.setPassword(newParamUser.getPassword());
        newUser.setEmail(newParamUser.getEmail());

        return Mapper.map(userRepo.save(newUser), UserDTO.class);
    }

    public void delete(Long id) {
        userRepo.deleteById(id);
    }
}
