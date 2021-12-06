package ru.lanit.service.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.lanit.model.security.User;
import ru.lanit.repository.security.UserRepository;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository repository;

    @Autowired
    public UserServiceImpl(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public void save(User user) {
        String encryptedPassword = encrypt(user.getPassword());
        user.setPassword(encryptedPassword);
        repository.save(user);
    }

    @Override
    public boolean verify(User user) {

        return false;
    }

    private String encrypt(String password) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.encode(password);
    }
}
