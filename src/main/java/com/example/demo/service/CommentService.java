package com.example.demo.service;

import com.example.demo.entity.Comment;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.CommentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommentService {

    @Autowired
    private CommentRepo commentRepo;

    public List<Comment> getAll() {
        return commentRepo.findAll();
    }

    public Comment getOne(Long id) throws ResourceNotFoundException {
        Optional<Comment> comment = commentRepo.findById(id);

        if (comment.isEmpty()) {
            throw new ResourceNotFoundException("Комментарий не был найден");
        } else {
            return comment.get();
        }
    }

    public Comment add(Comment newComment) {
        return commentRepo.save(newComment);
    }

    public void delete(Long id) {
        commentRepo.deleteById(id);
    }
}
