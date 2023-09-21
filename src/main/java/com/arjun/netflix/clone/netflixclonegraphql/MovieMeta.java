package com.arjun.netflix.clone.netflixclonegraphql;

import jakarta.persistence.Entity;

import java.time.Duration;
import java.time.LocalDate;

@Entity
public class MovieMeta {
    private Movie movie;
    private String genre;
    private String title;
    private Duration duration;
    private LocalDate releaseOn;
}
