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
		//this is lambda expressiong - method without name
		// executes inline code what mentioned here
		return (args) -> {
			Book book1 = new Book();
			book1.setTitle("The Great Gatsby");
			book1.setAuthor("Tuan Doan");
			bookRepository.save(book1);
		};
	}

}
