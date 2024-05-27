package com.nthnetchill.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@Entity
@Table(name = "movie")
public class Movie extends Base{
    private String title;
    private String description;
    private Date releaseDate;
    private int duration;
    private boolean isSeries;
    private String imageUrl;
    private String TrailerUrl;
    @ManyToOne
    @JoinColumn(name = "country_id")
    Country country;

    @OneToMany(mappedBy = "movie")
    Set<Seasion> seasions;

    @OneToMany(mappedBy = "movie")
    Set<Rating> ratings;

    @OneToMany(mappedBy = "movie")
    Set<Comment> comments;

    @OneToMany(mappedBy = "movie")
    Set<WatchList> watchLists;

    @ManyToMany
    @JoinTable(
            name = "movie_derector",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "derector_id"))
    Set<Derector> derectors;

    @ManyToMany
    @JoinTable(
            name = "movie_actor",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "actor_id"))
    Set<Actor> actors;

    @ManyToMany
    @JoinTable(
            name = "movie_genre",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id"))
    Set<Genre> genres;
}
