package com.nthnetchill.controller;

import com.nthnetchill.model.Movie;
import com.nthnetchill.repository.MovieRepository;
import com.nthnetchill.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/movies")
public class MovieController {

    @Autowired
    private MovieService movieService;
    @Autowired
    private MovieRepository movieRepository;

    @GetMapping
    public ResponseEntity<List<Movie>> getAllMovies() {
        List<Movie> movies = movieService.getAllMovies();
        return ResponseEntity.ok().body(movies);
    }


    @GetMapping("/{movieId}")
    public ResponseEntity<Movie> getMovie(@PathVariable Long movieId) {
        try {
            Movie movie = movieService.getMovieById(movieId);
            return ResponseEntity.ok(movie);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/single")
    public List<Movie> getSingleMovies() {
        return movieRepository.findByIsSeries(false);
    }

    @GetMapping("/series")
    public List<Movie> getSeriesMovies() {
        return movieRepository.findByIsSeries(true);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Movie>> getMoviesByName(@RequestParam String name) {
        List<Movie> movies = movieService.getMoviesByName(name);
        return ResponseEntity.ok(movies);
    }


    @PostMapping
    public ResponseEntity<Movie> addMovie(@RequestBody Movie movie) {
        Movie savedMovie = movieService.addMovie(movie);
        return ResponseEntity.ok(savedMovie);
    }


    @PutMapping("/{movieId}")
    public ResponseEntity<Movie> updateMovie(@PathVariable Long movieId, @RequestBody Movie updatedMovie) {
        try {
            Movie movie = movieService.updateMovie(movieId, updatedMovie);
            return ResponseEntity.ok(movie);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }


    @PostMapping("/{movieId}/image")
    public ResponseEntity<Movie> addImageToMovie(@PathVariable Long movieId, @RequestParam("imageFile") MultipartFile imageFile) {
        try {
            Movie updatedMovie = movieService.addImageToMovie(movieId, imageFile);
            return ResponseEntity.ok(updatedMovie);
        } catch (IOException e) {
            return ResponseEntity.status(500).body(null);
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(null);
        }
    }


    @DeleteMapping("/{movieId}")
    public ResponseEntity<Void> deleteMovie(@PathVariable Long movieId) {
        try {
            movieService.deleteMovie(movieId);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
