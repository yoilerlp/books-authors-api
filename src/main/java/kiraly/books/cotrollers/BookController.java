package kiraly.books.cotrollers;

import kiraly.books.domain.dto.BookDto;
import kiraly.books.domain.entities.BookEntity;
import kiraly.books.mappers.Mappers;
import kiraly.books.mappers.impl.BookMapper;
import kiraly.books.services.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/books")
public class BookController {


    private final Mappers<BookEntity, BookDto> bookMapper;
    private final BookService bookService;

    public BookController(BookMapper bookMapper, BookService bookService) {
        this.bookMapper = bookMapper;
        this.bookService = bookService;
    }

    @PutMapping(path = "/{isbn}")
    public ResponseEntity<BookDto> addBook(@PathVariable("isbn") String bookIsbn, @RequestBody BookDto book) {
        var bookExist = bookService.exists(bookIsbn);
        var bookToSave = bookMapper.mapFrom(book);
        var bookSaved = bookService.createUpdateBook(bookIsbn, bookToSave);
        var bookSavedDto = bookMapper.mapTo(bookSaved);

        if (bookExist) {
            return new ResponseEntity<>(bookSavedDto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(bookSavedDto, HttpStatus.CREATED);

        }
    }

    @PatchMapping(path = "/{isbn}")
    public ResponseEntity<BookDto> updateBook(
            @PathVariable("isbn") String bookIsbn,
            @RequestBody BookDto book
    ) {
        if (!bookService.exists(bookIsbn)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        var bookEntity = bookMapper.mapFrom(book);
        var bookUpdated = bookService.partialUpdate(bookIsbn, bookEntity);
        return new ResponseEntity<>(
                bookMapper.mapTo(bookUpdated),
                HttpStatus.OK
        );

    }

    @GetMapping(path = "/{isbn}")
    public ResponseEntity<BookDto> getOneBook(@PathVariable("isbn") String isbn) {
        var authorResult = bookService.findOne(isbn);
        return authorResult.map((bookEntity) -> {
            var bookDto = bookMapper.mapTo(bookEntity);
            return new ResponseEntity<>(bookDto, HttpStatus.OK);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping(path = "")
    public List<BookDto> getAllBooks() {
        return this.bookService.findAll().stream().map(bookMapper::mapTo).toList();
    }

    @DeleteMapping(path = "/{isbn}")
    public ResponseEntity<Object> deleteBook(@PathVariable("isbn") String isbn) {
        bookService.delete(isbn);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
