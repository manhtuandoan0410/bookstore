package fi.haagahelia.bookstore.web;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.security.crypto.password.PasswordEncoder;

import fi.haagahelia.bookstore.domain.AppUserRepository;
import fi.haagahelia.bookstore.domain.Book;
import fi.haagahelia.bookstore.domain.BookRepository;
import fi.haagahelia.bookstore.domain.Category;
import fi.haagahelia.bookstore.domain.CategoryRepository;
import fi.haagahelia.bookstore.domain.AppUser;


@Controller
public class BookController {

    private final BookRepository bookRepository;
    private final CategoryRepository crepository;
    private final AppUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;    
    
    // Add new student
    // @RequestMapping(value = "/book/add")
    // public String addBook(Model model){
    //     model.addAttribute("book", new Book());
    //     model.addAttribute("category", crepository.findAll());
    //     return "addbook";
    // }

    @GetMapping("/book/addbook")
    public String addBook(Model model) {
        model.addAttribute("book", new Book());
        model.addAttribute("category", crepository.findAll());
        return "addbook";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/book/addbook")
    public String saveBook(@ModelAttribute("book") Book book,
                        @RequestParam Long categoryId) {
        // Set the category for the book
        Category category = crepository.findById(categoryId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid category id: " + categoryId));
        book.setCategory(category);

        category.getBooks().add(book); // Add the book to the category's book list

        bookRepository.save(book);
        return "redirect:/booklist";
    }

    public BookController(
                BookRepository bookRepository, 
                CategoryRepository crepository,
                AppUserRepository userRepository,
                PasswordEncoder passwordEncoder) {
        this.bookRepository = bookRepository;
        this.crepository = crepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }
  

    @RequestMapping(value = {"/", "/booklist"})
    public String listBooks(Model model, @AuthenticationPrincipal UserDetails user) {
        model.addAttribute("books", bookRepository.findAll());
        model.addAttribute("isAdmin", user != null && user.getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals("ROLE_ADMIN")));

        if (user != null) {
            model.addAttribute("username", user.getUsername());
            String role = user.getAuthorities().stream()
                        .map(authority -> authority.getAuthority().replaceFirst("^ROLE_", ""))
                        .findFirst()
                        .orElse("USER");
            model.addAttribute("role", role);
        }
        return "booklist";
    }

    @PostMapping("/book/delete/{id}")
    public String deleteBook(@PathVariable("id") Long id) {
        bookRepository.deleteById(id);
        return "redirect:/booklist";
    }

    // @RequestMapping("book/edit/{id}")
    // public String showModStu(@PathVariable("id") Long id, Model model){
    //     Book book = bookRepository.findById(id)
    //                 .orElseThrow(() -> new IllegalArgumentException("Invalid book id: " + id));
        
    //     model.addAttribute("book", book);
    //     model.addAttribute("category", crepository.findAll());
    //     return "editbook";
    // }

    @GetMapping("/book/edit/{id}")
    public String editBook(@PathVariable("id") Long id, Model model) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid book id: " + id));

        model.addAttribute("book", book);
        model.addAttribute("category", crepository.findAll());
        return "editbook";
    }

    @PostMapping("/book/edit/{id}")
    public String saveEditedBook(
            @PathVariable("id") Long id,
            @ModelAttribute("book") Book book) {

        book.setId(id);
        bookRepository.save(book);
        //crepository.saveAll(book);
        return "redirect:/booklist";
    }

    @GetMapping("/signup")
    public String signup() {
        return "signup";
    }

    @PostMapping("/signup")
    public String signup(
        @RequestParam String username, 
        @RequestParam String password,
        @RequestParam String email) {
        
            String cleanUsername = username.trim();

            if (cleanUsername.isEmpty() || password.isBlank() || email.isBlank() || userRepository.findByUsername(cleanUsername).isPresent()) {
                return "redirect:/signup?error";
            }
            
            AppUser user = new AppUser(
                cleanUsername,
                passwordEncoder.encode(password),
                "USER",
                email.trim()
            );

        userRepository.save(user);
        return "redirect:/login?registered";
    }
    
}