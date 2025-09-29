package com.example.secu.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Sort;

import com.example.secu.model.Post;

public interface PostRepository extends JpaRepository<Post, Long> {
	// 作成日時で降順（新しい順）に取得
    default List<Post> findAllByOrderByCreatedAtDesc() {
        return findAll(Sort.by(Sort.Direction.DESC, "createdAt"));
    }
}

