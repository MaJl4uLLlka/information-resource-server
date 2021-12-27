package com.example.demo.service;

import com.example.demo.dto.comment.CommentDTO;
import com.example.demo.dto.comment.CreateCommentDTO;
import com.example.demo.entity.Comment;
import com.example.demo.entity.Event;
import com.example.demo.entity.user.User;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.CommentRepo;
import com.example.demo.repository.EventRepo;
import com.example.demo.repository.UserRepo;
import com.example.demo.util.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommentService {
    @Autowired
    private CommentRepo commentRepo;

    @Autowired
    private EventRepo eventRepo;

    @Autowired
    private UserRepo userRepo;

    public List<CommentDTO> getAllCommentsOfEvent(Long eventId) {
        List<Comment> comments = commentRepo.findCommentsByEventId(eventId);

        List<CommentDTO> commentsDTO = Mapper.mapAll(comments, CommentDTO.class);

        for (CommentDTO commentDTO : commentsDTO) {
            comments.forEach(c -> {
                if(commentDTO.getId() == c.getId()) {
                    commentDTO.setUserId(c.getUser().getId());
                    commentDTO.setEventId(c.getEvent().getId());
                    commentDTO.setUsername(c.getUser().getUsername());
                }
            });
        }

        return commentsDTO;
    }

    public List<CommentDTO> getAllCommentsOfEventAndUser(Long eventId, Long userId) {
        List<Comment> comments = commentRepo.findCommentsByEvent_IdAndUser_Id(eventId, userId);

        List<CommentDTO> commentsDTO = Mapper.mapAll(comments, CommentDTO.class);

        for (CommentDTO commentDTO : commentsDTO) {
            comments.forEach(c -> {
                if(commentDTO.getId() == c.getId()) {
                    commentDTO.setUserId(c.getUser().getId());
                    commentDTO.setEventId(c.getEvent().getId());
                    commentDTO.setUsername(c.getUser().getUsername());
                }
            });
        }

        return commentsDTO;
    }

    public CommentDTO getOneCommentOfEvent(Long eventId, Long commentId) throws ResourceNotFoundException {
        Optional<Comment> comment = commentRepo.findCommentByEventIdAndId(eventId, commentId);

        if (comment.isEmpty()) {
            throw new ResourceNotFoundException("Комментарий данного мероприятия не был найден");
        } else {
            CommentDTO commentDTO = Mapper.map(comment.get(), CommentDTO.class);
            commentDTO.setEventId(comment.get().getEvent().getId());
            commentDTO.setUserId(comment.get().getUser().getId());
            commentDTO.setUsername(comment.get().getUser().getUsername());
            return commentDTO;
        }
    }

    public CommentDTO addCommentToEvent(Long eventId, CreateCommentDTO newCommentParam) {
        Event event = eventRepo.findById(eventId).get();
        User user = userRepo.findById(newCommentParam.getUserId()).get();

        Comment comment = new Comment();
        comment.setComment(newCommentParam.getComment());
        comment.setEvent(event);
        comment.setUser(user);

        CommentDTO commentDTO = Mapper.map(commentRepo.save(comment), CommentDTO.class);
        commentDTO.setEventId(eventId);
        commentDTO.setUserId(newCommentParam.getUserId());
        commentDTO.setUsername(comment.getUser().getUsername());

        return commentDTO;
    }

    public void deleteCommentFromEvent(Long commentId) {
        commentRepo.deleteById(commentId);
    }
}
