package br.com.luan.estudotestesjunit.repository;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import br.com.luan.estudotestesjunit.entity.Book;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@DataJpaTest
public class BookRepositoryTest {

  @Autowired
  TestEntityManager entityManager;

  @Autowired
  BookRepository repository;

  @Test
  @DisplayName("Deve retornar verdadeiro quando existir um livro com o mesmo isbn ja cadastrado.")
  public void returnTrueWhenIsnbExists(){
    String isbn = "123";

    this.entityManager.persist(Book.builder().author("Vader").title("Cronique's").isbn(isbn).build());

    boolean existsByIsbn = this.repository.existsByIsbn(isbn);

    assertThat(existsByIsbn).isTrue();
  }

  @Test
  @DisplayName("Deve retornar false quando não existir um livro com o mesmo isbn ja cadastrado.")
  public void returnFalseWhenIsnbExists(){
    String isbn = "123";

    boolean existsByIsbn = this.repository.existsByIsbn(isbn);

    assertThat(existsByIsbn).isFalse();
  }
  
}