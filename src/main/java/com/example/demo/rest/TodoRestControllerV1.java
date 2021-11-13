package com.example.demo.rest;

import com.example.demo.entity.TodoEntity;
import com.example.demo.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/todos")
public class TodoRestControllerV1 {

    @Autowired
    private TodoService todoService;

    @PostMapping()
    public ResponseEntity createTodo(@RequestBody TodoEntity todo, @RequestParam Long idUser) {
        try {
            return ResponseEntity.ok(todoService.createTodo(todo, idUser));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Произошла ошибка");
        }
    }

    @PatchMapping
    @PreAuthorize("hasAuthority('developers:write')")
    public ResponseEntity complete(@RequestParam Long id) {
        try {
            return ResponseEntity.ok(todoService.complete(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Произошла ошибка");
        }
    }
}
