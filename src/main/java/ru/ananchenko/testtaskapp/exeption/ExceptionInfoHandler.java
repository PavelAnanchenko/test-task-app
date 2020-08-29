package ru.ananchenko.testtaskapp.exeption;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import ru.ananchenko.testtaskapp.model.Error;
import ru.ananchenko.testtaskapp.service.ErrorService;
import ru.ananchenko.testtaskapp.util.ValidationUtil;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLDataException;

import static ru.ananchenko.testtaskapp.exeption.ErrorType.*;

@RestControllerAdvice(annotations = RestController.class)
@Order(Ordered.HIGHEST_PRECEDENCE + 5)
@Slf4j
@AllArgsConstructor
public class ExceptionInfoHandler {

    private final ErrorService errorService;

    @ResponseStatus(value = HttpStatus.BAD_REQUEST)  // 400
    @ExceptionHandler({MethodArgumentTypeMismatchException.class, HttpMessageNotReadableException.class, MethodArgumentNotValidException.class})
    public ErrorInfo badRequest(HttpServletRequest req, Exception e) {
        return logAndGetErrorInfo(req, e, BAD_REQUEST);
    }

    @ResponseStatus(value = HttpStatus.FORBIDDEN)  // 403
    @ExceptionHandler({DataIntegrityViolationException.class, ConstraintViolationException.class, SQLDataException.class, DuplicateEmailException.class})
    public ErrorInfo duplicateEmail(HttpServletRequest req, DuplicateEmailException e) {
        return logAndGetErrorInfo(req, e, DUPLICATE_ERROR);
    }

    @ResponseStatus(value = HttpStatus.NOT_FOUND)   //404
    @ExceptionHandler(NotFoundException.class)
    public ErrorInfo handleError(HttpServletRequest req, NotFoundException e) {
        return logAndGetErrorInfo(req, e, DATA_NOT_FOUND);
    }

    private ErrorInfo logAndGetErrorInfo(HttpServletRequest req, Exception e, ErrorType errorType) {
        Throwable rootCause = ValidationUtil.getRootCause(e);
        log.warn("{} at request  {}: {}", errorType, req.getRequestURL(), rootCause.toString());
        String message = e.getMessage();
        errorService.save(new Error(message));
        return new ErrorInfo(message);
    }
}