package ru.lanit.service.security;

import ru.lanit.model.security.User;

public interface UserService {

    public void save(User user);
    public boolean verify(User user);
}
