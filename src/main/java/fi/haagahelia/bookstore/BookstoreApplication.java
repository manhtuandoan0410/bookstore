package fi.haagahelia.bookstore;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import fi.haagahelia.bookstore.domain.Book;
import fi.haagahelia.bookstore.domain.BookRepository;

import fi.haagahelia.bookstore.domain.Category;
import fi.haagahelia.bookstore.domain.CategoryRepository;

@SpringBootApplication
public class BookstoreApplication {

    public static void main(String[] args) {
        SpringApplication.run(BookstoreApplication.class, args);
    }

    @Bean
    public CommandLineRunner initializeDatabase(
                    BookRepository bookRepository,
                    CategoryRepository categoryRepository) {
        return args -> {

            Category politics = new Category("Politics");
            Category fiction = new Category("Fiction");
            Category horror = new Category("Horror");

            categoryRepository.save(politics);
            categoryRepository.save(fiction);
            categoryRepository.save(horror);

            Book book1 = new Book();
            book1.setTitle("The Great Gatsby");
            book1.setAuthor("Tuan Doan");
            book1.setIsbn("9780743273565");
            book1.setYear(1925);
            book1.setCategory(fiction);
            book1.setPrice(15.99);

            fiction.addBook(book1);
            categoryRepository.save(fiction);

            Book book2 = new Book();
            book1.setTitle("The Worst Gatsby");
            book1.setAuthor("Tuan Doan");
            book1.setIsbn("9780743273562");
            book1.setYear(1926);
            book1.setCategory(fiction);
            book1.setPrice(15.99); 

            fiction.addBook(book2);
            categoryRepository.save(fiction);
        };
    }
}