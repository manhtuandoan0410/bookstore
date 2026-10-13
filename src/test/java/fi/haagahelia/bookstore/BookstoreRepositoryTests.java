package fi.haagahelia.bookstore;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
//import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.boot.test.context.TestConfiguration;

import fi.haagahelia.bookstore.domain.AppUser;
import fi.haagahelia.bookstore.domain.AppUserRepository;
import fi.haagahelia.bookstore.domain.Book;
import fi.haagahelia.bookstore.domain.BookRepository;
import fi.haagahelia.bookstore.domain.Category;
import fi.haagahelia.bookstore.domain.CategoryRepository;

 

@Import(BookstoreRepositoryTests.RepositoryTestConfig.class)
@DataJpaTest
public class BookstoreRepositoryTests {
    
    @Autowired
    private BookRepository bRepository;

    @Autowired
    private CategoryRepository cRepository;

    @Autowired
    private AppUserRepository auRepository;

    @Test 
    public void findBookByAuthor() {
        List<Book> books = bRepository.findByAuthor("Tuan Doan");

        assertThat(books).hasSize(2);
    }

    @Test
    public void addNewBook() {
        Category category = new Category("Comedy");
        cRepository.save(category);

        Book book = new Book();
        book.setTitle("The New Gatsby");
        book.setAuthor("Tuan Doan");
        book.setIsbn("97807431235");
        book.setYear(1993);
        book.setPrice(14.99);
        book.setCategory(category);

        bRepository.save(book); 
        assertThat(book.getId()).isNotNull();
    }

    @Test
    public void deleteBook() {
        List<Book> books = bRepository.findByAuthor("Tuan Doan");
        assertThat(books).hasSize(2);
        Book book = books.get(0);
        bRepository.delete(book);
        List<Book> newBooks = bRepository.findByAuthor("Tuan Doan");
        assertThat(newBooks).hasSize(1);
    }

    @Test
    public void addNewUser() {
        
        AppUser userX = new AppUser("user_x", "sample-user-password", "USER", "user_x@example.com");

        auRepository.save(userX); 
        assertThat(userX.getId()).isNotNull();
    }

    @Test
    public void deleteUser() {
        AppUser user = auRepository.findByUsername("user_1")
                        .orElseThrow(() -> new AssertionError("User user_1 was not found"));
        
        auRepository.delete(user);

        assertThat(auRepository.findByUsername("user_1")).isEmpty();
    }
    
    @TestConfiguration
    static class RepositoryTestConfig {
        @Bean
        PasswordEncoder passwordEncoder() {
            return new BCryptPasswordEncoder();
        }
    }

}
