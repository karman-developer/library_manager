package com.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
				.authorizeHttpRequests(auth -> auth
						.requestMatchers("/loginForm", "/css/**", "/js/**", "/images/**").permitAll()
						.anyRequest().authenticated())
				.formLogin(form -> form
						.loginProcessingUrl("/login") // 認証処理のURL
						.loginPage("/loginForm") // ログインページ
						.usernameParameter("email") // フォームのユーザー名フィールド名
						.passwordParameter("password") // フォームのパスワードフィールド名
						.defaultSuccessUrl("/library", true) // 認証成功後の遷移先
						.failureUrl("/loginForm?error") // 認証失敗時の遷移先
						.permitAll())
				.logout(logout -> logout
						.logoutUrl("/logout") // ログアウト用のURL（POST送信）
						.logoutSuccessUrl("/loginForm") // ログアウト成功後の遷移先
						.invalidateHttpSession(true) // セッションの無効化
						.deleteCookies("JSESSIONID") // Cookie削除
				);
		return http.build();
	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
