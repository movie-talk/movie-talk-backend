package com.movie.talk.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class MovieService {

    @Value("${TMDB_API_KEY}")
    private String apiKey;

    private static final String TMDB_API_URL = "https://api.themoviedb.org/3/movie";

    private final RestTemplate restTemplate = new RestTemplate();

    public String getNowPlayingMovies() {
        String url = UriComponentsBuilder.fromHttpUrl(TMDB_API_URL + "/now_playing")
                .queryParam("api_key", apiKey)
                .queryParam("language", "ko-KR")
                .toUriString();

        return restTemplate.getForObject(url, String.class);
    }

    public String getMovieDetails(Long movieId) {
        String url = UriComponentsBuilder.fromHttpUrl(TMDB_API_URL + "/" + movieId)
                .queryParam("api_key", apiKey)
                .queryParam("language", "ko-KR")
                .toUriString();

        return restTemplate.getForObject(url, String.class);
    }
}

