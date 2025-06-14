package com.example.application.services;

import com.example.application.models.Student;
import com.example.application.repositories.StudentRepository;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.List;

@Service
public class StudentService {

    private final StudentRepository StudentRepository;

     public StudentService(StudentRepository studentRepository) {
        this.StudentRepository = studentRepository;
     }

    public List<Student> importarDesdeJSON(String rutaArchivo) {
        Gson gson = new Gson();
        List<Student> students = null;

        try (FileReader reader = new FileReader(rutaArchivo)) {
            Type tipoLista = new TypeToken<List<Student>>() {}.getType();
            students = gson.fromJson(reader, tipoLista);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return students;
    }

    public Student save(Student student) {
        return StudentRepository.save(student);
    }

    public void deleteAll() {
        StudentRepository.deleteAll();
    }

    public List<Student> saveAll(List<Student> students) {
        return StudentRepository.saveAll(students);
    }

    
}
