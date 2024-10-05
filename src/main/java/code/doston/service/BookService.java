package code.doston.service;

import code.doston.entity.Book;
import code.doston.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    public boolean createBook(Book book) {
        // Check if the book already exists by title and author
        boolean isExists = bookRepository.existsByTitleAndAuthor(book.getTitle(), book.getAuthor());
        if (isExists) {
            return false;
        }

        // Validate book details
        if (book.getTitle() == null || book.getTitle().isEmpty() ||
                book.getAuthor() == null || book.getAuthor().isEmpty() ||
                book.getPublishedDate() == null || book.getPublishedDate().isAfter(LocalDate.now())) {
            return false;
        }

        // Save the new book
        bookRepository.save(book);
        return true;
    }

    public List<Book> getAll() {
        return bookRepository.findAll();
    }

    public Book getBookById(Long id) {
        return bookRepository.findById(id).orElse(null);
    }

    public void updateBookById(Long id, Book book) {
        Book existedBook = bookRepository.findById(id).orElse(null);
        if (existedBook == null) {
            System.out.println("The book with id: " + id + " not found");
        } else {
            existedBook.setTitle(book.getTitle());
            existedBook.setAuthor(book.getAuthor());
            existedBook.setPublishedDate(book.getPublishedDate());
            bookRepository.save(existedBook);
        }
    }

    public void deleteBookById(Long id) {
        bookRepository.deleteById(id);
        System.out.println("The book with id: " + id + " deleted");
    }
}
