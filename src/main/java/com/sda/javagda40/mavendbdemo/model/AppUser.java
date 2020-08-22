package com.sda.javagda40.mavendbdemo.model;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Data // Getter, Setter, ToString, EqualsAndHashCode, RequiredArgsConstructor
@Entity
@Builder
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

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    @EqualsAndHashCode.Exclude
    private Set<Address> addresses;

    @OneToMany(fetch = FetchType.EAGER)
    private Set<ServiceOrder> serviceOrders;

    public AppUser(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }
}
