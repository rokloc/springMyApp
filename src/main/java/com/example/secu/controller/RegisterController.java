package com.example.secu.controller;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.secu.model.UserEntity;
import com.example.secu.repository.LoginRepository;

@Controller
public class RegisterController {

    private final LoginRepository loginRepository;
    private final PasswordEncoder passwordEncoder;

    public RegisterController(LoginRepository loginRepository, PasswordEncoder passwordEncoder) {
        this.loginRepository = loginRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/register")
    public String registerForm() {
        return "auth/register"; // register.html を返す
    }

    @PostMapping("/register")
    public String registerUser(@RequestParam("username") String username,
                               @RequestParam("password") String password) {

        // ユーザー作成
        UserEntity user = new UserEntity();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setRole("ADMIN");

        // DBに保存
        loginRepository.save(user);

        System.out.println("ユーザー保存成功: " + username);

        // 登録完了後ログインページにリダイレクト
        return "login/login";
    }
}




//package com.example.secu.controller;
//
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//
//import com.example.secu.model.UserEntity;
//import com.example.secu.repository.LoginRepository;
//import com.example.secu.service.LoginService;
//
//@Controller
//public class RegisterController {
//	private final LoginService loginservice;
//    private final PasswordEncoder passwordEncoder;
//
//    public RegisterController(LoginService loginservice, PasswordEncoder passwordEncoder) {
//        this.loginservice = loginservice;
//        this.passwordEncoder = passwordEncoder;
//    }
//    
//    @GetMapping("/register")
//    public String registerGet() {
//    	return ("auth/register");
//    	
//    }
//    
//    @PostMapping("/register/")
//    public String registerUser(@RequestParam("username") String username,@RequestParam("password") String password) {
//        // 1. ユーザーエンティティ作成
//        UserEntity user = new UserEntity();
//        user.setUsername(username);
//
//        // 2. パスワードをハッシュ化
//        user.setPassword(passwordEncoder.encode(password));
//
//        // 3. 権限を設定
//        user.setRole("USER");
//
//        // 4. DB に保存
//        loginservice.registerUser(user);
//
//        // 5. 登録完了後ログインページへリダイレクト
//        return "redirect:/";
//    }
//}
