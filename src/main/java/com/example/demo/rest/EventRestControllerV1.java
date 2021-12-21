package com.example.demo.rest;

import com.example.demo.entity.Event;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/events")
public class EventRestControllerV1 {

    @Autowired
    private EventService eventService;

    @GetMapping
    public ResponseEntity<List<Event>> getAllEvents() {
        return new ResponseEntity<>(eventService.getAll(), HttpStatus.OK);
    }

    @GetMapping(value = {"{id}"})
    public ResponseEntity<Event> getOneEvent(@PathVariable Long id) throws ResourceNotFoundException {
        return new ResponseEntity<>(eventService.getOne(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Event> addEvent(@Valid @RequestBody Event newEvent) {
        return new ResponseEntity<>(eventService.add(newEvent), HttpStatus.CREATED);
    }

    @PutMapping(value = {"{id}"})
    public ResponseEntity<Event> updateEvent(@PathVariable Long id, @Valid @RequestBody Event updateEvent) throws ResourceNotFoundException {
        return new ResponseEntity<>(eventService.update(id, updateEvent), HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteEvent(@PathVariable Long id) {
        eventService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
