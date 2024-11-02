package kiraly.books.services;

import kiraly.books.domain.entities.AuthorEntity;

import java.util.List;
import java.util.Optional;

public interface AuthorService {
    AuthorEntity save(AuthorEntity author);

    List<AuthorEntity> findAll();

    Optional<AuthorEntity> findOne(long id);

    boolean exists(long id);

    AuthorEntity partialUpdate(Long id, AuthorEntity authorAsEntity);

    void delete(Long id);
}
