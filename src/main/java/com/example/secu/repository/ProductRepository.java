package com.example.secu.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.secu.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>{

    // ADMIN 用: カテゴリ指定あり
    @Query(value = "SELECT * FROM product WHERE category = :category", nativeQuery = true)
    List<Product> findAllByCategory(@Param("category") String category);

    // USER 用: released = 1 だけ
    @Query(value = "SELECT * FROM product WHERE category = :category AND released = 1", nativeQuery = true)
    List<Product> findByCategoryAndReleased(@Param("category") String category);

    // USER 用: released = 1 全件（カテゴリ指定なし）
    @Query(value = "SELECT * FROM product WHERE released = 1", nativeQuery = true)
    List<Product> findByReleased();
}

