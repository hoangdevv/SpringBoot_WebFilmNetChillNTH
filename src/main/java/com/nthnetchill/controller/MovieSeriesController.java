package com.nthnetchill.controller;

import com.nthnetchill.model.Episode;
import com.nthnetchill.model.Season;
import com.nthnetchill.service.MovieSeriesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://127.0.0.1:5500")
@RestController
@RequestMapping("/movie-series")
public class MovieSeriesController {

    @Autowired
    private MovieSeriesService seasonService;

    //========================================================SEASON===================================================================================

    @GetMapping("/{movieId}/seasons")
    public ResponseEntity<List<Season>> getAllSeasons(@PathVariable Long movieId) {
        List<Season> seasons = seasonService.getAllSeasons(movieId);
        return ResponseEntity.ok(seasons);
    }

    // Endpoint để lấy thông tin một mùa phim cụ thể
    @GetMapping("/seasons/{seasonId}")
    public ResponseEntity<Season> getSeasonById(@PathVariable Long seasonId) {
        Optional<Season> season = seasonService.getSeasonById(seasonId);
        return season.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/{movieId}/seasons")
    public ResponseEntity<Season> addSeason(@PathVariable Long movieId,
                                            @RequestParam("title") String title,
                                            @RequestParam("seasonNumber") int seasonNumber,
                                            @RequestParam(value = "description", required = false) String description) {
        try {
            Season season = seasonService.addSeason(movieId, title, seasonNumber, description);
            return ResponseEntity.ok(season);
        } catch (IOException e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @PutMapping("/seasons/{seasonId}")
    public ResponseEntity<Season> updateSeason(@PathVariable Long seasonId,
                                               @RequestParam(value = "title", required = false) String title,
                                               @RequestParam(value = "seasonNumber", required = false) int seasonNumber,
                                               @RequestParam(value = "description", required = false) String description) {
        try {
            Season updatedSeason = seasonService.updateSeason(seasonId, title, seasonNumber, description);
            return ResponseEntity.ok(updatedSeason);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    //========================================================EPISODE===================================================================================

    @GetMapping("/seasons/{seasonId}/episodes")
    public ResponseEntity<List<Episode>> getAllEpisodes(@PathVariable Long seasonId) {
        List<Episode> episodes = seasonService.getAllEpisodes(seasonId);
        return ResponseEntity.ok(episodes);
    }

    // Endpoint để lấy thông tin một tập phim cụ thể
    @GetMapping("/episodes/{episodeId}")
    public ResponseEntity<Episode> getEpisodeById(@PathVariable Long episodeId) {
        Optional<Episode> episode = seasonService.getEpisodeById(episodeId);
        return episode.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/seasons/{seasonId}/episodes")
    public ResponseEntity<Episode> addEpisode(@PathVariable Long seasonId,
                                              @RequestParam(value = "name", required = false) String name,
                                              @RequestParam("number") int number,
                                              @RequestParam("videoFile") MultipartFile videoFile,
                                              @RequestParam(value = "description", required = false) String description) {
        try {
            Episode episode = seasonService.addEpisode(seasonId, name, number, videoFile, description);
            return ResponseEntity.ok(episode);
        } catch (IOException e) {
            return ResponseEntity.status(500).body(null);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PutMapping("/episodes/{episodeId}")
    public ResponseEntity<Episode> updateEpisode(@PathVariable Long episodeId,
                                                 @RequestParam(value = "name", required = false) String name,
                                                 @RequestParam(value = "number", required = false) Integer number,
                                                 @RequestParam(value = "description", required = false) String description,
                                                 @RequestParam(value = "videoFile", required = false) MultipartFile videoFile) {
        try {
            Episode updatedEpisode = seasonService.updateEpisode(episodeId, name, number, description, videoFile);
            return ResponseEntity.ok(updatedEpisode);
        } catch (IOException e) {
            return ResponseEntity.status(500).body(null);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
