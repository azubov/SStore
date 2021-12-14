package ru.lanit.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.lanit.model.entity.User;
import ru.lanit.repository.UserRepository;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository repository;

    @Autowired
    public UserServiceImpl(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public void save(User user) {
        String encryptedPassword = new BCryptPasswordEncoder(12).encode(user.getPassword());
        user.setPassword(encryptedPassword);
        repository.save(user);
    }

    @Override
    public List<User> findAll() {
        return repository.findAll();
    }

    @Override
    public User findByName(String name) {
        return repository.findByName(name).orElseThrow(() -> new UsernameNotFoundException("User doesn't exist"));
    }

    @Override
    public User getCurrentUser() {
        String username;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            username = ((UserDetails)principal).getUsername();
        } else {
            username = principal.toString();
        }

        return findByName(username);
    }

    @Override
    public boolean existsUserByName(String name) {
        return repository.existsUserByName(name);
    }
}
