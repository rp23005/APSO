package com.example.application.controllers;

import com.example.application.models.Student;
import com.example.application.services.StudentService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;


@RestController
@RequestMapping("/api/import")
public class ImportarController {

    private final StudentService studentService;
    private final ObjectMapper objectMapper;

    @Autowired
    public ImportarController(StudentService studentService) {
        this.studentService = studentService;
        this.objectMapper = new ObjectMapper();
    }

    /**
     * Endpoint para subir un archivo JSON y guardar estudiantes.
     * @param file archivo JSON que contiene una lista de estudiantes
     * @return respuesta con estudiantes guardados
     */

     @PostMapping("/students")
     public ResponseEntity<?> importStudents(@RequestParam("file") MultipartFile file) {
        try {

            List<Student> students = objectMapper.readValue(
                file.getInputStream(),
                new TypeReference<List<Student>>() {}
            );

            for (Student student : students) {
                studentService.save(student);
            }

            return ResponseEntity.ok(students);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Error al procesar el archivo JSON: " + e.getMessage());
        }
     }

     @DeleteMapping("/students")
     public ResponseEntity<?> deleteAllStudents() {
        studentService.deleteAll();
        return ResponseEntity.ok("Todo los estudiantes han sido eliminados");
     }
    
}
