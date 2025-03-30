package com.example.ecommerce.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import jakarta.persistence.*;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@ToString
@Entity
@Table(name = "flower")
public class Flower {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "flower_id_seq")
    @SequenceGenerator(name = "flower_id_seq", sequenceName = "flower_id_seq", initialValue = 109, allocationSize = 1)
    @Column(name = "id")
    private Long id;

    @Column(name = "flower_name")
    private String flowerName;

    @Column(name = "category")
    private String category; // e.g., Bouquet, Potted Plant, Seasonal

    @Column(name = "color")
    private String color;

    @Column(name = "origin_country")
    private String originCountry;

    @Column(name = "description")
    private String description;

    @Column(name = "image_filename")
    private String imageFilename;

    @Column(name = "price")
    private Integer price;

    @Column(name = "availability")
    private Boolean availability; // In stock or not

    @Column(name = "flower_rating")
    private Double flowerRating;

    @OneToMany
    @ToString.Exclude
    private List<Review> reviews;

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Flower flower = (Flower) o;
        return Objects.equals(id, flower.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
