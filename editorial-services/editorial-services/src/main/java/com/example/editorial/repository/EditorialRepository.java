package com.example.editorial.repository;

import com.example.editorial.entity.Editorial;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EditorialRepository extends JpaRepository<Editorial,Integer> {
}
