package ru.lanit.model.security;

import lombok.Data;
import ru.lanit.model.entity.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "usersSecurity")
public class User extends BaseEntity {
    private String name;
    private String email;
    private String password;
    @Enumerated(value = EnumType.STRING)
    private Role role;

}
