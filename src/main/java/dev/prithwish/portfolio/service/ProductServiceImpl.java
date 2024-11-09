package dev.prithwish.portfolio.service;

import dev.prithwish.portfolio.exceptions.ApiException;
import dev.prithwish.portfolio.exceptions.ResourceNotFoundException;
import dev.prithwish.portfolio.model.Category;
import dev.prithwish.portfolio.model.Product;
import dev.prithwish.portfolio.payload.ProductDTO;
import dev.prithwish.portfolio.payload.ProductResponse;
import dev.prithwish.portfolio.repositories.CategoryRepository;
import dev.prithwish.portfolio.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final StorageService storageService;
    private final ModelMapper modelMapper;

    @Override
    public ProductDTO addProduct(long categoryId, ProductDTO productDTO) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "Category Id", String.valueOf(categoryId)));

        List<Product> existingProducts = productRepository.findByCategory(category);
        boolean isProductAlreadyExist = false;
        for (Product product : existingProducts) {
            if (product.getProductName().equalsIgnoreCase(productDTO.getProductName())) {
                isProductAlreadyExist = true;
                break;
            }
        }
        if (!isProductAlreadyExist) {
            Product product = getProductFromDTO(productDTO);
            product.setImageUrl("default.jpg");
            product.setCategory(category);
            Product savedProduct = productRepository.save(product);
            return modelMapper.map(savedProduct, ProductDTO.class);
        } else {
            throw new ApiException("Product already exists with name: " + productDTO.getProductName());
        }
    }

    @Override
    public ProductResponse getProducts(int pageNumber, int pageSize, String sortBy, String sortOrder) {
        Sort sort = sortOrder.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Page<Product> page = productRepository.findAll(PageRequest.of(pageNumber, pageSize, sort));
        List<Product> products = page.getContent();
        List<ProductDTO> productsList = products.stream().map(product -> modelMapper.map(product, ProductDTO.class)).toList();
        return ProductResponse.builder()
                .content(productsList)
                .pageNumber(page.getNumber())
                .pageSize(page.getSize())
                .totalPages(page.getTotalPages())
                .totalElements(page.getTotalElements())
                .isLastPage(page.isLast()).build();
    }

    @Override
    public ProductResponse getProductsByCategory(long categoryId, int pageNumber, int pageSize, String sortBy, String sortOrder) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "Category Id", String.valueOf(categoryId)));

        Sort sort = sortOrder.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Page<Product> page = productRepository.findByCategory(category, PageRequest.of(pageNumber, pageSize, sort));
        List<Product> products = page.getContent();
        List<ProductDTO> productsList = products.stream().map(product -> modelMapper.map(product, ProductDTO.class)).toList();
        return ProductResponse.builder()
                .content(productsList)
                .pageNumber(page.getNumber())
                .pageSize(page.getSize())
                .totalPages(page.getTotalPages())
                .totalElements(page.getTotalElements())
                .isLastPage(page.isLast()).build();
    }

    @Override
    public ProductResponse searchProductsByKeyword(String keyword, int pageNumber, int pageSize, String sortBy, String sortOrder) {
        Sort sort = sortOrder.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Page<Product> page = productRepository.findByProductNameLikeIgnoreCase("%" + keyword + "%", PageRequest.of(pageNumber, pageSize, sort));
        List<Product> products = page.getContent();
        List<ProductDTO> productsList = products.stream().map(product -> modelMapper.map(product, ProductDTO.class)).toList();
        return ProductResponse.builder()
                .content(productsList)
                .pageNumber(page.getNumber())
                .pageSize(page.getSize())
                .totalPages(page.getTotalPages())
                .totalElements(page.getTotalElements())
                .isLastPage(page.isLast()).build();
    }

    @Override
    public ProductDTO updateProduct(long productId, ProductDTO productDTO) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "Product Id", String.valueOf(productId)));

        Product productToUpdate = getProductFromDTO(productDTO);
        productToUpdate.setProductId(product.getProductId());
        productToUpdate.setCategory(product.getCategory());
        productToUpdate.setImageUrl(product.getImageUrl());
        Product updatedProduct = productRepository.save(productToUpdate);
        return modelMapper.map(updatedProduct, ProductDTO.class);
    }

    @Override
    public ProductDTO updateProductImage(long productId, MultipartFile file) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "Product Id", String.valueOf(productId)));

        String fileName = storageService.store(file);
        product.setImageUrl(fileName);
        Product updatedProduct = productRepository.save(product);
        return modelMapper.map(updatedProduct, ProductDTO.class);
    }

    @Override
    public ProductDTO deleteProduct(long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "Product Id", String.valueOf(productId)));
        productRepository.deleteById(productId);
        return modelMapper.map(product, ProductDTO.class);
    }

    private static Product getProductFromDTO(ProductDTO productDTO) {
        Product product = new Product();
        product.setProductName(productDTO.getProductName());
        product.setDescription(productDTO.getDescription());
        product.setPrice(productDTO.getPrice());
        product.setDiscount(productDTO.getDiscount());
        double specialPrice = productDTO.getPrice() - (productDTO.getPrice() * productDTO.getDiscount() * 0.01);
        product.setSpecialPrice(specialPrice);
        product.setQuantity(productDTO.getQuantity());
        return product;
    }
}
