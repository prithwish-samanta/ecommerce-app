package dev.prithwish.portfolio.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "TB_PRODUCTS")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long productId;
    private String productName;
    private String description;
    private String imageUrl;
    private double price;
    private double specialPrice;
    private double discount;
    private int quantity;
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
}
