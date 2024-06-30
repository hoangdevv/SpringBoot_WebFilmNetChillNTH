package com.nthnetchill.service;

import com.nthnetchill.model.Actor;
import com.nthnetchill.model.Genre;
import com.nthnetchill.model.Movie;
import com.nthnetchill.repository.ActorRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ActorService {
    @Autowired
    private ActorRepository actorRepository;

    @Transactional
    public List<Actor> getAllActors(){
        return actorRepository.findAll();
    }

    @Transactional
    public Actor getOneActor(Long actorId){
        Optional<Actor> actorOptional = actorRepository.findById(actorId);
        if (actorOptional.isPresent()) {
            return actorOptional.get();
        } else {
            throw new RuntimeException("Actor not found with id " + actorId);
        }
    }

    @Transactional
    public Actor addActor(Actor actor) {
        return actorRepository.save(actor);
    }

    @Transactional
    public Actor updateActor(Long actorId, Actor updatedActor) {
        Actor updateActor = actorRepository.findById(actorId).orElseThrow(() -> new RuntimeException("Genre not found with id " + actorId));

        updateActor.setName(updatedActor.getName());
        updateActor.setDateOfBirth(updatedActor.getDateOfBirth());
        updateActor.setInformation(updatedActor.getInformation());

        return actorRepository.save(updateActor);
    }

    @Transactional
    public Actor addAvatarToActor(Long actorId, MultipartFile avatarFile) throws IOException {
        Optional<Actor> actorOptional = actorRepository.findById(actorId);
        if (actorOptional.isPresent()) {
            Actor actor = actorOptional.get();
            String avatarUrl = saveAvatarFile(avatarFile);
            actor.setAvatarUrl(avatarUrl);
            return actorRepository.save(actor);
        } else {
            throw new RuntimeException("Movie not found with id " + actorId);
        }
    }

    public String saveAvatarFile(MultipartFile avatarFile) throws IOException {
        String fileName = UUID.randomUUID().toString() + "_" + avatarFile.getOriginalFilename();
        Path filePath = Paths.get("src/main/resources/static/images/actor", fileName);
        Files.createDirectories(filePath.getParent());
        Files.write(filePath, avatarFile.getBytes());
        return "/images/actor/" + fileName;
    }

    @Transactional
    public void deleteActor(Long actorId) {
        Actor actor = actorRepository.findById(actorId)
                .orElseThrow(() -> new RuntimeException("Genre not found with id " + actorId));
        actor.getMovies().forEach(movie -> movie.getActors().remove(actor));
        actorRepository.delete(actor);
    }
}
