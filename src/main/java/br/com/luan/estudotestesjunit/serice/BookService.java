package br.com.luan.estudotestesjunit.serice;

import br.com.luan.estudotestesjunit.api.dto.BookDTO;
import br.com.luan.estudotestesjunit.entity.Book;

public interface BookService {

  BookDTO save(Book entity);
}