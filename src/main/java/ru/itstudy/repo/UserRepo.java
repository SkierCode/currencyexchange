package ru.itstudy.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itstudy.domain.User;

public interface UserRepo extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
