package net.ivan.kavaliou.moneyman.repository;

import net.ivan.kavaliou.moneyman.model.persistence.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<User, Integer> {
    public Optional<User> findByEmail(String email);
    User findOneByEmail(String email);
}
