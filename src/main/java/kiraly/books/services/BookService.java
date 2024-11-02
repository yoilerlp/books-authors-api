package kiraly.books.services;

import kiraly.books.domain.entities.BookEntity;

import java.util.List;
import java.util.Optional;

public interface BookService {
    public BookEntity createUpdateBook(String isbn, BookEntity bookEntity);
    public List<BookEntity> findAll();
    public Optional<BookEntity> findOne(String isbn );
    public boolean exists(String isbn);
    public BookEntity partialUpdate(String isbn, BookEntity bookEntity);
    public void delete(String isbn);
}
