package dev.prithwish.portfolio.exceptions;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String resourceName, String fieldName, String fieldId) {
        super(String.format("Resource %s not found for field %s : %s", resourceName, fieldName, fieldId));
    }
}
