package org.niki.dao;


import org.niki.dto.Student;

import java.util.List;

public interface StudentDAO {

    void restoreIdInternals(List<Student> students);

    Student readStudent(String file);

    void writeStudent(String file, Student student);
}
