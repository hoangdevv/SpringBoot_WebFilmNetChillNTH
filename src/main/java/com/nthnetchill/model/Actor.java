package com.nthnetchill.model;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@Entity
@Table(name = "actor")
public class Actor extends Base{
    private String name;
    private Date dateOfBirth;
    private String information;
    private String avatarUrl;

    @ManyToMany(mappedBy = "actors")
    Set<Movie> movies;
}
