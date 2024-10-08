package code.doston.service;

import code.doston.entity.Book;
import code.doston.entity.Student;
import code.doston.entity.StudentBook;
import code.doston.entity.enums.Status;
import code.doston.exceptions.DataValidationException;
import code.doston.exceptions.IdExistsException;
import code.doston.repository.BookRepository;
import code.doston.repository.StudentBookRepository;
import code.doston.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class StudentBookService {

    @Autowired
    private StudentBookRepository studentBookRepository;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private BookRepository bookRepository;

    public String takeBook(Long studentId, Long bookId) {

        // Check if student exists
        studentIdNotExists(studentId);

        // Check if book exists
        bookIdNotExists(bookId);


        // Validate the status of the book
        validateData(studentBookRepository.findByStudentIdAndBookId(studentId, bookId));


        // Take book
        Student student = studentRepository.findById(studentId).get();
        Book book = bookRepository.findById(bookId).get();

        StudentBook studentBook = new StudentBook();
        studentBook.setStudent(student);
        studentBook.setBook(book);
        studentBook.setCreatedDate(LocalDate.now());
        studentBook.setStatus(Status.BORROWED);
        studentBook.setReturnedDate(null);
        studentBook.setDuration(0);
        studentBookRepository.save(studentBook);

//        return studentBook;

        return "Student with id: " + studentId + " took book with id: " + bookId;
    }


    public String returnBook(Long studentId, Long bookId) {


        // Check if student exists
        studentIdNotExists(studentId);
        // Check if book exists
        bookIdNotExists(bookId);


        StudentBook studentBook = studentBookRepository.findByStudentIdAndBookId(studentId, bookId);


        studentBook.setStatus(Status.RETURNED);
        studentBook.setReturnedDate(LocalDate.now());
        studentBook.setDuration((int) (LocalDate.now().getDayOfYear() - studentBook.getCreatedDate().getDayOfYear()));
        studentBookRepository.save(studentBook);

//        return studentBook;
        return "Student with id: " + studentId + " returned book with id: " + bookId + " in " + studentBook.getDuration() + " days";
    }


    public List<StudentBook> getStudentBooksTakenByStudent(Long studentId) {


        // Check if student exists
        studentIdNotExists(studentId);

        // Get all the books taken by the student
        Student student = studentRepository.findById(studentId).get();

        return studentBookRepository.findByStudent(student);

    }

    public List<StudentBook> getAllStudentBooks() {

        return studentBookRepository.findAll();
    }

    public StudentBook getStudentBook(Long bookId) {

        // Check if book exists
        bookIdNotExists(bookId);

        return studentBookRepository.findByBookId(bookId);
    }

    public void bookIdNotExists(Long bookId) {
        boolean isBookExist = bookRepository.existsById(bookId);


        if (!isBookExist) {
            throw new IdExistsException("Book not found with id: ", bookId);
        }


    }

    public void studentIdNotExists(Long studentId) {
        boolean isExist = studentRepository.existsById(studentId);
        if (!isExist) {
            throw new IdExistsException("Student not found with id: ", studentId);
        }
    }

    public void studentBookIdNotExists(Long studentId, Long bookId) {
        StudentBook studentBook = studentBookRepository.findByStudentIdAndBookId(studentId, bookId);
        if (studentBook == null) {
//            throw new IdExistsException("StudentBook not found with id: ", studentId, bookId);
        }
    }

    public void validateData(StudentBook studentBook) {

        if (studentBook.getStatus() == Status.BORROWED) {
            throw new DataValidationException("Book is already borrowed");
        }
    }
}
