package com.example.examhomework.repository;

import com.example.examhomework.model.Sellable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SellableRepository extends JpaRepository<Sellable, Long> {
}
