package ru.ananchenko.testtaskapp.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ru.ananchenko.testtaskapp.exeption.DuplicateEmailException;
import ru.ananchenko.testtaskapp.exeption.NotFoundException;
import ru.ananchenko.testtaskapp.model.User;
import ru.ananchenko.testtaskapp.repository.UserRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository repository;

    public User findById(int id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundException("User with id: " + id + " not found"));
    }

    public User findByEmail(String email) {
        Assert.notNull(email, "email must not be null");
        User user = repository.findByEmail(email);
        if (user == null) {
            throw new NotFoundException("User with " + email + " email not found");
        }
        return user;
    }

    public User save(User user) {
        Assert.notNull(user, "user must not be null");
        Assert.notNull(user.getEmail(), "user email must not be null");
        if (repository.findByEmail(user.getEmail()) != null) {
            throw new DuplicateEmailException("User with email" + user.getEmail() + " already exist");
        }
        user.setName(user.getName().toLowerCase());
        user.setEmail(user.getEmail().toLowerCase());
        return repository.save(user);
    }

    public User findByCreatedIsMax() {
        List<User> users = repository.findByCreatedIsMax();
        return users.size() == 0 ? null : users.get(0);
    }

    public List<User> getAll() {
        return repository.findAll();
    }
}
