package com.cvProjectBackend.loginRegister.entity;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@Table(name = "users")
@Getter
@Setter
@RequiredArgsConstructor
@ToString
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(max = 50)
    @Column(name = "firstName")
    private String firstName;

    @NotNull
    @Size(max = 50)
    @Column(name = "lastName")
    private String lastName;

    @NotNull
    @Size(max = 50)
    @Column(unique = true, name = "email")
    @Email
    private String email;

    @NotNull
    @Size(max = 120)
    @Column(name = "password")
    private String password;







}
