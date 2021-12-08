package ru.lanit.security;

import lombok.Data;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import ru.lanit.model.entity.User;

@Data
public class SecurityUser {

    public static UserDetails fromUser(User user) {
        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getName())
                .password(new BCryptPasswordEncoder(12).encode(user.getPassword()))
                .roles(user.getRole().name())
                .build();

    }
}
