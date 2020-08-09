package com.sda.javagda40.mavendbdemo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Data // Getter, Setter, ToString, EqualsAndHashCode, RequiredArgsConstructor
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class AppUser { // POJO
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;

    private String login;
    private String password;

    private String phoneNumber;
    private String email;

    @OneToMany(mappedBy = "user")
    @EqualsAndHashCode.Exclude
    private Set<Address> addresses;


    public AppUser(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }
}
