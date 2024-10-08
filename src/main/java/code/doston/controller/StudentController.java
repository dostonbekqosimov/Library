package code.doston.controller;


import code.doston.entity.Student;
import code.doston.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/students")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @PostMapping
    public ResponseEntity<?> addStudent(@Valid @RequestBody Student student) {

        return ResponseEntity.ok().body(studentService.createStudent(student));
    }

    @GetMapping
    public ResponseEntity<List<Student>> getAllStudents() {

        return ResponseEntity.ok().body(studentService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getStudentById(@PathVariable("id") Long id) {
        return ResponseEntity.ok().body(studentService.getStudentById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteStudentById(@PathVariable("id") Long id) {
        return ResponseEntity.ok().body(studentService.deleteStudentById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateStudentById(@PathVariable("id") Long id, @RequestBody Student student) {
        return ResponseEntity.ok().body(studentService.updateStudentById(id, student));

    }

}
