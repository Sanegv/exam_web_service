package com.ynov.controle.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Size(min = 3)
    private String name;
    @Size(min = 10)
    private String description;
    @Min(0)
    private double price;
    @Min(0)
    private int quantity;

    @ManyToOne()
    private User owner;
}
