package com.example.demo;

import com.example.demo.dto.event.CreateEventDTO;
import com.example.demo.dto.event.EventDTO;
import com.example.demo.dto.user.CreateUserDTO;
import com.example.demo.dto.user.UserDTO;
import com.example.demo.entity.user.User;
import com.example.demo.exception.UserAlreadyExistException;
import com.example.demo.security.jwt.JwtTokenProvider;
import com.example.demo.service.EventService;
import com.example.demo.service.UserService;
import com.example.demo.util.Mapper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UnitTests {

    @Autowired
    EventService eventService;

    @Autowired
    UserService userService;

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @Test
    public void addEvent() {
        CreateEventDTO CreateEventDTO = new CreateEventDTO("test.jpg", "Test", "test");

        EventDTO eventDTO = eventService.add(CreateEventDTO);
        Assert.assertNotNull(eventDTO);
        Assert.assertNotNull(eventDTO.getId());
        Assert.assertEquals(CreateEventDTO.getImage(), eventDTO.getImage());
        Assert.assertEquals(CreateEventDTO.getTitle(), eventDTO.getTitle());
        Assert.assertEquals(CreateEventDTO.getDescription(), eventDTO.getDescription());
    }

    @Test(expected = UserAlreadyExistException.class)
    public void saveDuplicateUser() throws UserAlreadyExistException {
        CreateUserDTO createUserDTO = new CreateUserDTO("test", "test", "test@test.test");
        userService.add(createUserDTO);
        userService.add(createUserDTO);
    }

    @Test
    public void testMapper() {
        User user = new User("test", "test", "test@test.test");
        UserDTO userDTO = Mapper.map(user, UserDTO.class);

        Assert.assertEquals(user.getUsername(), userDTO.getUsername());
        Assert.assertEquals(user.getEmail(), userDTO.getEmail());
    }

    @Test
    public void testGetUsernameFromToken() {
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsInJvbGUiOiJBRE1JTiIsImlhdCI6MTY0MDYxOTA5NywiZXhwIjoxNjQwNzA1NDk3fQ.0mc9IvMytb8evrfl6YcszLhrgPHQQioNLe1qvmpJKoU";
        String username = jwtTokenProvider.getUsername(token);
        Assert.assertEquals("admin", username);
    }
}