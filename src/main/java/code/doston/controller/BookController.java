package code.doston.controller;


import code.doston.entity.Book;
import code.doston.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @PostMapping
    public String addBook(@RequestBody Book book) {
        boolean created = bookService.createBook(book);

        if (!created) {
            return "Book already exists";
        }
        return "Book successfully added";
    }

    @GetMapping
    public List<Book> getAllBooks(){

        return bookService.getAll();
    }

    @GetMapping("/book/{id}")
    public Book getBookById(@PathVariable("id") Long id){

        return bookService.getBookById(id);
    }

    @PutMapping("/book/{id}")
    public String updateBookById(@PathVariable("id") Long id,
                                 @RequestBody Book book){

        bookService.updateBookById(id, book);
        return "Book successfully updated";
    }

    @DeleteMapping("/book/{id}")
    public String deleteBookById(@PathVariable("id") Long id){

        bookService.deleteBookById(id);
        return "Book successfully deleted";
    }
}
