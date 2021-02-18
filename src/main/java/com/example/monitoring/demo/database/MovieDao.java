package com.example.monitoring.demo.database;

import java.util.List;

import com.example.monitoring.demo.database.entity.Movie;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MovieDao extends JpaRepository<Movie, Long> {

    @Query("SELECT m FROM Movie m")
    List<Movie> findAll();
}
