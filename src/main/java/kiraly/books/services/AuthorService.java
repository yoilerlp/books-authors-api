package kiraly.books.services;

import kiraly.books.domain.entities.AuthorEntity;

import java.util.List;
import java.util.Optional;

public interface AuthorService {
    public AuthorEntity save(AuthorEntity author);
    public List<AuthorEntity> findAll();

    public Optional<AuthorEntity> findOne(long id);

    public boolean exists(long id);

    public AuthorEntity partialUpdate(Long id, AuthorEntity authorAsEntity);

    void delete(Long id);
}
