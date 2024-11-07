package dev.prithwish.portfolio.controller;

import dev.prithwish.portfolio.config.AppConstants;
import dev.prithwish.portfolio.payload.CategoryDTO;
import dev.prithwish.portfolio.payload.CategoryResponse;
import dev.prithwish.portfolio.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @PostMapping("/admin/category")
    public ResponseEntity<CategoryDTO> createCategory(@Valid @RequestBody CategoryDTO categoryDTO) {
        CategoryDTO result = categoryService.createCategory(categoryDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @GetMapping("/public/categories")
    public ResponseEntity<CategoryResponse> getCategories(
            @RequestParam(defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int pageNumber,
            @RequestParam(defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int pageSize,
            @RequestParam(defaultValue = AppConstants.CATEGORY_DEFAULT_SORT_BY) String sortBy,
            @RequestParam(defaultValue = AppConstants.CATEGORY_DEFAULT_SORT_DIRECTION) String sortDirection) {
        CategoryResponse result = categoryService.getCategories(pageNumber, pageSize, sortBy, sortDirection);
        return ResponseEntity.ok(result);
    }

    @PutMapping("admin/categories/{categoryId}")
    public ResponseEntity<CategoryDTO> updateCategory(@PathVariable long categoryId, @Valid @RequestBody CategoryDTO categoryDTO) {
        CategoryDTO result = categoryService.updateCategory(categoryId, categoryDTO);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/admin/categories/{categoryId}")
    public ResponseEntity<CategoryDTO> deleteCategory(@PathVariable long categoryId) {
        CategoryDTO result = categoryService.deleteCategory(categoryId);
        return ResponseEntity.ok(result);
    }
}
