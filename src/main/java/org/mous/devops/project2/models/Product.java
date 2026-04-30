package org.mous.devops.project2.models;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Data
@Table(name = "Products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 16)
    private String name;
//
    @Column(length = 16)
    private String supplier;

    private BigDecimal price;
}
