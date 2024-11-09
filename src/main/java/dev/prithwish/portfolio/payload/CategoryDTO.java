package dev.prithwish.portfolio.payload;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDTO {
    private Long categoryId;
    @NotNull(message = "Category name cannot be null")
    @Size(min = 5, message = "Category name must have length of 5 or more")
    private String categoryName;
}
