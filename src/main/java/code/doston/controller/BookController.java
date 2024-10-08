package code.doston.controller;


import code.doston.entity.Book;
import code.doston.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @PostMapping
    public ResponseEntity<?> addBook(@RequestBody Book book) {
        return ResponseEntity.status(201).body(bookService.createBook(book));

    }

    @GetMapping
    public ResponseEntity<List<Book>> getAllBooks() {

        return ResponseEntity.ok().body(bookService.getAll());
    }

    @GetMapping("/book/{id}")
    public ResponseEntity<?> getBookById(@PathVariable("id") Long id) {

        return ResponseEntity.ok().body(bookService.getBookById(id));
    }

    @PutMapping("/book/{id}")
    public ResponseEntity<?> updateBookById(@PathVariable("id") Long id,
                                            @RequestBody Book book) {


        return ResponseEntity.ok().body(bookService.updateBookById(id, book));
    }

    @DeleteMapping("/book/{id}")
    public ResponseEntity<?> deleteBookById(@PathVariable("id") Long id) {
        return ResponseEntity.ok().body(bookService.deleteBookById(id)
        );
    }
}
