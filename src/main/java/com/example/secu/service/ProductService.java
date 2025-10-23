package com.example.secu.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.secu.model.Product;
import com.example.secu.repository.ProductRepository;

@Service
public class ProductService {

    private final ProductRepository repository;

    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public void save(Product product) {
        repository.save(product);
    }

    // 全件取得（ADMIN 用）
    public List<Product> findAll() {
        return repository.findAll();
    }

    // カテゴリ指定（ADMIN 用）
    public List<Product> findAllByCategory(String category) {
        return repository.findAllByCategory(category);
    }

    // USER 用: released=1 のみ取得
    public List<Product> findByCategoryAndReleased(String category) {
        return repository.findByCategoryAndReleased(category);
    }

    // USER 用: released=1 全件取得
    public List<Product> findByReleased() {
        return repository.findByReleased();
    }

    // USER/ADMIN 切替
    public List<Product> getProductsForUser(String category, boolean isAdmin) {
        if (isAdmin) {
            if (category == null) {
                return findAll(); // ADMIN 全件
            } else {
                return findAllByCategory(category); // ADMIN カテゴリ指定
            }
        } else {
            if (category == null) {
                return findByReleased(); // USER 全件（released=1）
            } else {
                return findByCategoryAndReleased(category); // USER カテゴリ指定（released=1）
            }
        }
    }

    // ID で検索
    public Product findById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
