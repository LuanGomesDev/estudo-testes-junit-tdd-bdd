package br.com.luan.estudotestesjunit.api.resource;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import br.com.luan.estudotestesjunit.api.dto.BookDTO;
import br.com.luan.estudotestesjunit.entity.Book;
import br.com.luan.estudotestesjunit.exception.BadRequestException;
import br.com.luan.estudotestesjunit.serice.BookService;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@WebMvcTest
@AutoConfigureMockMvc
public class BookControllerTest {

  static String BOOK_API = "/api/books";

  @Autowired
  MockMvc mvc;

  @MockBean
  BookService service;

  @Test
  @DisplayName("Deve criar um livro com sucesso.")
  public void createBookTest() throws Exception {

    BookDTO book = this.newBookCreating();

    BookDTO saveBook = BookDTO.builder().id(1L).author("Luan Gomes").title("As Aventuras de Lis Maria").isbn("123456")
        .build();
    BDDMockito.given(service.save(Mockito.any(Book.class))).willReturn(saveBook);

    String json = new ObjectMapper().writeValueAsString(book);

    MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post(BOOK_API)
        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(json);

    mvc.perform(request).andExpect(status().isCreated()).andExpect(jsonPath("id").isNotEmpty())
        .andExpect(jsonPath("title").value(book.getTitle())).andExpect(jsonPath("author").value(book.getAuthor()))
        .andExpect(jsonPath("isbn").value(book.getIsbn()));
  }

  @Test
  @DisplayName("Deve lançar erro quando não houver dados sucificentes para criação do livro.")
  public void createInvalidBookTest() throws Exception {

    String json = new ObjectMapper().writeValueAsString(new BookDTO());

    MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post(BOOK_API)
        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(json);

    mvc.perform(request).andExpect(status().isBadRequest()).andExpect(jsonPath("errors", hasSize(3)));
  }

  @Test
  @DisplayName("Deve lançar erro ao tentar cadastrar um livro com isbn ja vinculado a outro livro.")
  public void createBookWithDuplicateISBN() throws Exception {

    String json = new ObjectMapper().writeValueAsString(this.newBookCreating());

    String messageError = "ISBN already registered.";
    BDDMockito.given(service.save(Mockito.any(Book.class)))
        .willThrow(new BadRequestException(messageError));

    MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post(BOOK_API)
        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(json);

    mvc.perform(request).andExpect(status().isBadRequest()).andExpect(jsonPath("errors", hasSize(1)))
        .andExpect(jsonPath("errors[0]").value(messageError));
  }

  private BookDTO newBookCreating() {
    return BookDTO.builder().author("Luan Gomes").title("As Aventuras de Lis Maria").isbn("123456").build();
  }

}
