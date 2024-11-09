package dev.prithwish.portfolio.service;

import dev.prithwish.portfolio.payload.ProductDTO;
import dev.prithwish.portfolio.payload.ProductResponse;
import org.springframework.web.multipart.MultipartFile;

public interface ProductService {
    ProductDTO addProduct(long categoryId, ProductDTO productDTO);

    ProductResponse getProducts(int pageNumber, int pageSize, String sortBy, String sortOrder);

    ProductResponse getProductsByCategory(long categoryId, int pageNumber, int pageSize, String sortBy, String sortOrder);

    ProductResponse searchProductsByKeyword(String keyword, int pageNumber, int pageSize, String sortBy, String sortOrder);

    ProductDTO updateProduct(long productId, ProductDTO productDTO);

    ProductDTO updateProductImage(long productId, MultipartFile file);

    ProductDTO deleteProduct(long productId);
}
