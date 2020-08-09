package com.sda.javagda40.mavendbdemo.model;

import com.sun.xml.bind.v2.model.core.ID;
import lombok.*;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String city;
    private String street;
    private String houseNum;
    private String postalCode;

    @ManyToOne
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private AppUser user;
}
