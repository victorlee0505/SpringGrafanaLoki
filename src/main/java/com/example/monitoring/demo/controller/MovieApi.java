package com.example.monitoring.demo.controller;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.monitoring.demo.database.MovieDao;
import com.example.monitoring.demo.database.entity.Movie;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class MovieApi {
    
    private static final Logger LOG = LoggerFactory.getLogger(MovieApi.class);

    @Autowired
    private MovieDao movieDao;

    @GetMapping(value = "/mov")
    public ResponseEntity<Map<String, Object>> getMovie() {
        LOG.info("/api/mov Started");

        Map<String, Object> response = new HashMap<String, Object>();

        List<Movie> movies = this.movieDao.findAll();

        response.put("Status", "OK");
        response.put("Movie", movies);

        ResponseEntity<Map<String, Object>> resEnt = ResponseEntity.status(HttpStatus.OK)
                .body(Collections.unmodifiableMap(response));

        LOG.info("/api/mov Ended");
        return resEnt;
    }

    @PostMapping(value = "/mov/add")
    public ResponseEntity<Map<String, Object>> addMovie(final @RequestBody Map<String, String> body) {
        LOG.info("/api/mov/add Started");

        String title = body.get("title");
        String year = body.get("year");
        String ratingStr = body.get("rating");
        Integer rating = StringUtils.isNotBlank(ratingStr) ? Integer.valueOf(body.get("rating")) : null;

        LOG.debug("Data Entry [{}] [{}] [{}]", title, year, rating);

        // Check data
        if (StringUtils.isNotBlank(title) && StringUtils.isNotBlank(year) && rating != null) {

            if (rating >= 0 && rating <= 10) {
                Movie entry = new Movie();
                entry.setTitle(title);
                entry.setYear(year);
                entry.setRating(rating);

                this.movieDao.saveAndFlush(entry);

                return getMovie();
            }
        }

        Map<String, Object> response = new HashMap<String, Object>();

        response.put("Status", "Not OK");
        response.put("title", StringUtils.isNotBlank(title) ? "OK" : "invalid");
        response.put("year", StringUtils.isNotBlank(year) ? "OK" : "invalid");
        response.put("rating", rating != null && rating >= 0 && rating <= 10 ? "OK" : "invalid");

        ResponseEntity<Map<String, Object>> resEnt = ResponseEntity.status(HttpStatus.OK)
                .body(Collections.unmodifiableMap(response));

        LOG.info("/api/mov/add Ended");
        return resEnt;

    }
}
