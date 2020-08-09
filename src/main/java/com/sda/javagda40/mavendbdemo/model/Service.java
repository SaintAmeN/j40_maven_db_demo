package com.sda.javagda40.mavendbdemo.model;

import com.sun.xml.bind.v2.model.core.ID;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Service {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private Double price; // sugerowana cena

    private Integer duration; // w ilościach godzin (ile czasu zajmuje usługa)

    private ServiceType type;

    public Service(String name, Double price, Integer duration, ServiceType type) {
        this.name = name;
        this.price = price;
        this.duration = duration;
        this.type = type;
    }

    @OneToMany
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<ExtraService> availableExtraServices;

    @OneToMany(mappedBy = "service")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<ServiceOrder> orders;
}
