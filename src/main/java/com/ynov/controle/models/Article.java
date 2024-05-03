package com.ynov.controle.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Size(min = 3, message = "Title must be at least 3 characters long!")
    private String name;
    @Size(min = 10, message = "Description must be at least 10 characters long!")
    private String description;
    @Min(value = 0, message = "Price cannot be below 0!")
    private double price;
    @Min(value = 0, message = "Quantity cannot be below 0!")
    private int quantity;

    @ManyToOne()
    private User owner;
}
