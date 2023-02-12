package com.example.examhomework.repository;

import com.example.examhomework.model.Sellable;
import com.example.examhomework.model.dto.SellableListResponseDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;


public interface SellableRepository extends JpaRepository<Sellable, Long>, PagingAndSortingRepository<Sellable, Long> {
    @Query(nativeQuery = true)
    List<SellableListResponseDTO> findAll_ListDTO(Pageable pageable);
}
