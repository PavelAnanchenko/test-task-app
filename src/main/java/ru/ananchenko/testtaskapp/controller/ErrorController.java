package ru.ananchenko.testtaskapp.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ru.ananchenko.testtaskapp.model.Error;
import ru.ananchenko.testtaskapp.service.ErrorService;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/error/last", produces = MediaType.APPLICATION_JSON_VALUE, method = {RequestMethod.GET, RequestMethod.POST})
public class ErrorController {

    private final ErrorService errorService;

    @GetMapping
    public ResponseEntity<Error> getLast() {
        return ResponseEntity.ok(errorService.getLast());
    }
}
