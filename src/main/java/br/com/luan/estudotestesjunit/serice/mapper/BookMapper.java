package br.com.luan.estudotestesjunit.serice.mapper;

import br.com.luan.estudotestesjunit.api.dto.BookDTO;
import br.com.luan.estudotestesjunit.entity.Book;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BookMapper {

    Book toEntity(BookDTO dto);

    BookDTO toDTO(Book entity);
}