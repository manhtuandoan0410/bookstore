package fi.haagahelia.bookstore;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import fi.haagahelia.bookstore.domain.Book;
import fi.haagahelia.bookstore.domain.BookRepository;

@SpringBootApplication
public class BookstoreApplication {

    public static void main(String[] args) {
        SpringApplication.run(BookstoreApplication.class, args);
    }

    @Bean
    public CommandLineRunner initializeDatabase(BookRepository bookRepository) {
        return args -> {
            Book book1 = new Book();
            book1.setTitle("The Great Gatsby");
            book1.setAuthor("Tuan Doan");
            book1.setIsbn("9780743273565");
            book1.setYear(1925);
            book1.setPrice(15.99);

            bookRepository.save(book1);
        };
    }
}