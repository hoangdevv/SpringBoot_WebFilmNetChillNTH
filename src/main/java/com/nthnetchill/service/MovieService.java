package com.nthnetchill.service;

import com.nthnetchill.model.*;
import com.nthnetchill.repository.*;
import jakarta.transaction.Transactional;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
public class MovieService {
    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private GenreRepository genreRepository;

    @Autowired
    private CountryRepository countryRepository;

    @Autowired
    private ActorRepository actorRepository;

    @Autowired
    private DirectorRepository directorRepository;

    @Transactional
    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

    @Transactional
    public Movie getMovieById(Long movieId) {
        Optional<Movie> movieOptional = movieRepository.findById(movieId);
        if (movieOptional.isPresent()) {
            Movie movie = movieOptional.get();
            return movie;
        } else {
            throw new RuntimeException("Movie not found with id " + movieId);
        }
    }

    @Transactional
    public List<Movie> getMoviesByName(String name) {
        List<Movie> movies = movieRepository.findByTitleContainingIgnoreCase(name);
        return movies;
    }

    @Transactional
    public Movie addMovie(Movie movie) {
        return movieRepository.save(movie);
    }

    @Transactional
    public Movie updateMovie(Long movieId, Movie updatedMovie) {
        Optional<Movie> movieOptional = movieRepository.findById(movieId);
        if (movieOptional.isPresent()) {
            Movie movie = movieOptional.get();
            movie.setTitle(updatedMovie.getTitle());
            movie.setDescription(updatedMovie.getDescription());
            movie.setReleaseDate(updatedMovie.getReleaseDate());
            movie.setDuration(updatedMovie.getDuration());
            movie.setIsSeries(updatedMovie.getIsSeries());
            movie.setTrailerUrl(updatedMovie.getTrailerUrl());

            // Update country
            Country updatedCountry = updatedMovie.getCountry();
            if (updatedCountry != null) {
                Optional<Country> countryOptional = countryRepository.findById(updatedCountry.getId());
                if (countryOptional.isPresent()) {
                    movie.setCountry(countryOptional.get());
                } else {
                    throw new RuntimeException("Country not found with id " + updatedCountry.getId());
                }
            }

            // Update genres
            Set<Genre> updatedGenres = updatedMovie.getGenres();
            for (Genre genre : updatedGenres) {
                Optional<Genre> genreOptional = genreRepository.findById(genre.getId());
                if (genreOptional.isPresent()) {
                    genre = genreOptional.get();
                } else {
                    throw new RuntimeException("Genre not found with id " + genre.getId());
                }
            }
            movie.setGenres(updatedGenres);

            // Update actors
            Set<Actor> updatedActors = updatedMovie.getActors();
            for (Actor actor : updatedActors) {
                Optional<Actor> actorOptional = actorRepository.findById(actor.getId());
                if (actorOptional.isPresent()) {
                    actor = actorOptional.get();
                } else {
                    throw new RuntimeException("Actor not found with id " + actor.getId());
                }
            }
            movie.setActors(updatedActors);

            // Update directors
            Set<Director> updatedDirectors = updatedMovie.getDirectors();
            for (Director director : updatedDirectors) {
                Optional<Director> directorOptional = directorRepository.findById(director.getId());
                if (directorOptional.isPresent()) {
                    director = directorOptional.get();
                } else {
                    throw new RuntimeException("Director not found with id " + director.getId());
                }
            }
            movie.setDirectors(updatedDirectors);

            return movieRepository.save(movie);
        } else {
            throw new RuntimeException("Movie not found with id " + movieId);
        }
    }

    @Transactional
    public Movie addImageToMovie(Long movieId, MultipartFile imageFile) throws IOException {
        Optional<Movie> movieOptional = movieRepository.findById(movieId);
        if (movieOptional.isPresent()) {
            Movie movie = movieOptional.get();
            String imageUrl = saveImageFile(imageFile);
            movie.setImageUrl(imageUrl);
            return movieRepository.save(movie);
        } else {
            throw new RuntimeException("Movie not found with id " + movieId);
        }
    }

    public String saveImageFile(MultipartFile imageFile) throws IOException {
        String fileName = UUID.randomUUID().toString() + "_" + imageFile.getOriginalFilename();
        Path filePath = Paths.get("src/main/resources/static/images/movie", fileName);
        Files.createDirectories(filePath.getParent());
        Files.write(filePath, imageFile.getBytes());
        return "/images/movie/" + fileName;
    }

//    @Transactional
//    public Movie addTrailerToMovie(Long movieId, MultipartFile trailerFile) throws IOException {
//        Optional<Movie> movieOptional = movieRepository.findById(movieId);
//        if (movieOptional.isPresent()) {
//            Movie movie = movieOptional.get();
//            String trailerUrl = saveTrailerFile(trailerFile);
//            movie.setTrailerUrl(trailerUrl);
//            return movieRepository.save(movie);
//        } else {
//            throw new RuntimeException("Movie not found with id " + movieId);
//        }
//    }
//
//    public String saveTrailerFile(MultipartFile trailerFile) throws IOException {
//        String fileName = UUID.randomUUID().toString() + "_" + trailerFile.getOriginalFilename();
//        Path filePath = Paths.get("src/main/resources/static/videos/trailer", fileName);
//        Files.createDirectories(filePath.getParent());
//        Files.write(filePath, trailerFile.getBytes());
//        return "/videos/trailer/" + fileName;
//    }

    @Transactional
    public void deleteMovie(Long movieId) {
        Optional<Movie> movieOptional = movieRepository.findById(movieId);
        if (movieOptional.isPresent()) {
            movieRepository.deleteById(movieId);
        } else {
            throw new RuntimeException("Movie not found with id " + movieId);
        }
    }
}
