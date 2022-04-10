package org.niki.dao;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.niki.dto.Student;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class StudentDAOImpl implements StudentDAO {

    private final ObjectMapper objectMapper = new ObjectMapper();
    {
        objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
    }

    @Override
    public Student readStudent(String file) {

        try (FileInputStream fs = new FileInputStream(file)) {
            Student student = objectMapper.readValue(fs, Student.class);
            return student;
        }
        catch (Throwable ex) {
            System.out.println("error reading"+ex.getMessage());
            return null;
        }
        finally {
            System.out.println("file was read");
        }
    }

    @Override
    public void writeStudent(String file, Student student) {
        try (FileOutputStream fs = new FileOutputStream(file)) {
            objectMapper.writeValue(fs, student);
        } catch (Throwable ex) {
            System.out.println("error reading"+ex.getMessage());
        }
        finally {
            System.out.println("file was written");
        }
    }
}
