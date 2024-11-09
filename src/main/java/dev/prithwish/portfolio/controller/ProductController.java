package dev.prithwish.portfolio.controller;

import dev.prithwish.portfolio.config.AppConstants;
import dev.prithwish.portfolio.payload.ProductDTO;
import dev.prithwish.portfolio.payload.ProductResponse;
import dev.prithwish.portfolio.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @PostMapping("/admin/categories/{categoryId}/product")
    public ResponseEntity<ProductDTO> addProduct(@PathVariable long categoryId, @Valid @RequestBody ProductDTO productDTO) {
        ProductDTO result = productService.addProduct(categoryId, productDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @GetMapping("/public/products")
    public ResponseEntity<ProductResponse> getProducts(
            @RequestParam(defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int pageNumber,
            @RequestParam(defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int pageSize,
            @RequestParam(defaultValue = AppConstants.PRODUCT_DEFAULT_SORT_BY) String sortBy,
            @RequestParam(defaultValue = AppConstants.DEFAULT_SORT_ORDER) String sortOrder) {
        ProductResponse result = productService.getProducts(pageNumber, pageSize, sortBy, sortOrder);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/public/categories/{categoryId}/products")
    public ResponseEntity<ProductResponse> getProductsByCategory(
            @PathVariable long categoryId,
            @RequestParam(defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int pageNumber,
            @RequestParam(defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int pageSize,
            @RequestParam(defaultValue = AppConstants.PRODUCT_DEFAULT_SORT_BY) String sortBy,
            @RequestParam(defaultValue = AppConstants.DEFAULT_SORT_ORDER) String sortOrder) {
        ProductResponse result = productService.getProductsByCategory(categoryId, pageNumber, pageSize, sortBy, sortOrder);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/public/products/keyword/{keyword}")
    public ResponseEntity<ProductResponse> searchProductsByKeyword(
            @PathVariable String keyword,
            @RequestParam(defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int pageNumber,
            @RequestParam(defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int pageSize,
            @RequestParam(defaultValue = AppConstants.PRODUCT_DEFAULT_SORT_BY) String sortBy,
            @RequestParam(defaultValue = AppConstants.DEFAULT_SORT_ORDER) String sortOrder) {
        ProductResponse result = productService.searchProductsByKeyword(keyword, pageNumber, pageSize, sortBy, sortOrder);
        return ResponseEntity.status(HttpStatus.FOUND).body(result);
    }

    @PutMapping("/admin/products/{productId}")
    public ResponseEntity<ProductDTO> updateProduct(@PathVariable long productId, @Valid @RequestBody ProductDTO productDTO) {
        ProductDTO result = productService.updateProduct(productId, productDTO);
        return ResponseEntity.ok(result);
    }

    @PutMapping("/admin/products/{productId}/image")
    public ResponseEntity<ProductDTO> updateProductImage(@PathVariable long productId, @RequestParam("image") MultipartFile image) {
        ProductDTO result = productService.updateProductImage(productId, image);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/admin/products/{productId}")
    public ResponseEntity<ProductDTO> deleteProduct(@PathVariable long productId) {
        ProductDTO result = productService.deleteProduct(productId);
        return ResponseEntity.ok(result);
    }
}
