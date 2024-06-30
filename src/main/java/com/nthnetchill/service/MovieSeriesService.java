package com.nthnetchill.service;

import com.nthnetchill.model.Episode;
import com.nthnetchill.model.Movie;
import com.nthnetchill.model.Season;
import com.nthnetchill.repository.EpisodeRepository;
import com.nthnetchill.repository.MovieRepository;
import com.nthnetchill.repository.SeasonRepository;
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
public class MovieSeriesService {

    @Value("${file.upload-dir}")
    private String uploadDir;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private SeasonRepository seasonRepository;

    @Autowired
    private EpisodeRepository episodeRepository;

    //========================================================SEASON===================================================================================

    @Transactional
    public List<Season> getAllSeasons(Long movieId) {
        return seasonRepository.findAllByMovieId(movieId);
    }

    @Transactional
    public Optional<Season> getSeasonById(Long seasonId) {
        return seasonRepository.findById(seasonId);
    }

    @Transactional
    public Season addSeason(Long movieId, String title, int seasonNumber, String description) throws IOException {
        Season season = new Season();
        Optional<Movie> movieOptional = movieRepository.findById(movieId);
        if (movieOptional.isPresent()) {
            Movie movie = movieOptional.get();

            if (!movie.getIsSeries()) {
                throw new RuntimeException("Phim thuộc phim bộ, không áp dụng cho phim lẻ");
            }
            season.setMovie(movie);
            season.setTitle(title);
            season.setSeasonNumber(seasonNumber);
            season.setDescription(description);
            return seasonRepository.save(season);
        } else {
            throw new RuntimeException("Không tìm thấy phim với id " + movieId);
        }
    }

    @Transactional
    public Season updateSeason(Long seasonId, String title, int seasonNumber, String description) {
        Optional<Season> seasonOptional = seasonRepository.findById(seasonId);
        if (seasonOptional.isPresent()) {
            Season season = seasonOptional.get();

            // Cập nhật thông tin nếu được cung cấp
            if (title != null) {
                season.setTitle(title);
            }
            if (seasonNumber > 0) {
                season.setSeasonNumber(seasonNumber);
            }
            if (description != null) {
                season.setDescription(description);
            }

            return seasonRepository.save(season);
        } else {
            throw new RuntimeException("Không tìm thấy mùa phim với id " + seasonId);
        }
    }

    //========================================================EPISODE===================================================================================

    @Transactional
    public List<Episode> getAllEpisodes(Long seasonId) {
        return episodeRepository.findAllBySeasonId(seasonId);
    }

    @Transactional
    public Optional<Episode> getEpisodeById(Long episodeId) {
        return episodeRepository.findById(episodeId);
    }

    @Transactional
    public Episode addEpisode(Long seasonId, String name, int number, MultipartFile videoFile, String description) throws IOException {
        Episode episode = new Episode();
        Optional<Season> seasonOptional = seasonRepository.findById(seasonId);
        if (seasonOptional.isPresent()) {
            Season season = seasonOptional.get();

            episode.setSeason(season);
            episode.setName(name);
            episode.setNumber(number);
            episode.setDescription(description);
            String videoUrl = saveVideoFile(videoFile);
            episode.setVideoUrl(videoUrl);
            return episodeRepository.save(episode);
        } else {
            throw new RuntimeException("Không tìm thấy mùa phim với id " + seasonId);
        }
    }

    @Transactional
    public Episode updateEpisode(Long episodeId, String name, int number, String description, MultipartFile videoFile) throws IOException {
        Optional<Episode> episodeOptional = episodeRepository.findById(episodeId);
        if (episodeOptional.isPresent()) {
            Episode episode = episodeOptional.get();

            // Cập nhật thông tin nếu được cung cấp
            if (name != null) {
                episode.setName(name);
            }
            if (number > 0) {
                episode.setNumber(number);
            }
            if (description != null) {
                episode.setDescription(description);
            }
            if (videoFile != null && !videoFile.isEmpty()) {
                String videoUrl = saveVideoFile(videoFile);
                episode.setVideoUrl(videoUrl);
            }

            return episodeRepository.save(episode);
        } else {
            throw new RuntimeException("Không tìm thấy tập phim với id " + episodeId);
        }
    }

    private String saveVideoFile(MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            throw new RuntimeException("Tập tin rỗng");
        }
        String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
        Path filePath = Paths.get(uploadDir, fileName);
        Files.createDirectories(filePath.getParent());
        Files.write(filePath, file.getBytes());
        return "/videos/" + fileName;
    }
}
