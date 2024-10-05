package code.doston.controller;


import code.doston.entity.StudentBook;
import code.doston.service.StudentBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/student_book")
public class StudentBookController {

    @Autowired
    private StudentBookService studentBookService;

    @PostMapping("/student/{studentId}/book/{bookId}")
    public String takeBook(@PathVariable("studentId") Long studentId, @PathVariable("bookId") Long bookId) {


        return studentBookService.takeBook(studentId, bookId);
    }

    @PutMapping("/student/{studentId}/book/{bookId}")
    public String returnBook(@PathVariable("studentId") Long studentId, @PathVariable("bookId") Long bookId) {
        return studentBookService.returnBook(studentId, bookId);
    }

    @GetMapping("/student/{studentId}")
    public List<StudentBook> getStudentBooks(@PathVariable("studentId") Long studentId) {
        return studentBookService.getStudentBooksTakenByStudent(studentId);
    }

    @GetMapping("/book/{bookId}")
    public StudentBook getBooks(@PathVariable("bookId") Long bookId) {
        return studentBookService.getStudentBook(bookId);
    }

    @GetMapping
    public List<StudentBook> getAllStudentBooks() {
        return studentBookService.getAllStudentBooks();
    }

}
