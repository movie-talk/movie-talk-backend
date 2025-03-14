package com.movie.talk.controller;

import com.movie.talk.service.MovieService;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/movies")
@CrossOrigin(origins = {"http://127.0.0.1:5500", "http://127.0.0.1:5501"})
public class MovieController {

    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping("/now-playing")
    public String getNowPlayingMovies() {
        return movieService.getNowPlayingMovies();
    }

    @GetMapping("/now-playing/{movieId}")
    public String getMovieDetails(@PathVariable Long movieId) {
        return movieService.getMovieDetails(movieId);
    }
}

