package ru.ananchenko.testtaskapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.ananchenko.testtaskapp.model.User;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {

    @Query("SELECT u FROM User u WHERE UPPER(u.email) = UPPER(:email)")
    User findByEmail(@Param("email") String email);

    @Query("SELECT u FROM User u WHERE u.created = (SELECT max(um.created) FROM User um) ORDER BY u.id DESC")
    List<User> findByCreatedIsMax();
}
