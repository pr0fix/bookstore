package hh.sof03.bookstore.webcontrol;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import hh.sof03.bookstore.domain.Book;
import hh.sof03.bookstore.domain.BookRepository;
import hh.sof03.bookstore.domain.CategoryRepository;

@CrossOrigin
@Controller
@RequestMapping("/webcontrol")
public class BookController {

    @Autowired
    BookRepository bookRepository;

    @Autowired
    CategoryRepository categoryRepository;

    // Lists all the books in booklist
    @RequestMapping(value = "/booklist", method = RequestMethod.GET)
    public String listBooks(Model model) {
        model.addAttribute("books", bookRepository.findAll());
        return "booklist";
    }

    // Adds a book into booklist
    @RequestMapping(value = "/addbook")
    public String addBook(Model model) {
        model.addAttribute("book", new Book());
        model.addAttribute("categories", categoryRepository.findAll());
        return ("addbook");
    };

    // Saves added/edited book in booklist
    @RequestMapping(value = "/savebook", method = RequestMethod.POST)
    public String saveBook(Book book) {
        bookRepository.save(book);
        return "redirect:booklist";
    }

    // Deletes a book from booklist using id only if request comes from "ADMIN"
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('ADMIN')")
    public String deleteBook(@PathVariable("id") Long bookId, Model model) {
        bookRepository.deleteById(bookId);
        return "redirect:../booklist";
    }

    // Edits a book in booklist using id
    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String showModBook(@PathVariable("id") Long bookId, Model model) {
        model.addAttribute(("book"), bookRepository.findById(bookId));
        model.addAttribute("categories", categoryRepository.findAll());
        return "editbook";
    }

    //REST Service methods:

    //REST Service that lists all the books in booklist
    @GetMapping("/books")
    public @ResponseBody List<Book> bookListRest() {
        return (List<Book>) bookRepository.findAll();
    }

    //Rest service to find a book by id
    @GetMapping("/books/{id}")
    public @ResponseBody Optional<Book> findBookByIdRest(@PathVariable("id") Long bookId) {
        return bookRepository.findById(bookId);
    }
}
