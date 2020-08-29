package ru.ananchenko.testtaskapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.ananchenko.testtaskapp.model.Error;

import java.util.List;

public interface ErrorRepository extends JpaRepository<Error, Integer> {

    @Query("SELECT e FROM Error e WHERE e.created = (SELECT max(em.created) FROM Error em )")
    List<Error> findByCreatedIsMax();
}
