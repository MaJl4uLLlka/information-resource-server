package com.example.demo.rest;

import com.example.demo.dto.event.CreateEventDTO;
import com.example.demo.dto.event.EventDTO;
import com.example.demo.entity.Event;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/events")
public class EventRestControllerV1 {

    @Autowired
    private EventService eventService;

    @GetMapping
    public ResponseEntity<List<EventDTO>> getAllEvents() {
        return new ResponseEntity<>(eventService.getAll(), HttpStatus.OK);
    }

    @GetMapping("{eventId}")
    public ResponseEntity<EventDTO> getOneEvent(@PathVariable Long eventId) throws ResourceNotFoundException {
        return new ResponseEntity<>(eventService.getOne(eventId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<EventDTO> addEvent(@Valid @RequestBody CreateEventDTO newEvent) {
        return new ResponseEntity<>(eventService.add(newEvent), HttpStatus.CREATED);
    }

    @PostMapping("sub")
    public ResponseEntity<Void> subEvent(@RequestBody HashMap<String, Long> ids) {
        Long eventId = ids.get("eventId");
        Long userId = ids.get("userId");

        eventService.sub(eventId, userId);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("unsub")
    public ResponseEntity<Void> unsubEvent(@RequestBody HashMap<String, Long> ids) {
        Long eventId = ids.get("eventId");
        Long userId = ids.get("userId");

        eventService.unsub(eventId, userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("{eventId}")
    public ResponseEntity<EventDTO> updateEvent(@PathVariable Long eventId, @Valid @RequestBody CreateEventDTO updateEvent) throws ResourceNotFoundException {
        return new ResponseEntity<>(eventService.update(eventId, updateEvent), HttpStatus.OK);
    }

    @DeleteMapping("{eventId}")
    public ResponseEntity<Void> deleteEvent(@PathVariable Long eventId) {
        eventService.delete(eventId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
