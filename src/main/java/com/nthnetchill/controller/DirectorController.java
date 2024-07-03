package com.nthnetchill.controller;

import com.nthnetchill.model.Actor;
import com.nthnetchill.model.Director;
import com.nthnetchill.service.DirectorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@CrossOrigin(origins = "http://127.0.0.1:5500")
@RestController
@RequestMapping("/directors")
public class DirectorController {
    @Autowired
    private DirectorService directorService;

    @GetMapping
    public ResponseEntity<List<Director>> getAllDirectors() {
        List<Director> directors = directorService.getAllDirectors();
        return ResponseEntity.ok(directors);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Director> getOneDirector(@PathVariable Long id) {
        Director director = directorService.getOneDirector(id);
        return ResponseEntity.ok(director);
    }

    @PostMapping
    public ResponseEntity<Director> addDirector(@RequestBody Director director) {
        Director savedDirector = directorService.addDirector(director);
        return ResponseEntity.ok(savedDirector);
    }

    @PutMapping("/{directorId}")
    public ResponseEntity<Director> updateDirector(@PathVariable Long directorId, @RequestBody Director updatedDirector) {
        Director updated = directorService.updateDirector(directorId, updatedDirector);
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    @PostMapping("/{directorId}/avatar")
    public ResponseEntity<Director> addAvatarToDirector(@PathVariable Long directorId, @RequestParam("avatarFile") MultipartFile avatarFile) {
        try {
            Director updatedDirector = directorService.addAvatarToDirector(directorId, avatarFile);
            return ResponseEntity.ok(updatedDirector);
        } catch (IOException e) {
            return ResponseEntity.status(500).body(null);
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(null);
        }
    }

    @DeleteMapping("/{directorId}")
    public ResponseEntity<Void> deleteDirector(@PathVariable Long directorId) {
        directorService.deleteDirector(directorId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
