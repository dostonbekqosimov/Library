package code.doston.controller;


import code.doston.entity.Student;
import code.doston.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/students")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @PostMapping
    public String addStudent(@RequestBody Student student) {

        boolean created = studentService.createStudent(student);
        if (!created) {
            return "Student already exists";
        }
        return "Student successfully added";
    }

    @GetMapping
    public List<Student> getAllStudents(){

        return studentService.getAll();
    }

    @GetMapping("/{id}")
    public Student getStudentById(@PathVariable("id") Long id){
        return studentService.getStudentById(id);
    }

    @DeleteMapping("/{id}")
    public String deleteStudentById(@PathVariable("id") Long id){
        studentService.deleteStudentById(id);
        return "Student successfully deleted";
    }

    @PutMapping("/{id}")
    public String updateStudentById(@PathVariable("id") Long id, @RequestBody Student student){
        studentService.updateStudentById(id, student);
        return "Student successfully updated";
    }

}
