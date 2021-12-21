package com.example.demo.service;

import com.example.demo.entity.user.User;
import com.example.demo.exception.UserAlreadyExistException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.dto.UserDTO;
import com.example.demo.repository.UserRepo;
import com.example.demo.util.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepo userRepo;

    public List<UserDTO> getAll() {
        List<User> users = userRepo.findAll();
        return Mapper.mapAll(users, UserDTO.class);
    }

    public UserDTO getOne(Long id) throws ResourceNotFoundException {
        Optional<User> user = userRepo.findById(id);

        if (user.isEmpty()) {
            throw new ResourceNotFoundException("Пользователь не был найден");
        } else {
            return Mapper.map(user.get(), UserDTO.class);
        }
    }

    public UserDTO add(User user) throws UserAlreadyExistException {
        if (userRepo.findByUsername(user.getUsername()).isPresent()) {
            throw new UserAlreadyExistException("Пользователь с таким логином уже существует");
        }
        return Mapper.map(userRepo.save(user), UserDTO.class);
    }

    public void delete(Long id) {
        userRepo.deleteById(id);
    }
}
