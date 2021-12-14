package ru.lanit.security;

import lombok.Data;
import org.springframework.security.core.userdetails.UserDetails;
import ru.lanit.model.entity.User;

@Data
public class SecurityUser {

    public static UserDetails fromUser(User user) {
        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getName())
                .password(user.getPassword())
                .roles(user.getRole().name())
                .build();
    }
}
