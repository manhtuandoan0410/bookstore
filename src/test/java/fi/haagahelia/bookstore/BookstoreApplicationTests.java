package fi.haagahelia.bookstore;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import fi.haagahelia.bookstore.domain.BookRepository;
import fi.haagahelia.bookstore.web.BookController;

@SpringBootTest
class BookstoreApplicationTests {

	private final BookController controller;
	private final BookRepository bookRepository;

	 @Autowired
    public BookstoreApplicationTests(BookController controller, BookRepository bookRepository) {
        this.controller = controller;
		this.bookRepository = bookRepository;
	}
	
	@Test
	void contextLoads() {
	}

	@Test
    public void controllerLoads() throws Exception {
        assertThat(controller).isNotNull();
    }	

	@Test
	void initializesTenBooksIncludingTheEightAdditionalBooks() {
		assertThat(bookRepository.count()).isEqualTo(10);
		assertThat(bookRepository.findByTitle("To Kill a Mockingbird")).hasSize(1);
		assertThat(bookRepository.findByTitle("1984")).hasSize(1);
		assertThat(bookRepository.findByTitle("Pride and Prejudice")).hasSize(1);
		assertThat(bookRepository.findByTitle("The Hobbit")).hasSize(1);
		assertThat(bookRepository.findByTitle("Dune")).hasSize(1);
		assertThat(bookRepository.findByTitle("The Shining")).hasSize(1);
		assertThat(bookRepository.findByTitle("The Handmaid's Tale")).hasSize(1);
		assertThat(bookRepository.findByTitle("The Road")).hasSize(1);
	}
}
