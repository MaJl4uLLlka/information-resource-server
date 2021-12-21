package com.example.demo.service;

import com.example.demo.entity.Event;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.EventRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EventService {

    @Autowired
    private EventRepo eventRepo;

    public List<Event> getAll() {
        return eventRepo.findAll();
    }

    public Event getOne(Long id) throws ResourceNotFoundException {
        Optional<Event> event = eventRepo.findById(id);

        if (event.isEmpty()) {
            throw new ResourceNotFoundException("Мероприятие не было найдено");
        } else {
            return event.get();
        }
    }

    public Event add(Event newEvent) {
        return eventRepo.save(newEvent);
    }

    public Event update(Long id, Event updateEvent) throws ResourceNotFoundException {
        Optional<Event> event = eventRepo.findById(id);

        if (event.isEmpty()) {
            throw new ResourceNotFoundException("Мероприятие не было найдено");
        } else {
            event.get().setTitle(updateEvent.getTitle());
            event.get().setImage(updateEvent.getImage());
            event.get().setDescription(updateEvent.getDescription());
            return eventRepo.save(event.get());
        }
    }

    public void delete(Long id) {
        eventRepo.deleteById(id);
    }
}
