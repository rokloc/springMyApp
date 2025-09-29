package com.example.secu.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.secu.model.Post;
import com.example.secu.model.Product;
import com.example.secu.repository.PostRepository;
import com.example.secu.service.PostService;
import com.example.secu.service.ProductService;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@Controller
public class PostController {

    private final PostRepository postRepository;

    public PostController(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @GetMapping("/post")
    public String post(Model model) {
    	model.addAttribute("posts", postRepository.findAllByOrderByCreatedAtDesc());
        model.addAttribute("post", new Post());
        return "post/post";
    }

    @PostMapping("/post")
    public String createPost(@ModelAttribute Post post, Model model, Principal principal) {
    	// Principal からログイン中ユーザー名を取得
        String username = principal.getName();
        post.setUsername(username);
        postRepository.save(post);
        
        model.addAttribute("posts", postRepository.findAllByOrderByCreatedAtDesc());
        return "post/post";
    }
    
}
