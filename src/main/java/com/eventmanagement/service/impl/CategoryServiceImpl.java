package com.eventmanagement.service.impl;

import com.eventmanagement.dto.request.CategoryRequestDTO;
import com.eventmanagement.dto.response.CategoryResponseDTO;
import com.eventmanagement.entity.Category;
import com.eventmanagement.exception.ResourceNotFoundException;
import com.eventmanagement.repository.CategoryRepository;
import com.eventmanagement.service.CategoryService;
import com.eventmanagement.util.DTOMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final DTOMapper mapper;

    @Override
    public CategoryResponseDTO createCategory(CategoryRequestDTO requestDTO) {
        Category category = Category.builder()
                .name(requestDTO.getName())
                .build();
        return mapper.toCategoryResponseDTO(categoryRepository.save(category));
    }

    @Override
    public List<CategoryResponseDTO> getAllCategories() {
        return categoryRepository.findAll().stream()
                .map(mapper::toCategoryResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteCategory(Long id) {
        if (!categoryRepository.existsById(id)) {
            throw new ResourceNotFoundException("Category not found with id: " + id);
        }
        categoryRepository.deleteById(id);
    }
}
