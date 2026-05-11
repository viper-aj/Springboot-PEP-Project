package com.eventmanagement.service;

import com.eventmanagement.dto.request.CategoryRequestDTO;
import com.eventmanagement.dto.response.CategoryResponseDTO;

import java.util.List;

public interface CategoryService {
    CategoryResponseDTO createCategory(CategoryRequestDTO requestDTO);
    List<CategoryResponseDTO> getAllCategories();
    void deleteCategory(Long id);
}
