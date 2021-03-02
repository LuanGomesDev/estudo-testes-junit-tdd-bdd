package br.com.luan.estudotestesjunit.api.dto;

import javax.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookDTO {

    private Long id;

    @NotEmpty(message = "Title is not empty or null")
    private String title;

    @NotEmpty(message = "Author is not empty or null")
    private String author;

    @NotEmpty(message = "ISBN is not empty or null")
    private String isbn;
}
