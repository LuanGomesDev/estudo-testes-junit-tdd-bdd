package br.com.luan.estudotestesjunit.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.luan.estudotestesjunit.entity.Book;

@Repository
public interface BookRepository extends JpaRepository<Book,Long> {
}
