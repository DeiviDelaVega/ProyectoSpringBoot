package com.polo.webreservas.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.polo.webreservas.controller.LoginController;
import com.polo.webreservas.model.Usuario;
import com.polo.webreservas.repository.UsuarioRepository;

@Configuration

public class SecurityConfig {

    @SuppressWarnings("unused")
	private final LoginController loginController;

	
	private final UsuarioRepository usuarioRepository;
	
	public SecurityConfig(UsuarioRepository usuarioRepository, LoginController loginController) {
		this.usuarioRepository = usuarioRepository;
		this.loginController = loginController;
	}
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
		.addFilterBefore(new NoCacheFilter(), UsernamePasswordAuthenticationFilter.class)
		.authorizeHttpRequests(auth -> auth 
				.requestMatchers("/registro/**").permitAll()
				.requestMatchers("/admin/**").hasRole("admin")
				.requestMatchers("/cliente/**").hasRole("cliente")
				.anyRequest().permitAll()	
		)
		.formLogin(form -> form
				.loginPage("/login")
				.defaultSuccessUrl("/default", true)
				.permitAll()
				)
		.logout(logout -> logout
				.logoutSuccessUrl("/login?logout")
				.permitAll()
				);
		return http.build();
	}
	
	@Bean
	public UserDetailsService userDetailsService() {
	    return username -> {
	        Usuario usuario = usuarioRepository.findByCorreo(username)
	                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

	        return User.builder()
	                .username(usuario.getCorreo())
	                .password(usuario.getClave())
	                .roles(usuario.getRol().name())
	                .build();
	    };
	}

	
	
	@Bean
	 public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
