package fi.haagahelia.bookstore.web;
import fi.haagahelia.bookstore.domain.Book;

import java.util.List;
import java.util.ArrayList;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class BookController {
    private final List<Book> books = new ArrayList<>();

    @RequestMapping("/index")
	public String showMaingPage() {
		return "main";
	}
	
	@RequestMapping("/hello")
	public String showGreetings(@RequestParam (name="nimi", required=false) String name, Model model) {
		model.addAttribute("name", name);
		return "main";
	}

}
