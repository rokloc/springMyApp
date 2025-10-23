package com.example.secu.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import com.example.secu.service.LoginService;

import org.springframework.security.core.userdetails.User;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    // パスワードハッシュ化
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/register").permitAll()
                .requestMatchers("/mypage/**").hasRole("USER")
                .requestMatchers("/admin/**").hasRole("ADMIN")
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
            	    .loginPage("/")                      // ログインページ
            	    .loginProcessingUrl("/login")        // 認証処理をするURL（フォームの action と一致させる）
            	    .successHandler((request, response, authentication) -> {
            	        var auth = authentication.getAuthorities()
            	                                  .iterator()
            	                                  .next()
            	                                  .getAuthority();

            	        if (auth.equals("ROLE_ADMIN")) {
            	            response.sendRedirect("/admin");
            	        } else {
            	            response.sendRedirect("/mypage");
            	        }
            	    })
            	    .permitAll()
            	)
         // クリックジャッキング対策の設定
            .headers(headers -> headers
                // iframe埋め込みを全面禁止（全サイトからのiframeを拒否）
                .frameOptions(frame -> frame.deny())
                
                // 同じオリジンからのiframe埋め込みのみ許可したいなら以下のコメントアウトを外し、X-Frame-Optionsを無効化する。（ブラウザはより制約の強い方（DENY）が優先するため）
                //.contentSecurityPolicy(csp -> csp
//                    .policyDirectives("frame-ancestors 'self';")
//                )
            )
            


            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/?logout")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
            )
            .sessionManagement(session -> session
                .maximumSessions(1)
                .maxSessionsPreventsLogin(true)
            )
            .sessionManagement(session -> session
                .sessionFixation().changeSessionId()
            );

        return http.build();
    }
}













/*
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    //パスワードハッシュ化
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    
//    GET  /          -> AuthController が login.html を返す
//	POST /login     -> Spring Security が認証処理
//	成功            -> /mypage にリダイレクト
//	/mypage GET     -> AuthController が mypage.html を返す
 
     

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
        	//認可
            .authorizeHttpRequests(auth -> auth
        	    //.requestMatchers("/public/**").permitAll()// 誰でもOK
        	    .requestMatchers("/mypage/**").hasRole("USER")
        	    .requestMatchers("/register").permitAll() //登録フォームを全員アクセス認可
            	
                .requestMatchers(HttpMethod.GET, "/mypage/**").hasRole("USER") //
                .requestMatchers("/admin/**").hasRole("ADMIN") // ADMINのみ
                .anyRequest().authenticated()            // それ以外はログイン必須
            )
            .formLogin(form -> form
                .loginPage("/")      // 自分の作ったログインページのURL
                .loginProcessingUrl("/login") // POST /login を認証処理
                .defaultSuccessUrl("/mypage") // 認証成功後に遷移するURL
                .permitAll()              // ログインページ自体は誰でもアクセスOK
            )
            .logout(logout -> logout
        		.logoutUrl("/logout")             // ログアウトURL（デフォルトも /logout）
        	    .logoutSuccessUrl("/login?logout") // 成功後にリダイレクト
        	    .invalidateHttpSession(true)      // セッション破棄
        	    .deleteCookies("JSESSIONID")      // Cookie 削除
            )
            //同時ログイン制御
            .sessionManagement(session -> session
        	    .maximumSessions(1)   // 同時ログインは1セッションのみ
        	    .maxSessionsPreventsLogin(true) // 既存ログインを拒否
            )
            // セッション固定攻撃対策
            //セッション固定攻撃とは：攻撃者が事前に自分のセッションIDを被害者に使わせてログインさせる手法
			//対策：ログイン時にセッションIDを新規発行
            .sessionManagement(session -> session
        	    .sessionFixation().changeSessionId() // デフォルトで有効
        	);
        
        return http.build();
    }
}

*/
/*
[ Webアプリとしての安全なログインフロー ]
1) ログイン → 
.loginPage("/")

2) 認証 → 
.loginProcessingUrl("/login")

3) 認可 →
.authorizeHttpRequests
 
4) ログアウト → 
.logout(logout -> logout

5) セッション管理
//同時ログイン制御
.sessionManagement(session -> session
    .maximumSessions(1)   // 同時ログインは1セッションのみ
    .maxSessionsPreventsLogin(true) // 既存ログインを拒否
)
セッション固定攻撃対策
セッション固定攻撃とは：攻撃者が事前に自分のセッションIDを被害者に使わせてログインさせる手法
対策：ログイン時にセッションIDを新規発行
.sessionManagement(session -> session
    .sessionFixation().changeSessionId() // デフォルトで有効
);
*/
