package br.com.lipcam.todolist.model;


import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@Table(name = "user_tb")
public class UserModel {

    @Id
    @GeneratedValue(generator = "UUID")
    private UUID Id;

    @Column(unique = true)
    private String userName;
    private String name;
    private String password;

    @CreationTimestamp
    private LocalDateTime createAt;
}
