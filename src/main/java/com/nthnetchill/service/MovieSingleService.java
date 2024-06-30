package com.nthnetchill.service;

import com.nthnetchill.model.Movie;
import com.nthnetchill.model.MovieSingle;
import com.nthnetchill.repository.MovieRepository;
import com.nthnetchill.repository.MovieSingleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class MovieSingleService {
    @Value("${file.upload-dir}")
    private String uploadDir;
    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private MovieSingleRepository movieSingleRepository;


    public List<MovieSingle> findAll() {
        return movieSingleRepository.findAll();
    }

    public Optional<MovieSingle> findById(Long id) {
        return movieSingleRepository.findById(id);
    }
    @Transactional
    public MovieSingle addMovieSingle(Long movieId, String description, MultipartFile videoFile) throws IOException {
        Optional<Movie> movieOptional = movieRepository.findById(movieId);
        if (movieOptional.isPresent()) {
            Movie movie = movieOptional.get();

            if (movie.getIsSeries()) {
                throw new RuntimeException("Phim thuộc phim lẻ, không áp dụng cho phim bộ");
            }

            MovieSingle movieSingle = new MovieSingle();
            movieSingle.setMovie(movie);
            movieSingle.setDescription(description);
            String videoUrl = saveVideoFile(videoFile);
            movieSingle.setVideoUrl(videoUrl);

            movie.setMovieSingle(movieSingle);
            movieRepository.save(movie);

            MovieSingle savedMovieSingle = movie.getMovieSingle();
            return savedMovieSingle;
        } else {
            throw new RuntimeException("Movie not found with id " + movieId);
        }
    }
    @Transactional
    public MovieSingle updateMovieSingle(Long movieId, String description, MultipartFile videoFile) throws IOException {
        Optional<MovieSingle> movieSingleOptional = movieSingleRepository.findById(movieId);
        if (movieSingleOptional.isPresent()) {
            MovieSingle movieSingle = movieSingleOptional.get();

            // Update description if provided
            if (description != null) {
                movieSingle.setDescription(description);
            }

            // Update video file if provided
            if (videoFile != null && !videoFile.isEmpty()) {
                String videoUrl = saveVideoFile(videoFile);
                movieSingle.setVideoUrl(videoUrl);
            }

            // Save updated movie single
            movieSingleRepository.save(movieSingle);
            return movieSingle;
        } else {
            throw new RuntimeException("Movie single not found with id " + movieId);
        }
    }

    @Transactional
    public void deleteMovieSingle(Long id) {
        movieSingleRepository.deleteById(id);
    }

    private String saveVideoFile(MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            throw new RuntimeException("File is empty");
        }
        String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
        Path filePath = Paths.get(uploadDir, fileName);
        Files.createDirectories(filePath.getParent());
        Files.write(filePath, file.getBytes());
        return "/videos/" + fileName;
    }
}
