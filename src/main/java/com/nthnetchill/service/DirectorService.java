package com.nthnetchill.service;

import com.nthnetchill.model.Director;
import com.nthnetchill.repository.DirectorRepository;
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
public class DirectorService {

    @Autowired
    private DirectorRepository directorRepository;

    @Transactional
    public List<Director> getAllDirectors() {
        return directorRepository.findAll();
    }

    @Transactional
    public Director getOneDirector(Long directorId) {
        Optional<Director> directorOptional = directorRepository.findById(directorId);
        if (directorOptional.isPresent()) {
            return directorOptional.get();
        } else {
            throw new RuntimeException("Actor not found with id " + directorId);
        }
    }

    @Transactional
    public Director addDirector(Director director) {
        return directorRepository.save(director);
    }

    @Transactional
    public Director updateDirector(Long directorId, Director updatedDirector) {
        Director updateDirector = directorRepository.findById(directorId).orElseThrow(() -> new RuntimeException("Director not found with id " + directorId));

        updateDirector.setName(updatedDirector.getName());
        updateDirector.setDateOfBirth(updatedDirector.getDateOfBirth());
        updateDirector.setInformation(updatedDirector.getInformation());

        return directorRepository.save(updateDirector);
    }

    @Transactional
    public Director addAvatarToDirector(Long directorId, MultipartFile avatarFile) throws IOException {
        Optional<Director> directorOptional = directorRepository.findById(directorId);
        if (directorOptional.isPresent()) {
            Director director = directorOptional.get();
            String avatarUrl = saveAvatarFile(avatarFile);
            director.setAvatarUrl(avatarUrl);
            return directorRepository.save(director);
        } else {
            throw new RuntimeException("Movie not found with id " + directorId);
        }
    }

    public String saveAvatarFile(MultipartFile avatarFile) throws IOException {
        String fileName = UUID.randomUUID().toString() + "_" + avatarFile.getOriginalFilename();
        Path filePath = Paths.get("src/main/resources/static/images/director", fileName);
        Files.createDirectories(filePath.getParent());
        Files.write(filePath, avatarFile.getBytes());
        return "/images/director/" + fileName;
    }

    @Transactional
    public void deleteDirector(Long directorId) {
        Director director = directorRepository.findById(directorId)
                .orElseThrow(() -> new RuntimeException("Genre not found with id " + directorId));
        director.getMovies().forEach(movie -> movie.getDirectors().remove(director));
        directorRepository.delete(director);
    }
}
