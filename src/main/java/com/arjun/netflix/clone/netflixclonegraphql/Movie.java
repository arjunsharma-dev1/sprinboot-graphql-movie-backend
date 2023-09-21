package com.arjun.netflix.clone.netflixclonegraphql;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Movie {
    @Id
    private String id;
    @OneToOne
//    @JoinColumn(referencedColumnName = "movie_id")
    private MovieMeta meta;
    @ManyToMany
    private List<Actor> actors;
    private List<Movie> next;
    private Rating rating;
}
