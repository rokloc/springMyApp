package com.example.secu.service;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import com.example.secu.model.UserEntity;
import com.example.secu.repository.LoginRepository;

@Service
public class LoginService implements UserDetailsService {

    private final LoginRepository loginRepository; // JPAリポジトリ

    public LoginService(LoginRepository loginRepository) {
        this.loginRepository = loginRepository;
    }    

    @Transactional
    public void registerUser(UserEntity user) {
        loginRepository.save(user);
    }
    
    //DB連携でユーザー認証を行うコア部分
    //userテーブルのusernameに一致するレコード探す。なければUsernameNotFoundException投げる。これでSpringSecurityは認証失敗と認識
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = loginRepository.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return User.builder()
                   .username(user.getUsername())
                   .password(user.getPassword()) // すでにハッシュ化されている
                   .roles(user.getRole())       // 例: "USER" または "ADMIN"
                   .build();
    }
}
