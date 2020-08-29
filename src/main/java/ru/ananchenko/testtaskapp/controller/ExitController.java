package ru.ananchenko.testtaskapp.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
public class ExitController {

    @GetMapping
    @RequestMapping("/exit")
    public void exit(HttpServletResponse response, HttpServletRequest request) throws IOException, ServletException {
        request.getRequestDispatcher("/exit-success").forward(request, response);
    }

    @GetMapping
    @RequestMapping("/exit-success")
    public ResponseEntity<String> exitSuccess() {
        return ResponseEntity.status(499).body("Приложение закрыто");
    }
}
