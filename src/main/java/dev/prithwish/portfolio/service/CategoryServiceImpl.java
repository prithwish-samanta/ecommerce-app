package dev.prithwish.portfolio.service;

import dev.prithwish.portfolio.exceptions.ApiException;
import dev.prithwish.portfolio.exceptions.ResourceNotFoundException;
import dev.prithwish.portfolio.model.Category;
import dev.prithwish.portfolio.payload.CategoryDTO;
import dev.prithwish.portfolio.payload.CategoryResponse;
import dev.prithwish.portfolio.repositories.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    @Override
    public CategoryDTO createCategory(CategoryDTO categoryDTO) {
        Optional<Category> categoryOptional = categoryRepository.findByCategoryNameIgnoreCase(categoryDTO.getCategoryName());
        if (categoryOptional.isPresent()) {
            throw new ApiException("Category with name " + categoryDTO.getCategoryName() + " already exists");
        }
        Category category = modelMapper.map(categoryDTO, Category.class);
        category.setCategoryId(null);
        Category savedCategory = categoryRepository.save(category);
        return modelMapper.map(savedCategory, CategoryDTO.class);
    }

    @Override
    public CategoryResponse getCategories(int pageNumber, int pageSize, String sortBy, String sortOrder) {
        Sort sort = sortOrder.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Page<Category> page = categoryRepository.findAll(PageRequest.of(pageNumber, pageSize, sort));
        List<Category> categories = page.getContent();
        List<CategoryDTO> categoryList = categories.stream().map(category ->
                modelMapper.map(category, CategoryDTO.class)
        ).toList();
        return CategoryResponse.builder()
                .content(categoryList)
                .pageNumber(page.getNumber())
                .pageSize(page.getSize())
                .totalPages(page.getTotalPages())
                .totalElements(page.getTotalElements())
                .isLastPage(page.isLast())
                .build();
    }

    @Override
    public CategoryDTO updateCategory(long categoryId, CategoryDTO categoryDTO) {
        Category category = categoryRepository.findById(categoryId).
                orElseThrow(() -> new ResourceNotFoundException("Category", "Category Id", String.valueOf(categoryId)));
        category.setCategoryName(categoryDTO.getCategoryName());
        Category savedCategory = categoryRepository.save(category);
        return modelMapper.map(savedCategory, CategoryDTO.class);
    }

    @Override
    public CategoryDTO deleteCategory(long categoryId) {
        Category category = categoryRepository.findById(categoryId).
                orElseThrow(() -> new ResourceNotFoundException("Category", "Category Id", String.valueOf(categoryId)));
        categoryRepository.deleteById(categoryId);
        return modelMapper.map(category, CategoryDTO.class);
    }
}
