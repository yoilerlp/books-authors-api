package kiraly.books.cotrollers;

import kiraly.books.domain.dto.AuthorDto;
import kiraly.books.domain.entities.AuthorEntity;
import kiraly.books.mappers.Mappers;
import kiraly.books.mappers.impl.AuthorMapper;
import kiraly.books.services.AuthorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/authors")
public class AuthorController {

    private final AuthorService authorService;
    private final Mappers<AuthorEntity, AuthorDto> authorMapper;

    public AuthorController(AuthorService authorService, AuthorMapper authorMapper) {
        this.authorService = authorService;
        this.authorMapper = authorMapper;
    }

    @PostMapping(path = "")
    public AuthorDto createAuthor(@RequestBody AuthorDto author) {
        AuthorEntity newAuthor = authorMapper.mapFrom(author);
        var createdAuthor = authorService.save(newAuthor);
        return authorMapper.mapTo(createdAuthor);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<AuthorDto> getOneAuthor(@PathVariable Long id) {
        var authorResult = authorService.findOne(id);
        return authorResult.map((authorEntity) -> {
            var authorDto = authorMapper.mapTo(authorEntity);
            return new ResponseEntity<>(authorDto, HttpStatus.OK);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping(path = "")
    public List<AuthorDto> getAllAuthors() {
        return this.authorService.findAll().stream().map(authorMapper::mapTo).toList();
    }

    // update author
    @PutMapping(path = "/{id}")
    public ResponseEntity<AuthorDto> updateAuthor(
            @PathVariable("id") Long id,
            @RequestBody AuthorDto author) {

        var authorExist = authorService.exists(id);

        if (!authorExist) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        author.setId(id);
        var authorEntity = authorMapper.mapFrom(author);
        var updatedAuthor = authorService.save(authorEntity);
        var updatedAuthorDto = authorMapper.mapTo(updatedAuthor);
        return new ResponseEntity<>(updatedAuthorDto, HttpStatus.OK);
    }

    @PatchMapping(path = "/{id}")
    public ResponseEntity<AuthorDto> partialUpdateAuthor(
            @PathVariable("id") Long id,
            @RequestBody AuthorDto author
    ) {
        var authorExist = authorService.exists(id);
        if (!authorExist) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        var authorAsEntity = authorMapper.mapFrom(author);
        var updatedAuthor = authorService.partialUpdate(id, authorAsEntity);
        var updatedAuthorDto = authorMapper.mapTo(updatedAuthor);
        return new ResponseEntity<>(updatedAuthorDto, HttpStatus.OK);

    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Object> deleteAuthor(@PathVariable Long id) {
        authorService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
