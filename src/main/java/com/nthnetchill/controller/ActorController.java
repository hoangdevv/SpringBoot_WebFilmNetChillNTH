package com.nthnetchill.controller;

import com.nthnetchill.model.Actor;
import com.nthnetchill.model.Genre;
import com.nthnetchill.model.Movie;
import com.nthnetchill.service.ActorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/actors")
public class ActorController {

    @Autowired
    private ActorService actorService;

    @GetMapping
    public ResponseEntity<List<Actor>> getAllActors() {
        List<Actor> actors = actorService.getAllActors();
        return ResponseEntity.ok(actors);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Actor> getOneActor(@PathVariable Long id) {
        Actor actor = actorService.getOneActor(id);
        return ResponseEntity.ok(actor);
    }

    @PostMapping
    public ResponseEntity<Actor> addActor(@RequestBody Actor actor) {
        Actor savedActor = actorService.addActor(actor);
        return ResponseEntity.ok(savedActor);
    }

    @PutMapping("/{actorId}")
    public ResponseEntity<Actor> updateActor(@PathVariable Long actorId, @RequestBody Actor updatedActor) {
        Actor updated = actorService.updateActor(actorId, updatedActor);
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    @PostMapping("/{actorId}/avatar")
    public ResponseEntity<Actor> addAvatarToActor(@PathVariable Long actorId, @RequestParam("avatarFile") MultipartFile avatarFile) {
        try {
            Actor updatedActor = actorService.addAvatarToActor(actorId, avatarFile);
            return ResponseEntity.ok(updatedActor);
        } catch (IOException e) {
            return ResponseEntity.status(500).body(null);
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(null);
        }
    }

    @DeleteMapping("/{actorId}")
    public ResponseEntity<Void> deleteActor(@PathVariable Long actorId) {
        actorService.deleteActor(actorId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
