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
        var actor1 = new Actor()
                .setName("Actor 1")
                .setDob(LocalDate.of(1980, 1, 15));

        var actor2 = new Actor()
                .setName("Actor 2")
                .setDob(LocalDate.of(1990, 5, 20));

        // Save actors to the database
        actorRepository.saveAll(List.of(actor1, actor2));

        // Create and populate movies
        var movieMeta1 =
                createMovieMeta("Movie 1", List.of("Action", "Sci-fi"), Duration.ofMinutes(120), LocalDate.of(2022, 3, 10));

        var movie1 = new Movie()
                .setMeta(movieMeta1)
                .setActors(List.of(actor1, actor2))
                .setRating(createRating(4.3f, 5f));

        movieMeta1.setMovie(movie1);

        var movieMeta2 = createMovieMeta("Movie 2", List.of("Comedy", "Adventure"), Duration.ofMinutes(90), LocalDate.of(2021, 8, 15));
        var movie2 = new Movie()
                .setMeta(movieMeta2)
                .setActors(List.of(actor2))
                .setRating(createRating(2.9f, 5f));

        movieMeta2.setMovie(movie2);

        movieRepository.saveAll(List.of(movie1, movie2));
    }

    private static Rating createRating(float rate, float scale) {
        return new Rating().setRate(rate).setScale(scale);
    }

    private MovieMeta createMovieMeta(String title, List<String> genre, Duration duration, LocalDate releaseOn) {
        return new MovieMeta()
                .setTitle(title)
                .setGenre(genre)
                .setDuration(duration)
                .setReleasedOn(releaseOn);
    }
}

