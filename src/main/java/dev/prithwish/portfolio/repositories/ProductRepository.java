package dev.prithwish.portfolio.repositories;

import dev.prithwish.portfolio.model.Category;
import dev.prithwish.portfolio.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByCategory(Category category);

    Page<Product> findByCategory(Category category, Pageable pageable);

    Page<Product> findByProductNameLikeIgnoreCase(String name, Pageable pageable);
}
