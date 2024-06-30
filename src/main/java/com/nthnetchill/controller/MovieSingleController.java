package com.nthnetchill.controller;

import com.nthnetchill.model.Movie;
import com.nthnetchill.model.MovieSingle;
import com.nthnetchill.service.MovieSingleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/movie-singles")
public class MovieSingleController {
    @Autowired
    private MovieSingleService movieSingleService;

    @GetMapping
    public List<MovieSingle> getAllMovieSingles() {
        return movieSingleService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<MovieSingle> getMovieSingleById(@PathVariable Long id) {
        Optional<MovieSingle> movieSingle = movieSingleService.findById(id);
        return movieSingle.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/{movieId}/single")
    public ResponseEntity<MovieSingle> addMovieSingle(@PathVariable Long movieId,
                                                      @RequestParam(value = "description", required = false) String description,
                                                      @RequestParam("videoFile") MultipartFile videoFile) {
        try {
            MovieSingle movieSingle = movieSingleService.addMovieSingle(movieId, description, videoFile);
            return ResponseEntity.ok(movieSingle);
        } catch (IOException e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @PutMapping("/{movieId}")
    public ResponseEntity<MovieSingle> updateMovieSingle(@PathVariable Long movieId,
                                                         @RequestParam(value = "description", required = false) String description,
                                                         @RequestParam(value = "videoFile", required = false) MultipartFile videoFile) {
        try {
            MovieSingle updatedMovieSingle = movieSingleService.updateMovieSingle(movieId, description, videoFile);
            return ResponseEntity.ok(updatedMovieSingle);
        } catch (IOException e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMovieSingle(@PathVariable Long id) {
        try {
            movieSingleService.deleteMovieSingle(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
