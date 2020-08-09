package com.sda.javagda40.mavendbdemo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

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

    public AppUser(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }
}
