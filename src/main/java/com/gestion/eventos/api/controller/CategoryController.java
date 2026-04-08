package com.gestion.eventos.api.controller;


import com.gestion.eventos.api.domain.Category;
import com.gestion.eventos.api.dto.CategoryDto;
import com.gestion.eventos.api.mapper.CategoryMapper;
import com.gestion.eventos.api.service.ICategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
public class CategoryController {
    private final UserDetailsService userDetailsService;
    private final ICategoryService categoryService;
    private final CategoryMapper categoryMapper;


    @GetMapping
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<List<CategoryDto>> getAllCategories(){
        List<Category> categories = categoryService.findAll();
        return ResponseEntity.ok(
                categories.stream().map(cat -> categoryMapper.toDto(cat)).collect(Collectors.toList())
        );
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<CategoryDto> getCategoryById(@Valid @PathVariable(name = "id") Long id){
        return ResponseEntity.ok(categoryMapper.toDto(categoryService.findById(id)));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CategoryDto> createCategory(@RequestBody(required = true) CategoryDto categoryDto){
        Category categoryToCreate = categoryMapper.toEntity(categoryDto);
        Category createdCategory = categoryService.save(categoryToCreate);
        return new ResponseEntity<>(categoryMapper.toDto(createdCategory), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CategoryDto> updateCategory(@PathVariable(name = "id") Long id, @Valid @RequestBody CategoryDto categoryDto){
        Category categoryToUpdate = categoryMapper.toEntity(categoryDto);
        Category updatedCategory = categoryService.update(id, categoryToUpdate);

        return new ResponseEntity<>(categoryMapper.toDto(updatedCategory), HttpStatus.OK);

    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteCategory(@PathVariable Long id){
        categoryService.deleteById(id);
        return ResponseEntity.ok("Categoria borrada correctamente");
    }





}
