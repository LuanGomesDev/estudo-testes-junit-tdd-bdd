package br.com.luan.estudotestesjunit.api.resource;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.luan.estudotestesjunit.api.dto.BookDTO;
import br.com.luan.estudotestesjunit.entity.Book;
import br.com.luan.estudotestesjunit.exception.ApiErrors;
import br.com.luan.estudotestesjunit.serice.BookService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class BookController {

  private final BookService service;

  @PostMapping
  public ResponseEntity<BookDTO> criar(@RequestBody @Valid BookDTO book) {
    return ResponseEntity.status(HttpStatus.CREATED).body(this.service.save(Book.builder().build()));
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseStatus(code = HttpStatus.BAD_REQUEST)
  public ApiErrors heandlerValidationExceptions(MethodArgumentNotValidException exception){
    return new ApiErrors(exception);
  }
}
