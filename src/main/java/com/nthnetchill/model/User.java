package com.nthnetchill.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class User extends Base{
    private String name;
    private String email;
    private String password;

    @OneToMany(mappedBy = "user")
    Set<Payment> payments;

    @OneToMany(mappedBy = "user")
    @JsonManagedReference
    Set<Rating> ratings;

    @OneToMany(mappedBy = "user")
    Set<Comment> comments;

    @OneToMany(mappedBy = "user")
    Set<WatchList> watchLists;

    @ManyToMany
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    Collection<Role> roles = new HashSet<>();

}
