package org.niki.dao;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.niki.dto.Student;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;





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
            //System.out.println("file was read");
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
            //System.out.println("file was written");
        }
    }

    @Override
    public void restoreIdInternals(List<Student> students) {
        Map<String, Student> friendsMatrix = students.stream()
                .collect(Collectors.toMap(
                        y -> y.familyName + y.name, Function.identity()));

        students.stream()
                .flatMap(x -> x.friends.stream())
                .forEach(x -> {
                    x.id = friendsMatrix.get(x.familyName + x.name).id;
                });
    }
}

