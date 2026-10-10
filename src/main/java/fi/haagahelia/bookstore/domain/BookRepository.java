package fi.haagahelia.bookstore.domain;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface BookRepository extends CrudRepository<Book, Long> {
    List<Book> findByIsbn(String isbn);
    List<Book> findByTitle(String title);
    List<Book> findByAuthor(String author);

    // Enabling ignoring case
    List<Book> findByTitleIgnoreCase(String title);
    
    // Enabling ORDER BY for a query
    List<Book> findByTitleOrderByTitleAsc(String title);

}