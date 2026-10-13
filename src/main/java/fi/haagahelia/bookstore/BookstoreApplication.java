package fi.haagahelia.bookstore;

// import org.jspecify.annotations.Nullable;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import fi.haagahelia.bookstore.domain.AppUserRepository;
import fi.haagahelia.bookstore.domain.Book;
import fi.haagahelia.bookstore.domain.BookRepository;
import fi.haagahelia.bookstore.domain.Category;
import fi.haagahelia.bookstore.domain.CategoryRepository;
import fi.haagahelia.bookstore.domain.AppUser;

@SpringBootApplication
public class BookstoreApplication {

//     private final AppUserRepository appUserRepository;

//     BookstoreApplication(AppUserRepository appUserRepository) {
//         this.appUserRepository = appUserRepository;
//     }

    public static void main(String[] args) {
        SpringApplication.run(BookstoreApplication.class, args);
    }

    @Bean
    public CommandLineRunner initializeDatabase(
                        BookRepository bookRepository,
                        CategoryRepository categoryRepository,
                        AppUserRepository appUserRepository,
                        PasswordEncoder passwordEncoder) {
        return args -> {
            Category politics = getOrCreateCategory("Politics", categoryRepository);
            Category fiction = getOrCreateCategory("Fiction", categoryRepository);
            Category horror = getOrCreateCategory("Horror", categoryRepository);

            addBookIfMissing(bookRepository, "The Great Gatsby", "Tuan Doan",
                    "9780743273565", 1925, 15.99, fiction);
            addBookIfMissing(bookRepository, "The Worst Gatsby", "Tuan Doan",
                    "9780743273562", 1926, 15.99, fiction);
            addBookIfMissing(bookRepository, "To Kill a Mockingbird", "Harper Lee",
                    "9780061120084", 1960, 12.99, fiction);
            addBookIfMissing(bookRepository, "1984", "George Orwell",
                    "9780451524935", 1949, 11.99, politics);
            addBookIfMissing(bookRepository, "Pride and Prejudice", "Jane Austen",
                    "9780141439518", 1813, 9.99, fiction);
            addBookIfMissing(bookRepository, "The Hobbit", "J.R.R. Tolkien",
                    "9780547928227", 1937, 14.99, fiction);
            addBookIfMissing(bookRepository, "Dune", "Frank Herbert",
                    "9780441172719", 1965, 16.99, fiction);
            addBookIfMissing(bookRepository, "The Shining", "Stephen King",
                    "9780307743657", 1977, 13.99, horror);
            addBookIfMissing(bookRepository, "The Handmaid's Tale", "Margaret Atwood",
                    "9780385490818", 1985, 14.99, politics);
            addBookIfMissing(bookRepository, "The Road", "Cormac McCarthy",
                    "9780307387899", 2006, 12.99, fiction);

            if (appUserRepository.findByUsername("admin").isEmpty()) {
                appUserRepository.save(new AppUser(
                        "admin",
                        passwordEncoder.encode("sample-admin-password"),
                        "ADMIN", 
                        "admin@example.com"));
            }
            if (appUserRepository.findByUsername("user_1").isEmpty()) {
                appUserRepository.save(new AppUser(
                        "user_1",
                        passwordEncoder.encode("sample-user-password"),
                        "USER", 
                        "user_1@example.com"));
            }
            addUserIfMissing(appUserRepository, "user_2", passwordEncoder.encode("sample-user-password"), "USER", "user_2@example.com");

        };
    }

    private Category getOrCreateCategory(String name, CategoryRepository categoryRepository) {
        return categoryRepository.findByName(name).stream()
                .findFirst()
                .orElseGet(() -> categoryRepository.save(new Category(name)));
    }

    private void addBookIfMissing(
            BookRepository bookRepository,
            String title,
            String author,
            String isbn,
            int year,
            double price,
            Category category) {
        if (!bookRepository.findByIsbn(isbn).isEmpty()) {
            return;
        }

        Book book = new Book();
        book.setTitle(title);
        book.setAuthor(author);
        book.setIsbn(isbn);
        book.setYear(year);
        book.setPrice(price);
        book.setCategory(category);
        bookRepository.save(book);
    }

    private void addUserIfMissing(
            AppUserRepository appUserRepository,
            String username,
            String password,
            String role,
            String email) {
        if (!appUserRepository.findByUsername(username).isEmpty()) {
            return;
        }

        AppUser user = new AppUser();
        user.setUsername(username);
        user.setPasswordHash(password);
        user.setRole(role);
        user.setEmail(email);
    
        appUserRepository.save(user);
    }
}