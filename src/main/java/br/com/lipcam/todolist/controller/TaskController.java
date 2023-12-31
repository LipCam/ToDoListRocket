package br.com.lipcam.todolist.controller;

import br.com.lipcam.todolist.model.TaskModel;
import br.com.lipcam.todolist.repository.ITaskRepository;
import br.com.lipcam.todolist.utils.Utils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.UUID;

@RestController
@RequestMapping(value = "/tasks")
public class TaskController {

    @Autowired
    ITaskRepository iTaskRepository;

    @PostMapping
    public ResponseEntity create(@RequestBody TaskModel entity, HttpServletRequest request) {
        var currentData = LocalDateTime.now();
        if(currentData.isAfter(entity.getStartAt()) || currentData.isAfter(entity.getEndAt())){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("A data de inicio/termino deve ser maior que a data atual");
        }

        if(entity.getStartAt().isAfter(entity.getEndAt())){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("A data de inicio deve ser menor que a data de termino");
        }

        //recuperando o atributo do filter e setando no IdUser do Task
        entity.setIdUser((UUID)request.getAttribute("idUser"));
        return ResponseEntity.status(HttpStatus.CREATED).body(iTaskRepository.save(entity));
    }

    @GetMapping
    public ResponseEntity getAllByIdUser(HttpServletRequest request){
        return ResponseEntity.status(HttpStatus.OK).body(iTaskRepository.findByIdUser((UUID)request.getAttribute("idUser")));
    }

    @PutMapping("/{id}")
    public ResponseEntity update(@RequestBody TaskModel entity, @PathVariable UUID id, HttpServletRequest request){

        var task = iTaskRepository.findById(id).orElse(null);

        if(task == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Tarefa nao encontrada");
        }

        var idUser = request.getAttribute("idUser");

        if(!task.getIdUser().equals(idUser)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Usuario nao tem permissao para alterar essa tarefa");
        }

        Utils.copyNonNullProperties(entity,task);
        var taskUptaded = iTaskRepository.save(task);
        return ResponseEntity.ok().body(taskUptaded);
    }
}
