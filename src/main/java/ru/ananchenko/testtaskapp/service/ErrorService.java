package ru.ananchenko.testtaskapp.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ru.ananchenko.testtaskapp.model.Error;
import ru.ananchenko.testtaskapp.repository.ErrorRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class ErrorService {

    private final ErrorRepository repository;

    public void save(Error error) {
        Assert.notNull(error, "error must not be null");
        repository.save(error);
    }

    public Error getLast() {
        List<Error> errors = repository.findByCreatedIsMax();
        return errors.size() == 0 ? null : errors.get(0);
    }
}
