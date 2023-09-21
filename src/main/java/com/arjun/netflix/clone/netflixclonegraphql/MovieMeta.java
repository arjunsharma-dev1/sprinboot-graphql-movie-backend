package com.arjun.netflix.clone.netflixclonegraphql;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.Duration;
import java.time.LocalDate;
import java.util.List;

@Setter
@Getter
@Entity
@Accessors(chain = true)
public class MovieMeta {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @OneToOne
    @JoinColumn(name = "movie_id", nullable = false)
    private Movie movie;
    @ElementCollection
    @CollectionTable(name = "movie_genre", joinColumns = @JoinColumn(name = "movie_id"))
    private List<String> genre;
    private String title;
    private Duration duration;
    private LocalDate releaseOn;
}
