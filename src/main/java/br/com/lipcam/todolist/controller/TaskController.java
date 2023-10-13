package br.com.lipcam.todolist.controller;

import br.com.lipcam.todolist.model.TaskModel;
import br.com.lipcam.todolist.repository.ITaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/tasks")
public class TaskController {

    @Autowired
    ITaskRepository iTaskRepository;

    @PostMapping
    public ResponseEntity create(@RequestBody TaskModel entity) {

        return ResponseEntity.status(HttpStatus.CREATED).body(iTaskRepository.save(entity));
    }
}
