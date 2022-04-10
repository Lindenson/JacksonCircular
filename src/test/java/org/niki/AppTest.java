package org.niki;


import org.junit.jupiter.api.*;
import org.niki.dao.StudentDAO;
import org.niki.dao.StudentDAOImpl;
import org.niki.dto.Group;
import org.niki.dto.Student;
import java.io.File;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AppTest
{

    static Student student1 = new Student(
            1,
            "Krutov",
            "Vasyl",
            List.of(Group.CHINESE, Group.ENGLISH),
            null);

    static Student student2 = new Student(
            2,
            "Stez",
            "Vlad",
            List.of(Group.CHINESE, Group.SPAIN),
            null);


    @BeforeAll
    void setup(){
        student1.friends = List.of(student2);
        student2.friends = List.of(student1);

        StudentDAO studentDAO = new StudentDAOImpl();
        studentDAO.writeStudent("./Student1.json", student1);
        studentDAO.writeStudent("./Student2.json", student2);
    }


    @Test
    void serdo_should_return_the_same_class() {
        StudentDAO studentDAO = new StudentDAOImpl();
        Student student11 = studentDAO.readStudent("./Student1.json");
        Student student22 = studentDAO.readStudent("./Student2.json");

        assertEquals(student1, student11);
        assertEquals(student2, student22);
    }


    @Test
    void method_should_restore_cyclic_dependencies() {
        StudentDAO studentDAO = new StudentDAOImpl();
        Student student11 = studentDAO.readStudent("./Student1.json");
        Student student22 = studentDAO.readStudent("./Student2.json");
        List<Student> students = List.of(student11, student22);

        studentDAO.restoreIdInternals(students);

        assertEquals(student11.friends.get(0), student22);
        assertEquals(student22.friends.get(0), student11);
    }

    @AfterAll
    void clean(){
        File file1 = new File("./Student1.json");
        File file2 = new File("./Student2.json");
        if (file1.exists()) file1.delete();
        if (file2.exists()) file2.delete();
    }
}
