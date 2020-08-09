package com.sda.javagda40.mavendbdemo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class ServiceOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreationTimestamp
    private LocalDateTime orderDate;

    private LocalDateTime completedDate;
    private Double duration;
    private Double price;

    @ManyToOne
    private Service service;

    @ManyToMany
    private Set<ExtraService> extraServices;
}
