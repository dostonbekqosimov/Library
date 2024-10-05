package code.doston.service;

import code.doston.entity.Book;
import code.doston.entity.Student;
import code.doston.entity.StudentBook;
import code.doston.entity.enums.Status;
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

        Student student = studentRepository.findById(studentId).orElse(null);
        Book book = bookRepository.findById(bookId).orElse(null);

        if (student == null) {
            return "The student with id: " + studentId + " not found";
        } else if (book == null) {
            return "The book with id: " + bookId + " not found";
        } else {


            StudentBook studentBook = new StudentBook();
            studentBook.setStudent(student);
            studentBook.setBook(book);
            studentBook.setCreatedDate(LocalDate.now());
            studentBook.setStatus(Status.BORROWED);
            studentBook.setReturnedDate(null);
            studentBook.setDuration(0);
            studentBookRepository.save(studentBook);

            return "Student with id: " + studentId + " took book with id: " + bookId;
        }

    }

    public String returnBook(Long studentId, Long bookId) {

        StudentBook studentBook = studentBookRepository.findByStudentIdAndBookId(studentId, bookId);
        if (studentBook == null) {
            return "The student with id: " + studentId + " and book with id: " + bookId + " not found";
        } else {
            studentBook.setStatus(Status.RETURNED);
            studentBook.setReturnedDate(LocalDate.now());
            studentBook.setDuration((int) (LocalDate.now().getDayOfYear() - studentBook.getCreatedDate().getDayOfYear()));
            studentBookRepository.save(studentBook);
            return "Student with id: " + studentId + " and book with id: " + bookId + " returned";
        }
    }

    public List<StudentBook> getStudentBooksTakenByStudent(Long studentId) {

        Student student = studentRepository.findById(studentId).orElse(null);
        if (student == null) {
            return null;
        } else {
            return studentBookRepository.findByStudent(student);
        }
    }

    public List<StudentBook> getAllStudentBooks() {

        return studentBookRepository.findAll();
    }

    public StudentBook getStudentBook(Long bookId) {

        return studentBookRepository.findByBookId(bookId);
    }
}
