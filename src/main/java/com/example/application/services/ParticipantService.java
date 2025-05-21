package com.example.application.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.application.models.Participant;
import com.example.application.repositories.ParticipantRepository;

@Service
public class ParticipantService {
    private ParticipantRepository participantRepository;

    //Inyectando la clase ParticipantRepository a traves del constructor para poder usar los metodos por defecto.---------
    public ParticipantService (ParticipantRepository participantRepository){ 
        this.participantRepository = participantRepository;
    }

    //CRUD----------------------------------------------------------------------------------------------------------------
    // Crear un nuevo participante (Registro)
    public Participant register (Participant participant) {
        return participantRepository.save(participant);
    }

    // Leer (consultar todos los participantes)
    public List<Participant> findAll() {
        return participantRepository.findAll();
    }

    // Leer (Consultar por ID)
    public Optional<Participant> findById(Long id) {
        return participantRepository.findById(id);
    }

     // Leer (Consultar por username)
    public Optional<Participant> findByUsername(String username) {
        return participantRepository.findByUsername(username);
    }

    // Actualizar participante
    public Participant updateParticipant(Long id, Participant participanteUpdated) {
        // buscar el participante por ID, si no se encuentra lanza una excepción
        Participant Participant = participantRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Participante no encontrado con ID: " + id));
    
        // Actualizar los datos del participante existente con los nuevos valores
        Participant.setUsername(participanteUpdated.getUsername());
        Participant.setPassword(participanteUpdated.getPassword());
        Participant.setEmail(participanteUpdated.getEmail());
    
        // Guardar y retornar el participante actualizado
        return participantRepository.save(Participant);
    }

    // Eliminar participante por su id
    public void deleteById(Long id) {
        participantRepository.deleteById(id);
    }
    
    // Iniciar sesión (Verifica usuario y contraseña)
    public Optional<Participant> Login (String username, String password) {
        return participantRepository.findByUsername(username)
                    .filter(p -> p.getPassword().equals(password));
    }


}
