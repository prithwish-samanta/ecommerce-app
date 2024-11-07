package dev.prithwish.portfolio.payload;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDTO {
    private Long categoryId;
    @NotEmpty(message = "Category name cannot be empty")
    @Length(min = 5, message = "Category name must have length of 5 or more")
    private String categoryName;
}
