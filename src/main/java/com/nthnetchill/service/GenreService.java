package com.nthnetchill.service;

import com.nthnetchill.model.Genre;
import com.nthnetchill.model.Movie;
import com.nthnetchill.repository.GenreRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class GenreService {
    @Autowired
    private GenreRepository genreRepository;

    @Transactional
    public List<Genre> getAllGenres() {
        return genreRepository.findAll();
    }

    @Transactional
    public Set<Genre> getGenresByMovieId(Long movieId) {
        return genreRepository.findByMoviesId(movieId);
    }

    @Transactional
    public Genre addGenre(Genre genre) {
        return genreRepository.save(genre);
    }

    @Transactional
    public Genre updateGenre(Long genreId, Genre updatedGenre) {
        Genre updateGenre = genreRepository.findById(genreId)
                .orElseThrow(() -> new RuntimeException("Genre not found with id " + genreId));

        updateGenre.setName(updatedGenre.getName());

        return genreRepository.save(updateGenre);
    }

    @Transactional
    public void deleteGenre(Long genreId) {
        Genre genre = genreRepository.findById(genreId)
                .orElseThrow(() -> new RuntimeException("Genre not found with id " + genreId));
        genre.getMovies().forEach(movie -> movie.getGenres().remove(genre));
        genreRepository.delete(genre);
    }
}
