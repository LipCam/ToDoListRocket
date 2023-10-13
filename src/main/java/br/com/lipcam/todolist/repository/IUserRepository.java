package br.com.lipcam.todolist.repository;

import br.com.lipcam.todolist.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface IUserRepository extends JpaRepository<UserModel, UUID> {
    UserModel findByUserName(String userName);
}
