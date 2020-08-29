package ru.ananchenko.testtaskapp.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "errors")
public class Error extends AbstractBaseEntity {

    @NotNull
    private String message;

    private LocalDateTime created = LocalDateTime.now();

    public Error(String message) {
        this.message = message;
    }
}
