package kiraly.books.services.impl;

import kiraly.books.domain.entities.AuthorEntity;
import kiraly.books.respositories.AuthorRepository;
import kiraly.books.services.AuthorService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;

    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public AuthorEntity save(AuthorEntity author) {
        return authorRepository.save(author);
    }

    @Override
    public List<AuthorEntity> findAll() {
        return StreamSupport.stream(authorRepository.findAll().spliterator(), false).toList();
    }

    @Override
    public Optional<AuthorEntity> findOne(long id) {
        return authorRepository.findById(id);
    }

    @Override
    public boolean exists(long id) {
        return authorRepository.existsById(id);
    }

    @Override
    public AuthorEntity partialUpdate(Long id, AuthorEntity authorAsEntity) {
        authorAsEntity.setId(id);
        return authorRepository.findById(id).map((existingAuthor) -> {
            Optional.ofNullable(authorAsEntity.getAge()).ifPresent(existingAuthor::setAge);
            Optional.ofNullable(authorAsEntity.getName()).ifPresent(existingAuthor::setName);
            return authorRepository.save(existingAuthor);
        }).orElseThrow(() -> new RuntimeException("Could not find author with id " + id));
    }

    @Override
    public void delete(Long id) {
        authorRepository.deleteById(id);
    }
}
