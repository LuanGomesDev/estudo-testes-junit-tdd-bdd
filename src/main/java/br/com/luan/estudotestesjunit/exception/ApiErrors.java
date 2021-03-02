package br.com.luan.estudotestesjunit.exception;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.validation.BindingResult;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiErrors {

  @Getter
  private List<String> errors;

  public ApiErrors(BindingResult bindingResult) {
    this.errors = new ArrayList<>();

    bindingResult.getAllErrors().forEach(obj -> this.errors.add(obj.getDefaultMessage()));
  }

  public ApiErrors(BadRequestException exception) {

    this.errors = Arrays.asList(exception.getMessage());
  }

}