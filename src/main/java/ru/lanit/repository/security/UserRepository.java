package ru.lanit.repository.security;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.lanit.model.security.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
