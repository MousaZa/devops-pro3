package org.mous.devops.project2.repository;

import org.mous.devops.project2.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}