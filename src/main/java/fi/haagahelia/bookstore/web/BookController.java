package fi.haagahelia.bookstore.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import fi.haagahelia.bookstore.domain.Book;
import fi.haagahelia.bookstore.domain.BookRepository;
import fi.haagahelia.bookstore.domain.Category;
//import fi.haagahelia.bookstore.domain.Category;
import fi.haagahelia.bookstore.domain.CategoryRepository;

@Controller
public class BookController {

    private final BookRepository bookRepository;

    private final CategoryRepository crepository;
    
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

    public BookController(BookRepository bookRepository, CategoryRepository crepository) {
        this.bookRepository = bookRepository;
        this.crepository = crepository;
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
}