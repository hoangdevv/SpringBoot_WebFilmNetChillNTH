package com.nthnetchill.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@Entity
@Table(name = "season")
public class Season extends Base{
    @ManyToOne
    @JoinColumn(name = "movie_id")
    Movie movie;

    private String description;

    @OneToMany(mappedBy = "season")
    Set<Episode> episodes;
}
