package com.nthnetchill.controller;

import com.nthnetchill.model.Genre;
import com.nthnetchill.model.Movie;
import com.nthnetchill.service.GenreService;
import com.nthnetchill.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/genres")
public class GenreController {
    @Autowired
    private GenreService genreService;

    @GetMapping
    public ResponseEntity<List<Genre>> getAllGenres() {
        List<Genre> genres = genreService.getAllGenres();
        return ResponseEntity.ok(genres);
    }

    @GetMapping("/movie/{movieId}")
    public Set<Genre> getGenresByMovieId(@PathVariable Long movieId) {
        return genreService.getGenresByMovieId(movieId);
    }

    @PostMapping
    public ResponseEntity<Genre> addGenre(@RequestBody Genre genre) {
        Genre savedGenre = genreService.addGenre(genre);
        return ResponseEntity.ok(savedGenre);
    }

    @PutMapping("/{genreId}")
    public ResponseEntity<Genre> updateGenre(@PathVariable Long genreId, @RequestBody Genre updatedGenre) {
        Genre updated = genreService.updateGenre(genreId, updatedGenre);
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    @DeleteMapping("/{genreId}")
    public ResponseEntity<Void> deleteGenre(@PathVariable Long genreId) {
        genreService.deleteGenre(genreId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
