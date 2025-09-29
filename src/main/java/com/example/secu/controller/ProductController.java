package com.example.secu.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.secu.model.Product;
import com.example.secu.service.ProductService;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@Controller
public class ProductController {
	
	private final ProductService service;
	@PersistenceContext
    private EntityManager em;
	
	public ProductController(ProductService service) {
		this.service = service;
	}
	
	@GetMapping("/product")
	public String productGet(Model model){

		model.addAttribute("products", service.findAll());
		return ("product");
	}
	
    @GetMapping("/add")
    public String addRecord(Model model) {
    	model.addAttribute("product", new Product());
    	return ("add");
    }
	
    @PostMapping("/add")
    @Transactional
    public String addRecord(@ModelAttribute Product product) {
    	service.save(product);
    	return ("redirect:/product");
    }
    
    @GetMapping("/product/filter")
    public String filterProducts(@RequestParam("category") String category, Model model) {
        List<Product> products = service.findByCategory(category);
        model.addAttribute("products", products);
        model.addAttribute("selectedCategory", category);
        return "product";
    }

    
}
