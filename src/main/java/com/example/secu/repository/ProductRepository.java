package com.example.secu.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.secu.model.Product;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>{
	
	//セキュア版　@ParamはSQLインジェクションを府げぐ
    @Query(value = "SELECT * FROM product WHERE category = :category AND released = 1", nativeQuery = true)
    List<Product> findByCategory(@Param("category") String category);
  
}



