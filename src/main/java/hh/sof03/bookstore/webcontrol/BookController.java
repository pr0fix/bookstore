package hh.sof03.bookstore.webcontrol;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import hh.sof03.bookstore.domain.BookRepository;

@Controller
public class BookController {

    @Autowired
    BookRepository bookRepository;

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String welcome(Model model) {
        return "welcomepage";
    }

    @RequestMapping(value = "/booklist", method = RequestMethod.GET)
    public String listBooks(Model model) {
        model.addAttribute("books", bookRepository.findAll());
        return "booklist";
    }

}
