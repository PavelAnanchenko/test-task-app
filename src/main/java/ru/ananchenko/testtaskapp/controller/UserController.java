package ru.ananchenko.testtaskapp.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.ananchenko.testtaskapp.dto.EmailDto;
import ru.ananchenko.testtaskapp.dto.IdDto;
import ru.ananchenko.testtaskapp.model.User;
import ru.ananchenko.testtaskapp.service.UserService;

import javax.validation.Valid;
import java.util.List;

@RestController
@Validated
@AllArgsConstructor
@RequestMapping(value = "/profiles", produces = MediaType.APPLICATION_JSON_VALUE, method = {RequestMethod.GET, RequestMethod.POST})
public class UserController {

    private final UserService userService;

    @PostMapping(value = "/set", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<IdDto> create(@Valid @RequestBody User user) {
        User created = userService.save(user);
        return ResponseEntity.ok(new IdDto(created.getId()));
    }

    @GetMapping(value = "/last")
    public ResponseEntity<User> getLast() {
        return ResponseEntity.ok(userService.findByCreatedIsMax());
    }

    @GetMapping
    public ResponseEntity<List<User>> getAll() {
        return ResponseEntity.ok(userService.getAll());
    }

    @GetMapping(value = "/{id}")
    @ResponseBody
    public ResponseEntity<User> get(@PathVariable int id) {
        User user = userService.findById(id);
        return ResponseEntity.ok(user);
    }

    @GetMapping(value = "/get", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> getByEmail(@Valid @RequestBody EmailDto emailDto) {
        User user = userService.findByEmail(emailDto.getEmail());
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }
}
