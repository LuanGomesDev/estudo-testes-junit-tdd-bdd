package br.com.luan.estudotestesjunit.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import br.com.luan.estudotestesjunit.api.dto.BookDTO;
import br.com.luan.estudotestesjunit.entity.Book;
import br.com.luan.estudotestesjunit.exception.BadRequestException;
import br.com.luan.estudotestesjunit.repository.BookRepository;
import br.com.luan.estudotestesjunit.serice.BookService;
import br.com.luan.estudotestesjunit.serice.impl.BookServiceImpl;
import br.com.luan.estudotestesjunit.serice.mapper.BookMapper;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class BookServiceTest {

    BookService service;

    @MockBean
    BookRepository repository;

    @MockBean
    BookMapper mapper;

    @BeforeEach
    public void setUp() {
        this.service = new BookServiceImpl(repository, mapper);
    }

    @Test
    @DisplayName("Deve salvar um livro com sucesso.")
    public void saveBookTest() {

        Book book = createNewBook();
        Mockito.when(repository.existsByIsbn(Mockito.anyString())).thenReturn(Boolean.FALSE);
        Mockito.when(mapper.toDTO(repository.save(book))).thenReturn(
                BookDTO.builder().id(1l).author(book.getAuthor()).title(book.getTitle()).isbn(book.getIsbn()).build());

        BookDTO bookSave = service.save(book);

        assertThat(bookSave.getId()).isNotNull();
        assertThat(bookSave.getAuthor()).isEqualTo(book.getAuthor());
        assertThat(bookSave.getTitle()).isEqualTo(book.getTitle());
        assertThat(bookSave.getIsbn()).isEqualTo(book.getIsbn());
    }

    private Book createNewBook() {
        return Book.builder().author("Luan").title("Nord").isbn("123").build();
    }

    @Test
    @DisplayName("Deve lançar erro ao tentar cadastrar um livro com isbn ja vinculado a outro livro.")
    public void shouldNotSaveABookWithDuplicateISNB() {

        Book book = this.createNewBook();
        Mockito.when(repository.existsByIsbn(Mockito.anyString())).thenReturn(Boolean.TRUE);

        Throwable exception = Assertions.catchThrowable(() -> service.save(book));

        assertThat(exception).isInstanceOf(BadRequestException.class).hasMessage("ISBN already registered.");

        // valida para não ser chamado o metodo save de repository
        Mockito.verify(repository, Mockito.never()).save(book);
    }
}
