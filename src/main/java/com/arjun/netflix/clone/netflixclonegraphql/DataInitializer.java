package com.arjun.netflix.clone.netflixclonegraphql;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDate;
import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {

    private final MovieRepository movieRepository;
    private final ActorRepository actorRepository;

    public DataInitializer(MovieRepository movieRepository, ActorRepository actorRepository) {
        this.movieRepository = movieRepository;
        this.actorRepository = actorRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        // Create and populate actors
        Actor actor1 = new Actor();
        actor1.setName("Actor 1");
        actor1.setDob(LocalDate.of(1980, 1, 15));

        Actor actor2 = new Actor();
        actor2.setName("Actor 2");
        actor2.setDob(LocalDate.of(1990, 5, 20));

        // Save actors to the database
        actorRepository.save(actor1);
        actorRepository.save(actor2);

        // Create and populate movies
        MovieMeta movieMeta1 = createMovieMeta("Movie 1", List.of("Action", "Sci-fi"), Duration.ofMinutes(120), LocalDate.of(2022, 3, 10));
        Movie movie1 = new Movie();
        movie1.setMeta(movieMeta1);
        movie1.setActors(List.of(actor1, actor2));
        movie1.setRating(new Rating().setRate(4.3f).setScale(5));// Associate actors with the movie
        movieMeta1.setMovie(movie1);

        MovieMeta movieMeta2 = createMovieMeta("Movie 2", List.of("Comedy", "Adventure"), Duration.ofMinutes(90), LocalDate.of(2021, 8, 15));
        Movie movie2 = new Movie();
        movie2.setMeta(movieMeta2);
        movie2.setActors(List.of(actor2)); // Associate actors with the movie
        movie2.setRating(new Rating().setRate(2.9f).setScale(5));
        movieMeta2.setMovie(movie2);

        // Save movies to the database
        movieRepository.save(movie1);
        movieRepository.save(movie2);
    }

    private MovieMeta createMovieMeta(String title, List<String> genre, Duration duration, LocalDate releaseOn) {
        return new MovieMeta()
                .setTitle(title)
                .setGenre(genre)
                .setDuration(duration)
                .setReleaseOn(releaseOn);
    }
}

