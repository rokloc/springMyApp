package com.example.secu.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.secu.model.Product;
import com.example.secu.model.UserEntity;

@Repository
public interface LoginRepository extends JpaRepository<UserEntity, Long>{

	// username フィールドで検索
    Optional<UserEntity> findByUsername(String username);
}
