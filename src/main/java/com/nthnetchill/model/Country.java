package com.nthnetchill.model;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@Entity
@Table(name = "country")
public class Country extends Base{
    @OneToMany(mappedBy = "country")
    Set<Movie> movies;

    private String name;
}
