package org.niki.dao;


import org.niki.dto.Student;

public interface StudentDAO {

    Student readStudent(String file);

    public void writeStudent(String file, Student student);
}
