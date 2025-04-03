package com.example.application.repositories;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.application.models.Participant;

public interface ParticipantRepository extends JpaRepository<Participant, Long>{
    Optional<Participant> findByUsername(String username); 
}
