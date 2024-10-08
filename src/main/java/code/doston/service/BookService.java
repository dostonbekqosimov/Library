package code.doston.service;

import code.doston.entity.Book;
import code.doston.exceptions.DataValidationException;
import code.doston.exceptions.IdExistsException;
import code.doston.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public Book createBook(Book book) {

        // Check if the book already exists by title and author
        boolean isTitleAndAuthorExist = bookRepository.existsByTitleAndAuthor(book.getTitle(), book.getAuthor());

        if (isTitleAndAuthorExist) {
            throw new IdExistsException("Book with title: " + book.getTitle() + " and author: " + book.getAuthor() + " already exists");
        }

        // Validate book details
        validateData(book);

        // Save the new book
        return bookRepository.save(book);
    }

    public List<Book> getAll() {
        return bookRepository.findAll();
    }

    public Book getBookById(Long id) {

        idNotExists(id);
        return bookRepository.findById(id).get();
    }

    public String updateBookById(Long id, Book book) {

        // Check if the book exists
        idNotExists(id);

        // Validate book details
        validateData(book);

        // Update the book
        Book existedBook = bookRepository.findById(id).get();
        existedBook.setTitle(book.getTitle());
        existedBook.setAuthor(book.getAuthor());
        existedBook.setPublishedDate(book.getPublishedDate());
        bookRepository.save(existedBook);
        return "Book successfully updated";
    }


    public String deleteBookById(Long id) {

        // Check if the book exists
        idNotExists(id);

        // Delete the book
        bookRepository.deleteById(id);
        return "The book with id: " + id + " deleted";
    }

    public void idNotExists(Long id) {
        boolean isExist = bookRepository.existsById(id);
        if (!isExist) {
            throw new IdExistsException("Book not found with id: ", id);
        }
    }

//    public void idExists(Long id) {
//        boolean isExist = bookRepository.existsById(id);
//        if (isExist) {
//            throw new IdExistsException(id);
//        }
//
//    }

    public void validateData(Book book) {
        if (book.getTitle() == null || book.getTitle().isBlank()) {
            throw new DataValidationException("Title can't be empty");
        }
        if (book.getAuthor() == null || book.getAuthor().isEmpty()) {
            throw new DataValidationException("Author name can't be empty");

        }
        if (book.getPublishedDate() == null) {
            throw new DataValidationException("Published date can't be empty");
        }
        if (book.getPublishedDate().isAfter(LocalDate.now())) {
            throw new DataValidationException("Published date can't be in future");
        }
    }
}
