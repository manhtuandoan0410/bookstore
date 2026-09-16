package fi.haagahelia.bookstore.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import fi.haagahelia.bookstore.domain.BookRepository;

@Controller
public class BookController {
    

	private final BookRepository bookRepository;

	public BookController(BookRepository bookRepository) {
		this.bookRepository = bookRepository;
	}

    @RequestMapping(value = {"/", "/booklist"})
	public String listbooks(Model model) {
		model.addAttribute("books", bookRepository.findAll());
		return "booklist";
	}
	
	@GetMapping("/book/delete/{id}")
	public String deleteBook(@PathVariable("id") Long id) {
    	bookRepository.deleteById(id);
    	return "redirect:/booklist";
	}

}
