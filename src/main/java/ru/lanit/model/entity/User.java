package ru.lanit.model.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "user_table")
public class User extends BaseEntity {
    private String name;
    private String email;
    private String password;
    @Enumerated(value = EnumType.STRING)
    private Role role;
}
