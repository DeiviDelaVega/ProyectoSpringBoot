package com.polo.webreservas.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
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
@EnableWebSecurity
public class SecurityConfig {

    @SuppressWarnings("unused")
	private final LoginController loginController;

	
	private final UsuarioRepository usuarioRepository;
	
	public SecurityConfig(UsuarioRepository usuarioRepository, LoginController loginController) {
		this.usuarioRepository = usuarioRepository;
		this.loginController = loginController;
	}
	

    @Autowired
    private CaptchaValidationFilter captchaValidationFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .addFilterBefore(captchaValidationFilter, UsernamePasswordAuthenticationFilter.class)
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/login", "/registro", "/captcha", "/css/**", "/imagenes/**").permitAll()
                .requestMatchers("/admin/**").hasRole("admin")
                .requestMatchers("/cliente/**").hasRole("cliente")
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/login")
                .loginProcessingUrl("/login") // ✅ CLAVE: procesa POST desde el formulario
                .defaultSuccessUrl("/default", true)
                .failureUrl("/login?error=true")
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
