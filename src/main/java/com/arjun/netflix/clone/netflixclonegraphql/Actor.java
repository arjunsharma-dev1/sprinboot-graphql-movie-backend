package com.arjun.netflix.clone.netflixclonegraphql;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;

import java.time.LocalDate;
import java.util.List;

@Entity
public class Actor {
    @Id
    private String id;
    private String name;
    private LocalDate dob;
    @ManyToMany
    private List<Movie> movies;
}
