package fi.haagahelia.bookstore.domain;

import java.util.List;
import org.springframework.data.repository.CrudRepository;

public interface AppUserRepository extends CrudRepository<AppUser, Long> {
    List<AppUser> findbyname (String username);
}