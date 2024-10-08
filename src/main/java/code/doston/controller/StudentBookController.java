package code.doston.controller;


import code.doston.entity.StudentBook;
import code.doston.service.StudentBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/student_book")
public class StudentBookController {

    @Autowired
    private StudentBookService studentBookService;

    @PostMapping("/student/{studentId}/book/{bookId}")
    public ResponseEntity<?> takeBook(@PathVariable("studentId") Long studentId, @PathVariable("bookId") Long bookId) {

        return ResponseEntity.ok().body(studentBookService.takeBook(studentId, bookId));
    }

    @PutMapping("/student/{studentId}/book/{bookId}")
    public ResponseEntity<?> returnBook(@PathVariable("studentId") Long studentId, @PathVariable("bookId") Long bookId) {
        return ResponseEntity.ok().body(studentBookService.returnBook(studentId, bookId));
    }

    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<StudentBook>> getStudentBooks(@PathVariable("studentId") Long studentId) {
        return ResponseEntity.ok().body(studentBookService.getStudentBooksTakenByStudent(studentId));
    }

    @GetMapping("/book/{bookId}")
    public ResponseEntity<?> getBooks(@PathVariable("bookId") Long bookId) {
        return ResponseEntity.ok().body(studentBookService.getStudentBook(bookId));
    }

    @GetMapping
    public ResponseEntity<List<StudentBook>> getAllStudentBooks() {
        return ResponseEntity.ok().body(studentBookService.getAllStudentBooks());
    }

}
