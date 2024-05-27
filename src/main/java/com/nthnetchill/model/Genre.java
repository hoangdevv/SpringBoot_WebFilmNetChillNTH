package com.nthnetchill.model;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@Entity
@Table(name = "genre")
public class Genre extends Base{
    private String name;

    @ManyToMany(mappedBy = "genres")
    Set<Movie> movies;
}
