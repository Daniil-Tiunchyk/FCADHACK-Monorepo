package com.example.dataingestionservice.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Data
@Table(name = "person")
@EqualsAndHashCode(of = {"username"})
public class Person {


    @Column(unique = true)
    private String username;
    private String password;

    private String passwordCode;

    @OneToOne(mappedBy = "person")
    private RefreshToken refreshToken;

    @ManyToMany
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Collection <Role> roles;

    @Id
    @GeneratedValue
    private Long id;

}