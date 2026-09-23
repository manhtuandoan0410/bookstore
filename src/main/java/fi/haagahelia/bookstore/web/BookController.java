package fi.haagahelia.bookstore.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import fi.haagahelia.bookstore.domain.Book;
import fi.haagahelia.bookstore.domain.BookRepository;
//import fi.haagahelia.bookstore.domain.Category;
import fi.haagahelia.bookstore.domain.CategoryRepository;

@Controller
public class BookController {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private CategoryRepository crepository;
    
    // Add new student
    // @RequestMapping(value = "/book/add")
    // public String addBook(Model model){
    //     model.addAttribute("book", new Book());
    //     model.addAttribute("category", crepository.findAll());
    //     return "addbook";
    // }

    @GetMapping("/book/add")
    public String addBook(Model model) {
        model.addAttribute("book", new Book());
        model.addAttribute("category", crepository.findAll());
        return "addbook";
    }

    @PostMapping("/book/add")
    public String saveBook(@ModelAttribute("book") Book book) {
        bookRepository.save(book);
        return "redirect:/booklist";
    }

    public BookController(BookRepository bookRepository, CategoryRepository crepository) {
        this.bookRepository = bookRepository;
        this.crepository = crepository;
    }
  

    @RequestMapping(value = {"/", "/booklist"})
    public String listBooks(Model model) {
        model.addAttribute("books", bookRepository.findAll());
        return "booklist";
    }

    @GetMapping("/book/delete/{id}")
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