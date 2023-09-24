package com.arjun.netflix.clone.netflixclonegraphql;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Entity
@Accessors(chain = true)
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @OneToOne(mappedBy = "movie", cascade = CascadeType.ALL)
    private MovieMeta meta;
    @ManyToMany
    @JoinTable(
            name = "movie_actor",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "actor_id")
    )
    private List<Actor> actors = new ArrayList<>();
    @ManyToMany
    private List<Movie> next = new ArrayList<>();
    @Embedded
    private Rating rating;

    public void addActors(List<Actor> actors) {
        actors.forEach(this::addActor);
    }

    public void addActor(Actor actor) {
        if(this.actors == null) {
            this.actors = new ArrayList<>();
        }
        this.actors.add(actor);
    }
}
