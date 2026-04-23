package org.mous.devops.project2.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "Customers")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 16)
    private String name;

    @Column(length = 32)
    private String address;

    @Column(length = 16)
    private String telephone;

}