package br.com.lipcam.todolist.controller;

import br.com.lipcam.todolist.model.UserModel;
import br.com.lipcam.todolist.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    IUserRepository iUserRepository;

    @PostMapping
    public ResponseEntity create(@RequestBody UserModel entity) {
        UserModel userModel = iUserRepository.findByUserName(entity.getUserName());
        if(userModel != null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Usuário já existente.");

        return ResponseEntity.status(HttpStatus.CREATED).body(iUserRepository.save(entity));
    }
}
