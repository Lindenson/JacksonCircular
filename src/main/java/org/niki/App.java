package org.niki;


import org.niki.dao.StudentDAO;
import org.niki.dao.StudentDAOImpl;
import org.niki.dto.Group;
import org.niki.dto.Student;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class App {
    public static void main(String[] args) {

        Student student1 = new Student(
                1,
                "Krutov",
                "Vasyl",
                List.of(Group.CHINESE, Group.ENGLISH),
                null);

        Student student2 = new Student(
                2,
                "Stez",
                "Vlad",
                List.of(Group.CHINESE, Group.SPAIN),
                null);

        student1.friends = List.of(student2);
        student2.friends = List.of(student1);

        StudentDAO studentDAO = new StudentDAOImpl();
        studentDAO.writeStudent("./Student1.json", student1);
        studentDAO.writeStudent("./Student2.json", student2);

        Student student11 = studentDAO.readStudent("./Student1.json");
        Student student22 = studentDAO.readStudent("./Student2.json");
        List<Student> students = List.of(student11, student22);


        //Without this code would be zero ID's! So lets recover it
        Map<String, Student> friendsMatrix = students.stream()
                .collect(Collectors.toMap(
                        y -> y.familyName + y.name, Function.identity()));

        students.stream()
                .flatMap(x -> x.friends.stream())
                .forEach(x -> {
                    x.id = friendsMatrix.get(x.familyName + x.name).id;
                });


        assert student1.equals(student11);
        assert student2.equals(student22);

        System.out.println("Student after serializ/deserializ:");
        students.stream().forEach(System.out::println);
        System.out.println("Work is done!");

    }
}
