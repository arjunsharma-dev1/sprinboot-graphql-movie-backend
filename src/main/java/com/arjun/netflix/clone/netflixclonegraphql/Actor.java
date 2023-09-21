package com.arjun.netflix.clone.netflixclonegraphql;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Entity
@Accessors(chain = true)
public class Actor {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String name;
    private LocalDate dob;
    @ManyToMany(mappedBy = "actors")
    private List<Movie> movies = new ArrayList<>();
}
