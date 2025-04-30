package com.ppiyong.backend.api.manual.repository;

import com.ppiyong.backend.api.manual.entity.Manual;
import com.ppiyong.backend.api.manual.common.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ManualRepository extends JpaRepository<Manual, Long> {
    List<Manual> findByNameContaining(String name);
    List<Manual> findByCategory(Category category);

    @Query("SELECT m.name FROM Manual m WHERE m.name LIKE %:Name%")
    List<String> autocompleteByName(String Name);

    @Query("SELECT m FROM Manual m WHERE m.detail LIKE %:keyword% OR m.manualSummary LIKE %:keyword%")
    List<Manual> findByKeyword(String keyword);
}
