package dev.prithwish.portfolio.payload;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductDTO {
    private long productId;
    @NotNull
    @Size(min = 5, message = "Product name must have at least 5 characters")
    private String productName;
    @NotNull
    @Size(min = 10, message = "Product description must have at least 10 characters")
    private String description;
    private String imageUrl;
    @NotNull
    @DecimalMin(value = "10.0", message = "Product price must be equals or above Rs. 10.00")
    private Double price;
    private Double specialPrice;
    @NotNull
    @PositiveOrZero(message = "Discount value must be positive or zero")
    private Double discount;
    @NotNull
    @PositiveOrZero(message = "Quantity of the product must be positive or zero")
    private Integer quantity;
}
