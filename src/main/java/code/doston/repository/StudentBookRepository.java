package code.doston.repository;

import code.doston.entity.Student;
import code.doston.entity.StudentBook;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudentBookRepository extends JpaRepository<StudentBook, Long> {
    StudentBook findByStudentIdAndBookId(Long studentId, Long bookId);

    List<StudentBook> findByStudent(Student student);

    StudentBook findByBookId(Long studentId);
}
