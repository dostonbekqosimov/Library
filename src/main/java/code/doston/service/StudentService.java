package code.doston.service;

import code.doston.entity.Student;
import code.doston.exceptions.DataValidationException;
import code.doston.exceptions.IdExistsException;
import code.doston.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    public Student createStudent(Student student) {

        // Validate student details
//        validateData(student);

        // Save the new student
        student.setCreatedDate(LocalDate.now());
        studentRepository.save(student);

        return student;
    }

    public List<Student> getAll() {

        return studentRepository.findAll();
    }

    public Student getStudentById(Long id) {

        // Check if the student exists
        idNotExists(id);

        return studentRepository.findById(id).get();
    }

    public String deleteStudentById(Long id) {

        // Check if the student exists
        idNotExists(id);

        // Delete the student
        studentRepository.deleteById(id);

        return "Student with id: " + id + " deleted";
    }

    public Boolean updateStudentById(Long id, Student student) {

        // Check if the student exists
        idNotExists(id);

        // Validate student details
        validateData(student);

        Student existedStudent = studentRepository.findById(id).orElse(null);


        existedStudent.setName(student.getName());
        existedStudent.setSurname(student.getSurname());
        existedStudent.setPhone(student.getPhone());
        existedStudent.setCreatedDate(LocalDate.now());
        studentRepository.save(existedStudent);

        return true;

    }

    public void idNotExists(Long id) {
        boolean isExist = studentRepository.existsById(id);
        if (!isExist) {
            throw new IdExistsException("Student not found with id: ", id);
        }
    }

    public void validateData(Student student) {
        if (student.getName() == null || student.getName().isBlank()) {
            throw new DataValidationException("Name can't be empty");
        }
        if (student.getSurname() == null || student.getSurname().isBlank()) {
            throw new DataValidationException("Surname can't be empty");
        }
        if (student.getPhone() == null || student.getPhone().isEmpty()) {
            throw new DataValidationException("Phone can't be empty");
        }


    }
}
