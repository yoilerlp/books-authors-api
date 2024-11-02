package kiraly.books.services;

import kiraly.books.domain.entities.BookEntity;

import java.util.List;
import java.util.Optional;

public interface BookService {
     BookEntity createUpdateBook(String isbn, BookEntity bookEntity);
     List<BookEntity> findAll();
     Optional<BookEntity> findOne(String isbn );
     boolean exists(String isbn);
     BookEntity partialUpdate(String isbn, BookEntity bookEntity);
     void delete(String isbn);
}
