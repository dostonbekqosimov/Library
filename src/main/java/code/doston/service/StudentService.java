package code.doston.service;

import code.doston.entity.Student;
import code.doston.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    public boolean createStudent(Student student) {

        student.setCreatedDate(LocalDate.now());


        studentRepository.save(student);
        return true;
    }

    public List<Student> getAll() {

        return studentRepository.findAll();
    }

    public Student getStudentById(Long id) {

        return studentRepository.findById(id).orElse(null);
    }

    public void deleteStudentById(Long id) {

        studentRepository.deleteById(id);
    }

    public void updateStudentById(Long id, Student student) {

        Student existedStudent = studentRepository.findById(id).orElse(null);
        if (existedStudent == null) {
            System.out.println("The student with id: " + id + " not found");
        } else {
            existedStudent.setName(student.getName());
            existedStudent.setSurname(student.getSurname());
            existedStudent.setPhone(student.getPhone());
            existedStudent.setCreatedDate(student.getCreatedDate());
            studentRepository.save(existedStudent);
        }
    }
}
