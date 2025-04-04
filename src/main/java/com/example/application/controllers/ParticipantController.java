package com.example.application.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.application.models.Participant;
import com.example.application.services.ParticipantService;

@RestController
@RequestMapping("/user")
public class ParticipantController {
    private final ParticipantService participantService;

    public ParticipantController(ParticipantService participantService) {
        this.participantService = participantService;
    }
    
    // Endpoint de tipo POST encargado de crear un nuevo participante (Registro)
    @PostMapping("/register")
    public ResponseEntity<Participant> register(@RequestBody Participant participant) {
        Participant savedParticipant = participantService.register(participant);
        return ResponseEntity.ok(savedParticipant);
    }

    // Endpoint de tipo GET encargado de obtener todos los participantes
    @GetMapping
    public ResponseEntity<List<Participant>> getAllParticipants() {
        return ResponseEntity.ok(participantService.findAll());
    }

    // Endpoint de tipo GET encargado de buscar participante por ID
    @GetMapping("/{id}")
    public ResponseEntity<Participant> getParticipantById(@PathVariable Long id) {
        return participantService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Endpoint de tipo GET encargado de buscar participante por su username
    @GetMapping("/username/{username}")
    public ResponseEntity<Participant> getParticipantByUsername(@PathVariable String username) {
        return participantService.findByUsername(username)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Endpoint de tipo PUT encargado de actualizar un participante segun su id
    @PutMapping("/{id}")
    public ResponseEntity<Participant> updateParticipant(@PathVariable Long id, @RequestBody Participant updatedParticipant) {
        Participant participant = participantService.updateParticipant(id, updatedParticipant);
        return ResponseEntity.ok(participant);
    }

    // Endpoint de tipo DELETE encargado de eliminar un participante segun su id
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteParticipant(@PathVariable Long id) {
        participantService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

     //  Endpoint de tipo POST encargado de iniciar sesion
    @PostMapping("/login")
    public ResponseEntity<Participant> login(@RequestParam String username, @RequestParam String password) {
        Optional<Participant> participant = participantService.Login(username, password);
        return participant.map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(401).build()); // devuelve 401 si falla el login
    }

}
