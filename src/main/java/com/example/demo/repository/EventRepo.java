package com.example.demo.repository;

import com.example.demo.entity.Event;
import com.example.demo.entity.user.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface EventRepo extends CrudRepository<Event, Long> {
    List<Event> findAll();
}
