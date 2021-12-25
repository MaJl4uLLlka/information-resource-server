package com.example.demo.rest;

import com.example.demo.dto.comment.CommentDTO;
import com.example.demo.dto.comment.CreateCommentDTO;
import com.example.demo.entity.Comment;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/events/{eventId}/comments")
public class CommentRestControllerV1 {

    @Autowired
    private CommentService commentService;

    @GetMapping
    public ResponseEntity<List<CommentDTO>> getAllCommentsOfEvent(@PathVariable Long eventId, @RequestParam(required = false) Long userId) {
        if (userId != null) {
            return new ResponseEntity<>(commentService.getAllCommentsOfEventAndUser(eventId, userId), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(commentService.getAllCommentsOfEvent(eventId), HttpStatus.OK);
        }
    }

    @GetMapping("{commentId}")
    public ResponseEntity<CommentDTO> getOneCommentOfEvent(@PathVariable Long eventId, @PathVariable Long commentId) throws ResourceNotFoundException {
        return new ResponseEntity<>(commentService.getOneCommentOfEvent(eventId, commentId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<CommentDTO> addCommentToEvent(@PathVariable Long eventId, @Valid @RequestBody CreateCommentDTO newComment) {
        return new ResponseEntity<>(commentService.addCommentToEvent(eventId, newComment), HttpStatus.CREATED);
    }

    @DeleteMapping("{commentId}")
    public ResponseEntity<Void> deleteCommentFromEvent(@PathVariable Long eventId, @PathVariable Long commentId) {
        commentService.deleteCommentFromEvent(commentId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
