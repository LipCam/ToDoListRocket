package br.com.lipcam.todolist.repository;

import br.com.lipcam.todolist.model.TaskModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ITaskRepository extends JpaRepository<TaskModel, UUID> {

}