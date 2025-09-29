package com.example.secu.controller;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.secu.model.UserEntity;
import com.example.secu.repository.LoginRepository;
import com.example.secu.service.ProductService;

@Controller
public class AuthController {
	
	private final LoginRepository loginRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthController(LoginRepository loginRepository, PasswordEncoder passwordEncoder) {
        this.loginRepository = loginRepository;
        this.passwordEncoder = passwordEncoder;
    }
	@GetMapping("/")
	public String loginGet(Model model){ 
		return ("login/login");
	}
	@GetMapping("/mypage")
	public String mypageGet(Model model){ 
		return ("login/mypage");
	}
	@GetMapping("/admin")
	public String adminGet(Model model){ 
		return ("login/admin");
	}
	
	
	
}
