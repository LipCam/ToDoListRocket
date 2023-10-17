package br.com.lipcam.todolist.controller;

import br.com.lipcam.todolist.model.TaskModel;
import br.com.lipcam.todolist.repository.ITaskRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping(value = "/tasks")
public class TaskController {

    @Autowired
    ITaskRepository iTaskRepository;

    @PostMapping
    public ResponseEntity create(@RequestBody TaskModel entity, HttpServletRequest request) {
        //recuperando o atributo do filter e setando no IdUser do Task
        entity.setIdUser((UUID)request.getAttribute("idUser"));
        return ResponseEntity.status(HttpStatus.CREATED).body(iTaskRepository.save(entity));
    }
}
