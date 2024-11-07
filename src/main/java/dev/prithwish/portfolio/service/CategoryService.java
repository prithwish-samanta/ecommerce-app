package dev.prithwish.portfolio.service;

import dev.prithwish.portfolio.payload.CategoryDTO;
import dev.prithwish.portfolio.payload.CategoryResponse;

public interface CategoryService {
    CategoryDTO createCategory(CategoryDTO categoryDTO);

    CategoryResponse getCategories(int pageNumber, int pageSize, String sortBy, String sortOrder);

    CategoryDTO updateCategory(long categoryId, CategoryDTO categoryDTO);

    CategoryDTO deleteCategory(long categoryId);
}
