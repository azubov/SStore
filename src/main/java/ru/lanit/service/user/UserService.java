package ru.lanit.service.user;

import ru.lanit.model.entity.User;

import java.util.List;

public interface UserService {
    void save(User user);
    List<User> findAll();
    User findByName(String name);
    User getCurrentUser();
    boolean existsUserByName(String name);
}
