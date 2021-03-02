package br.com.luan.estudotestesjunit.serice.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.luan.estudotestesjunit.api.dto.BookDTO;
import br.com.luan.estudotestesjunit.entity.Book;
import br.com.luan.estudotestesjunit.repository.BookRepository;
import br.com.luan.estudotestesjunit.serice.BookService;
import br.com.luan.estudotestesjunit.serice.mapper.BookMapper;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class BookServiceImpl implements BookService {

  private final BookRepository repository;
  private final BookMapper mapper;

  @Override
  public BookDTO save(Book entity) {
    return this.mapper.toDTO(this.repository.save(entity));
  }
    
}